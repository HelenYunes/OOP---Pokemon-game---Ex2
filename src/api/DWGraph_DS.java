package api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.*;

public class DWGraph_DS implements directed_weighted_graph, Serializable {

    @SerializedName(value = "Nodes")
    private HashMap<Integer, node_data> NodeGraph;

    @SerializedName(value = "Edges")
    private HashMap<Integer, HashMap<Integer, edge_data>> EdgeGraph;
    private int ModeCount, EdgeGraphSize;

    public DWGraph_DS() {
        this.NodeGraph = new HashMap<>();
        this.EdgeGraph = new HashMap<>();
        this.ModeCount = 0;
        this.EdgeGraphSize = 0;
    }

    public HashMap<Integer, node_data> getNodeGraph() {
        return this.NodeGraph;
    }

    public HashMap<Integer, HashMap<Integer, edge_data>> getEdgeGraph() {
        return this.EdgeGraph;
    }

    /**
     * returns the node_data by the node_id,
     *
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_data getNode(int key) {
        if (!NodeGraph.containsKey(key)) return null;
        return NodeGraph.get(key);
    }

    /**
     * returns the data of the edge (src,dest), null if none.
     * Note: this method should run in O(1) time.
     *
     * @param src
     * @param dest
     * @return
     */
    @Override
    public edge_data getEdge(int src, int dest) {
        if (src == dest || !NodeGraph.containsKey(src) || !NodeGraph.containsKey(dest) || !this.EdgeGraph.containsKey(src))
            return null;
        HashMap<Integer, edge_data> neighbors = EdgeGraph.get(src);
        if (neighbors.containsKey(dest)) {
            return neighbors.get(dest);
        }
        return null;
    }

    /**
     * adds a new node to the graph with the given node_data.
     * Note: this method should run in O(1) time.
     *
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        if (NodeGraph.containsKey(n.getKey()) || n == null || n.getWeight() < 0) {
            return;
        }
        this.ModeCount++;
        NodeGraph.put(n.getKey(), n);
    }

    /**
     * Connects an edge with weight w between node src to node dest.
     * * Note: this method should run in O(1) time.
     *
     * @param src  - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w    - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {
        if (!NodeGraph.containsKey(dest) || !NodeGraph.containsKey(src) || src == dest || w < 0)
            return;
        if (this.EdgeGraph.containsKey(src)) {
            if (getEdge(src, dest) != null) {
                if (getEdge(src, dest).getWeight() == w)
                    return;
                EdgeData edge = new EdgeData(src, dest, w);
                this.EdgeGraph.get(src).replace(dest, edge);
                this.ModeCount++;
                return;
            }
            EdgeData edge = new EdgeData(src, dest, w);
            this.EdgeGraph.get(src).put(dest, edge);
            this.ModeCount++;
            this.EdgeGraphSize++;
            return;
        }

        if (!this.EdgeGraph.containsKey(src)) {
            HashMap<Integer, edge_data> newEdges = new HashMap<>();
            EdgeData edge = new EdgeData(src, dest, w);
            newEdges.put(dest, edge);
            this.EdgeGraph.put(src, newEdges);
            this.EdgeGraphSize++;
            this.ModeCount++;
        }
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the nodes in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_data> getV() {
        if (this.getNodeGraph().isEmpty()) return new HashMap<Integer, node_data>().values();
        return this.NodeGraph.values();
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the edges getting out of
     * the given node (all the edges starting (source) at the given node).
     * Note: this method should run in O(k) time, k being the collection size.
     *
     * @return Collection<edge_data>
     */
    @Override
    public Collection<edge_data> getE(int node_id) {
        if (!EdgeGraph.containsKey(node_id)) return new HashMap<Integer, edge_data>().values();
        return this.EdgeGraph.get(node_id).values();
    }

    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(k), V.degree=k, as all the edges should be removed.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_data removeNode(int key) {
        if (!this.NodeGraph.containsKey(key))
            return null;
        node_data removedNode = this.NodeGraph.get(key);
        Collection<edge_data> neighbors = this.getE(key);
        if (this.EdgeGraph.containsKey(key)) {
            this.EdgeGraphSize -= neighbors.size();
            this.ModeCount += neighbors.size();
            this.EdgeGraph.remove(key);
            Iterator<Integer> iterator = this.EdgeGraph.keySet().iterator();
            while (iterator.hasNext()) {
                Integer src = iterator.next();
                if (this.EdgeGraph.get(src).containsKey(key)) {
                    this.EdgeGraph.get(src).remove(key);
                }
            }
        }
        this.ModeCount++;
        this.NodeGraph.remove(key);
        return removedNode;
    }

    /**
     * Deletes the edge from the graph,
     * Note: this method should run in O(1) time.
     *
     * @param src
     * @param dest
     * @return the data of the removed edge (null if none).
     */
    @Override
    public edge_data removeEdge(int src, int dest) {
        edge_data removedEdge = getEdge(src, dest);
        if (removedEdge == null)
            return null;
        this.EdgeGraph.get(src).remove(dest);
        this.ModeCount++;
        this.EdgeGraphSize--;
        return removedEdge;
    }

    /**
     * Returns the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return NodeGraph.size();
    }

    /**
     * Returns the number of edges (assume directional graph).
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int edgeSize() {
        return this.EdgeGraphSize;
    }

    /**
     * Returns the Mode Count - for testing changes in the graph.
     *
     * @return
     */
    @Override
    public int getMC() {
        return this.ModeCount;
    }

    @Override
    public String toString() {
        return "WGraph_DS [NodeGraph=" + NodeGraph.toString() + "\nEdgeGraph=" + EdgeGraph.toString() + "\nModeCount=" + ModeCount
                + "\nEdgeGraphSize=" + EdgeGraphSize + "\nNodeGraphSize=" + NodeGraph.size() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DWGraph_DS)) return false;
        DWGraph_DS that = (DWGraph_DS) o;
        return ModeCount == that.ModeCount &&
                EdgeGraphSize == that.EdgeGraphSize &&
                Objects.equals(getNodeGraph(), that.getNodeGraph()) &&
                Objects.equals(getEdgeGraph(), that.getEdgeGraph());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNodeGraph(), getEdgeGraph(), ModeCount, EdgeGraphSize);
    }
}
