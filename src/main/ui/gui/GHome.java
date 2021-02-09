package ui.gui;


import javax.swing.*;
import java.awt.*;

// Home GUI
public class GHome extends GTemplate {

    JButton pokedex;
    JButton teamBuilder;

    public GHome() {
        super("Home");
    }

    // MODIFIES: this
    // EFFECT: initialize fields
    protected void initializeFields() {
        pokedex = new JButton();
        teamBuilder = new JButton();
    }

    // MODIFIES: this
    // EFFECT: initialize graphics
    protected void initializeGraphics() {
        setLayout(new GridLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultLookAndFeelDecorated(true);
        ImageIcon icon0 = new ImageIcon("data/image/morpeko-0.png");
        ImageIcon icon1 = new ImageIcon("data/image/morpeko-1.png");

        pokedex.setBorderPainted(false);

        pokedex.setIcon(icon0);
        pokedex.setText("<html><span color=brown>Pokedex Mode</span><html>");
        pokedex.setHorizontalTextPosition(SwingConstants.CENTER);
        pokedex.setVerticalTextPosition(SwingConstants.BOTTOM);
        pokedex.setFont(new Font("Calibri", Font.BOLD, 50));
        pokedex.setBackground(new Color(0x926B42, false));
        teamBuilder.setIcon(icon1);
        teamBuilder.setText("<html><span color=#bf149f>TeamBuilder Mode</span><html>");
        teamBuilder.setVerticalTextPosition(SwingConstants.BOTTOM);
        teamBuilder.setHorizontalTextPosition(SwingConstants.CENTER);
        teamBuilder.setFont(new Font("Calibri", Font.BOLD, 50));
        teamBuilder.setBackground(new Color(0x4C494C, false));
        add(pokedex);
        add(teamBuilder);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECT: initialize actions
    protected void initializeAction() {
        pokedex.addActionListener(e -> {
            close();
            new GPokedex();
        });


        teamBuilder.addActionListener(e -> {
            close();
            new GTeamBuilder();
        });
    }
}
