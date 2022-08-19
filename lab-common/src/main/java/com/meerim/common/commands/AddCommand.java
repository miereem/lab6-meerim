package com.meerim.common.commands;
import com.meerim.common.data.Dragon;
import com.meerim.common.util.CollectionManager;

public class AddCommand extends Command {

    public AddCommand(Dragon arg) {
        super(arg, "add");
    }

    @Override
    public CommandResult execute(CollectionManager collectionManager) {
        Dragon dragon = (Dragon) arg;
        dragon.setId(collectionManager.getNextId());
        collectionManager.add(dragon);
        return new CommandResult("The element was added successfully");
    }
}
