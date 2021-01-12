package com.teinelund.jproject_info.commands;

import com.teinelund.jproject_info.common.VerboseOutput;
import com.teinelund.jproject_info.context.Context;

public abstract class AbstractCommand extends VerboseOutput implements Command {

    protected Context context;

    public AbstractCommand(Context context) {
        this.context = context;
    }
}
