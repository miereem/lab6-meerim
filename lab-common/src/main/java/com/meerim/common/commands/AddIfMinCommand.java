package com.meerim.common.commands;

import com.meerim.common.data.Dragon;
import com.meerim.common.util.OutputManager;
import com.meerim.common.util.CollectionManager;
import com.meerim.common.util.DragonMaker;
import com.meerim.common.util.UserInputManager;

import java.util.Scanner;


public class AddIfMinCommand extends Command {

    public AddIfMinCommand(Dragon arg) {
        super(arg,"add_if_min");

    }

    @Override
    public CommandResult execute(CollectionManager collectionManager) {
        final Scanner scanner = new Scanner(System.in);
        final OutputManager outputManager = new OutputManager();
        UserInputManager userInputManager = new UserInputManager(scanner);
        Dragon dragon = new DragonMaker(userInputManager, outputManager).makeDragon();
        if (collectionManager.addIfMin(dragon)) {
            return new CommandResult("The element was added successfully");
        } else {
            return new CommandResult("The element was not min, so it was not added");
        }
    }
}
