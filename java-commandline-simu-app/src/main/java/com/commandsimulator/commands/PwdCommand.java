package com.commandsimulator.commands;

import com.commandsimulator.State;

public class PwdCommand extends BaseCommand {

    protected Integer sortingNumber = 0;

    @Override
    public void run() {
        try {
            this.validate();
            this.runCommand();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void runCommand() {
        System.out.println(this.state.getCurrentDirectory());
    }

    @Override
    public void parseArgs(String args) {

    }

    @Override
    public void validate() throws Exception {

    }

    public static String getCommandKey() {
        return "pwd";
    }
}
