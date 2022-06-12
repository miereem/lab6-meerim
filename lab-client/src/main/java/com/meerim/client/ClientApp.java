package com.meerim.client;


import com.meerim.client.util.Console;
import com.meerim.common.commands.Command;
import com.meerim.common.commands.CommandResult;
import com.meerim.common.util.*;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.sql.Wrapper;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientApp {
    public static final Logger logger = Logger.getLogger(ClientApp.class.getName());
    private Map commands = null;
    private Collection<String> listOfCommands = new HashSet<>();
    private final int MAX_ATTEMPTS = 5;
    //private final int clientPort;
    private final int serverPort;
    //private final String clientIp;
    private final String serverIp;
    private final int capacity = 10000;

    ClientApp(int serverPort, String serverIp) {
            this.serverPort = serverPort;
            this.serverIp = serverIp;
            logger.setLevel(Level.INFO);
    }

    public CommandResult sendRequest(Command request) {
        if (request == null) {
            throw new IllegalArgumentException("The request is null.");
        }
        int attempts = 0;
        while (attempts < MAX_ATTEMPTS) {
            try {
                Socket socket = new Socket(serverIp, serverPort);
                OutputStream os = socket.getOutputStream();
                ByteBuffer buf = ObjectWrapper.serialize(request);
                os.write(buf.array());
                logger.info("Request sent successfully.");
                buf.clear();
                buf.flip();

                //ObjectOutputStream oos = new ObjectOutputStream(os);
                //oos.writeObject(request);
                InputStream is = socket.getInputStream();
                ByteBuffer buffer = ByteBuffer.allocate(capacity);
                is.read(buffer.array());
                System.out.println(ObjectWrapper.deserialize(buffer));
                CommandResult result = (CommandResult) ObjectWrapper.deserialize(buffer);
                logger.info("Response accepted.");
                if (attempts != 0) {
                    logger.info("Client connected.");
                }

                return result;
            } catch (IOException | ClassNotFoundException | NullPointerException e) {
                System.out.println("Couldn't connect to the server");
                attempts++;
                try {
                    Thread.sleep(5 * 1000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }
}

