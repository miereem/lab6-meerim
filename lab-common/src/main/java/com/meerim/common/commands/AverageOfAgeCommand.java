package com.meerim.common.commands;


import com.meerim.common.util.CollectionManager;


public class AverageOfAgeCommand extends Command {


    public AverageOfAgeCommand() {
        super("", "average_of_age");
    }

    @Override
    public CommandResult execute(CollectionManager collectionManager) {
        if (collectionManager.getMainData().isEmpty()) {
            return new CommandResult("The method wasn't executed due to empty collection.");
        }
        return new CommandResult(String.valueOf(collectionManager.getAverage()));
    }
}
