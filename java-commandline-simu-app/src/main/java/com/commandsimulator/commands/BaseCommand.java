package com.commandsimulator.commands;

import com.commandsimulator.State;

public class BaseCommand implements CommandInterface  {

    protected Integer sortingNumber = 0;
    protected State state;

    public int compareTo(CommandInterface command) {
        if (this.getNumberForShorting() == command.getNumberForShorting()) {
            return 0;
        } else if (this.getNumberForShorting() < command.getNumberForShorting()) {
            return -1;
        } else {
            return 1;
        }
    }

    public void run() {

    }

    public void parseArgs(String args) {

    }

    public void setState(State state) {
        this.state = state;
    }

    public void validate() throws Exception {

    }

    public Integer getNumberForShorting() {
        return this.sortingNumber;
    }
}
