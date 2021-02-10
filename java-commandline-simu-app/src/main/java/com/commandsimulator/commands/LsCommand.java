package com.commandsimulator.commands;

import com.commandsimulator.State;

import java.io.File;

public class LsCommand extends BaseCommand {

    protected Integer sortingNumber = 0;

    private void runCommand() {
        File fileName = new File(this.state.getCurrentDirectory());
        File[] fileList = fileName.listFiles();

        int count = 0;

        for (File file: fileList) {
            count++;

            System.out.print(file.getName());
            System.out.print("    ");

            if (count % 4 == 0) {
                System.out.print("\n");
            }
        }
    }

    @Override
    public void run() {
        try {
            this.validate();
            this.runCommand();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void parseArgs(String args) {

    }

    public static String getCommandKey() {
        return "ls";
    }

    @Override
    public void validate() throws Exception {

    }
}
