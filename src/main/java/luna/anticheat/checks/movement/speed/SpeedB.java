package luna.anticheat.checks.movement.speed;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;

import io.github.retrooper.packetevents.packet.PacketType;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;
import org.bukkit.Material;

@CheckInfo(name = "Speed (B)", dev = true)
public class SpeedB extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if(PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
                if(data.getOnSolidGroundTicks() > 2 && data.getUnderBlockTicks() == 0 && data.currDeltaXz > 0 && data.deltaY == 0 && data.getBlockUnder() != Material.AIR) {
                    float slipperiness = data.getSlipperiness();
                    float friction = 0.91F * slipperiness;
                    float expectedDeltaXZ = (float) (data.lastDeltaXz * friction);
                    //e.getPlayer().sendMessage(expectedDeltaXZ + " " + data.currDeltaXz);
                }
            }
        }
    }
}
