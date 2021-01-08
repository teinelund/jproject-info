package com.teinelund.jproject_info.commands;

import com.teinelund.jproject_info.context.Context;

import javax.inject.Inject;

class ValidateCommandLineArgumentsCommandMock extends ValidateCommandLineArgumentsCommandImpl {
    private boolean executedInvoked = false;

    @Inject
    public ValidateCommandLineArgumentsCommandMock(Context context) {
        super(context, null);
    }

    public boolean isExecutedInvoked() {
        return this.executedInvoked;
    }

    @Override
    public void execute() {
        executedInvoked = true;
    }
}
