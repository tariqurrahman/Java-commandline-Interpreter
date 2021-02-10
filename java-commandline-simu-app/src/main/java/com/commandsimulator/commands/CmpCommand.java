package com.commandsimulator.commands;

import com.commandsimulator.State;
import com.commandsimulator.exceptions.CannotReadDirectoryAsFileException;
import com.commandsimulator.exceptions.FileNotFoundException;
import com.commandsimulator.exceptions.NoFilePathProvidedException;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.MessageDigest;

public class CmpCommand extends BaseCommand {

    protected Integer sortingNumber = 0;
    private String firstFilePath = null;
    private String secondFilePath = null;

    @Override
    public void run() {
        try {
            this.validate();
            this.runCommand();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String getFileFullContent(String fileFullPath) throws Exception {

        String fileContent = "";

        BufferedReader reader = new BufferedReader(new FileReader(this.getFileFullPath(fileFullPath)));
        String line = reader.readLine();
        while (line != null) {
            fileContent = fileContent + line;
            line = reader.readLine();
        }
        reader.close();

        return fileContent;
    }

    private void runCommand() throws Exception {

        String firstFileFullPath = this.getFileFullPath(this.firstFilePath);
        String secondFileFullPath = this.getFileFullPath(this.secondFilePath);

        String firstFileContent = this.getFileFullContent(firstFileFullPath);
        String secondFileContent = this.getFileFullContent(secondFileFullPath);

        MessageDigest firstMd = MessageDigest.getInstance("MD5");
        firstMd.update(firstFileContent.getBytes());

        MessageDigest secondMd = MessageDigest.getInstance("MD5");
        secondMd.update(secondFileContent.getBytes());

        byte[] firstDigest = firstMd.digest();
        byte[] secondDigest = secondMd.digest();

        String firstHash = DatatypeConverter.printHexBinary(firstDigest).toUpperCase();
        String secondHash = DatatypeConverter.printHexBinary(secondDigest).toUpperCase();

        if (firstHash.equals(secondHash)) {
            System.out.println("Two files have same message");
        } else {
            System.out.println("Two files don't have same message");
        }
    }

    @Override
    public void parseArgs(String args) {
        String[] parsedArgs = args.split(" ");

        if (parsedArgs.length >= 2) {
            this.firstFilePath = parsedArgs[1];
        }

        if (parsedArgs.length >= 3) {
            this.secondFilePath = parsedArgs[2];
        }
    }

    private String getFileFullPath(String filePath) {
        if (filePath.matches("^/.*$")) {
            return filePath;
        } else {
            return this.state.getCurrentDirectory() + "/" + filePath;
        }
    }

    @Override
    public void validate() throws Exception {
        if (this.firstFilePath == null || this.secondFilePath == null) {
            throw new NoFilePathProvidedException("To compare file, you need to provide 2 file path");
        }

        String firstFileFullPath = this.getFileFullPath(this.firstFilePath);
        String secondFileFullPath = this.getFileFullPath(this.secondFilePath);

        File firstFile = new File(firstFileFullPath);
        File secondFile = new File(secondFileFullPath);

        if (!firstFile.exists() || !secondFile.exists()) {
            throw new FileNotFoundException("One or either files you want to compare does not exist");
        }

        if (firstFile.isDirectory() || secondFile.isDirectory()) {
            throw new CannotReadDirectoryAsFileException("One or either files you want to compare is a directory");
        }
    }

    public static String getCommandKey() {
        return "cmp";
    }
}
