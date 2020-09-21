package luna.anticheat.utils;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class MathUtils {

    public static double vectorDist3D(Vector a, Vector b) {

        double xSqrd = Math.pow(Math.abs(a.getX() - b.getX()), 2);
        double ySqrd = Math.pow(Math.abs(a.getY() - b.getY()), 2);
        double zSqrd = Math.pow(Math.abs(a.getZ() - b.getZ()), 2);

        double dist3D = Math.sqrt(xSqrd + ySqrd + zSqrd);

        return dist3D;
    }

    public static double locDist3D(Location a, Location b) {
        double xSqrd = Math.pow(Math.abs(a.getX() - b.getX()), 2);
        double ySqrd = Math.pow(Math.abs(a.getY() - b.getY()), 2);
        double zSqrd = Math.pow(Math.abs(a.getZ() - b.getZ()), 2);

        return Math.sqrt(xSqrd + ySqrd + zSqrd);
    }

    public static double angle(Vector a, Vector b) {
        double dot = Math.min(Math.max(a.dot(b) / (a.length() * b.length()), -1), 1);
        return Math.acos(dot);
    }

    public static List<Float> findDuplicates(ArrayList<Float> floats) {
        ArrayList<Float> duplicates = new ArrayList<>();
        for(int i = 0; i < floats.size(); i++) {
            for(int j = i + 1; j < floats.size(); j++) {
                if(floats.get(i).equals(floats.get(j))) {
                    duplicates.add(floats.get(j));
                }
            }
        }
        return duplicates;
    }

    public static Vector getDirection(float yaw, float pitch) {
        Vector vector = new Vector();
        float rotX = (float)Math.toRadians(yaw);
        float rotY = (float)Math.toRadians(pitch);
        vector.setY(-sin(rotY));
        double xz = cos(rotY);
        vector.setX(-xz * sin(rotX));
        vector.setZ(xz * cos(rotX));
        return vector;
    }

    public static float getSmallestFloat(ArrayList<Float> al) {
        float smallest = Float.POSITIVE_INFINITY;
        for(Float f : al) {
            if(f < smallest) smallest = f;
        }
        return smallest;
    }

    public static float getSmallestFloatThatIsMoreThanZero(ArrayList<Float> al) {
        float smallest = Float.POSITIVE_INFINITY;
        for(Float f : al) {
            if(f < smallest && smallest > 0F) smallest = f;
        }
        return smallest;
    }

    public static float getLargestFloat(ArrayList<Float> al) {
        float smallest = Float.NEGATIVE_INFINITY;
        for(Float f : al) {
            if(f > smallest) smallest = f;
        }
        return smallest;
    }

    public static double getSmallestDouble(ArrayList<Double> al) {
        double smallest = Float.POSITIVE_INFINITY;
        for(Double f : al) {
            if(f < smallest) smallest = f;
        }
        return smallest;
    }

    public static double getLargestDouble(ArrayList<Double> al) {
        double smallest = Float.NEGATIVE_INFINITY;
        for(Double f : al) {
            if(f > smallest) smallest = f;
        }
        return smallest;
    }

    public static float yawTo180F(float flub) {
        if ((flub %= 360.0f) >= 180.0f) {
            flub -= 360.0f;
        }
        if (flub < -180.0f) {
            flub += 360.0f;
        }
        return flub;
    }

    public static float yawToDebugScreen(float flub) {
        if(flub > 180.0f) {
            return 360f - flub;
        }
        if(flub < 180.0f) {
            return flub - (flub * 2);
        }

        return flub;
    }

    public static double dist2D(double a, double b) {

        double xSqrd = Math.pow(Math.abs(a - b), 2);
        double zSqrd = Math.pow(Math.abs(a - b), 2);

        double dist2D = Math.sqrt(xSqrd + zSqrd);

        return dist2D;
    }

    public static float sin(float f) {
        return net.minecraft.server.v1_8_R3.MathHelper.sin(f);
    }

    public static float cos(float f) {
        return net.minecraft.server.v1_8_R3.MathHelper.cos(f);
    }

    public boolean similarY(Vector a, Vector b) {
        if(a.getY() - b.getY() < Math.abs(0.3)) {
            return true;
        }
        return false;
    }

    public static float addAll(ArrayList<Float> al) {
        float sum = 0.0F;
        for(float f : al) {
            sum = sum + f;
        }
        return sum;
    }

    public static float getPercentOfNumSmallerThan(double threshold, ArrayList<Float> al) {
        int count = 0;
        int total = al.size();
        for(float f : al) {
            if(f < threshold) count++;
        }

        float percentage = (count * 100) / total;
        return percentage;
    }

    public static double greatestCommonDivisor(double p, double q) {
        if(q == 0) {
            return p;
        }
        return greatestCommonDivisor(q, p % q);
    }

    public static float roundedGCD(float x, float y) {
        if(x == 0) {
            return y;
        }

        int quotient = getQuotient(y, x);
        float remainder = ((y / x) - quotient) * x;
        if(Math.abs(remainder) < Math.max(x, y) * 1E-3F) {
            remainder = 0;
        }
        return roundedGCD(remainder, x);
    }

    public static float roundedGCD(List<Float> values) {
        float answer = values.get(0);
        for (int i = 1; i < values.size(); i++) {
            answer = roundedGCD(values.get(i), answer);
        }
        return answer;
    }

    public static int getQuotient(float dividend, float divisor) {
        float answer = dividend / divisor;
        float error = Math.max(dividend, divisor) * 1E-3F;
        return (int)(answer + error);
    }

    public double averageTwoNum(double a, double b) {
        double avg = (a + b) / 2;
        return avg;
    }

    public double averageThreeNum(double a, double b, double c) {
        double avg = (a + b + c) / 3;
        return avg;
    }

    public static float averageAllFloats(ArrayList<Float> al) {
        float num = 0;
        for(float fl : al) {
            num = num + fl;
        }
        return num / al.size();
    }
    public static double sortForMin(ArrayList<Double> al) {
        double min = (double) al.get(0);

        for (int i = 1; i < al.size(); i++) {
            if (al.get(i) < min) {
                min = al.get(i);
            }
        }

        return min;
    }

    public static float normalizeAngle(float angle) {
        return (angle %= 360) >= 0 ? angle : (angle + 360);
    }

    public static float standardDeviation(ArrayList<Float> al) {
        float sum = 0.0F, standardDeviation = 0.0F;
        int length = al.size();

        for(float num : al) {
            sum += num;
        }

        double mean = sum/length;

        for (float num : al) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return (float) Math.sqrt(standardDeviation/length);
    }

    public static boolean isRoughlyEqual(double d1, double d2) {
        return Math.abs(d1 - d2) < 0.0001;
    }

    public static boolean isPrettyClose(double d1, double d2) {
        return Math.abs(d1 - d2) < 0.001;
    }

    public static boolean isPrettyClose(long l1, long l2) {
        return Math.abs(l1 - l2) < 0.001;
    }

    public static double averageAllDoubles(ArrayList<Double> al) {
        double num = 0;
        for(double fl : al) {
            num = num + fl;
        }
        return num / al.size();

    }
}
