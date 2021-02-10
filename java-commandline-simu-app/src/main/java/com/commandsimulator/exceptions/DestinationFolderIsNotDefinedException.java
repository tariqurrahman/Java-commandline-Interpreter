package com.commandsimulator.exceptions;

public class DestinationFolderIsNotDefinedException extends Exception {
    public DestinationFolderIsNotDefinedException(String errorMessage) {
        super(errorMessage);
    }
}
