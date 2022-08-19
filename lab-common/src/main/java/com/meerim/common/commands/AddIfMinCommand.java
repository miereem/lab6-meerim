package com.meerim.common.commands;

import com.meerim.common.data.Dragon;
import com.meerim.common.util.CollectionManager;

public class AddIfMinCommand extends Command {

    public AddIfMinCommand(Dragon arg) {
        super(arg, "add_if_min");

    }

    @Override
    public CommandResult execute(CollectionManager collectionManager) {
        Dragon dragon = (Dragon) arg;
        if (collectionManager.addIfMin(dragon)) {
            return new CommandResult("The element was added successfully");
        } else {
            return new CommandResult("The element was not min, so it was not added");
        }
    }
}
