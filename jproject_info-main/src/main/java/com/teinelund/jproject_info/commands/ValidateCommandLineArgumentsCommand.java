package com.teinelund.jproject_info.commands;

import com.teinelund.jproject_info.context.Context;

import javax.inject.Inject;


public class ValidateCommandLineArgumentsCommand implements Command {

    @Inject
    public ValidateCommandLineArgumentsCommand() {

    }

    @Override
    public void execute() {
        System.out.println("ValidateCommandLineArgumentsCommnd.execute().");
    }
}
