package com.teinelund.jproject_info.commands;

import com.teinelund.jproject_info.context.Context;

public abstract class AbstractCommand implements Command {

    protected Context context;

    public AbstractCommand(Context context) {
        this.context = context;
    }
}
