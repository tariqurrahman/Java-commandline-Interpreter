package com.commandsimulator.exceptions;

public class CommandNotFoundException extends Exception {
    public CommandNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
