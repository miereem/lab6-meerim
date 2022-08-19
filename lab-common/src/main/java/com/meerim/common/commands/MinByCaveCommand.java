package com.meerim.common.commands;

import com.meerim.common.data.Dragon;
import com.meerim.common.util.CollectionManager;

public class MinByCaveCommand extends Command {



    public MinByCaveCommand() {
        super("", "min_by_cave");
    }

    @Override
    public CommandResult execute(CollectionManager collectionManager) {
        Dragon minCaveDragon = collectionManager.getMinCave();
        assert minCaveDragon != null;
        return new CommandResult(minCaveDragon.toString());
    }
}
