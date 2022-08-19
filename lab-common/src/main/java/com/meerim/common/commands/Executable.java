package com.meerim.common.commands;

import com.meerim.common.util.CollectionManager;

public interface Executable {
     CommandResult execute(CollectionManager collectionManager);
}
