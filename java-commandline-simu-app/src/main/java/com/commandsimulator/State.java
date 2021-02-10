package com.commandsimulator;

public class State {
    private String currentDirectory;
    private Boolean isRunning = true;

    public String getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(String currentDirectory) {
        this.currentDirectory = currentDirectory;
    }

    public void setRunning(Boolean running) {
        isRunning = running;
    }

    public Boolean getRunning() {
        return isRunning;
    }
}
