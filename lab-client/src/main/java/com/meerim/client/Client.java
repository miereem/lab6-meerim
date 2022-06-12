package com.meerim.client;

import com.meerim.common.util.*;
import com.meerim.client.util.Console;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;

public final class Client {
    private static final Collection<String> listOfCommands = new HashSet<>();
    private static int serverPort;
    private static int clientPort;
    private static String clientIp;
    private static String serverIp;

    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }


    public static void main(String[] args) {
        OutputManager outputManager = new OutputManager();
        initCommandList();
        try {
            initArgs(args);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            //outputManager.println("Entered arguments are invalid. Please, use the program as \"java -jar <name> <server port> <client port> <clientIp> <serverIp>\"");
            e.getStackTrace();
            //return;
        }
        try {
            ClientApp clientApp = new ClientApp(serverPort, serverIp);
            new Console(new UserInputManager(new Scanner(System.in)), outputManager, listOfCommands, clientApp).start();
        } catch (IOException e) {
            outputManager.println("Something went wrong with IO, the message is: " + e.getMessage());
        }
    }

    private static void initCommandList() {
        Client.listOfCommands.add("add");
        Client.listOfCommands.add("add_if_min");
        Client.listOfCommands.add("clear");
        Client.listOfCommands.add("exit");
        Client.listOfCommands.add("help");
        Client.listOfCommands.add("history");
        Client.listOfCommands.add("info");
        Client.listOfCommands.add("min_by_id");
        Client.listOfCommands.add("print_ascending");
        Client.listOfCommands.add("remove_by_id");
        Client.listOfCommands.add("remove_greater");
        Client.listOfCommands.add("show");
        Client.listOfCommands.add("update");
        Client.listOfCommands.add("execute_script");
        Client.listOfCommands.add("filter_less_than_semester_enum");
        Client.listOfCommands.add("print_ascending");
    }

    private static void initArgs(String[] args) throws IllegalArgumentException, IndexOutOfBoundsException {

        serverPort = Integer.parseInt(args[0]);
        //clientPort = Integer.parseInt(args[1]);
       // clientIp = args[2];
        //final int three = 3;
        serverIp = "localhost";
    }


}
