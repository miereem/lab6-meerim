package com.meerim.client.util;



import com.meerim.client.Client;
import com.meerim.client.ClientApp;
import com.meerim.client.commands.ExecuteScriptCommand;
import com.meerim.common.commands.*;
import com.meerim.common.commands.AddCommand;
import com.meerim.common.commands.Command;
import com.meerim.common.data.Dragon;
import com.meerim.common.util.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

public class Console {

    private final UserInputManager userInputManager;
    private final Collection<String> listOfCommands;
    private final DragonMaker dragonMaker;
    private final OutputManager outputManager;
    private Command request = null;
    private final ClientApp clientApp;

    public Console(UserInputManager userInputManager, OutputManager outputManager, Collection<String> listOfCommands, ClientApp clientApp) {
        this.userInputManager = userInputManager;
        this.listOfCommands = listOfCommands;
        this.dragonMaker = new DragonMaker(userInputManager, outputManager);
        this.outputManager = outputManager;
        this.clientApp = clientApp;
    }


    public void start() throws IllegalArgumentException, IOException {
        String input;
        do {
            input = readNextCommand();
            final String[] parsedInp = parseToNameAndArg(input);
            final String commandName = parsedInp[0];
            Serializable commandArg = parsedInp[1];
            String commandArg2 = "";
            if (listOfCommands.contains(commandName)) {
                if ("add".equals(commandName) || "add_if_min".equals(commandName) || "remove_greater".equals(commandName)) {
                    commandArg = dragonMaker.makeDragon();
                }
                if ("update".equals(commandName)) {
                    commandArg2 = (String) commandArg;
                    commandArg = dragonMaker.makeDragon();
                }
                if ("execute_script".equals(commandName)) {
                    new ExecuteScriptCommand((String) commandArg).execute(userInputManager);
                } else {

                    CommandResult result = clientApp.sendRequest(getCommandObjectByName(commandName, commandArg, commandArg2));
                   System.out.println(result);
                }
            } else {
                outputManager.println("The command was not found. Please use \"help\" to know about commands.");
            }
        } while (!"exit".equals(input));
    }


    private String[] parseToNameAndArg(String input) {
        String[] arrayInput = input.split(" ");
        String inputCommand = arrayInput[0];
        String inputArg = "";

        if (arrayInput.length >= 2) {
            inputArg = arrayInput[1];
        }

        return new String[]{inputCommand, inputArg};
    }

    private String readNextCommand() {
        System.out.print(">>>");
        return userInputManager.nextLine();
    }

    private Command getCommandObjectByName(String commandName, Serializable arg, String arg2) {
        Command command;
        switch (commandName) {
            case "add":
                command = new AddCommand((Dragon) arg);
                break;
            case "add_if_min":
                command = new AddIfMinCommand((Dragon) arg);
                break;
            case "clear":
                command = new ClearCommand();
                break;
            case "average_of_age":
                command = new AverageOfAgeCommand();
                break;
            case "info":
                command = new InfoCommand();
                break;
            case "min_by_cave":
                command = new MinByCaveCommand();
                break;
            case "print_ascending":
                command = new PrintAscendingCommand();
                break;
            case "remove_by_id":
                command = new RemoveByIdCommand((String) arg);
                break;
            case "remove_greater":
                command = new RemoveGreaterCommand((Dragon) arg);
                break;
            case "show":
                command = new ShowCommand();
                break;
            case "update":
                command = new UpdateCommand((Dragon) arg, arg2);
                break;
            default:
                command = new HelpCommand();
                break;
        }
        return command;
    }
    public Command getRequest() {
        System.out.println(request);
        return request;
    }

}

