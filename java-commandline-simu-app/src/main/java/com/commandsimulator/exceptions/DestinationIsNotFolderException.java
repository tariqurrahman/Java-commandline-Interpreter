package com.commandsimulator.exceptions;

public class DestinationIsNotFolderException extends Exception {
    public DestinationIsNotFolderException(String errorMessage) {
        super(errorMessage);
    }
}
