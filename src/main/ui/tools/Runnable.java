package ui.tools;

import exceptions.CommandNotValidException;

import java.util.Scanner;

// a super class for all ui classes
public abstract class Runnable {
    protected Scanner input;

    // EFFECT: run application
    public void run() {
        boolean running = true;
        input = new Scanner(System.in);

        while (running) {
            display();
            String command = input.nextLine().toLowerCase();

            if (command.equals("q")) {
                running = false;
            } else if (command.equals("q!")) {
                System.exit(0);
            } else if (command.equals("man")) {
                help();
            } else {
                try {
                    selector(command);
                } catch (CommandNotValidException e) {
                    System.out.println("Command not valid");
                }
            }
        }
    }

    // EFFECT: display welcome content
    protected abstract void display();

    // EFFECT: do command operation
    protected abstract void selector(String command) throws CommandNotValidException;

    // EFFECT: Show instructions
    protected abstract void help();
}
