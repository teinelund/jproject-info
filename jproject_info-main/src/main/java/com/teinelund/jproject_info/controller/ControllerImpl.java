package com.teinelund.jproject_info.controller;

import com.teinelund.jproject_info.context.Context;
import com.teinelund.jproject_info.strategy.PrintHelpStrategy;
import com.teinelund.jproject_info.strategy.Strategy;

import javax.inject.Inject;

public class ControllerImpl implements Controller {

    private Context context;
    private Strategy printHelpStrategy;

    @Inject
    public ControllerImpl(Context context, PrintHelpStrategy printHelpStrategy) {
        this.context = context;
        this.printHelpStrategy = printHelpStrategy;
    }

    @Override
    public void execute() {
        Strategy strategy = null;
        if (this.context.getParameters().isHelpOption()) {
            strategy = this.printHelpStrategy;
        }

        if (strategy != null) {
            strategy.execute();
        }
    }
}
