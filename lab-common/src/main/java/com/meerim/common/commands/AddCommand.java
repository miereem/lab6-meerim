package com.meerim.common.commands;


import com.meerim.common.data.Dragon;
import com.meerim.common.util.OutputManager;
import com.meerim.common.util.CollectionManager;
import com.meerim.common.util.DragonMaker;
import com.meerim.common.util.UserInputManager;

import java.io.Serializable;
import java.util.Collection;
import java.util.Scanner;


public class AddCommand extends Command {



    public AddCommand(Dragon arg) {
        super(arg,"add");

    }

    @Override
    public CommandResult execute(CollectionManager collectionManager) {
        Dragon dragon = new DragonMaker(new UserInputManager(new Scanner(System.in)), new OutputManager()).makeDragon();
        collectionManager.add(dragon);
        return new CommandResult("The element was added successfully");
    }
}
