package com.meerim.common.commands;

import com.meerim.common.data.Dragon;
import com.meerim.common.util.CollectionManager;

public class RemoveGreaterCommand extends Command {

    public RemoveGreaterCommand(Dragon arg) {
        super(arg, "remove_greater");

    }

    @Override
    public CommandResult execute(CollectionManager collectionManager) {
        Dragon dragon = (Dragon) arg;
        collectionManager.removeGreater(dragon);
        return new CommandResult("Greater elements were removed successfully");
    }
}
