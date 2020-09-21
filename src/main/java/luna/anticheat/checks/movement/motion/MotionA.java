package luna.anticheat.checks.movement.motion;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;

import io.github.retrooper.packetevents.packet.PacketType;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;

@CheckInfo(name = "Motion (A)", punishVL = 20)
public class MotionA extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if (PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
                if (Float.toString(data.deltaY).contains("E")) {
                    if(++data.motionAVerbose >= 3) {
                        flag(data,
                                "extremely low Y change: " + data.deltaY + " for " + data.motionAVerbose + " ticks!");
                    } else {
                        reward(data);
                    }
                } else {
                    if(data.motionAVerbose > 0) {
                        --data.motionAVerbose;
                    } else {
                        data.motionAVerbose = 0;
                    }
                }
            }
        }
    }
}
