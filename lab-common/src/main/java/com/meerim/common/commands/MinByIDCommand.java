package com.meerim.common.commands;

import com.meerim.common.data.Dragon;
import com.meerim.common.util.CollectionManager;



public class MinByIDCommand extends Command {

    public MinByIDCommand() {
        super("", "min_by_id");
    }

    @Override
    public CommandResult execute(CollectionManager collectionManager) {

        if (collectionManager.getMainData().isEmpty()) {
            return new CommandResult("The method wasn't executed due to empty collection.");
        }

        Dragon minIdDragon = collectionManager.getMinId();
        return new CommandResult(minIdDragon.toString());
    }
}
