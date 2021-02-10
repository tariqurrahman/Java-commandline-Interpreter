package com.commandsimulator.exceptions;

public class CannotReadDirectoryAsFileException extends Exception {
    public CannotReadDirectoryAsFileException(String errorMessage) {
        super(errorMessage);
    }
}
