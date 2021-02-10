package com.commandsimulator.commands;

import com.commandsimulator.State;

public class ExitCommand extends BaseCommand {

    protected Integer sortingNumber = 1;

    public void run() {
        try {
            this.validate();
            this.runCommand();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void runCommand() {
        this.state.setRunning(false);
    }

    @Override
    public void parseArgs(String args) {

    }

    @Override
    public void validate() throws Exception {

    }

    public static String getCommandKey() {
        return "exit";
    }
}
