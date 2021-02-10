package com.commandsimulator.commands;

import com.commandsimulator.State;
import com.commandsimulator.exceptions.CannotReadDirectoryAsFileException;
import com.commandsimulator.exceptions.FileNotFoundException;
import com.commandsimulator.exceptions.NoFilePathProvidedException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class CatCommand extends BaseCommand {

    protected Integer sortingNumber = 0;
    private ArrayList<String> fileNames = new ArrayList<String>();

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
        for (String fileName : this.fileNames) {
            BufferedReader reader = new BufferedReader(new FileReader(this.getFileFullPath(fileName)));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }
            reader.close();
        }
    }

    private String getFileFullPath(String fileName) {
        if (fileName.matches("^/.*$")) {
           return fileName;
        } else {
            return this.state.getCurrentDirectory() + "/" + fileName;
        }
    }

    @Override
    public void parseArgs(String args) {
        String[] parsedArgs = args.split(" ");

        if (parsedArgs.length > 1) {
            for (int i = 1; i < parsedArgs.length; i++) {
                String fileName = parsedArgs[i];
                this.fileNames.add(fileName);
            }
        }
    }

    @Override
    public void validate() throws Exception {
        if (this.fileNames.size() == 0) {
            throw new NoFilePathProvidedException("Please provide file path");
        }

        for (String fileName : this.fileNames) {

            String fileFullPath = this.getFileFullPath(fileName);

            File file = new File(fileFullPath);

            if (file.isDirectory()) {
                throw new CannotReadDirectoryAsFileException("Cannot read directory as file");
            }

            if (!file.exists()) {
                throw new FileNotFoundException("File " + fileName + " does not exist");
            }
        }
    }

    public static String getCommandKey() {
        return "cat";
    }
}
