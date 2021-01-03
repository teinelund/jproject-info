package com.teinelund.jproject_info.commands;

public class ValidateCommandLineArgumentsException extends RuntimeException {

    public ValidateCommandLineArgumentsException() {}

    public ValidateCommandLineArgumentsException(String message) {
        super(message);
    }
}
