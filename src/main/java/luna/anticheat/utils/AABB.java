package luna.anticheat.utils;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

// Just an AABB class I made with somedataseful methods I needed.

@SuppressWarnings("unused")
public class AABB {

    private Vector min, max, mid; // min/max locations

    // Create Bounding Box from min/max locations.
    public AABB(Vector min, Vector max) {
        this(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
    }

    // Main constructor for AABB
    public AABB(double x1, double y1, double z1, double x2, double y2, double z2) {
        MathUtils mu = new MathUtils();
        this.min = new Vector(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2));
        this.max = new Vector(Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));
        this.mid = new Vector(mu.averageTwoNum(x1, x2), mu.averageTwoNum(y1, y2), mu.averageTwoNum(z1, z2));
    }


    public AABB(Entity e) {
        AxisAlignedBB bb = ((CraftEntity) e).getHandle().getBoundingBox();
        this.min = new Vector(bb.a - 0.1, bb.b - 0.1, bb.c - 0.1);
        this.max = new Vector(bb.d + 0.1, bb.e + 0.1, bb.f + 0.1);
    }

    public AABB(Location loc) {
        this.min = new Vector(loc.getX() - 0.4005, loc.getY() - 0, loc.getZ() - 0.4005);
        this.max = new Vector(loc.getX() + 0.4005, loc.getY() + 1.5, loc.getZ() + 0.4005);
    }

    // Create an AABB based on a player's hitbox
    public static luna.anticheat.utils.AABB from(Entity entity) {
        return new luna.anticheat.utils.AABB(entity);
    }

    public static luna.anticheat.utils.AABB from(Location location) { //ONLY USE FOR PLAYER
        return new luna.anticheat.utils.AABB(location);
    }

    public Vector getMin() {
        return min;
    }

    public Vector getMax() {
        return max;
    }

    public Vector getMid() {
        return mid;
    }

    // Returns minimum x, y, or z point from inputs 0, 1, or 2.
    public double min(int i) {
        switch (i) {
            case 0:
                return min.getX();
            case 1:
                return min.getY();
            case 2:
                return min.getZ();
            default:
                return 0;
        }
    }

    // Returns maximum x, y, or z point from inputs 0, 1, or 2.
    public double max(int i) {
        switch (i) {
            case 0:
                return max.getX();
            case 1:
                return max.getY();
            case 2:
                return max.getZ();
            default:
                return 0;
        }
    }

    // Check if a Ray passes through this box. tmin and tmax are the bounds.
    // Example: If you wanted to see if the Ray collides anywhere from its
    // origin to 5datanits away, the values would be 0 and 5.
    public boolean collides(Ray ray, double tmin, double tmax) {
        for (int i = 0; i < 3; i++) {
            double d = 1 / ray.direction(i);
            double t0 = (min(i) - ray.origin(i)) * d;
            double t1 = (max(i) - ray.origin(i)) * d;
            if (d < 0) {
                double t = t0;
                t0 = t1;
                t1 = t;
            }
            tmin = Math.max(t0, tmin);
            tmax = Math.min(t1, tmax);

            if (tmax <= tmin) {
                // DecimalFormat df = new DecimalFormat("#.##");
                Bukkit.broadcastMessage(tmax + " " + tmin);
                return false;
            }
        }
        return true;
    }

    // Same as other collides method, but returns the distance of the nearest
    // point of collision of the ray and box, or -1 if no collision.
    public double collidesD(Ray ray, double tmin, double tmax) {
        for (int i = 0; i < 3; i++) {
            double d = 1 / ray.direction(i);
            double t0 = (min(i) - ray.origin(i)) * d;
            double t1 = (max(i) - ray.origin(i)) * d;
            if (d < 0) {
                double t = t0;
                t0 = t1;
                t1 = t;
            }
            tmin = Math.max(t0, tmin);
            tmax = Math.min(t1, tmax);
            if (tmax <= tmin) return -1;
        }
        return tmin;
    }

    // Check if the location is in this box.
    public boolean containsLoc(Location location) {
        if (location.getX() > max.getX()) return false;
        if (location.getY() > max.getY()) return false;
        if (location.getZ() > max.getZ()) return false;
        if (location.getX() < min.getX()) return false;
        if (location.getY() < min.getY()) return false;
        if (location.getZ() < min.getZ()) return false;
        return true;
    }

    public boolean containsVec(Vector vec) {
        if (vec.getX() > max.getX()) return false;
        if (vec.getY() > max.getY()) return false;
        if (vec.getZ() > max.getZ()) return false;
        if (vec.getX() < min.getX()) return false;
        if (vec.getY() < min.getY()) return false;
        if (vec.getZ() < min.getZ()) return false;
        return true;
    }
}