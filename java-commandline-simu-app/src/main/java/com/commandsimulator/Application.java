package com.commandsimulator;

import com.commandsimulator.commands.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Application {

    private String prompt = "myshell> ";

    private State state;
    private Scanner scanner;
    private CommandFactory commandFactory;

    public Application(
            State state,
            Scanner scanner,
            CommandFactory commandFactory
    ) {
        this.state = state;
        this.scanner = scanner;
        this.commandFactory = commandFactory;
    }

    public void run() {

        //this is the main loop
        while (this.state.getRunning()) {

            System.out.print("\n");
            System.out.print(this.prompt);

            try {
                String inputCommand = this.scanner.nextLine();

                ArrayList<CommandInterface> toBeRunCommands = this.commandFactory
                        .parseInputCommandToGetCommand(inputCommand);

                for (CommandInterface command : toBeRunCommands) {
                    command.setState(this.state);
                }

                int numberOfCommands = toBeRunCommands.size();
                Thread threads[] = new Thread[numberOfCommands];
                for (int i = 0; i < numberOfCommands; i++) {
                    threads[i] = new Thread(toBeRunCommands.get(i));
                    threads[i].start();
                    threads[i].join();
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {

        State state = new State();
        state.setCurrentDirectory(System.getProperty("user.home"));

        Scanner scanner = new Scanner(System.in);

        CommandFactory commandFactory = new CommandFactory();
        commandFactory.addAvailableCommands(PwdCommand.getCommandKey(), PwdCommand.class.getName());
        commandFactory.addAvailableCommands(LsCommand.getCommandKey(), LsCommand.class.getName());
        commandFactory.addAvailableCommands(ExitCommand.getCommandKey(), ExitCommand.class.getName());
        commandFactory.addAvailableCommands(CdCommand.getCommandKey(), CdCommand.class.getName());
        commandFactory.addAvailableCommands(CatCommand.getCommandKey(), CatCommand.class.getName());
        commandFactory.addAvailableCommands(CmpCommand.getCommandKey(), CmpCommand.class.getName());
        commandFactory.addAvailableCommands(SortCommand.getCommandKey(), SortCommand.class.getName());

        Application application = new Application(
                state,
                scanner,
                commandFactory
        );

        application.run();
    }
}
