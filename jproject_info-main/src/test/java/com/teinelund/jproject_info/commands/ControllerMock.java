package com.teinelund.jproject_info.commands;

import com.teinelund.jproject_info.context.Context;
import com.teinelund.jproject_info.controller.ControllerImpl;
import com.teinelund.jproject_info.strategy.PathInformationStrategy;
import com.teinelund.jproject_info.strategy.PrintHelpStrategy;
import com.teinelund.jproject_info.strategy.PrintVersionStrategy;

import javax.inject.Inject;

class ControllerMock extends ControllerImpl {
    private boolean executedInvoked = false;

    @Inject
    public ControllerMock(Context context) {
        super(context, null, null, null, null);
    }

    public boolean isExecutedInvoked() {
        return this.executedInvoked;
    }

    @Override
    public void execute() {
        executedInvoked = true;
    }
}
