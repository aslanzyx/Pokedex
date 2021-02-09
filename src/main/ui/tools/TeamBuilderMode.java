package ui.tools;

import exceptions.StatInvalidException;
import exceptions.TeamFullException;
import model.Team;
import persistence.Reader;

import java.io.IOException;

public class TeamBuilderMode extends Runnable {

    public TeamBuilderMode() {
        run();
    }

    // EFFECT: display welcome
    @Override
    protected void display() {
        System.out.println("Welcome to team builder mode menu!");
    }

    // EFFECT: process command
    @Override
    protected void selector(String command) {
        if (command.equals("n")) {
            System.out.println("Please input a name for your team");
            String name = input.next();
            new TeamBuildTool(name);
        } else {
            try {
                openTeam(command);
            } catch (IOException e) {
                System.out.println("Team not found");
            }
        }
    }

    // EFFECT: show commands
    @Override
    protected void help() {
        System.out.println("Input the name of your team to open if or: ");
        System.out.println("n -> Create a new team and edit");
        System.out.println("q -> Back to home");
    }

    // EFFECT: open team builder tool
    private void openTeam(String name) throws IOException {
        Team team = null;
        try {
            team = Reader.readTeam(name);
        } catch (StatInvalidException e) {
            e.printStackTrace();
        } catch (TeamFullException e) {
            e.printStackTrace();
        }
        new TeamBuildTool(team);
    }
}
