package luna.anticheat.utils;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.util.ArrayList;


// Just a ray class I made withdataseful methods I needed.

public class Ray {

    private final Vector origin;
    private final Vector direction;

    // Create a ray at the origin pointing in a direction.
    public Ray(Vector origin, Vector direction) {
        this.origin = origin;
        this.direction = direction;
    }

    // Create a ray based on where the player is looking.
    // Origin: Player Eye Location
    // Direction: Player-looking direction
    public static luna.anticheat.utils.Ray from(Player player) {
        return new luna.anticheat.utils.Ray(player.getEyeLocation().toVector(), player.getEyeLocation().getDirection());
    }

    public static luna.anticheat.utils.Ray from(Vector origin, Vector direction) {
        return new luna.anticheat.utils.Ray(origin, direction);
    }

    // (Used for rotating vectors) Creates a vector in the horizontal plane (y=0) perpendicular to a vector.
    public static Vector right(Vector vector) {
        Vector n = vector.clone();
        n = n.setY(0).normalize();
        double x = n.getX();
        n.setX(n.getZ());
        n.setZ(-x);
        return n;
    }

    // Returns a normalized version of this Ray with the Y component set to 0
    public luna.anticheat.utils.Ray level() {
        return new luna.anticheat.utils.Ray(origin, direction.setY(0).normalize());
    }

    public Vector getOrigin() {
        return origin;
    }

    public Vector getDirection() {
        return direction;
    }

    public double origin(int i) {
        switch (i) {
            case 0:
                return origin.getX();
            case 1:
                return origin.getY();
            case 2:
                return origin.getZ();
            default:
                return 0;
        }
    }

    public double direction(int i) {
        switch (i) {
            case 0:
                return direction.getX();
            case 1:
                return direction.getY();
            case 2:
                return direction.getZ();
            default:
                return 0;
        }
    }

    public double getClosestPointDist(double range, double accuracy, Entity entity) { //Gets multiple points on the ray, then returns the point closest to the specified hitbox
        ArrayList<Vector> points = new ArrayList<>();
        ArrayList<Double> distances = new ArrayList<>();
        for(double i = 0; i <= range; i += accuracy) {
            points.add(getPoint(i));
        }
        AABB aabb = new AABB(entity);
        Vector aabbMin = aabb.getMin();
        Vector aabbMax = aabb.getMax();
        Vector aabbMid = aabb.getMid();
        for(int i = 0; i < points.size(); i++) {
            distances.add(MathUtils.vectorDist3D(points.get(i), aabbMin));
            distances.add(MathUtils.vectorDist3D(points.get(i), aabbMax));
            distances.add(MathUtils.vectorDist3D(points.get(i), aabbMid));
        }
        double smallestDist = MathUtils.sortForMin(distances);
        // Bukkit.broadcastMessage(df.format(smallestDist) + "");
        return smallestDist;
    }

    public ArrayList<Vector> traverse(double blocksAway, double accuracy) {
        ArrayList<Vector> positions = new ArrayList<>();
        for (double d = 0; d <= blocksAway; d += accuracy) {
            positions.add(getPoint(d));
        }
        return positions;
    }

    public boolean intersectsBB(Vector position, Vector min, Vector max) {

        if (position.getX() < min.getX() || position.getX() > max.getX()) {
            return false;
        } else if (position.getY() < min.getY() || position.getY() > max.getY()) {
            return false;
        } else if (position.getZ() < min.getZ() || position.getZ() > max.getZ()) {
            return false;
        }
        return true;
    }

    // Get a point x distance away from this ray.
    // Can bedatased to get a point 2 blocks in front of a player's face.
    public Vector getPoint(double distance) {
        Vector dir = new Vector(direction.getX(), direction.getY(), direction.getZ());
        Vector orig = new Vector(origin.getX(), origin.getY(), origin.getZ());
        return orig.add(dir.multiply(distance));
    }



    // Same as above, but no need to construct object.
    public static Location getPoint(Player player, double distance) {
        Vector point = luna.anticheat.utils.Ray.from(player).getPoint(distance);
        return new Location(player.getWorld(), point.getX(), point.getY(), point.getZ());
    }

    public void highlight(World world, double blocksAway, double accuracy) {
        for(Vector position: traverse(blocksAway, accuracy)) {
            world.playEffect(position.toLocation(world), Effect.SMOKE, 0);
        }
    }
}