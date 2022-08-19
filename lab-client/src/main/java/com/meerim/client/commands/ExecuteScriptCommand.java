package com.meerim.client.commands;

import com.meerim.common.commands.CommandResult;
import com.meerim.common.util.UserInputManager;
import java.io.File;
import java.io.IOException;


public class ExecuteScriptCommand {

    private final String arg;

    public ExecuteScriptCommand(String arg) {
        this.arg = arg;
    }


    public void execute(UserInputManager userInputManager) {
        try {
            userInputManager.connectToFile(new File(arg));
            new CommandResult("Starting to execute script...");
        } catch (IOException e) {
            new CommandResult("There was a problem opening the file. Check if it is available and you have written it in the command arg correctly.");
        } catch (UnsupportedOperationException e) {
           new CommandResult(e.getMessage());
        }
    }
}
