package com.commandsimulator.commands;

import com.commandsimulator.exceptions.DestinationFolderIsNotDefinedException;
import com.commandsimulator.exceptions.DestinationIsNotFolderException;
import com.commandsimulator.exceptions.FolderNotFoundException;

import java.io.File;

public class CdCommand extends BaseCommand {

    private String destination;
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

    private void runCommand() throws Exception {
        String folderPath = this.getFolderPath();
        this.state.setCurrentDirectory(folderPath.replaceAll("//", "/"));
    }

    private String getFolderPath() throws Exception {
        String folderPath = "";

        if (this.destination.matches("^/.*$")) {
            folderPath = "";
        } else {
            folderPath = this.state.getCurrentDirectory();
        }

        String[] folderNames = this.destination.split("/");
        for (String folderName : folderNames) {
            if (folderName.matches("..")) {
                String newFolderPath = "";
                String[] splittedFolderPath = folderPath.split("/");
                if (splittedFolderPath.length > 1) {
                    for (int i = 0; i < (splittedFolderPath.length - 1); i++) {
                        newFolderPath = newFolderPath + splittedFolderPath[i] + "/";
                    }
                }

                folderPath = newFolderPath;
            } else {
                folderPath = folderPath + "/" + folderName;
                File folder = new File(folderPath);

                if (!folder.exists()) {
                    throw new FolderNotFoundException("Destination " + folderPath + " does not exist");
                }

                if (!folder.isDirectory()) {
                    throw new DestinationIsNotFolderException("The destination " + folderPath + " is not a folder");
                }
            }
        }

        return folderPath;
    }

    @Override
    public void parseArgs(String args) {
        String[] parsedArgs = args.split(" ");
        if (parsedArgs.length <= 1) {
            this.destination = null;
        } else {
            this.destination = parsedArgs[1];
        }
    }

    @Override
    public void validate() throws Exception {
        String destination = this.destination;

        if (destination == null) {
            throw new DestinationFolderIsNotDefinedException("You need to specify the destination folder");
        }
    }

    public static String getCommandKey() {
        return "cd";
    }
}
