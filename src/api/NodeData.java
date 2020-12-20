package api;


import com.google.gson.annotations.SerializedName;

import java.util.*;
import java.io.Serializable;

/**
 * This class represents the set of operations applicable on a
 * node (vertex) in a (directional) weighted graph.
 * @author boaz.benmoshe
 *
 */
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

    /**
     * Return the key (id) associated with this node.
     * Note: each node_data have a unique key.
     *
     * @return
     */
    @Override
    public int getKey() {
        return this.Key;
    }

    /**
     * returns the location of the node
     *
     * @return Location
     */
    @Override
    public geo_location getLocation() {
        return this.Location;
    }

    /**
     * returns the location of the node
     *
     * @param p - new new location  (position) of this node.
     */
    @Override
    public void setLocation(geo_location p) {
        this.Location = new GeoLocation(p);

    }

    /**
     * returns the weight of the node
     *
     * @return
     */
    @Override
    public double getWeight() {
        return this.Weight;
    }

    /**
     * Set the weight of the node
     *
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w) {
        this.Weight = w;
    }

    /**
     * returns the Info of the node
     *
     * @return Info
     */
    @Override
    public String getInfo() {
        return this.Info;
    }

    /**
     * set the Info of the node
     *
     * @param s
     */
    @Override
    public void setInfo(String s) {
        this.Info = s + "";
    }

    /**
     * returns the Tag of the node
     *
     * @return
     */
    @Override
    public int getTag() {
        return this.Tag;
    }

    /**
     * set the Tag of the node
     *
     * @param t - the new value of the tag
     */
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
