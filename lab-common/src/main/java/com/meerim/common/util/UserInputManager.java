package com.meerim.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

/**
 * This class is used for all the user input: keyboard and script execution
 */
public class UserInputManager {
    private Scanner scanner;
    private final Stack<BufferedReader> currentFilesReaders = new Stack<>();
    private final Stack<File> currentFiles = new Stack<>();

    public UserInputManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public String nextLine() {
        if (currentFilesReaders.isEmpty()) {
            return scanner.nextLine();
        }

        String input = null;
        try {
            input = currentFilesReaders.peek().readLine();
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        if (input != null) {
            System.out.println(input);
            return input;
        }
        currentFiles.pop();
        try {
            currentFilesReaders.pop().close();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
        return nextLine();
    }

    public void connectToFile(File file) throws IOException, UnsupportedOperationException {
        if (currentFiles.contains(file)) {
            throw new UnsupportedOperationException("The file was not executed due to recursion");
        } else {
            BufferedReader newReader = new BufferedReader(new FileReader(file));
            currentFiles.push(file);
            currentFilesReaders.push(newReader);
            System.out.println(currentFiles);
        }
    }

}
