package ui.tools;

import exceptions.CommandNotValidException;

// Home application
public class Home extends Runnable {

    public Home() {
        run();
    }

    // EFFECT: display command operations
    @Override
    protected void display() {
        System.out.println("Welcome");
    }

    // REQUIRE: input command is meaningful
    // EFFECT: run command operations based on input command
    @Override
    protected void selector(String command) throws CommandNotValidException {
        switch (command) {
            case "d":
                new PokedexMode();
                break;
            case "b":
                new TeamBuilderMode();
                break;
            default:
                throw new CommandNotValidException();
        }
    }

    @Override
    protected void help() {
        System.out.println("Type in words or corresponding characters to enter the modes");
        System.out.println("d -> Pokedex Mode");
        System.out.println("b -> Team Builder Mode");
        System.out.println("q -> Quit");
    }
}
