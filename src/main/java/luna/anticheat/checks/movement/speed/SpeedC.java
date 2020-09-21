package luna.anticheat.checks.movement.speed;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;

import io.github.retrooper.packetevents.packet.PacketType;
import luna.anticheat.checks.Check;
import luna.anticheat.checks.CheckInfo;
import luna.anticheat.playerdata.PlayerData;

@CheckInfo(name = "Speed (C)")
public class SpeedC extends Check {

    @Override
    public void runCheck(PlayerData data, PacketEvent event) {
        if(event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if (PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())) {
                //e.getPlayer().sendMessage(data.currDeltaXz + "");
                double MAX_SPEED = 0.36;
                int speedLvl = (byte) data.getSpeedLevel();
                if (speedLvl > 0) {
                    MAX_SPEED += (speedLvl + 1) * 0.1F;
                }
                if(data.currDeltaXz > MAX_SPEED && !e.getPlayer().isInsideVehicle() && data.getTicksSinceUnderBlock() > 10 && data.getTicksSinceFlying() > 250 &&
                  data.getTicksSinceIce() > 15 &&data.getTicksSinceSlime() > 5 && data.getTicksSinceVelocity() > 20 && data.getTicksSinceOnStairs() >= 5) {
                    if(++data.speedCVerbose > 3) {
                        flag(data, "moving too fast horizontally: " + data.currDeltaXz + " > " + MAX_SPEED);
                    } else {
                        reward(data);
                    }
                } else {
                   data.speedCVerbose = 0;
                }
            }
        }
    }
}