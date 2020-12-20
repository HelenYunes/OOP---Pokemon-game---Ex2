package api;


import com.google.gson.annotations.SerializedName;
import java.util.*;
import java.io.Serializable;


public class NodeData implements node_data, Serializable, Comparable<node_data> {

    @SerializedName(value = "id")
    private final int Key;

    @SerializedName(value = "pos")
    private geo_location Location;

    @SerializedName(value = "w")
    private double Weight;
    private int Tag;
    private String Info;


    public NodeData(node_data n) {
        this.Key = n.getKey();
        this.Tag = n.getTag();
        this.Weight = n.getWeight();
        this.Info = n.getInfo();
        this.Location = new GeoLocation(n.getLocation());

    }

    public NodeData(int key) {
        this.Info = "";
        this.Key = key;
        this.Tag = 0;
        this.Weight = 0;
        this.Location = new GeoLocation();


    }

    @Override
    public int getKey() {
        return this.Key;
    }

    @Override
    public geo_location getLocation() {
        return this.Location;
    }

    @Override
    public void setLocation(geo_location p) {
        this.Location = new GeoLocation(p);

    }

    @Override
    public double getWeight() {
        return this.Weight;
    }

    @Override
    public void setWeight(double w) {
        this.Weight = w;
    }

    @Override
    public String getInfo() {
        return this.Info;
    }

    @Override
    public void setInfo(String s) {
        this.Info = s + "";
    }

    @Override
    public int getTag() {
        return this.Tag;
    }

    @Override
    public void setTag(int t) {
        this.Tag = t;

    }

    @Override
    public String toString() {
        return "{" +
                "key=" + Key +
                ", pos=" + Location +
                ", weight=" + Weight +
                ", info='" + Info + '\'' +
                ", tag=" + Tag +
                '}';
    }

    @Override
    public int compareTo(node_data t) {
        return Double.compare(this.getTag(), t.getTag());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeData nodeData = (NodeData) o;
        return getKey() == nodeData.getKey() &&
                Double.compare(nodeData.getWeight(), getWeight()) == 0 &&
                getTag() == nodeData.getTag() &&
                Objects.equals(getLocation(), nodeData.getLocation()) &&
                Objects.equals(getInfo(), nodeData.getInfo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getLocation(), getWeight(), getTag(), getInfo());
    }
}
