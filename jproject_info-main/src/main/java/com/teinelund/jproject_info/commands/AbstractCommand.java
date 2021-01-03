package com.teinelund.jproject_info.commands;

import com.teinelund.jproject_info.command_line_parameters_parser.Parameters;
import com.teinelund.jproject_info.context.Context;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

public abstract class AbstractCommand implements Command {

    protected Context context;

    public AbstractCommand(Context context) {
        this.context = context;
    }

    public static void verboseOutput(Parameters parameters, String text) {
        if (parameters.isVerbose()) {
            System.out.println(ansi().fg(Ansi.Color.MAGENTA).a("[VERBOSE] " + text).reset().toString());
        }
    }
}
