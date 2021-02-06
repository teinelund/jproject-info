package com.teinelund.jproject_info.strategy;

import com.teinelund.jproject_info.common.Printer;
import com.teinelund.jproject_info.context.Context;
import javax.inject.Inject;


class PrintHelpStrategyImpl extends AbstractStrategy implements PrintHelpStrategy {

    @Inject
    public PrintHelpStrategyImpl(Context context, Printer printer) {
        super(context, printer);
    }

    @Override
    public void execute() {
        this.printer.verbose("PrintHelpStrategy.execute()");
        this.printer.printHelp();
    }
}
