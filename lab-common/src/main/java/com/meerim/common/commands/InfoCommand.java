package com.meerim.common.commands;

import com.meerim.common.util.CollectionInfo;
import com.meerim.common.util.CollectionManager;



public class InfoCommand extends Command {

    public InfoCommand() {
        super("", "info");
    }

    @Override
    public CommandResult execute(CollectionManager collectionManager) {
            CollectionInfo collectionInfo = new CollectionInfo(collectionManager);
            return new CommandResult(collectionInfo.info());
        }
    }

