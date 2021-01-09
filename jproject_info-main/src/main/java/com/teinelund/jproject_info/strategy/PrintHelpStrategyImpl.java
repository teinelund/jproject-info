package com.teinelund.jproject_info.strategy;

import com.teinelund.jproject_info.context.Context;
import org.fusesource.jansi.Ansi;

import javax.inject.Inject;

import static org.fusesource.jansi.Ansi.ansi;

class PrintHelpStrategyImpl extends AbstractStrategy implements PrintHelpStrategy {

    @Inject
    public PrintHelpStrategyImpl(Context context) {
        super(context);
    }

    @Override
    public void execute() {
        System.out.println( ansi().fg(Ansi.Color.WHITE).toString() );
        this.context.getJCommander().usage();
    }
}
