package luna.anticheat.checks.combat.killaura;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;

import io.github.retrooper.packetevents.packet.PacketType;
import io.github.retrooper.packetevents.packetwrappers.in.useentity.WrappedPacketInUseEntity;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;
import org.bukkit.entity.EntityType;

@CheckInfo(name = "Killaura (A)", dev = true, punishVL = 50)
public class KillauraA extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if (PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
                double deltaXZ =data.currDeltaXz;
                double lastDeltaXZ =data.lastDeltaXz;

                boolean isSprinting =data.isSprinting;

                if (isSprinting && ++data.hitTicks <= 2) {
                    double acceleration = Math.abs(deltaXZ - lastDeltaXZ);
                    if (acceleration < 0.023) {
                        if (++data.killauraPreVL >= 4) {
                            flag(data, "keepsprint: " + acceleration);
                        } else {
                            reward(data);
                        }
                    } else {
                       data.killauraPreVL = 0;
                    }
                }
            } else if (e.getPacketId() == PacketType.Client.USE_ENTITY) {
                WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getNMSPacket());
                if(packet.getEntity() != null) {
                    if (packet.getEntity().getType() == EntityType.PLAYER) {
                       data.hitTicks = 0;
                    }
                }
            }
        }
    }
}
