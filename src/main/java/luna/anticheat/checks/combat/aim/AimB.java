package luna.anticheat.checks.combat.aim;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.PacketType;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;
import luna.anticheat.utils.MathUtils;

import java.util.ArrayList;

@CheckInfo(name = "Aim (B)", dev = true)
public class AimB extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if(PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
                if(++data.aimBHitTicks <= 2) {
                    data.aimBDeltas.add(MathUtils.yawTo180F(data.deltaYaw));
                    if(data.aimBDeltas.size() >= 120) {
                        float rotConst = MathUtils.getSmallestFloatThatIsMoreThanZero((ArrayList<Float>) MathUtils.findDuplicates(data.aimBDeltas));
                        e.getPlayer().sendMessage(rotConst + "");
                        data.aimBDeltas.clear();
                    }
                }
            } else if(e.getPacketId() == PacketType.Client.USE_ENTITY) {
                data.aimBHitTicks = 0;
            }
        }
    }
}
