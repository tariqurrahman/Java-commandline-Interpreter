package com.commandsimulator.commands;

import com.commandsimulator.State;

public interface CommandInterface  extends Runnable, Comparable<CommandInterface> {

    public int compareTo(CommandInterface command);

    public void parseArgs(String args);

    public void setState(State state);

    public void validate() throws Exception;

    public Integer getNumberForShorting();
}
