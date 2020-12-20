package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gameClient.GUI.MainPanel;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.*;
/**
 *This class is responsible for how the agents move in the game 
 */
public class GameStrategy implements Runnable {
    public static game_service _game;
    private static Arena _ar;
    private MainPanel mainPanel;
    public static Thread client;
    private static int id, level;

    /**
     * This function causes the game to start
     *
     * @param panel
     * @param id
     * @param level
     */
    public void startGame(MainPanel panel, int id, int level) {
        mainPanel = panel;
        _game = Game_Server_Ex2.getServer(level);
        //game.login(id);
        client = new Thread(this);
        client.start();
    }

    /**
     * This function updates all the information about the game
     */
    @Override
    public void run() {
        DWGraph_Algo g = new DWGraph_Algo();
        directed_weighted_graph GameGraph = g.getGraph();
        GameGraph = g.convertJsonToGraph(JsonParser.parseString(_game.getGraph()).getAsJsonObject(), GameGraph);
        updateAgents();
        mainPanel.update(_ar);
        //  _game = Game_Server_Ex2.getServer(level);
        System.out.println(_game.getAgents());
        _game.startGame();
        Date time = new Date();
        time.setTime(_game.timeToEnd());
        while (_game.isRunning()) {
            int dt = 95;
            if (this.distanceFromPikachu()) {
                dt = 130;
            }
            time.setTime(_game.timeToEnd());
            _ar.setTime(time);
            moveAgents(_game, GameGraph);
            try {
                mainPanel.repaint();
                Thread.sleep(dt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(_game.toString());
    }

    /**
     * This function checks whether the agent is close to the Pokemon (Pikachu)
     *
     * @return is close to the Pikachu
     */
    private boolean distanceFromPikachu() {
        for (Agent agent : _ar.JsonToAgents()) {
            for (Pokemon pokemon : _ar.getPokemons())
                if (agent.get_curr_edge() == pokemon.get_edge())
                    return true;
        }
        return false;
    }

    /**
     * This function causes agents to move strategically
     *
     * @param game
     * @param graph
     */
    private void moveAgents(game_service game, directed_weighted_graph graph) {
        String move = game.move();
        _ar.update(move);
        List<Agent> agentList = _ar.getAgents();
        HashSet<Pokemon> pokemonList = Arena.json2Pokemons(game.getPokemons());
        pokemonList.forEach(pokemon -> Arena.updateEdge(pokemon, graph));
        _ar.setPokemons(pokemonList);
        for (int i = 0; i < agentList.size(); i++) {
            Agent agent = agentList.get(i);
            int agentID = agent.getID();
            int dest = agent.getNextNode();
            if (dest == -1) {
                nextMove();
                _game.chooseNextEdge(agent.getID(), dest);
            }
            _game.chooseNextEdge(agent.getID(), dest);
            double value = agent.getValue();
            int src = agent.getSrcNode();

            //  System.out.println("Agent: " + agentID + ", val: " + value + "   turned to node: " + dest);
        }
    }

    /**
     * This function updates the information about the agents
     */
    private void updateAgents() {
        JsonObject j;
        JSONObject line;
        this._ar = new Arena();
        DWGraph_Algo g = new DWGraph_Algo();
        directed_weighted_graph graph = g.getGraph();
        j = JsonParser.parseString(_game.getGraph()).getAsJsonObject();
        graph = g.convertJsonToGraph(j, graph);
        this._ar.setGraph(graph);
        this._ar.setPokemons(this._game.getPokemons());
        this._ar.getAlgo().init(graph);
        try {
            System.out.println(_game.toString());
            line = new JSONObject(_game.toString());
            JSONObject ttt = line.getJSONObject("GameServer");
            int agents = ttt.getInt("agents");
            for (int a = 0; a < agents; a++) {
                int i;
                Pokemon c = _ar.getPokemons().get(a % _ar.getPokemons().size());
                edge_data e = c.get_edge();
                if (c.getType() == -1) {
                    i = Math.max(e.getSrc(), e.getDest());
                } else {
                    i = Math.min(e.getSrc(), e.getDest());
                }
                _game.addAgent(i);
            }
            _ar.setAgents(Arena.getAgents(_game.getAgents(), graph));
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * This function returns the highest value Pokemon
     *
     * @param source
     * @return highest Value of the Pokemon
     */
    private static Pokemon highestValuePokemon(int source) {
        double highestValueMoveScore = -Integer.MAX_VALUE;
        Pokemon pokemonToCatch = null;
        List<Pokemon> pokemonList = _ar.getPokemons();
        for (int i = 0; i < pokemonList.size(); i++) {

            double distance = _ar.getAlgo().shortestPathDist(source, pokemonList.get(i).get_edge().getSrc());
            double currentPokemonScore = pokemonList.get(i).getValue() - distance;
            if (highestValueMoveScore < currentPokemonScore) {
                highestValueMoveScore = currentPokemonScore;
                pokemonToCatch = pokemonList.get(i);
            }
        }
        Arena.updateEdge(pokemonToCatch, _ar.getGraph());
        return pokemonToCatch;
    }

    /**
     * This function is responsible for the next step in the game using the shortest distance
     */
    private static void nextMove() {
        int i = 0;
        double weight = -Integer.MAX_VALUE;
        List<Pokemon> pokemonList = _ar.getPokemons();
        List<Agent> agentList = _ar.getAgents();
        List<node_data> shortestPath;
        shortestPathHelper newpath = null;
        Set<Integer> agentPath = new HashSet<>();
        Set<Integer> pokemonPath = new HashSet<>();
        Pokemon pokemonToCatch = pokemonList.get(0);
        PriorityQueue<shortestPathHelper> pathHelpers = new PriorityQueue<>(new CompareToEdge());

        for (Agent agent : agentList) {
            pokemonToCatch = highestValuePokemon(agent.getSrcNode());
            weight = pokemonToCatch.get_edge().getWeight();
            if (_ar.getAlgo().shortestPath(agent.getSrcNode(), pokemonToCatch.get_edge().getSrc()) == null) {
                pokemonToCatch = pokemonList.get(i);
                weight = pokemonToCatch.get_edge().getWeight();
            }
            shortestPath = _ar.getAlgo().shortestPath(agent.getSrcNode(), pokemonToCatch.get_edge().getSrc());
            shortestPath.add(_ar.getGraph().getNode(pokemonToCatch.get_edge().getDest()));
            pathHelpers.add(new shortestPathHelper(shortestPath, pokemonToCatch.get_id(), agent.getID(), pokemonToCatch.get_edge().getSrc(), weight));
            i++;
        }


        while (!pathHelpers.isEmpty() && (agentPath.size() < agentList.size() && pokemonPath.size() < pokemonList.size())) {
            newpath = pathHelpers.poll();
            synchronized (pathHelpers) {
                while (!pathHelpers.isEmpty() && (agentPath.contains(newpath.getSrc()) || pokemonPath.contains(newpath.getPokemon()) || newpath.getWeight() == Integer.MAX_VALUE)) {
                    newpath = pathHelpers.poll();
                }
            }

            pokemonPath.add(newpath.getPokemon());
            agentPath.add(newpath.getSrc());
            _ar.setPathAgent(newpath.getShortestPath(), newpath.getSrc());

        }
    }

    private static class CompareToEdge implements Comparator<edge_data> {
        @Override
        public int compare(edge_data o, edge_data that) {
            if (o.getWeight() == that.getWeight())
                return 0;
            else if (o.getWeight() < that.getWeight())
                return -1;
            return 1;
        }
    }

    private static class shortestPathHelper extends EdgeData {
        private double weight;
        private Integer pokemon;
        private List<node_data> shortestPath;

        public shortestPathHelper(List<node_data> path, int pokemon, int src, int dest, double w) {
            super(src, dest, w);
            this.shortestPath = path;
            this.pokemon = pokemon;
            //this.weight=w;
        }

        public double getWeight() {
            return this.weight;
        }

        public List<node_data> getShortestPath() {
            return this.shortestPath;
        }

        public Integer getPokemon() {
            return this.pokemon;
        }

    }
}
