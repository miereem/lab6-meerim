package com.meerim.common.commands;

import com.meerim.common.util.CollectionManager;

public interface Executable {
    public abstract CommandResult execute(CollectionManager collectionManager);
}
