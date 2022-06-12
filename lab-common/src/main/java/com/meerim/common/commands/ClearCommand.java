package com.meerim.common.commands;

import com.meerim.common.util.CollectionManager;

public class ClearCommand extends Command {


    public ClearCommand() {
        super("","clear");
    }

    @Override
    public CommandResult execute(CollectionManager collectionManager) {
        collectionManager.clear();
        return new CommandResult("The collection was cleared successfully.");
    }
}
