package com.meerim.common.commands;

import com.meerim.common.data.Dragon;
import com.meerim.common.util.CollectionManager;

import java.util.StringJoiner;
import java.util.TreeSet;


public class PrintAscendingCommand extends Command {


    public PrintAscendingCommand() {
        super("","print_ascending");
    }

    @Override
    public CommandResult execute(CollectionManager collectionManager) {
        StringJoiner output = new StringJoiner("\n\n");
        TreeSet<Dragon> ascending = new TreeSet<>(collectionManager.getMainData());

        for (Dragon dragon : ascending) {
            output.add(dragon.toString());
        }

        return new CommandResult(output.toString());
    }
}
