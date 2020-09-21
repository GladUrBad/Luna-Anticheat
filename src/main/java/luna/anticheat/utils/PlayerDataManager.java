package luna.anticheat.utils;

import io.github.retrooper.packetevents.enums.minecraft.PlayerDigType;
import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.PacketListener;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.event.impl.PacketSendEvent;

import io.github.retrooper.packetevents.packet.PacketType;
import io.github.retrooper.packetevents.packetwrappers.in.blockdig.WrappedPacketInBlockDig;
import io.github.retrooper.packetevents.packetwrappers.in.blockplace.WrappedPacketInBlockPlace;
import io.github.retrooper.packetevents.packetwrappers.in.entityaction.WrappedPacketInEntityAction;
import io.github.retrooper.packetevents.packetwrappers.in.flying.WrappedPacketInFlying;
import io.github.retrooper.packetevents.packetwrappers.out.entityvelocity.WrappedPacketOutEntityVelocity;
import luna.anticheat.playerdata.PlayerData;


public class PlayerDataManager implements PacketListener {

    public static void processData(PacketEvent event, PlayerData data) {
        if (event instanceof PacketReceiveEvent) {
            PacketReceiveEvent e = (PacketReceiveEvent) event;
            if (e.getPacketId() ==(PacketType.Client.POSITION_LOOK) || e.getPacketId() ==(PacketType.Client.POSITION)) {
                //---------------------------------------------------------------------
                WrappedPacketInFlying packet = new WrappedPacketInFlying(e.getNMSPacket());
                CustomLocation playerLoc = new CustomLocation(e.getPlayer().getWorld().getName(), packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch());
                CustomLocation lastLoc = data.location != null ? data.location : playerLoc;

                data.lastLocation = lastLoc;
                data.location = playerLoc;

                if(data.hasPendingTeleport) data.hasPendingTeleport = false;

                data.setTicksSinceRespawn(data.getTicksSinceRespawn() + 1);
                data.setTicksSinceTeleport(data.getTicksSinceTeleport() + 1);
                data.setTicksSinceVelocity(data.getTicksSinceVelocity() + 1);

                float lastDeltaX = data.deltaX;
                float deltaX = (float) (playerLoc.getX() - lastLoc.getX());

                data.lastDeltaX = lastDeltaX;
                data.deltaX = deltaX;

                float lastDeltaY = data.deltaY;
                float deltaY = (float) (playerLoc.getY() - lastLoc.getY());

                data.lastDeltaY = lastDeltaY;
                data.deltaY = deltaY;

                float lastDeltaZ = data.deltaZ;
                float deltaZ = (float) (playerLoc.getZ() - lastLoc.getZ());

                data.lastDeltaZ = lastDeltaZ;
                data.deltaZ = deltaZ;

                data.currDeltaXz = (float) (Math.hypot(deltaX, deltaZ));
                data.lastDeltaXz = (float) (Math.hypot(lastDeltaX, lastDeltaZ));


                float lastDeltaPitch = data.deltaPitch;
                float deltaPitch = Math.abs(playerLoc.getPitch() - lastLoc.getPitch());

                data.lastDeltaPitch = lastDeltaPitch;
                data.deltaPitch = deltaPitch;

                float lastDeltaYaw = data.deltaYaw;
                float deltaYaw = Math.abs(playerLoc.getYaw() - lastLoc.getYaw());

                data.lastDeltaYaw = lastDeltaYaw;
                data.deltaYaw = deltaYaw;

                if(data.deltaY >= 0) {
                   data.setPositiveDeltaYTicks(data.getPositiveDeltaYTicks() + 1);
                   data.setNegativeDeltaYTicks(0);
                } else {
                   data.setNegativeDeltaYTicks(data.getNegativeDeltaYTicks() + 1);
                   data.setPositiveDeltaYTicks(0);
                }

                        if (data.isOnGround()) {
                           data.setOnGroundTicks(data.getOnGroundTicks() + 1);
                           data.setInAirTicks(0);
                        } else {
                           data.setOnGroundTicks(0);
                           data.setInAirTicks(data.getAirTicks() + 1);

                        }

                        if(data.isNearIce()) {
                           data.setOnIceTicks(data.getOnIceTicks() + 1);
                           data.setTicksSinceIce(0);
                        } else {
                           data.setOnIceTicks(0);
                           data.setTicksSinceIce(data.getTicksSinceIce() + 1);
                        }

                        if(data.isNearCeiling()) {
                            data.setUnderBlockTicks(data.getUnderBlockTicks() + 1);
                            data.setTicksSinceUnderBlock(0);
                        } else {
                            data.setUnderBlockTicks(0);
                            data.setTicksSinceUnderBlock(data.getTicksSinceUnderBlock() + 1);
                        }

                        if(data.isNearSlime()) {
                           data.setOnSlimeTicks(data.getOnSlimeTicks() + 1);
                           data.setTicksSinceSlime(0);
                        } else {
                           data.setOnSlimeTicks(0);
                           data.setTicksSinceSlime(data.getTicksSinceSlime() + 1);
                        }

                        if (data.isOnSolidGround()) {
                           data.setOnSolidGroundTicks(data.getOnGroundTicks() + 1);
                        } else {
                           data.setOnSolidGroundTicks(0);
                        }

                        if (data.isOnClimbable()) {
                           data.setOnClimbableTicks(data.getOnClimbableTicks() + 1);
                           data.setTicksSinceOnClimbable(0);
                        } else {
                           data.setOnClimbableTicks(0);
                           data.setTicksSinceOnClimbable(data.getTicksSinceOnClimbable() + 1);
                        }

                        if (data.getPlayer().isFlying()) {
                           data.setTicksSinceFlying(0);
                           data.setFlyingTicks(data.getFlyingTicks() + 1);
                        } else {
                           data.setFlyingTicks(0);
                           data.setTicksSinceFlying(data.getTicksSinceFlying() + 1);
                        }

                        if (packet.isOnGround()) {
                           data.setClaimedOnGroundTicks(data.getClaimedOnGroundTicks() + 1);
                           data.setTicksSinceClaimedOnGround(0);
                        } else {
                           data.setClaimedOnGroundTicks(0);
                           data.setTicksSinceClaimedOnGround(data.getTicksSinceClaimedOnGround() + 1);
                        }

                        if (data.isWaterBelow()) {
                           data.setWaterBelowTicks(data.getWaterBelowTicks() + 1);
                        } else {
                           data.setWaterBelowTicks(0);
                        }

                        if (data.isInWater()) {
                           data.setInWaterTicks(data.getInWaterTicks() + 1);
                           data.setTicksSinceInWater(0);
                        } else {
                           data.setInWaterTicks(0);
                           data.setTicksSinceInWater(data.getTicksSinceInWater() + 1);
                        }

                        if(data.isOnStairs()) {
                            data.setOnStairsTicks(data.getOnStairsTicks() + 1);
                            data.setTicksSinceOnStairs(0);
                        } else {
                            data.setOnStairsTicks(0);
                            data.setTicksSinceOnStairs(data.getTicksSinceOnStairs() + 1);
                        }

               data.lastFlyingTime = e.getTimestamp();
            } else if(e.getPacketId() == PacketType.Client.LOOK) {
                WrappedPacketInFlying packet = new WrappedPacketInFlying(e.getNMSPacket());
                CustomLocation loc = new CustomLocation(e.getPlayer().getWorld().getName(),data.location.getX(),
                       data.location.getY(),data.location.getZ(), packet.getYaw(), packet.getPitch());
                CustomLocation lastLoc = data.location != null ? data.location : loc;

                float lastDeltaYaw =data.deltaYaw;
                float deltaYaw = Math.abs(loc.getYaw() - lastLoc.getYaw());

               data.lastDeltaYaw = lastDeltaYaw;
               data.deltaYaw = deltaYaw;

                float lastDeltaPitch =data.deltaPitch;
                float deltaPitch = Math.abs(loc.getPitch() - lastLoc.getPitch());

               data.lastDeltaPitch = lastDeltaPitch;
               data.deltaPitch = deltaPitch;

            } else if (e.getPacketId() == PacketType.Client.ENTITY_ACTION) {
                WrappedPacketInEntityAction packet = new WrappedPacketInEntityAction((e.getNMSPacket()));
                switch (packet.getAction()) {
                    case START_SPRINTING:
                       data.isSprinting = true;
                        break;
                    case STOP_SPRINTING:
                       data.isSprinting = false;
                        break;
                    case START_SNEAKING:
                       data.isSneaking = true;
                        break;
                    case STOP_SNEAKING:
                       data.isSneaking = false;
                        break;
                }
            } else if(e.getPacketId() == PacketType.Client.BLOCK_PLACE) {
                WrappedPacketInBlockPlace packet = new WrappedPacketInBlockPlace(e.getPlayer(), e.getNMSPacket());
                if(packet.getItemStack().getType().toString().contains("SWORD")) {
                   data.isBlocking = true;
                   data.lastBlockingTime = e.getTimestamp();
                }
            } else if(e.getPacketId() == PacketType.Client.BLOCK_DIG) {
                WrappedPacketInBlockDig packet = new WrappedPacketInBlockDig(e.getNMSPacket());
                if(packet.getDigType() == PlayerDigType.RELEASE_USE_ITEM) {
                   data.isBlocking = false;
                   data.lastUnblockingTime = e.getTimestamp();
                }
            } else if(e.getPacketId() == PacketType.Client.ARM_ANIMATION) {
               data.lastSwingTime = e.getTimestamp();
            }
        } else if(event instanceof PacketSendEvent) {
            PacketSendEvent e = (PacketSendEvent) event;
            if(e.getPacketId() == PacketType.Server.ENTITY_VELOCITY) {
                WrappedPacketOutEntityVelocity packet = new WrappedPacketOutEntityVelocity(e.getNMSPacket());
                if(packet.getEntityId() ==data.getPlayer().getEntityId()) {
                   data.setTicksSinceVelocity(0);
                }
            } else if(e.getPacketId() == PacketType.Server.RESPAWN) {
               data.setTicksSinceRespawn(0);
            } else if(e.getPacketId() == PacketType.Server.POSITION) {
               data.hasPendingTeleport = true;
               data.setTicksSinceTeleport(0);
            }
        }
    }
}
