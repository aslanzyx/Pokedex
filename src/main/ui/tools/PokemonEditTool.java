package ui.tools;

import exceptions.CommandNotValidException;
import exceptions.StatInvalidException;
import model.PokemonToEdit;
import model.Stats;

public class PokemonEditTool extends Runnable {

    private PokemonToEdit pkm;

    public PokemonEditTool(PokemonToEdit pkm) {
        this.pkm = pkm;
        run();
    }

    // EFFECT: display welcome
    @Override
    protected void display() {
        System.out.println("You are editting " + pkm.getName());
    }

    // Command is meaningful
    // EFFECT: process command
    @Override
    protected void selector(String command) throws CommandNotValidException {
        String[] commands = command.split(" ");
        Stats stats = new Stats();
        try {
            switch (commands[0]) {
                case "show":
                    System.out.println(pkm);
                    break;
//            case "check":
//                valid();
//                break;
                case "lv":
                    processCommandLv(commands);
                    break;
                case "iv":
                    processCommandIV(commands);
//                    pkm.setIndividualValues(stats);
                    break;
                case "bs":
                    processCommandBS(commands);
//                    pkm.setBaseStats(stats);
                    break;
                default:
                    System.out.println("Command not found");
            }
        } catch (StatInvalidException e) {
            System.out.println("Invalid value");
        }
    }

    // EFFECT: show command menu
    @Override
    protected void help() {
        System.out.println("man -> show command menu");
        System.out.println("show -> Show the stats");
        System.out.println("check -> check if valid");

        System.out.println("bs -> Configure pokemon base stats\n");
        System.out.println("bs -hp -> to config hit point base stats");
        System.out.println("bs -atk [value]-> to config attack individual value");
        System.out.println("bs -spa [value]-> to config special attack base stats");
        System.out.println("bs -def [value]-> to config defence base stats");
        System.out.println("bs -spc [value]-> to config special base stats");
        System.out.println("bs -spd [value]-> to config speed base stats");

        System.out.println("iv -> Configure pokemon individual values\n");
        System.out.println("iv -hp [value]-> to config hit point individual value");
        System.out.println("iv -atk [value]-> to config attack individual value");
        System.out.println("iv -spa [value]-> to config special attack individual value");
        System.out.println("iv -def [value]-> to config defence individual value");
        System.out.println("iv -spc [value]-> to config special individual value");
        System.out.println("iv -spd [value]-> to config speed individual value");

        System.out.println("lv -> Configure pokemon level\n");

        System.out.println("For example: ");
        System.out.println("iv -hp 31 \n would set the hit point individual value to 31");
        System.out.println("lv 50 \n would set the level to 50");

        System.out.println("hit any key to continue");
    }

    private void processCommandLv(String[] commands) throws CommandNotValidException, StatInvalidException {
        if (commands.length == 2) {
            pkm.setLevel(Integer.parseInt(commands[1]));
        } else {
            throw new CommandNotValidException();
        }
    }

    // MODIFY: pkm
    // EFFECT: process command and modify current pokemon's base stats
    private void processCommandBS(String[] commands) throws CommandNotValidException, StatInvalidException {
        int value = Integer.parseInt(commands[2]);
        switch (commands[1]) {
            case "-hp":
                pkm.setBS(0, value);
//                stats.set(0, Integer.parseInt(commands[2]));
                break;
            case "-atk":
                pkm.setBS(1, value);
//                stats.set(1, Integer.parseInt(commands[2]));
                break;
            case "-spa":
                pkm.setBS(2, value);
//                stats.set(2, Integer.parseInt(commands[2]));
                break;
            case "-def":
                pkm.setBS(3, value);
//                stats.set(3, Integer.parseInt(commands[2]));
                break;
            case "-spc":
                pkm.setBS(4, value);
//                stats.set(4, Integer.parseInt(commands[2]));
                break;
            case "-spd":
                pkm.setBS(5, value);
//                stats.set(5, Integer.parseInt(commands[2]));
                break;
            default:
                throw new CommandNotValidException();
        }
    }

    // MODIFY: pkm
    // EFFECT: process command and modify current pokemon's individual value
    private void processCommandIV(String[] commands) throws CommandNotValidException, StatInvalidException {
        int value = Integer.parseInt(commands[2]);
        switch (commands[1]) {
            case "-hp":
                pkm.setIV(0, value);
//                stats.set(0, Integer.parseInt(commands[2]));
                break;
            case "-atk":
                pkm.setIV(1, value);
//                stats.set(1, Integer.parseInt(commands[2]));
                break;
            case "-spa":
                pkm.setIV(2, value);
//                stats.set(2, Integer.parseInt(commands[2]));
                break;
            case "-def":
                pkm.setIV(3, value);
//                stats.set(3, Integer.parseInt(commands[2]));
                break;
            case "-spc":
                pkm.setIV(4, value);
//                stats.set(4, Integer.parseInt(commands[2]));
                break;
            case "-spd":
                pkm.setIV(5, value);
//                stats.set(5, Integer.parseInt(commands[2]));
                break;
            default:
                throw new CommandNotValidException();
        }
    }

    //EFFECT check if current pokemon is valid
//    private void valid() {
//        System.out.println(pkm.valid() ? "valid" : "invalid");
//    }
}
