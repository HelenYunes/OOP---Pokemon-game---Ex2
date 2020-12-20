package api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class EdgeData implements edge_data, Serializable {
    //@Expose
    @SerializedName(value = "src")
    private int Source;
    //  @Expose
    @SerializedName(value = "dest")
    private int Destination;
    //  @Expose
    @SerializedName(value = "w")
    private double Weight;
    private String Info;
    private int Tag;

    public EdgeData(int src, int dest, double weight) {
        this.Source = src;
        this.Destination = dest;
        this.Weight = weight;
        this.Info = "";
        this.Tag = 0;
    }

    @Override
    public int getSrc() {
        return this.Source;
    }

    @Override
    public int getDest() {
        return this.Destination;
    }

    @Override
    public double getWeight() {
        return this.Weight;
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

    public void setWeight(double weight) {
        this.Weight = weight;
    }

    //    @Override
//    public String toString() {
//        return "[Edge: {Source= " + this.Source + ", Destination= " + this.Destination + ", Weight= " + this.Weight+"}]";
//    }
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
