package com.meerim.common.commands;

import com.meerim.common.util.CollectionManager;

import java.io.Serializable;

/**
 * This is an abstract class for all the commands.
 */
public abstract class Command{

    protected final Serializable arg;
    private final String name;

    public Command(Serializable arg, String name) {
        this.arg = arg;
        this.name = name;
    }

    public abstract CommandResult execute(CollectionManager collectionManager);

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Command{"
                + "name='" + name + '\''
                + ", arg=" + arg
                + '}';
    }
}
