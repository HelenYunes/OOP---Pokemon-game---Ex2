package api;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents the set of operations applicable on a
 * directional edge(src,dest) in a (directional) weighted graph.
 *
 * @author boaz.benmoshe
 */
public class EdgeData implements edge_data, Serializable {

    @SerializedName(value = "src")
    private int Source;
    @SerializedName(value = "dest")
    private int Destination;
    @SerializedName(value = "w")
    private double Weight;
    private String Info;
    private int Tag;

    /**
     * Constructor:
     */
    public EdgeData(int src, int dest, double weight) {
        this.Source = src;
        this.Destination = dest;
        this.Weight = weight;
        this.Info = "";
        this.Tag = 0;
    }

    /**
     * Return the id of the source node of this edge.
     *
     * @return Source
     */
    @Override
    public int getSrc() {
        return this.Source;
    }

    /**
     * Return the id of the destination node of this edge
     *
     * @return Destination
     */
    @Override
    public int getDest() {
        return this.Destination;
    }

    /**
     * Return the weight of this edge (positive value).
     *
     * @return Weight
     */
    @Override
    public double getWeight() {
        return this.Weight;
    }

    /**
     * @return remark (meta data) associated with this edge
     */
    @Override
    public String getInfo() {
        return this.Info;
    }

    /**
     * Allows changing the remark (meta data) associated with this edge.
     *
     * @param s
     */
    @Override
    public void setInfo(String s) {
        this.Info = s + "";
    }

    /**
     * Temporal data (aka color: e,g, white, gray, black)
     * which can be used be algorithms
     *
     * @return
     */
    @Override
    public int getTag() {
        return this.Tag;
    }

    /**
     * This method allows setting the "tag" value for temporal marking an edge - common
     * practice for marking by algorithms.
     *
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.Tag = t;

    }

    public void setWeight(double weight) {
        this.Weight = weight;
    }

    @Override
    public String toString() {
        return "{" +
                "src=" + Source +
                ", dest=" + Destination +
                ", weight=" + Weight +
                ", info='" + Info + '\'' +
                ", tag=" + Tag +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdgeData edgeData = (EdgeData) o;
        return Source == edgeData.Source &&
                Destination == edgeData.Destination &&
                Double.compare(edgeData.getWeight(), getWeight()) == 0 &&
                getTag() == edgeData.getTag() &&
                Objects.equals(getInfo(), edgeData.getInfo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(Source, Destination, getWeight(), getInfo(), getTag());
    }
}
