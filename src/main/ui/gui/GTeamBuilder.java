package ui.gui;

import exceptions.StatInvalidException;
import exceptions.TeamFullException;
import model.*;
import persistence.Crawler;
import persistence.Reader;
import persistence.Saver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

// Team builder GUI
public class GTeamBuilder extends GTemplate {
    private static final int MAX_LOAD = 10;

    JToolBar menu;
    JPanel teamView;
    JPanel pokemonEditArea;
    JButton back;

    Team current;
    PokemonToEdit editting;
    JButton[] teams;

    JButton open;
    JButton add;
    JButton addPokemon;
    JButton removePokemon;

    JButton[] pkms;
    JLabel[] props;
    JLabel[] ss;
    JTextField[] iv;
    JTextField[] bs;

    JTextField level;
    JLabel bsLeft;
    JLabel message;
    JButton save;

    public GTeamBuilder() {
        super("TeamBuilder");
    }

    String[] text;


    // MODIFIES: this
    // EFFECT: initialize fields
    @Override
    protected void initializeFields() {
        back = new JButton("Back");
        menu = new JToolBar();
        teamView = new JPanel();
        pokemonEditArea = new JPanel();
        teams = new JButton[MAX_LOAD];
        open = new JButton("Open");
        add = new JButton("New");
        removePokemon = new JButton("Remove pokemon");
        addPokemon = new JButton("Add pokemon");
        save = new JButton("Save");
        current = null;
        pkms = new JButton[6];
        level = new JFormattedTextField();
        props = new JLabel[6];
        ss = new JLabel[6];
        bs = new JTextField[6];
        iv = new JTextField[6];
        bsLeft = new JLabel();
        message = new JLabel("Load a team to start editing");
        text = new String[]{
                "Hit Point: ", "Attack: ", "Spec Attack: ", "Defence: ", "Special: ", "Speed: "
        };
    }

    // MODIFIES: this
    // EFFECT: initialize graphics
    @Override
    protected void initializeGraphics() {
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultLookAndFeelDecorated(true);

        setUpMenu();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        panel.add(teamView);
        panel.add(pokemonEditArea);
        setUpTeamView();
        setUpPokemonEditArea();
        add(panel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECT: initialize menu bar
    private void setUpMenu() {
        menu.setLayout(new GridLayout(1, 5));
        menu.add(back);
        menu.add(open);
        menu.add(add);
        menu.add(addPokemon);
        menu.add(removePokemon);
        menu.add(save);
    }

    // MODIFIES: this
    // EFFECT: initialize fields
    private void setUpTeamView() {
        teamView.setAlignmentY(40);
        teamView.setLayout(new GridLayout(2, 3));
        for (int i = 0; i < 6; i++) {
            pkms[i] = new JButton();
            teamView.add(pkms[i]);
        }
    }

    // MODIFIES: this
    // EFFECT: initialize editting area
    private void setUpPokemonEditArea() {
        pokemonEditArea.setLayout(new GridLayout(9, 1));
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(new JLabel("Level: "));
        panel.add(level);
        pokemonEditArea.add(panel);

        for (int i = 0; i < 6; i++) {
            setPropertyPanels(i);
        }
        pokemonEditArea.add(message);
        pokemonEditArea.add(menu);
    }

    // MODIFIES: this
    // EFFECT: initialize property panel
    private void setPropertyPanels(int i) {
        props[i] = new JLabel(text[i]);
        bs[i] = new JFormattedTextField();
        iv[i] = new JFormattedTextField();
        ss[i] = new JLabel();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3));
        panel.add(props[i]);
        panel.add(ss[i]);
        panel.add(iv[i]);
        panel.add(bs[i]);
        pokemonEditArea.add(panel);
    }

    // MODIFIES: this
    // EFFECT: initialize action
    @Override
    protected void initializeAction() {
        open.addActionListener(open());

        add.addActionListener(addNewTeam());

        addPokemon.addActionListener(addPokemon());

        save.addActionListener(save());

        level.addActionListener(e -> {
            try {
                editting.setLevel(Integer.parseInt(level.getText()));
            } catch (StatInvalidException ex) {
                message.setText("Invalid level");
            }
            displayPokemon(editting);
        });

        removePokemon.addActionListener(removePokemon());

        back.addActionListener(back());

        for (int i = 0; i < 6; i++) {
            iv[i].addActionListener(setUpIV(i));
            bs[i].addActionListener(setUpBS(i));
        }

    }

    // MODIFIES: this
    // EFFECT: save action
    private ActionListener save() {
        return e -> Saver.saveTeam(current);
    }

    // MODIFIES: this
    // EFFECT: initialize set up iv action
    private ActionListener setUpIV(int i) {
        return e -> {
            int value = Integer.parseInt(iv[i].getText());
            try {
                editting.setIV(i, value);
                message.setText("Individual Value of " + Stats.STATS_ORDER[i] + " is set to " + value);
            } catch (StatInvalidException ex) {
                message.setText("Invalid value!");
            }
        };
    }

    // MODIFIES: this
    // EFFECT: set up bs action
    private ActionListener setUpBS(int i) {
        return e -> {
            int value = Integer.parseInt(bs[i].getText());
            try {
                editting.setBS(i, value);
                message.setText("Base Stat of " + Stats.STATS_ORDER[i] + " is set to " + value);
            } catch (StatInvalidException ex) {
                message.setText("Invalid value!");
            }
        };
    }

    // MODIFIES: this
    // EFFECT: open team file
    private ActionListener open() {
        return e -> {
            JFileChooser chooser = new JFileChooser(new File("data"));
            chooser.showOpenDialog(null);
            File file = chooser.getSelectedFile();
            if (file != null) {
                message.setText("Reading " + file.getName());
                try {
                    current = Reader.readTeam(file.getName().split("\\.")[0]);
                    displayTeam();
                } catch (IOException ex) {
                    message.setText("Reading Error");
                } catch (TeamFullException ex) {
                    message.setText("Team full");
                } catch (StatInvalidException ex) {
                    message.setText("Invalid Stat found in file");
                }
            }

        };
    }

    // MODIFIES: this
    // EFFECT: add team
    private ActionListener addNewTeam() {
        return e -> {
            String name = JOptionPane.showInputDialog(this, "Input team name");
            current = new Team(name);
            Saver.saveTeam(current);
            message.setText("Team " + name + " created");
            displayTeam();
        };
    }

    // MODIFIES: this
    // EFFECT: add pokemon to team
    private ActionListener addPokemon() {
        return e -> {
            String name = JOptionPane.showInputDialog(this, "Input pokemon name");
            try {
                message.setText("Searching locally");
                addToTeam(Reader.readPokemon(name));
            } catch (IOException ex) {
                try {
                    message.setText("Searching online");
                    addToTeam(Crawler.loadPokemon(name));
                } catch (IOException exc) {
                    message.setText("No such pokemon found!");
                }

            }

        };
    }

    // MODIFIES: this
    // EFFECT: remove  pokemon to team
    private ActionListener removePokemon() {
        return e -> {
            removeFromTeam(editting);
            editting = null;
        };
    }

    // MODIFIES: this
    // EFFECT: go back
    private ActionListener back() {
        return e -> {
            setDefaultLookAndFeelDecorated(false);
            dispose();
            new GHome();
        };
    }

    // MODIFIES: this
    // EFFECT: add a pokemon to team
    private void addToTeam(PokemonToEdit pkm) {
        if (current == null) {
            message.setText("Please load a team first");
        } else {
            try {
                current.add(pkm);
            } catch (TeamFullException e) {
                message.setText("Team is full");
            }
            displayTeam();
            message.setText(pkm.getName() + " has been added");
        }
    }

    // MODIFIES: this
    // EFFECT: remove a pokemon from team
    private void removeFromTeam(PokemonToView pkm) {
        if (current == null) {
            message.setText("Please load a team first");
        } else {
            current.remove(pkm.getName());
            displayTeam();
            message.setText(pkm.getName() + " has been removed");
        }
    }

    // MODIFIES: this
    // EFFECT: display team
    private void displayTeam() {
        for (int i = 0; i < 6; i++) {
            pkms[i].setText("");
            for (ActionListener a : pkms[i].getActionListeners()) {
                pkms[i].removeActionListener(a);
            }
            if (i < current.count()) {
                PokemonToEdit pkm = current.index(i);
                pkms[i].setText(pkm.getName());
                pkms[i].addActionListener(e -> {
                    editting = pkm;
                    displayPokemon(pkm);
                });
            }

        }
    }

    // MODIFIES: this
    // EFFECT: display pokemon
    private void displayPokemon(PokemonToEdit pkm) {
        level.setText(pkm.getLevel() + "");
        Stats stats = pkm.getStats();
        int i = 0;
        for (Stat s : pkm.getStats()) {
            props[i].setText(Stats.STATS_ORDER[i] + ": " + stats.get(i).calculate(pkm.getLevel()));
            ss[i].setText(s.getSS() + "");
            bs[i].setText(s.getBS() + "");
            iv[i].setText(s.getIV() + "");
            i++;
        }
    }
}
