package luna.anticheat.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class CustomLocation {
    private long timestamp = System.currentTimeMillis();
    private String world;
    private double x, y, z;
    private float yaw;
    private float pitch;


    public CustomLocation(String world, double x, double y, double z, float yaw, float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public static CustomLocation fromBukkitLocation(Location location) {
        return new CustomLocation(location.getWorld().getName(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public Location toBukkitLocation() {
        return new Location(this.toBukkitWorld(), x, y, z, yaw, pitch);
    }

    public World toBukkitWorld() {
        if(this.world == null) {
            return Bukkit.getServer().getWorlds().get(0);
        } else {
            return Bukkit.getServer().getWorld(this.world);
        }
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getWorldName() {
        return world;
    }

    public World getWorld() {
        return this.toBukkitWorld();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public boolean isOnGround() {
        return PlayerUtils.isNearGround(this.toBukkitLocation());
    }

}
