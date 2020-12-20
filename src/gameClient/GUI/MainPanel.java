package gameClient.GUI;

import javax.swing.*;
import java.awt.*;

import api.*;
import gameClient.*;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

public class MainPanel extends JPanel {

    private Arena _ar;
    private Range2Range _w2f;
    private static MyFrame myFrame;
    private static GameStrategy gameStrategy;
    double grade;
    int moves, level;

    public MainPanel(MyFrame myFrame) {
        super();
        this.myFrame = myFrame;
        this._ar = new Arena();
        this.gameStrategy = new GameStrategy();
        this.setSize(myFrame.getWidth(), myFrame.getHeight());
        updateFrame();
        this.setOpaque(true);
    }

    //
    public void update(Arena ar) {
        this._ar = ar;
        updateFrame();
    }

    //
    public void startGame(int id, int level) {
        if (gameStrategy._game != null) {
            if (gameStrategy._game.isRunning()) {
                gameStrategy._game.stopGame();
                gameStrategy.client.stop();
            }}
        gameStrategy.startGame(this, id, level);
    }

    //
    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);
        int w = this.getWidth();
        int h = this.getHeight();
        g2.clearRect(0, 0, w, h);
        g2.fillRect(0, 0, w, h);
        updateFrame();

        Dimension d = myFrame.getSize();
        Image LoginImage = Toolkit.getDefaultToolkit().getImage(("src/gameClient/GUI/Images/LoginImage.png"));
        g2.drawImage(LoginImage, 0, 0, d.width, d.height, this);
        try {
            drawInfo(g2);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        drawGraph(g2);
        drawAgants(g2);
        drawPokemons(g2);
        updateFrame();

    }

    //
    private void updateFrame() {
        Range rx = new Range(50, this.getWidth() - 50);
        Range ry = new Range(this.getHeight() - 20, 70);
        Range2D frame = new Range2D(rx, ry);
        directed_weighted_graph g = _ar.getGraph();
        _w2f = Arena.w2f(g, frame);
    }

    //
    private void drawAgants(Graphics2D g) {
        List<Agent> rs = _ar.getAgents();
        for (Agent ag : rs) {
            geo_location c = ag.getLocation();
            int r = (int) (0.02 * this.getHeight());
            if (c != null) {
                geo_location fp = this._w2f.world2frame(c);
                Image zombie = Toolkit.getDefaultToolkit().getImage(("src/gameClient/GUI/Images/zombie1.png"));
                g.drawImage(zombie, (int) fp.x() - r, (int) fp.y() - r, 3 * r, 3 * r, this);
            }
        }
    }

    //
    private void drawInfo(Graphics g) throws JSONException {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.blue);
        g2D.setFont(new Font("MV Boli", Font.BOLD, (this.getHeight() + this.getWidth()) / 80));
        int x0 = this.getWidth() / (this.getWidth() / 5);
        int y0 = this.getHeight() / 20;
        for (Agent ignored : _ar.getAgents()) {
            JSONObject data = new JSONObject(GameStrategy._game.toString());
            JSONObject dataGame = data.getJSONObject("GameServer");
            grade = dataGame.getDouble("grade");
            moves = dataGame.getInt("moves");
            level = dataGame.getInt("game_level");
        }
        g2D.drawString("Timer: " + _ar.getTime(), (int) x0 * 5, (int) y0);
        y0 = y0 + this.getHeight() / 20;
        g2D.drawString("Level: " + level, (int) x0 * 5, (int) y0);
        y0 = y0 + this.getHeight() / 20;
        g2D.drawString("Grade: " + grade, (int) x0 * 5, (int) y0);
        y0 = y0 + this.getHeight() / 20;
        g2D.drawString("Moves: " + moves, (int) x0 * 5, (int) y0);
    }

    //
    private void drawGraph(Graphics g) {
        directed_weighted_graph gg = _ar.getGraph();
        g.setColor(Color.blue);
        g.setFont(new Font("MV Boli", Font.BOLD, 200));
        for (node_data n : gg.getV()) {
            for (edge_data e : gg.getE(n.getKey())) {
                drawEdge(e, g);
            }
        }
        for (node_data n : gg.getV()) {
            drawNode(n, g);
        }
    }

    //
    private void drawPokemons(Graphics2D g) {
        List<Pokemon> fs = _ar.getPokemons().stream().collect(Collectors.toList());
        if (fs != null) {
            for (Pokemon f : fs) {
                GeoLocation c = new GeoLocation(f.getLocation().toString());
                int r = (int) (0.03 * this.getHeight());
                if (c != null) {
                    geo_location fp = this._w2f.world2frame(f.getLocation());
                    Image pikachu = Toolkit.getDefaultToolkit().getImage(("src/gameClient/GUI/Images/Pikachu.png"));
                    g.drawImage(pikachu, (int) fp.x() - r, (int) fp.y() - r, 2 * r, 2 * r, this);
                }
            }
        }
    }


    private void drawNode(node_data n, Graphics g) {
        geo_location pos = n.getLocation();
        geo_location fp = this._w2f.world2frame(pos);
        Image pokemonBall = Toolkit.getDefaultToolkit().getImage(("src/gameClient/GUI/Images/pokemonBall.png"));
        int r = (int) (0.009 * this.getHeight());
        g.drawImage(pokemonBall, (int) fp.x() - r, (int) fp.y() - r, 4 * r, 4 * r, this);
    }

    private void drawEdge(edge_data e, Graphics g) {
        directed_weighted_graph gg = _ar.getGraph();
        geo_location s = gg.getNode(e.getSrc()).getLocation();
        geo_location d = gg.getNode(e.getDest()).getLocation();
        geo_location s0 = this._w2f.world2frame(s);
        geo_location d0 = this._w2f.world2frame(d);
        g.drawLine((int) s0.x(), (int) s0.y(), (int) d0.x(), (int) d0.y());
    }


}
