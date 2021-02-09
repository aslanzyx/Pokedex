package ui.tools;

import exceptions.TeamFullException;
import model.PokemonToEdit;
import model.Team;
import persistence.Crawler;
import persistence.Reader;
import persistence.Saver;

import java.io.IOException;

public class TeamBuildTool extends Runnable {
    Team team;

    // EFFECT: construct a team build tool for given team
    public TeamBuildTool(Team team) {
        this.team = team;
        run();
    }

    // EFFECT: construct a team build tool for given team name
    public TeamBuildTool(String name) {
        team = new Team(name);
        run();
    }

    // EFFECT: display command and operations
    @Override
    protected void display() {
        System.out.println("Welcome to team builder tool!");
        System.out.println("Current team: " + team.getName());
    }

    // EFFECT: process command
    @Override
    protected void selector(String command) {
        String[] commands = command.split(" ");
        switch (commands[0]) {
            case "save":
                save();
                break;
            case "show":
                show();
                break;
            case "rm":
                if (commands.length > 1) {
                    for (int i = 1; i < commands.length; i++) {
                        remove(commands[i]);
                    }
                } else {
                    System.out.println("No pokemon name found");
                    System.out.println("Please input the pokemon name to remove");
                }
                break;
            default:
                edit(commands[0]);
                break;
        }
    }

    @Override
    protected void help() {
        System.out.println("Type in the pokemon name to edit or add a new pokemon");
        System.out.println("rm [pokemon name]-> Remove a pokemon");
        System.out.println("save -> Save current team");
        System.out.println("show -> Show all pokemon");
        System.out.println("q -> Go back");
        System.out.println("q! -> Exit the program");
    }

    private void edit(String name) {
        PokemonToEdit pkm = team.getPokemon(name);
        if (pkm != null) {
            System.out.println("find " + name);
            new PokemonEditTool(pkm);
        } else {
            System.out.println(name + " is not found within the team.");
            System.out.println("Try adding a new pokemon...");
            add(name);
        }
    }

    // MODIFY: DIR + name + ".team"
    // EFFECT: save to a team file
    private void save() {
        Saver.saveTeam(team);
    }

    // EFFECT: show current team information
    private void show() {
        System.out.println(team.toString());
    }

    // REQUIRE: corresponding pokemon can be found
    // MODIFY: this
    // EFFECT: add a pokemon to team with given name
    private void add(String name) {
        try {
            PokemonToEdit pkm = Reader.readPokemon(name);
            team.add(pkm);
            System.out.println(name + " has been add to the team");
            new PokemonEditTool(pkm);
        } catch (IOException e) {
            System.out.println("Pokemon not found natively");
            System.out.println("Try looking for the pokemon online");
            try {
                PokemonToEdit pkm = Crawler.loadPokemon(name);
                Saver.savePokemon(pkm);
                team.add(pkm);
                System.out.println(name + " has been added to the team");
                new PokemonEditTool(pkm);
            } catch (IOException ex) {
                System.out.println("Pokemon not found");
            } catch (TeamFullException ex) {
                System.out.println("Team is full");
            }
        } catch (TeamFullException e) {
            System.out.println("Team is full");

        }
    }

    // MODIFY: this
    // EFFECT: remove a pokemon to team by name if can be found in current team
    private void remove(String name) {
        if (team.getPokemon(name) != null) {
            System.out.println("You are going to remove " + name);
            System.out.println("This process is inrevertable, are you sure? [y/n]");
            String command = input.next();
            if (command.equals("y") || command.equals("yes")) {
                team.remove(name);
                System.out.println(name + " has been removed form your team");
                System.out.println("You currently have " + team.count() + " pokemons in your team");
            }
        } else {
            System.out.println(name + " is not found within your team");
        }
    }
}
