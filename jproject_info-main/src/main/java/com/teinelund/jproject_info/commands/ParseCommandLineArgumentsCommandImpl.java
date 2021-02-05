package com.teinelund.jproject_info.commands;

import com.beust.jcommander.JCommander;
import com.teinelund.jproject_info.common.Printer;
import com.teinelund.jproject_info.context.Context;
import com.teinelund.jproject_info.controller.Controller;

import javax.inject.Inject;

class ParseCommandLineArgumentsCommandImpl extends AbstractCommand implements ParseCommandLineArgumentsCommand {

    private Controller controller;

    @Inject
    public ParseCommandLineArgumentsCommandImpl(Context context, Printer printer, Controller controller) {
        super(context, printer);
        this.controller = controller;
    }

    @Override
    public void execute() {
        JCommander jc = JCommander.newBuilder().
                addObject(this.context.getParameters()).
                programName("jproject_info").
                build();
        jc.parse(this.context.getCommandLineArguments());
        this.printer.verbose("Verbose output enabled.");
        this.printer.verbose("ParseCommandLineArgumentsCommand.execute()");
        this.context.setJCommander(jc);

        this.controller.execute();
    }
}
