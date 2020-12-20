package gameClient.GUI;

import javax.swing.border.Border;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class loginPanel extends JPanel implements ActionListener {

    private static TextField fieldForId = new TextField();
    private static JComboBox<String> levels = new JComboBox<>();
    private static JButton buttonForPlayGame = new JButton();
    private JLabel idLabel = new JLabel();
    private JLabel levelLabel = new JLabel();
    private JFrame idLabels = new JFrame();
    private JFrame levelLabels = new JFrame();

    public loginPanel() {
        Border border = BorderFactory.createLineBorder(Color.ORANGE);
        Border border2 = BorderFactory.createEtchedBorder(Color.RED, Color.ORANGE);
        Border border1 = BorderFactory.createTitledBorder(border2, "Welcome to the game", TitledBorder.LEFT, 1, new Font("MV Boli", Font.BOLD, 18), Color.ORANGE);
        fieldForId = new TextField();
        fieldForId.setFont(new Font("MV Boli", Font.BOLD, 18));
        fieldForId.setPreferredSize(new Dimension(100, 28));
        fieldForId.setForeground(Color.BLUE);
        fieldForId.setBackground(Color.ORANGE);
        fieldForId.addTextListener(e -> {
            try {
                Long.parseLong(fieldForId.getText());
                buttonForPlayGame.setEnabled(true);
            } catch (Exception ex) {
                buttonForPlayGame.setEnabled(false);
            }
        });
        buttonForPlayGame.setText("Start to play");
        buttonForPlayGame.setMnemonic(KeyEvent.VK_ENTER);
        buttonForPlayGame.setEnabled(false);
        buttonForPlayGame.setVerticalAlignment(JButton.BOTTOM);
        buttonForPlayGame.setVerticalAlignment(JButton.CENTER);
        buttonForPlayGame.setHorizontalAlignment(JButton.CENTER);
        buttonForPlayGame.addActionListener(this);
        buttonForPlayGame.setPreferredSize(new Dimension(200, 33));
        buttonForPlayGame.setHorizontalTextPosition(JButton.CENTER);
        buttonForPlayGame.setVerticalTextPosition(JButton.BOTTOM);
        buttonForPlayGame.setFont(new Font("Comic Sans", Font.BOLD, 25));
        buttonForPlayGame.setForeground(Color.ORANGE);
        buttonForPlayGame.setBackground(Color.black);
        buttonForPlayGame.setBorder(BorderFactory.createEtchedBorder(Color.RED, Color.ORANGE));

        levelLabel.setFont(new java.awt.Font("MV Boli", Font.BOLD, 18)); // NOI18N
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setText("Please select level:");
        levelLabel.setBorder(border);
        levelLabels.add(levelLabel);

        levels.setPreferredSize(new Dimension(100, 28));
        levels.setBackground(Color.orange);
        levels.setForeground(Color.BLUE);
        levels.setModel(new DefaultComboBoxModel<>(new String[]{"Level 0", "Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7", "Level 8", "Level 9", "Level 10", "Level 11", "Level 12", "Level 13", "Level 14", "Level 15", "Level 16", "Level 17", "Level 18", "Level 19", "Level 20", "Level 21", "Level 22", "Level 23"}));
        levels.setToolTipText("");
        levels.addActionListener(e -> levels.getSelectedIndex());

        idLabel.setFont(new Font("MV Boli", Font.BOLD, 18)); // NOI18N
        idLabel.setForeground(Color.white);
        idLabel.setText("Please enter your ID:");
        idLabel.setBorder(border);
        idLabel.setVerticalAlignment(JLabel.CENTER);
        idLabel.setHorizontalTextPosition(JLabel.LEFT);
        idLabels.add(idLabel);

        this.setBackground(Color.blue);
        this.setBorder(border1);
        this.add(idLabel);
        this.add(fieldForId);
        this.add(levelLabel);
        this.add(levels);
        this.add(buttonForPlayGame);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonForPlayGame) {
            MyFrame.startGame(Integer.parseInt(fieldForId.getText()), (levels.getSelectedIndex()));
            System.out.println("The game has started");
        }

    }

}
