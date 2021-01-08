package com.teinelund.jproject_info.commands;

import com.teinelund.jproject_info.context.Context;

class ValidateCommandLineArgumentsCommandImplMock extends ValidateCommandLineArgumentsCommandImpl {
    private boolean executedInvoked = false;

    public ValidateCommandLineArgumentsCommandImplMock(Context context) {
        super(context);
    }

    public boolean isExecutedInvoked() {
        return this.executedInvoked;
    }

    @Override
    public void execute() {
        executedInvoked = true;
    }
}
