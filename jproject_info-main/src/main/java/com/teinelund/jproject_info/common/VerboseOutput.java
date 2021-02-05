package com.teinelund.jproject_info.common;

import com.teinelund.jproject_info.command_line_parameters_parser.Parameters;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

public class VerboseOutput {
    public void verboseOutput(Parameters parameters, String text) {
        if (parameters.isVerboseOption()) {
            System.out.println(ansi().fg(Ansi.Color.MAGENTA).a("[VERBOSE] " + text).reset().toString());
        }
    }
}
