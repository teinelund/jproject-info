package com.teinelund.jproject_info.commands;

import com.beust.jcommander.JCommander;
import com.teinelund.jproject_info.context.Context;
import com.teinelund.jproject_info.controller.Controller;

import javax.inject.Inject;

class ParseCommandLineArgumentsCommandImpl extends AbstractCommand implements ParseCommandLineArgumentsCommand {

    private Controller controller;

    @Inject
    public ParseCommandLineArgumentsCommandImpl(Context context, Controller controller) {
        super(context);
        this.controller = controller;
    }

    @Override
    public void execute() {
        JCommander jc = JCommander.newBuilder().
                addObject(this.context.getParameters()).
                programName("jproject_info").
                build();
        jc.parse(this.context.getCommandLineArguments());
        this.context.setJCommander(jc);

        this.controller.execute();
    }
}
