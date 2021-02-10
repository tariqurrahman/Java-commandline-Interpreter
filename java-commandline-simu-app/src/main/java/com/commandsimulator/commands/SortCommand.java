package com.commandsimulator.commands;

import com.commandsimulator.State;
import com.commandsimulator.exceptions.CannotReadDirectoryAsFileException;
import com.commandsimulator.exceptions.FileNotFoundException;
import com.commandsimulator.exceptions.NoFilePathProvidedException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class SortCommand extends BaseCommand {

    private String filePath = null;

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
        String fullFilePath = this.getFileFullPath(this.filePath);

        ArrayList<String> fileContent = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(this.getFileFullPath(fullFilePath)));
        String line = reader.readLine();
        while (line != null) {
            fileContent.add(line);
            line = reader.readLine();
        }
        reader.close();

        Collections.sort(fileContent);

        for (String word : fileContent) {
            System.out.println(word);
        }
    }

    @Override
    public void parseArgs(String args) {
        String[] parsedArgs = args.split(" ");
        if (parsedArgs.length >= 2) {
            this.filePath = parsedArgs[1];
        }
    }

    @Override
    public void validate() throws Exception {
        if (this.filePath == null) {
            throw new NoFilePathProvidedException("To sort a file, you need to specify the file path");
        }

        String fullFilePath = this.getFileFullPath(this.filePath);
        File file = new File(fullFilePath);

        if (!file.exists()) {
            throw new FileNotFoundException("The file you want to sort does not exist");
        }

        if (file.isDirectory()) {
            throw new CannotReadDirectoryAsFileException("The file you want to sort is a directory");
        }
    }

    private String getFileFullPath(String fileName) {
        if (fileName.matches("^/.*$")) {
            return fileName;
        } else {
            return this.state.getCurrentDirectory() + "/" + fileName;
        }
    }

    public static String getCommandKey() {
        return "sort";
    }
}
