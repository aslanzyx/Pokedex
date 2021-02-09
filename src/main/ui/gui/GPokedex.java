package ui.gui;

import model.PokemonToView;
import model.Stat;
import model.Stats;
import persistence.Crawler;
import persistence.Reader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

// Pokedex GUI
public class GPokedex extends GTemplate {

    PokemonToView current;
    List<PokemonToView> history;

    JButton back;
    JButton search;

    JPanel historyArea;
    JPanel searchArea;
    JPanel displayArea;

    JButton clear;
    JButton[] pokemons;
    JTextField input;
    JLabel message;

    JLabel id;
    JLabel name;
    JLabel type;
    JLabel ability;
    JTextPane speciesStrength;

    public GPokedex() {
        super("Pokedex");
    }

    // MODIFIES: this
    // EFFECT: initialize fields
    @Override
    protected void initializeFields() {
        current = null;
        history = new LinkedList<>();
        back = new JButton("Back");
        search = new JButton("Search");
        historyArea = new JPanel();
        searchArea = new JPanel();
        displayArea = new JPanel();

        clear = new JButton("Clear history");
        pokemons = new JButton[10];
        input = new JFormattedTextField();
        message = new JLabel("input pokemon name to search");

        id = new JLabel();
        name = new JLabel();
        type = new JLabel();
        ability = new JLabel();
        speciesStrength = new JTextPane();
    }

    // MODIFIES: this
    // EFFECT: initialize graphics
    @Override
    protected void initializeGraphics() {
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultLookAndFeelDecorated(true);
        setLayout(new GridLayout(1, 2));

        setUpHistoryArea();
        add(historyArea);
        setUpDisplayArea();
        setUpSearchArea();
        add(searchArea);


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECT: initialize display area
    private void setUpDisplayArea() {
        displayArea.setLayout(new GridLayout(4, 1));
        displayArea.add(id);
        displayArea.add(name);
        displayArea.add(type);
        displayArea.add(ability);
    }

    // MODIFIES: this
    // EFFECT: initialize history area
    private void setUpHistoryArea() {
        historyArea.add(clear);
        historyArea.setLayout(new GridLayout(11, 1));
        for (int i = 0; i < 10; i++) {
            pokemons[i] = new JButton();
            historyArea.add(pokemons[i]);
        }
    }

    // MODIFIES: this
    // EFFECT: initialize search area
    private void setUpSearchArea() {
        searchArea.setLayout(new GridLayout(3, 1));
        searchArea.add(displayArea);
        searchArea.add(speciesStrength);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        panel.add(message);
        panel.add(input);
        panel.add(back);
        panel.add(search);
        searchArea.add(panel);
    }

    // MODIFIES: this
    // EFFECT: initialize actions
    @Override
    protected void initializeAction() {
        back.addActionListener(e -> {
            close();
            new GHome();
        });

        input.addActionListener(e -> searchPokemon(input.getText()));

        search.addActionListener(e -> searchPokemon(input.getText()));

        clear.addActionListener(e -> {
            for (int i = 0; i < 10; i++) {
                pokemons[i].setText("");
                for (ActionListener a : pokemons[i].getActionListeners()) {
                    pokemons[i].removeActionListener(a);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECT: search pokemon and display it on the display area
    private void searchPokemon(String name) {
        if (current != null) {
            addToHistory(current);
        }
        try {
            message.setText("Searching...");
            current = Reader.readPokemon(name);
            message.setText("Pokemon " + name + " Found");
            displayPokemon(current);
        } catch (IOException e) {
            try {
                message.setText("Searching Online...");
                current = Crawler.loadPokemon(name);
                message.setText("Pokemon " + name + " Found");
                displayPokemon(current);
            } catch (IOException ex) {
                message.setText("Please Input an existing Pokemon name");
                current = null;
            }
        }
    }

    // MODIFIES: this
    // EFFECT: add a pokemon to history area
    private void addToHistory(PokemonToView pkm) {
        history.remove(pkm);
        if (history.size() > 9) {
            history.remove(0);
        }
        history.add(pkm);
        for (int i = 0; i < history.size(); i++) {
            pokemons[i].setText(history.get(i).getName());
//            pokemons[i].setIcon(Crawler.loadImage(pkm));
            int finalI = i;
            pokemons[i].addActionListener(e -> displayPokemon(history.get(finalI)));
            historyArea.add(pokemons[i]);
        }
    }

    // MODIFIES: this
    // EFFECT: display pokemon
    private void displayPokemon(PokemonToView pkm) {
        id.setText("Id: " + pkm.getId());
        name.setText("Name: " + pkm.getName());
        type.setText("Type: " + pkm.getType());
        ability.setText("Ability: " + pkm.getAbility());
        String ss = "";
        int i = 0;
        for (Stat s : pkm.getStats()) {
            ss = ss.concat("    " + Stats.STATS_ORDER[i++] + ": " + s.getSS() + "\n");
        }
        speciesStrength.setText("Species Strength: \n\n" + ss);
    }
}
