package com.teinelund.jproject_info.controller;

import com.teinelund.jproject_info.context.Context;
import com.teinelund.jproject_info.strategy.PrintHelpStrategy;
import com.teinelund.jproject_info.strategy.PrintVersionStrategy;
import com.teinelund.jproject_info.strategy.Strategy;

import javax.inject.Inject;

public class ControllerImpl implements Controller {

    private Context context;
    private Strategy printHelpStrategy;
    private Strategy printVersionStrategy;

    @Inject
    public ControllerImpl(
            Context context,
            PrintHelpStrategy printHelpStrategy,
            PrintVersionStrategy printVersionStrategy) {
        this.context = context;
        this.printHelpStrategy = printHelpStrategy;
        this.printVersionStrategy = printVersionStrategy;
    }

    @Override
    public void execute() {
        Strategy strategy = null;
        if (this.context.getParameters().isHelpOption()) {
            strategy = this.printHelpStrategy;
        }
        else if (this.context.getParameters().isVersionOption()) {
            strategy = this.printVersionStrategy;
        }

        if (strategy != null) {
            strategy.execute();
        }
    }
}
