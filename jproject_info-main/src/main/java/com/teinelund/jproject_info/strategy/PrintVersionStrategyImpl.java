package com.teinelund.jproject_info.strategy;

import com.teinelund.jproject_info.common.Printer;
import com.teinelund.jproject_info.context.Context;

import javax.inject.Inject;

class PrintVersionStrategyImpl  extends AbstractStrategy implements PrintVersionStrategy {

    @Inject
    public PrintVersionStrategyImpl(Context context, Printer printer) {
        super(context, printer);
    }

    @Override
    public void execute() {
        this.printer.verbose("PrintVersionStrategy.execute()");
        VersionProvider versionProvider = new VersionProvider();
        for (String line : versionProvider.getVersion()) {
            this.printer.info(line);
        }
    }
}
