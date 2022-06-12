package com.meerim.common.commands;

import java.io.Serializable;

public class CommandResult {
    private final Serializable output;

    public CommandResult(Serializable output) {
        this.output = output;
    }

    public Serializable getOutput() {
        return output;
    }
}
