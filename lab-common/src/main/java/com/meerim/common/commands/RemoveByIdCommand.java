package com.meerim.common.commands;

import com.meerim.common.util.CollectionManager;


public class RemoveByIdCommand extends Command {


    public RemoveByIdCommand(String arg) {
        super(arg, "remove_by_id");
    }

    @Override
    public CommandResult execute(CollectionManager collectionManager) {
        int intArg;
        try {
            intArg = Integer.parseInt((String) arg);
        } catch (NumberFormatException e) {
            return new CommandResult("Your argument was incorrect. The command was not executed.");
        }
        if (collectionManager.removeById(intArg)) {
            return new CommandResult("The element was deleted successfully.");
        } else {
            return new CommandResult("Could not find the given id. The command was not executed");
        }
    }
}
