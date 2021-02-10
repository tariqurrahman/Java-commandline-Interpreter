package com.commandsimulator;

import com.commandsimulator.commands.CommandInterface;
import com.commandsimulator.exceptions.CommandNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class CommandFactory {
    private HashMap<String, String> availableCommands = new HashMap<String, String>();

    public void addAvailableCommands(String commandKey, String className) {
        this.availableCommands.put(commandKey, className);
    }

    public Boolean isCommandAvailable(String commandKey) {
        return this.availableCommands.get(commandKey) != null;
    }

    public String getCommandClassName(String commandKey) {
        return this.availableCommands.get(commandKey);
    }

    public ArrayList<CommandInterface> parseInputCommandToGetCommand(String commandString) throws Exception {

        ArrayList<CommandInterface> toBeRunCommand = new ArrayList<CommandInterface>();

        String[] commands = commandString
                .replaceAll("\\s+", " ")
                .split(";");

        for (String command : commands) {
            String trimmedCommandString = command.
                    trim()
                    .replaceAll("\\s+", " ");

            String[] commandWithArgs = trimmedCommandString.split(" ");

            String commandKey = commandWithArgs[0];

            if (!this.isCommandAvailable(commandKey)) {
                throw new CommandNotFoundException("Command " + commandKey + " cannot be found");
            }

            String className = this.getCommandClassName(commandKey);
            Class<?> clazz = Class.forName(className);
            CommandInterface instantiatedCommand = (CommandInterface) clazz.newInstance();
            instantiatedCommand.parseArgs(trimmedCommandString);
            toBeRunCommand.add(instantiatedCommand);
        }

        Collections.sort(toBeRunCommand);

        return toBeRunCommand;
    }
}
