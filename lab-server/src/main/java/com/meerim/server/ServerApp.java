package com.meerim.server;

import com.meerim.common.commands.Command;
import com.meerim.common.commands.CommandResult;
import com.meerim.common.data.Dragon;
import com.meerim.server.commands.SaveCommand;
import com.meerim.common.util.CollectionManager;
import com.meerim.common.util.FileManager;
import com.meerim.server.util.CSVMapper;
import com.meerim.common.util.ObjectWrapper;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerApp {
    private final Logger logger = Logger.getLogger(ServerApp.class.getName());
    private final CollectionManager collectionManager;
    private final BufferedReader in;
    private final FileManager fileManager;
    private final int capacity = 1000;

    public ServerApp(CollectionManager collectionManager, FileManager fileManager) {
        logger.setLevel(Level.CONFIG);
        this.in = new BufferedReader(new InputStreamReader(System.in));
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
    }
    public void start(int port) {
        try {
            initialize();
            SocketAddress address = new InetSocketAddress(port);
            ServerSocketChannel serverSocketChannel;
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(address);
            serverSocketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            logger.info("Server is running.");
            interact(selector, serverSocketChannel);
        } catch (ClosedChannelException e) {
            logger.info("Sorry, the channel you are trying to connect is closed.");
        } catch (IOException e) {
            logger.info("Couldn't run the server.");
        }
    }

    //Модуль приёма подключений.
    public void connect(ServerSocketChannel serverSocketChannel, Selector selector, SelectionKey selectionKey) {
        try {
            SocketChannel socket = serverSocketChannel.accept();
            socket.configureBlocking(false);
            socket.register(selector, SelectionKey.OP_READ);
            logger.info("Connected to the socket: " + socket + ".");
        } catch (IOException e) {
            logger.info("Couldn't connect to the socket.");
            selectionKey.cancel();
        }
    }

    //Модуль чтения запроса.

    public Command receive(SelectionKey selectionKey) throws IOException, ClassNotFoundException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer buf = readInBuf(socketChannel);
        Command request = (Command) ObjectWrapper.deserialize(buf);
        System.out.println("Request: " + request.getName());
        buf.clear();
        return request;
    }


    //Модуль обработки полученных команд.
    public void handle(Command request, SelectionKey selectionKey) {
        CommandResult result = request.execute(collectionManager);
        System.out.println(result);
        selectionKey.attach(result);
        selectionKey.interestOps(SelectionKey.OP_WRITE);
    }

    //Модуль отправки ответов клиенту.
    public boolean send(SelectionKey selectionKey) {
        try {
            selectionKey.interestOps(SelectionKey.OP_WRITE);
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            ObjectOutputStream oos = new ObjectOutputStream(socketChannel.socket().getOutputStream());
            oos.writeObject(selectionKey.attachment());
            oos.close();
        } catch (IOException e) {
            logger.info("Couldn't send the result to the client.");
        }
        return !"exit".equals(selectionKey.attachment());
    }


    public void interact(Selector selector, ServerSocketChannel serverSocketChannel) throws IOException {
        while (console(selector)) {
            int count = selector.select(1);
            if (count == 0) {
                continue;
            }
            Set<SelectionKey> keySet = selector.selectedKeys(); // unreachable code
            Iterator<SelectionKey> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isAcceptable() && selectionKey.isValid()) {
                    connect(serverSocketChannel, selector, selectionKey);
                }
                if (selectionKey.isValid() && selectionKey.isReadable()) {
                    try {
                        handle(receive(selectionKey), selectionKey); // todo mistake here
                    } catch (ClassNotFoundException | IOException e) {
                        logger.info("The client was disconnected" + selectionKey.channel());
                        selectionKey.cancel();
                    }
                }
                if (selectionKey.isValid() && selectionKey.isWritable()) {
                    if (!send(selectionKey)) {
                        logger.info("The client was disconnected" + selectionKey.channel());
                        selectionKey.cancel();
                    }
                }
                if (!selectionKey.isValid()) {
                    selectionKey.cancel();
                }
            }
        }
        selector.close();
        serverSocketChannel.close();
    }

    public boolean console(Selector selector) throws IOException {
        if (consoleInput()) {
            Set<SelectionKey> keySet = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                selectionKey.cancel();
            }
            new SaveCommand(fileManager).execute(collectionManager);
            logger.info("Collection saved successfully.\nSession terminated.");
            return false;
        }
        return true;
    }

    private boolean consoleInput() throws IOException {
        boolean flag = false;
        String command;
        int isReadyConsole = System.in.available();
        if (isReadyConsole > 0) {
            try {
                command = in.readLine().trim().toLowerCase();
                switch (command) {
                    case "exit":
                        flag = true;
                        break;
                    case "save":
                        new SaveCommand(fileManager).execute(collectionManager);
                        logger.info("Collection saved successfully.");
                        break;
                    default:
                        logger.config("Waiting for a command.");
                }
            } catch (NullPointerException e) {
                logger.warning("Please, enter a valid command.");
            }
        }
        return flag;
    }
    public void initialize() {
        LinkedHashSet<Dragon> dragons = new LinkedHashSet<Dragon>(new CSVMapper().deserialize(fileManager.getFilename()));
        collectionManager.initialiseData(dragons);
        logger.info("Collection is initialized.");
    }
    private ByteBuffer readInBuf(SocketChannel socketChannel) throws IOException {
        int newCapacity = capacity;
        ByteBuffer buf = ByteBuffer.allocate(capacity);
        while (socketChannel.read(buf) > 0) {
            newCapacity = newCapacity * 2;
            ByteBuffer byteBuffer = ByteBuffer.allocate(newCapacity);
            byteBuffer.put(buf.array());
            buf = byteBuffer.slice();
        }
        return buf;
    }
}






