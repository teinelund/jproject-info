package com.teinelund.jproject_info.commands;

import com.beust.jcommander.JCommander;
import com.teinelund.jproject_info.context.Context;

import javax.inject.Inject;

class ParseCommandLineArgumentsCommandImpl extends AbstractCommand implements ParseCommandLineArgumentsCommand {

    private Command command;

    @Inject
    public ParseCommandLineArgumentsCommandImpl(Context context, ValidateCommandLineArgumentsCommand command) {
        super(context);
        this.command = command;
    }

    @Override
    public void execute() {
        JCommander jc = JCommander.newBuilder().
                addObject(this.context.getParameters()).
                programName("jproject_info").
                build();
        jc.parse(this.context.getCommandLineArguments());
        this.context.setJCommander(jc);

        this.command.execute();
    }
}
