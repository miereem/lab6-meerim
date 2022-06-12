package com.meerim.server;

import com.meerim.common.util.CollectionManager;
import com.meerim.common.util.FileManager;


public final class Server {
    static final String ENVIRONMENT_VARIABLE = "PROG_PATH";

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
            final int port;
            final CollectionManager collectionManager = new CollectionManager();
            final FileManager fileManager = new FileManager(ENVIRONMENT_VARIABLE);
            final ServerApp serverApp = new ServerApp(collectionManager, fileManager);
            if (ENVIRONMENT_VARIABLE == null) {
                System.out.println("Environment variable is null.");
                return;
            }
            try {
                port = Integer.parseInt(args[0]);
                serverApp.start(port);
            } catch (NullPointerException e) {
                System.out.println("Please, set the program argument to the needed port");
            } catch (IllegalArgumentException e) {
                System.out.println("The file does not keep data in correct format.");
            } catch (Exception e) {
                //System.out.println("Could not execute the program");
                e.printStackTrace();
            }
        }

    }

