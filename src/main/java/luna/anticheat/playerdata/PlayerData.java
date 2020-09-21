package luna.anticheat.playerdata;

import luna.anticheat.utils.CustomLocation;
import luna.anticheat.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.util.*;

public class PlayerData {
    private final Player player;
    private final UUID playerUUID;


    public int nofallApreVL;
    public long lastFlyingTime;
    public int scaffoldACounter;
    public double lastDeltaXz;
    public int speedCVerbose;
    public int motionCVerbose;
    public int bpDSlotChanges;
    public int bpCVl;
    public int bpFVl;
    public boolean isBlocking;
    public long lastBlockingTime;
    public long lastUnblockingTime;
    public long lastSwingTime;
    public double autoblockAPreVL;
    public int ticksSinceVelocity;
    public int ticksSinceRespawn;
    public int ticksSinceTeleport;
    public float pitchDeviation;
    public float lastPitchDeviation = pitchDeviation;
    public int aimApreVL;
    public int aimAHitTicks;
    public boolean recentlyAttacked;
    public Entity entityAttacked;
    public ArrayList<Double> reaches = new ArrayList<>();
    public ArrayList<Float> aimBDeltas = new ArrayList<>();
    public int reachVL;
    public float lastDifference;
    public int aimBHitTicks;

    public int getTicksSinceVelocity() {
        return ticksSinceVelocity;
    }

    public void setTicksSinceVelocity(int ticksSinceVelocity) {
        this.ticksSinceVelocity = ticksSinceVelocity;
    }

    public int getTicksSinceRespawn() {
        return ticksSinceRespawn;
    }

    public void setTicksSinceRespawn(int ticksSinceRespawn) {
        this.ticksSinceRespawn = ticksSinceRespawn;
    }

    public int getTicksSinceTeleport() {
        return ticksSinceTeleport;
    }

    public void setTicksSinceTeleport(int ticksSinceTeleport) {
        this.ticksSinceTeleport = ticksSinceTeleport;
    }

    public int noslowAPreVL;
    public Long timerLastFlyingTime;
    public int motionAVerbose;
    public int motionBVerbose;
    public boolean hasPendingTeleport;
    public int liquidWalkCounterB;

    public PlayerData(UUID playerUUID) {
        this.playerUUID = playerUUID;
        this.player = Bukkit.getPlayer(playerUUID);
    }

    public Player getPlayer() {
        return player;
    }

    public UUID getUUID() {
        return playerUUID;
    }

    //------------NEW DATA SINCE PACKET-EVENTS---------------------
    public CustomLocation location, lastLocation, lastOnGroundLocation, lastLocationNotFlagged;
    public float deltaX, deltaY, deltaZ, lastDeltaX, lastDeltaY, lastDeltaZ;
    public float deltaYaw, deltaPitch, lastDeltaYaw, lastDeltaPitch;
    public float yAccel = Math.abs(deltaY - lastDeltaY);
    public float yawAccel = Math.abs(deltaYaw - lastDeltaYaw);
    public float pitchAccel = Math.abs(deltaPitch - lastDeltaPitch);
    public ArrayList<Float> pitchDeltas = new ArrayList<>();
    public ArrayList<Float> yawDeltas = new ArrayList<>();
    public float currDeltaXz;

    private int onGroundTicks;
    private int onSolidGroundTicks;
    private int underBlockTicks;
    private int inAirTicks;
    private int claimedOnGroundTicks;
    private int ticksSinceClaimedOnGround;
    private int flyingTicks;
    private int ticksSinceFlying;
    private int onClimbableTicks;
    private int inWaterTicks;
    private int waterBelowTicks;
    private int ticksSinceIce;
    private int onSlimeTicks;
    private int ticksSinceSlime;
    private int negativeDeltaYTicks;
    private int positiveDeltaYTicks;
    private int ticksSinceUnderBlock;
    private int ticksSinceOnStairs;
    private int onStairsTicks;
    private int ticksSinceInWater;
    private int ticksSinceOnClimbable;

    public int getTicksSinceOnClimbable() {
        return ticksSinceOnClimbable;
    }

    public void setTicksSinceOnClimbable(int ticksSinceOnClimbable) {
        this.ticksSinceOnClimbable = ticksSinceOnClimbable;
    }

    public int getTicksSinceInWater() {
        return ticksSinceInWater;
    }

    public void setTicksSinceInWater(int ticksSinceInWater) {
        this.ticksSinceInWater = ticksSinceInWater;
    }

    public int getNegativeDeltaYTicks() {
        return negativeDeltaYTicks;
    }

    public int getTicksSinceUnderBlock() { return ticksSinceUnderBlock; }

    public void setTicksSinceUnderBlock(int ticksSinceUnderBlock) {
        this.ticksSinceUnderBlock = ticksSinceUnderBlock;
    }



    public void setNegativeDeltaYTicks(int negativeDeltaYTicks) {
        this.negativeDeltaYTicks = negativeDeltaYTicks;
    }

    public int getPositiveDeltaYTicks() {
        return positiveDeltaYTicks;
    }

    public void setPositiveDeltaYTicks(int positiveDeltaYTicks) {
        this.positiveDeltaYTicks = positiveDeltaYTicks;
    }

    public void setTicksSinceClaimedOnGround(int ticksSinceClaimedOnGround) {
        this.ticksSinceClaimedOnGround = ticksSinceClaimedOnGround;
    }

    public int getTicksSinceClaimedOnGround() {
        return ticksSinceClaimedOnGround;
    }


    public int getTicksSinceIce() {
        return ticksSinceIce;
    }

    public void setTicksSinceIce(int ticksSinceIce) {
        this.ticksSinceIce = ticksSinceIce;
    }

    public int getOnSlimeTicks() {
        return onSlimeTicks;
    }

    public void setOnSlimeTicks(int onSlimeTicks) {
        this.onSlimeTicks = onSlimeTicks;
    }

    public int getTicksSinceSlime() {
        return ticksSinceSlime;
    }

    public void setTicksSinceSlime(int ticksSinceSlime) {
        this.ticksSinceSlime = ticksSinceSlime;
    }

    public int getOnIceTicks() {
        return onIceTicks;
    }

    public void setOnIceTicks(int onIceTicks) {
        this.onIceTicks = onIceTicks;
    }

    public int getOnStairsTicks() {
        return onStairsTicks;
    }

    public void setOnStairsTicks(int onStairsTicks) {
        this.onStairsTicks = onStairsTicks;
    }

    public int getTicksSinceOnStairs() {
        return ticksSinceOnStairs;
    }

    public void setTicksSinceOnStairs(int ticksSinceOnStairs) {
        this.ticksSinceOnStairs = ticksSinceOnStairs;
    }

    private int onIceTicks;

    public boolean isSprinting, isSneaking;

    public boolean isOnGround() {
        return PlayerUtils.isNearGround(player);
    }

    public boolean isOnStairs() {
        return PlayerUtils.isOnStair(player);
    }

    public boolean isInWater() {
        return PlayerUtils.isInLiquid(player);
    }

    public boolean isWaterBelow() {
        return PlayerUtils.isLiquidBelow(player);
    }

    public boolean isNearCeiling() {
        return PlayerUtils.isNearCeiling(player);
    }

    public boolean isNearIce() {
        return PlayerUtils.isNearIce(player);
    }

    public boolean isNearSlime() {
        return PlayerUtils.isNearModifiable(player, Material.SLIME_BLOCK);
    }

    public boolean isNearWall() {
        return PlayerUtils.isOnWall(player);
    }


    public int getSpeedLevel() {
        return PlayerUtils.getSpeedBoostLvl(player);
    }

    public int getInAirTicks() {
        return inAirTicks;
    }

    public int getInWaterTicks() {
        return inWaterTicks;
    }



    public void setInWaterTicks(int inWaterTicks) {
        this.inWaterTicks = inWaterTicks;
    }

    public int getWaterBelowTicks() {
        return waterBelowTicks;
    }

    public void setWaterBelowTicks(int waterBelowTicks) {
        this.waterBelowTicks = waterBelowTicks;
    }

    public boolean isOnSolidGround() {
        return PlayerUtils.isOnStandableGround(player);
    }

    public Material getBlockUnder() {
        return PlayerUtils.getMaterialUnder(player, 0.01);
    }


    public int getJumpBoostLevel() {
        return PlayerUtils.getJumpBoostLvl(player);
    }

    public float getSlipperiness() {
        switch (getBlockUnder().getId()) {
            case 79:
            case 174:
                return 0.98F;
            case 165:
                return 0.8F;
            default:
                return 0.6F;

        }
    }

    public boolean isOnClimbable() {
        return PlayerUtils.isCollidingWithClimable(player.getLocation());
    }

    public boolean isCollidingWithLiquid() {
        return PlayerUtils.isInLiquid(player);
    }

    public int getOnGroundTicks() {
        return onGroundTicks;
    }

    public void setOnGroundTicks(int onGroundTicks) {
        this.onGroundTicks = onGroundTicks;
    }

    public int getOnSolidGroundTicks() {
        return onSolidGroundTicks;
    }

    public void setOnSolidGroundTicks(int onSolidGroundTicks) {
        this.onSolidGroundTicks = onSolidGroundTicks;
    }

    public int getUnderBlockTicks() {
        return underBlockTicks;
    }

    public void setUnderBlockTicks(int underBlockTicks) {
        this.underBlockTicks = underBlockTicks;
    }

    public int getAirTicks() {
        return inAirTicks;
    }

    public void setInAirTicks(int inAirTicks) {
        this.inAirTicks = inAirTicks;
    }

    public int getClaimedOnGroundTicks() {
        return claimedOnGroundTicks;
    }

    public void setClaimedOnGroundTicks(int claimedOnGroundTicks) {
        this.claimedOnGroundTicks = claimedOnGroundTicks;
    }

    public void setFlyingTicks(int flyingTicks) {
        this.flyingTicks = flyingTicks;
    }

    public int getFlyingTicks() {
        return flyingTicks;
    }

    public void setTicksSinceFlying(int ticksSinceFlying) {
        this.ticksSinceFlying = ticksSinceFlying;
    }

    public int getTicksSinceFlying() {
        return ticksSinceFlying;
    }

    public int getOnClimbableTicks() {
        return onClimbableTicks;
    }

    public void setOnClimbableTicks(int onClimbableTicks) {
        this.onClimbableTicks = onClimbableTicks;
    }

    //Counters and other things I need.
    public int hitTicks;
    public int killauraPreVL;
    public long killauraCLastFlying;
    public int killauraCPreVl;
    public int liquidWalkCounterA;
    public long tickSpeedBalance;
    public long joinTime;
    public int killauraBLastEntityID;
    public int killauraBEntitiesHit;
    public int killauraCUseEntitySinceFlying;


}
