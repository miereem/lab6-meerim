package com.meerim.client;


import com.meerim.client.commands.ExecuteScriptCommand;
import com.meerim.common.commands.AddCommand;
import com.meerim.common.commands.AddIfMinCommand;
import com.meerim.common.commands.AverageOfAgeCommand;
import com.meerim.common.commands.ClearCommand;
import com.meerim.common.commands.Command;
import com.meerim.common.commands.CommandResult;
import com.meerim.common.commands.HelpCommand;
import com.meerim.common.commands.InfoCommand;
import com.meerim.common.commands.MinByCaveCommand;
import com.meerim.common.commands.MinByIDCommand;
import com.meerim.common.commands.PrintAscendingCommand;
import com.meerim.common.commands.RemoveByIdCommand;
import com.meerim.common.commands.RemoveGreaterCommand;
import com.meerim.common.commands.ShowCommand;
import com.meerim.common.commands.UpdateCommand;
import com.meerim.common.data.Dragon;
import com.meerim.common.util.DragonMaker;
import com.meerim.common.util.ObjectWrapper;
import com.meerim.common.util.OutputManager;
import com.meerim.common.util.UserInputManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientApp {

    public static final Logger LOGGER = Logger.getLogger(ClientApp.class.getName());
    private Socket clientSocket;
    private OutputStream out;
    private InputStream in;
    private int port;
    private String ip;
    private int capacity = 10000;
    private final Collection<String> listOfCommands;
    private final DragonMaker dragonMaker;
    private final OutputManager outputManager;
    private final UserInputManager userInputManager;



    public ClientApp(int port, String ip, Collection<String> listOfCommands, DragonMaker dragonMaker, OutputManager outputManager, UserInputManager userInputManager) {
        this.port = port;
        this.ip = ip;
        this.listOfCommands = listOfCommands;
        this.dragonMaker = dragonMaker;
        this.outputManager = outputManager;
        this.userInputManager = userInputManager;
        LOGGER.setLevel(Level.INFO);
    }

    public void startConnection() throws IOException {
        try {
            clientSocket = new Socket(this.ip, this.port);
            LOGGER.info("connection started");
            out = clientSocket.getOutputStream();
            in = clientSocket.getInputStream();
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
                        try {
                            System.out.println(sendMessage(getCommandObjectByName(commandName, commandArg, commandArg2)).getOutput()); /// fix output
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            System.out.println("Server was closed.");
                            break;
                        }
                    }
                } else {
                    outputManager.println("The command was not found. Please use \"help\" to know about commands.");
                }
            } while (!"exit".equals(input));
            stopConnection();
        } catch (ConnectException e) {
            System.out.println("Server is closed.");
        }
    }



    public CommandResult sendMessage(Command request) throws IOException, ClassNotFoundException {
        ByteBuffer buf = (ObjectWrapper.serialize(request));
        out.write(buf.array());
        //logger.info("request sent");
        ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
        in.read(byteBuffer.array());
        //logger.info("response accepted");
        return (CommandResult) ObjectWrapper.deserialize(byteBuffer);
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        LOGGER.info("connection stopped");

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
        outputManager.print(">>>");
        try {
            return userInputManager.nextLine();
        } catch (NoSuchElementException e) {
            return "exit";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Command getCommandObjectByName(String commandName, Serializable arg, String arg2) {
        Command command = null;
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
            case "min_by_id":
                command = new MinByIDCommand();
                break;
            case "help":
                command = new HelpCommand();
                break;
            default:
                break;
        }
        return command;
    }
}

