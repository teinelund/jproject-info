package com.teinelund.jproject_info.common;

import com.teinelund.jproject_info.command_line_parameters_parser.Parameters;
import com.teinelund.jproject_info.context.Context;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import javax.inject.Inject;

import static org.fusesource.jansi.Ansi.ansi;

class PrinterImpl implements Printer {

    private Parameters parameters;
    private Context context;

    @Inject
    public PrinterImpl(Parameters parameters, Context context) {
        this.parameters = parameters;
        this.context = context;
        AnsiConsole.systemInstall();
    }

    @Override
    public void verbose(String message) {
        if (parameters.isVerboseOption()) {
            System.out.println(ansi().fg(Ansi.Color.MAGENTA).a("[VERBOSE] " + message).reset().toString());
        }
    }

    @Override
    public void error(String message) {
        System.err.println(ansi().fg(Ansi.Color.RED).a("[ERROR] " + message + " Type --help to display usage of all options.").reset().toString());
    }

    @Override
    public void infoWhite(String message) {
        System.out.println(ansi().fg(Ansi.Color.WHITE).a(message).reset().toString());
    }

    @Override
    public void info(String message) {
        System.out.println(message);
    }

    @Override
    public void printHelp() {
        System.out.println( ansi().fg(Ansi.Color.WHITE).toString() );
        this.context.getJCommander().usage();
    }
}
