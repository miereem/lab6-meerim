package com.meerim.common.commands;

import com.meerim.common.data.Dragon;
import com.meerim.common.util.CollectionManager;
import com.meerim.common.util.OutputManager;
import com.meerim.common.util.DragonMaker;
import com.meerim.common.util.UserInputManager;

import java.util.Scanner;

public class RemoveGreaterCommand extends Command {




    public RemoveGreaterCommand(Dragon arg) {
        super(arg,"remove_greater");

    }

    @Override
    public CommandResult execute(CollectionManager collectionManager) {
        final Scanner scanner = new Scanner(System.in);
        final OutputManager outputManager = new OutputManager();
        UserInputManager userInputManager = new UserInputManager(scanner);
        Dragon dragon = new DragonMaker(userInputManager, outputManager).makeDragon();
        collectionManager.removeGreater(dragon);
        return new CommandResult("Greater elements were removed successfully");
    }
}
