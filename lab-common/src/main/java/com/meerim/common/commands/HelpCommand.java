package com.meerim.common.commands;

import com.meerim.common.util.CollectionManager;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("", "help");
    }

    @Override
    public CommandResult execute(CollectionManager collectionManager) {
        return new CommandResult(
                "help : gives information about available commands\n"
                        + "info : gives information about collection\n"
                        + "show : shows every element in collection with string\n"
                        + "add {element} : adds new element to collection\n"
                        + "update {element} : updates element info by it's id\n"
                        + "remove_by_id id : deletes element by it's id\n"
                        + "clear : clears collection\n"
                        + "execute_script file_name : executes script entered in a file\n"
                        + "exit : exits the program (!!!does not save data!!!)\n"
                        + "add_if_min {element} : adds new element to the collection if it's value less than min element's value\n"
                        + "remove_greater {element} : deletes every element in the collection with value more than entered element's value\n"
                        + "min_by_id : gives information about a random element from collection with minimum value\n"
                        + "print_ascending : prints every element in ascending order\n"
                        + "average_of_age : prints the average of elements' age\n"
                        + "min_by_cave : prints any element from the collection with the lowest number of treasures\n");

    }
}
