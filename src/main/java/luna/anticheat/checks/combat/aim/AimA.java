package luna.anticheat.checks.combat.aim;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.PacketType;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;
import luna.anticheat.utils.MathUtils;

@CheckInfo(name = "Aim (A)", dev = true)
public class AimA extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if(PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
                float deltaPitch = data.deltaPitch;
                data.pitchDeltas.add(deltaPitch);

                if(data.pitchDeltas.size() > 25 && data.deltaYaw > 0F && ++data.aimAHitTicks <= 2) {
                    data.pitchDeviation = MathUtils.roundedGCD(data.pitchDeltas);
                    float difference = data.pitchDeviation - data.lastPitchDeviation;
                    data.lastDifference = difference;
                    if(Float.toString(difference).contains("E") || Math.abs(difference - data.lastDifference) > 2F) flag(data, "Invalid aim GCD.");
                    data.pitchDeltas.clear();
                }
            } else if(e.getPacketId() == PacketType.Client.USE_ENTITY) {
                data.aimAHitTicks = 0;
            }
        }
    }
}
