package luna.anticheat.checks.combat.reach;

import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.PacketType;
import io.github.retrooper.packetevents.packetwrappers.in.useentity.WrappedPacketInUseEntity;
import io.github.retrooper.packetevents.utils.api.ServerUtils;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;
import luna.anticheat.utils.AABB;
import luna.anticheat.utils.MathUtils;
import luna.anticheat.utils.Ray;
import org.bukkit.entity.Player;

@CheckInfo(name = "Reach (A)", dev = true, punishVL = 50)
public class ReachA extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if(e.getPacketId() == PacketType.Client.USE_ENTITY) {
                WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getNMSPacket());
                data.recentlyAttacked = true;
                data.entityAttacked = packet.getEntity();
            } else if(PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
                if(data.recentlyAttacked) {
                    data.recentlyAttacked = false;
                    Ray ray = Ray.from(data.getPlayer());
                    AABB aabb = AABB.from(data.entityAttacked);
                    double dist = aabb.collidesD(ray, 0, 10);
                    if(dist != -1) {
                        long ping = getPing(data.getPlayer());
                        double maxReach = 3.2;
                        if(ping > 50L && ping < 100L) {
                            maxReach += 0.15;
                        } else if(ping > 100L && ping < 150L) {
                            maxReach += 0.3;
                        } else if(ping > 150L && ping < 200L) {
                            maxReach += 0.45;
                        } else if(ping > 200L && ping < 250L) {
                            maxReach += 0.6;
                        } else if(ping > 250L) {
                            reward(data);
                        }

                        if(dist > maxReach) {
                            if(++data.reachVL > 2) {
                                flag(data,"reach: " + dist);
                            }
                        } else {
                            data.reachVL = 0;
                        }
                    }
                }
            }
        }
    }

    public long getPing(Player player) {
        return PacketEvents.getAPI().getPlayerUtils().getPing(player);
    }
}
