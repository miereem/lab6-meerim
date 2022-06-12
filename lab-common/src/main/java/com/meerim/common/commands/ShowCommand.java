package com.meerim.common.commands;

import com.meerim.common.data.Dragon;
import com.meerim.common.util.CollectionManager;

import java.util.StringJoiner;


public class ShowCommand extends Command {

    public ShowCommand() {
        super("","show");
    }

    @Override
    public CommandResult execute(CollectionManager collectionManager) {
        StringJoiner output = new StringJoiner("\n\n");
        if (collectionManager.getMainData().isEmpty()) {
            return new CommandResult("Collection is empty");
        }
        for (Dragon dragon : collectionManager.getMainData()) {
            output.add(dragon.toString());
        }

        return new CommandResult(output.toString());
    }
}
