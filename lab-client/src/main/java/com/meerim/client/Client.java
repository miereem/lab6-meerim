package com.meerim.client;

import com.meerim.common.util.DragonMaker;
import com.meerim.common.util.OutputManager;
import com.meerim.common.util.UserInputManager;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.logging.Logger;

public final class Client {
    private static final Collection<String> LIST_OF_COMMANDS = new HashSet<>();
    private static String port;
    private static String host;

    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }


    public static void main(String[] args) throws IOException {
        final Logger logger = Logger.getLogger(Client.class.getName());
        OutputManager outputManager = new OutputManager();
        initCommandList();
        switch (args.length) {
            case 1:
                port = args[0];
                System.out.println(port + " " + host);
                break;
            case 2:
                port = args[0];
                host = args[1];
                break;
            default:
                logger.severe("Please write port and host( default it's localhost)");
        }
        if (port != null) {
            try {
                if (ValidInput.checkPort(port) && ValidInput.checkHost(host)) {
                    ClientApp clientApp = new ClientApp(ValidInput.getPort(port), host, LIST_OF_COMMANDS, new DragonMaker(new UserInputManager(System.in), outputManager), outputManager, new UserInputManager(System.in));
                    clientApp.startConnection(); //try catch
                } else {
                    logger.severe("Can't connect to the server, please check host address and port");
                }
            } catch (IOException e) {
                e.printStackTrace();
                logger.severe("Can't connect to the server, check host address and port");
            } catch (NumberFormatException e) {
                logger.severe("impossible pars host address and port ");
            }

        }
    }
        private static void initCommandList() {
            Client.LIST_OF_COMMANDS.add("add");
            Client.LIST_OF_COMMANDS.add("add_if_min");
            Client.LIST_OF_COMMANDS.add("clear");
            Client.LIST_OF_COMMANDS.add("exit");
            Client.LIST_OF_COMMANDS.add("help");
            Client.LIST_OF_COMMANDS.add("history");
            Client.LIST_OF_COMMANDS.add("info");
            Client.LIST_OF_COMMANDS.add("min_by_id");
            Client.LIST_OF_COMMANDS.add("min_by_cave");
            Client.LIST_OF_COMMANDS.add("print_ascending");
            Client.LIST_OF_COMMANDS.add("remove_by_id");
            Client.LIST_OF_COMMANDS.add("remove_greater");
            Client.LIST_OF_COMMANDS.add("show");
            Client.LIST_OF_COMMANDS.add("update");
            Client.LIST_OF_COMMANDS.add("execute_script");
            Client.LIST_OF_COMMANDS.add("average_of_age");


    }
}
