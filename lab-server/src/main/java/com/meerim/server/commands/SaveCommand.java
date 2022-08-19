package com.meerim.server.commands;

import com.meerim.common.commands.Command;
import com.meerim.common.commands.CommandResult;
import com.meerim.common.util.CollectionManager;
import com.meerim.common.util.FileManager;
import com.meerim.server.util.CSVMapper;

public class SaveCommand extends Command {
    private final FileManager fileManager;

    public SaveCommand(FileManager fileManager) {
        super("", "save");
        this.fileManager = fileManager;
    }

    public CommandResult execute(CollectionManager collectionManager) {
        try {
            new CSVMapper().serialize(fileManager.getFilename(), collectionManager.getDataForSerialization());
        } catch (Exception e) {
            System.out.println("Sorry, the data was not saved.");
        }
        return new CommandResult("The data was saved successfully");
    }
}
