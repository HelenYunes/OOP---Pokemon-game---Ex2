package api;

import com.google.gson.*;
import com.google.gson.Gson;

import java.io.*;
import java.util.*;

/**
 * This interface represents a Directed (positive) Weighted Graph Theory Algorithms including:
 * 0. clone(); (copy)
 * 1. init(graph);
 * 2. isConnected(); // strongly (all ordered pais connected)
 * 3. double shortestPathDist(int src, int dest);
 * 4. List<node_data> shortestPath(int src, int dest);
 * 5. Save(file); // JSON file
 * 6. Load(file); // JSON file
 *
 * @author boaz.benmoshe
 */
public class DWGraph_Algo implements dw_graph_algorithms, Serializable {
    private directed_weighted_graph Graph;

    public DWGraph_Algo() {
        this.Graph = new DWGraph_DS();
    }


    /**
     * Init the graph on which this set of algorithms operates on.
     *
     * @param g
     */
    @Override
    public void init(directed_weighted_graph g) {
        this.Graph = g;
    }

    /**
     * Return the underlying graph of which this class works.
     *
     * @return
     */
    @Override
    public directed_weighted_graph getGraph() {
        return this.Graph;
    }

    /**
     * Compute a deep copy of this weighted graph.
     *
     * @return
     */
    @Override
    public directed_weighted_graph copy() {
        directed_weighted_graph copyGraph = new DWGraph_DS();
        for (node_data node : Graph.getV()) {

            node_data copyNode = Graph.getNode(node.getKey());
            copyGraph.addNode(new NodeData(copyNode));

        }
        for (node_data node : Graph.getV()) {
            for (edge_data neighbor : Graph.getE(node.getKey())) {
                copyGraph.connect(neighbor.getSrc(), neighbor.getDest(), Graph.getEdge(neighbor.getSrc(), neighbor.getDest()).getWeight());
            }
        }
        return copyGraph;
    }

    /**
     * Returns true if and only if (iff) there is a valid path from each node to each
     * other node. NOTE: assume directional graph (all n*(n-1) ordered pairs).
     *
     * @return
     */
    @Override
    public boolean isConnected() {
        if (this.Graph.nodeSize() == 1 || Graph.nodeSize() == 0)
            return true;
        Collection<node_data> nodesOfGraph = Graph.getV();
        for (node_data node : nodesOfGraph) {
            resetData();
            if (Graph.nodeSize() != numberOfConnectedNodes(this.Graph, node))
                return false;

        }
        return true;

    }

    private int numberOfConnectedNodes(directed_weighted_graph G, node_data node) {
        if (node.getTag() == 1) return 0;
        node.setTag(1);
        int counter = 1;
        Collection<edge_data> neighbors = G.getE(node.getKey());

        for (edge_data edge : neighbors) {
            counter += numberOfConnectedNodes(G, G.getNode(edge.getDest()));
        }
        return counter;
    }

    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (src == dest)
            return 0;

        dijkstra(src);
        double distance = Graph.getNode(dest).getWeight();
        if (distance == Integer.MAX_VALUE)
            return -1;
        return distance;
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {

        LinkedList<node_data> path = new LinkedList<>();
        if (src == dest) {
            path.add(Graph.getNode(dest));
            return path;
        }
        dijkstra(src);
        node_data node = Graph.getNode(dest);
        path.addFirst(node);
        while (node.getKey() != src) {
            if ((node.getInfo().equals("") || node.getWeight() == Double.MAX_VALUE))
                return null;
            int parent = Integer.parseInt(node.getInfo());
            node = Graph.getNode(parent);
            path.addFirst(node);
        }
        if (!(path.contains(Graph.getNode(src)))) {
            return null;
        }
        return path;
    }


    private void dijkstra(int src) {
        resetData();
        PriorityQueue<node_data> queue = new PriorityQueue<>(Graph.getV());
        Graph.getNode(src).setWeight(0);
        queue.add(Graph.getNode(src));
        while (!queue.isEmpty()) {
            node_data firstNode = queue.poll();
            Collection<edge_data> neighbors = Graph.getE(firstNode.getKey());
            for (edge_data edge : neighbors) {
                double firstNodeWeight = firstNode.getWeight();
                node_data destinationNode = Graph.getNode(edge.getDest());
                double edgeWeight = edge.getWeight();
                if (destinationNode.getTag() != 1) {
                    if (edgeWeight + firstNodeWeight < Graph.getNode(edge.getDest()).getWeight()) {
                        queue.remove(destinationNode);
                        destinationNode.setWeight(edgeWeight + firstNodeWeight);
                        destinationNode.setInfo("" + firstNode.getKey());
                        queue.add(destinationNode);
                    }
                }
            }
        }
    }

    /**
     * A private function to reset the data of the nodes
     */
    private void resetData() {
        for (node_data node : Graph.getV()) {
            node.setWeight(Integer.MAX_VALUE);
            //  node.setInfo("0");
            node.setTag(0);

        }
    }

    /**
     * Saves this weighted (directed) graph to the given
     * file name - in JSON format
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */

    @Override
    public boolean save(String file) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
        JsonObject jsonObject = new JsonObject();
        jsonObject = convertGraphToJson(this.Graph, new JsonObject());
        try {
            PrintWriter pw = new PrintWriter(new File(file));
            pw.write(gson.toJson(jsonObject));
            pw.close();
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name of JSON file
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        directed_weighted_graph graph = new DWGraph_DS();
        try {
            JsonObject json = new JsonParser().parse(new FileReader(file)).getAsJsonObject();
            this.Graph = convertJsonToGraph(json, graph);
            return true;
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            return false;
        }
    }


        public directed_weighted_graph convertJsonToGraph(JsonObject jsonObject, directed_weighted_graph graph) {
            graph= new DWGraph_DS();
            JsonArray nodes = jsonObject.get("Nodes").getAsJsonArray();
            JsonArray edges = jsonObject.get("Edges").getAsJsonArray();
            for (int i = 0; i < nodes.size(); i++) {
                JsonObject node = nodes.get(i).getAsJsonObject();
                graph.addNode(new NodeData(node.get("id").getAsInt()));
                String s[] = node.get("pos").getAsString().split(",");
                graph.getNode(node.get("id").getAsInt()).setLocation(new GeoLocation(Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2])));
            }
            for (int i = 0; i < edges.size(); i++) {
                JsonObject edge = edges.get(i).getAsJsonObject();
                graph.connect(edge.get("src").getAsInt(), edge.get("dest").getAsInt(), edge.get("w").getAsDouble());
            }
            return graph;
        }

        public JsonObject convertGraphToJson(directed_weighted_graph graph, JsonObject jsonObject) {
            JsonArray edges = new JsonArray();
            JsonArray nodes = new JsonArray();
            for (node_data node : graph.getV()) {
                JsonObject thisFile = new JsonObject();
                thisFile.addProperty("pos", "" + node.getLocation().x() + "," + node.getLocation().y() + "," + node.getLocation().z());
                thisFile.addProperty("id", node.getKey());
                nodes.add(thisFile);
                for (edge_data edge : graph.getE(node.getKey())) {
                    JsonObject json = new JsonObject();
                    json.addProperty("src", edge.getSrc());
                    json.addProperty("dest", edge.getDest());
                    json.addProperty("w", edge.getWeight());
                    edges.add(json);
                }
            }
            jsonObject.add("Edges", edges);
            jsonObject.add("Nodes", nodes);
            return jsonObject;
        }


    @Override
    public String toString() {
        return "DWGraph_Algo1 [{" +
                "Graph=" + Graph.toString() + "}]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWGraph_Algo that = (DWGraph_Algo) o;
        return Objects.equals(getGraph(), that.getGraph());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGraph());
    }
}
