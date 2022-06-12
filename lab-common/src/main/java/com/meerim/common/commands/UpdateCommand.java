package com.meerim.common.commands;

import com.meerim.common.data.Dragon;
import com.meerim.common.util.OutputManager;
import com.meerim.common.util.CollectionManager;
import com.meerim.common.util.DragonMaker;
import com.meerim.common.util.UserInputManager;

import java.util.Scanner;

public class UpdateCommand extends Command {

    private final String arg;

    public UpdateCommand(Dragon dragon, String id) {
        super(dragon,"update");
        this.arg = id;
    }

    @Override
    public CommandResult execute(CollectionManager collectionManager) {
        int intArg;
        try {
            intArg = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            return new CommandResult("The entered argument was incorrect. The command was not executed.");
        }

        if (collectionManager.removeById(intArg)) {
            Dragon dragon = new DragonMaker(new UserInputManager(new Scanner(System.in)), new OutputManager()).makeDragon();
            collectionManager.removeId(dragon.getId());
            dragon.setId(intArg);
            collectionManager.add(dragon);
            return new CommandResult( "The element was updated successfully");
        } else {
            return new CommandResult("Written id was not found. The command was not executed");
        }
    }
}
