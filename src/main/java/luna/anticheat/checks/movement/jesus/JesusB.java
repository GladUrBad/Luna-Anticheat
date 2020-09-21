package luna.anticheat.checks.movement.jesus;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.PacketType;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;
import org.bukkit.entity.Player;

@CheckInfo(name = "Jesus (B)", punishVL = 20)
public class JesusB extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if (PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
                final Player p =data.getPlayer();
                if (data.isInWater() &&data.isWaterBelow() && !data.isOnSolidGround() && !p.isFlying() && !data.isNearCeiling()) {
                    double deltaX =data.deltaX;
                    double deltaY =data.deltaY;
                    double deltaZ =data.deltaZ;

                    if (deltaY == 0 && (deltaX != 0 || deltaZ != 0)) {
                        if (++data.liquidWalkCounterB > 10) {
                            flag(data, "consistent Y level whilst in water.");
                        } else {
                            reward(data);
                        }
                    }
                }
            }
        }
    }
}
