package gameClient.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MyFrame extends JFrame {
    private static MainPanel mainPanel;
    private static loginPanel loginPanel;
    private static JPanel topPage;
    private static JSplitPane panels;

    public MyFrame() throws IOException {
        super("Catch The Pokemons");
        this.setSize(1000,800);
        init();
    }
    public MyFrame(int id ,int level) throws IOException {
        super("Catch The Pokemons");
        this.setSize(1000,800);
        init();
        startGame(id,level);
    }
    public static void startGame(int id, int level){
        mainPanel.startGame(id, level);
    }
    private void init() throws IOException {
        JLabel title = new JLabel("Catch The Pokemons");
        topPage = new JPanel();
        topPage.setBackground(Color.BLUE);
        topPage.setPreferredSize(new Dimension(this.getWidth(), 30));
        topPage.setLayout(new BorderLayout());
        title.setForeground(Color.white);
        title.setFont(new Font("Consolas", 2, 15));
        topPage.add(title, BorderLayout.WEST);
        getContentPane().add(topPage, BorderLayout.PAGE_END);
        Image icon =ImageIO.read( new File("src/gameClient/GUI/Images/Pikachu.png"));
        //Image icon = Toolkit.getDefaultToolkit().getImage(("src/gameClient/Images/Pikachu.png"));
        setIconImage(icon);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        loginPanel = new loginPanel();
        mainPanel = new MainPanel(this);

        this.loginPanel.setPreferredSize(new Dimension(this.getWidth(),100));
        setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
        panels = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainPanel, loginPanel);
        getContentPane().add(panels, BorderLayout.CENTER);
        panels.setBackground(Color.red);
        panels.setBorder( BorderFactory.createLineBorder(Color.RED,10,true));
        setVisible(true);
        this.addLoginPanel();
        pack();
    }
    private void addLoginPanel() {
        this.getContentPane().add(loginPanel, BorderLayout.PAGE_START);
    }

}
