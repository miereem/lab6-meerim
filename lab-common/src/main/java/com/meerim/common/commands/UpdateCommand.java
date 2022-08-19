package com.meerim.common.commands;

import com.meerim.common.data.Dragon;
import com.meerim.common.util.CollectionManager;


public class UpdateCommand extends Command {

    private final String idarg;

    public UpdateCommand(Dragon dragon, String id) {
        super(dragon, "update");
        this.idarg = id;
    }

    @Override
    public CommandResult execute(CollectionManager collectionManager) {
        int intArg;
        try {
            intArg = Integer.parseInt(idarg);
        } catch (NumberFormatException e) {
            return new CommandResult("The entered argument was incorrect. The command was not executed.");
        }

        if (collectionManager.removeById(intArg)) {
            Dragon dragon = (Dragon) arg;
            collectionManager.removeId(dragon.getId());
            dragon.setId(intArg);
            collectionManager.add(dragon);
            return new CommandResult("The element was updated successfully");
        } else {
            return new CommandResult("Written id was not found. The command was not executed");
        }
    }
}
