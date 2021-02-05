package com.teinelund.jproject_info.strategy;

import com.teinelund.jproject_info.common.Printer;
import com.teinelund.jproject_info.context.Context;
import org.fusesource.jansi.Ansi;

import javax.inject.Inject;

import static org.fusesource.jansi.Ansi.ansi;

class PrintHelpStrategyImpl extends AbstractStrategy implements PrintHelpStrategy {

    @Inject
    public PrintHelpStrategyImpl(Context context, Printer printer) {
        super(context, printer);
    }

    @Override
    public void execute() {
        this.printer.verbose("PrintHelpStrategy.execute()");
        System.out.println( ansi().fg(Ansi.Color.WHITE).toString() );
        this.context.getJCommander().usage();
    }
}
