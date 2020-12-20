package gameClient;

import api.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;


import java.util.*;
import java.util.stream.Collectors;

public class Arena {
    public static final double EPS1 = 0.001, EPS2 = EPS1 * EPS1;
    private List<String> _info;
    private dw_graph_algorithms _algo;
    private directed_weighted_graph _gg;
    private HashMap<Integer, Agent> _agents;
    private List<Agent> _agents1 = new ArrayList<>();
    private HashSet<Pokemon> _pokemons;
    private List<Pokemon> _pokemons1;
    private static Point3D MIN = new Point3D(0, 100, 0);
    private static Point3D MAX = new Point3D(0, 100, 0);
    private String time = "";
    HashMap<Integer, Pokemon> map = new HashMap<>();

    public Arena() {
        this._agents1 = new ArrayList<>();
        this._pokemons1 = new LinkedList<>();
        this._info = new ArrayList<String>();
        this._algo = new DWGraph_Algo();
        this._agents = new HashMap<>();
        this._gg = new DWGraph_DS();
        this._pokemons = new HashSet<>();
    }


    //
    public static void updateEdge(Pokemon pokemon, directed_weighted_graph graph) {
        //	oop_edge_data ans = null;
        Iterator<node_data> itr = graph.getV().iterator();
        while (itr.hasNext()) {
            node_data v = itr.next();
            Iterator<edge_data> iter = graph.getE(v.getKey()).iterator();
            while (iter.hasNext()) {
                edge_data edgeData = iter.next();
                boolean f = isOnEdge(pokemon.getLocation(), edgeData, pokemon.getType(), graph);
                if (f) {
                    pokemon.set_edge(edgeData);
                }
            }
        }
    }

    public void update(String json) {
        JSONObject line;
        try {

            line = new JSONObject(json);
            JSONArray agents = line.getJSONArray("Agents");
            for (int i = 0; i < agents.length(); i++) {
                JsonObject agent = JsonParser.parseString(agents.get(i).toString()).getAsJsonObject();
                _agents.get(agent.get("Agent").getAsJsonObject().get("id").getAsInt()).updateAgents(agent.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static boolean isOnEdge(geo_location geoLocation, geo_location src, geo_location dest) {

        boolean ans = false;
        double dist = src.distance(dest);
        double d1 = src.distance(geoLocation) + geoLocation.distance(dest);
        if (dist > d1 - EPS2) {
            ans = true;
        }
        return ans;
    }

    private static boolean isOnEdge(geo_location p, int s, int d, directed_weighted_graph graph) {
        geo_location src = graph.getNode(s).getLocation();
        geo_location dest = graph.getNode(d).getLocation();
        return isOnEdge(p, src, dest);
    }

    private static boolean isOnEdge(geo_location geoLocation, edge_data edgeData, int type, directed_weighted_graph graph) {
        int src = graph.getNode(edgeData.getSrc()).getKey();
        int dest = graph.getNode(edgeData.getDest()).getKey();
        if (type < 0 && dest > src) {
            return false;
        }
        if (type > 0 && src > dest) {
            return false;
        }
        return isOnEdge(geoLocation, src, dest, graph);
    }

    public static HashSet<Pokemon> json2Pokemons(String fs) {
        HashSet<Pokemon> ans = new HashSet<>();
        JsonArray allPokemons = JsonParser.parseString(fs).getAsJsonObject().getAsJsonArray("Pokemons");
        for (int i = 0; i < allPokemons.size(); i++) {
            JsonObject pp = allPokemons.get(i).getAsJsonObject();
            JsonObject pk = pp.get("Pokemon").getAsJsonObject();
            int t = pk.get("type").getAsInt();
            double v = pk.get("value").getAsDouble();
            String p = pk.get("pos").getAsString();
            GeoLocation g = new GeoLocation(p);
            Pokemon f = new Pokemon(g, i, v, t, null);
            ans.add(f);
        }
        return ans;
    }

    public static List<Agent> getAgents(String aa, directed_weighted_graph gg) {
        ArrayList<Agent> ans = new ArrayList<Agent>();
        try {
            JSONObject ttt = new JSONObject(aa);
            JSONArray ags = ttt.getJSONArray("Agents");
            for (int i = 0; i < ags.length(); i++) {
                Agent c = new Agent(gg, 0);
                c.updateAgents(ags.get(i).toString());
                ans.add(c);
            }
            //= getJSONArray("Agents");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ans;
    }

    private static Range2D GraphRange(directed_weighted_graph g) {
        Iterator<node_data> itr = g.getV().iterator();
        double x0 = 0, x1 = 0, y0 = 0, y1 = 0;
        boolean first = true;
        while (itr.hasNext()) {
            geo_location p = itr.next().getLocation();
            if (first) {
                x0 = p.x();
                x1 = x0;
                y0 = p.y();
                y1 = y0;
                first = false;
            } else {
                if (p.x() < x0) {
                    x0 = p.x();
                }
                if (p.x() > x1) {
                    x1 = p.x();
                }
                if (p.y() < y0) {
                    y0 = p.y();
                }
                if (p.y() > y1) {
                    y1 = p.y();
                }
            }
        }
        Range xr = new Range(x0, x1);
        Range yr = new Range(y0, y1);
        return new Range2D(xr, yr);
    }

    public static Range2Range w2f(directed_weighted_graph g, Range2D frame) {
        Range2D world = GraphRange(g);
        Range2Range ans = new Range2Range(world, frame);
        return ans;
    }

    private void init() {
        MIN = null;
        MAX = null;
        double x0 = 0, x1 = 0, y0 = 0, y1 = 0;
        Iterator<node_data> iter = _gg.getV().iterator();
        while (iter.hasNext()) {
            geo_location c = iter.next().getLocation();
            if (MIN == null) {
                x0 = c.x();
                y0 = c.y();
                x1 = x0;
                y1 = y0;
                MIN = new Point3D(x0, y0);
            }
            if (c.x() < x0) {
                x0 = c.x();
            }
            if (c.y() < y0) {
                y0 = c.y();
            }
            if (c.x() > x1) {
                x1 = c.x();
            }
            if (c.y() > y1) {
                y1 = c.y();
            }
        }
        double dx = x1 - x0, dy = y1 - y0;
        MIN = new Point3D(x0 - dx / 10, y0 - dy / 10);
        MAX = new Point3D(x1 + dx / 10, y1 + dy / 10);

    }

    public String getTime() {
        return time;
    }

    public void setGraph(directed_weighted_graph graph) {
        this._gg = graph;
    }

    public dw_graph_algorithms getAlgo() {
        return _algo;
    }

    public void setTime(Date time) {
        this.time = time.getMinutes() + ":" + time.getSeconds();
    }

    public List<Agent> JsonToAgents() {
        return _agents1;
    }

    public List<Agent> getAgents() {
        return _agents.values().stream().collect(Collectors.toUnmodifiableList());
    }

    public List<Pokemon> getPokemons() {
        return _pokemons.stream().collect(Collectors.toUnmodifiableList());
    }

    public void setPathAgent(List<node_data> path, int agent) {
        _agents.get(agent).setPath(path);
    }

    //s
    public void setPokemons(HashSet<Pokemon> pokemons) {
        _pokemons = pokemons;
        _pokemons.forEach(pokemon -> updateEdge(pokemon, this.getGraph()));
    }

    public void setPokemons(String pokemons) {
        setPokemons(json2Pokemons(pokemons));
    }

    public void setAgents(List<Agent> agents) {
        for (Agent age : agents) {
            this._agents.put(age.getID(), age);
        }
    }

    public directed_weighted_graph getGraph() {
        return _gg;
    }


}

