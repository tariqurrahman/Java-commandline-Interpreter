package com.commandsimulator.exceptions;

public class FolderNotFoundException extends Exception {
    public FolderNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
