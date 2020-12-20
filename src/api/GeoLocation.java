package api;

import java.util.Objects;

public class GeoLocation implements geo_location {
    private double x;
    private double y;
    private double z;

    public GeoLocation() {

    }

    public GeoLocation(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public GeoLocation(geo_location g) {
        x = g.x();
        y = g.y();
        z = g.z();
    }

    public GeoLocation(String g) {
        String[] pos = g.split(",");
        x = Double.parseDouble(pos[0]);
        y = Double.parseDouble(pos[1]);
        z = Double.parseDouble(pos[2]);
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double z() {
        return y;
    }

    @Override
    public double distance(geo_location g) {
        double dx = this.x() - g.x();
        double dy = this.y() - g.y();
        double dz = this.z() - g.z();
        double t = (dx * dx + dy * dy + dz * dz);
        return Math.sqrt(t);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeoLocation)) return false;
        GeoLocation that = (GeoLocation) o;
        return Double.compare(that.x, x) == 0 &&
                Double.compare(that.y, y) == 0 &&
                Double.compare(that.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return x +
                "," + y +
                "," + z;
    }
}