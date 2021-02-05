package com.teinelund.jproject_info.commands;

import com.teinelund.jproject_info.common.Printer;
import com.teinelund.jproject_info.context.Context;

abstract class AbstractCommand implements Command {

    protected Context context;
    protected Printer printer;

    public AbstractCommand(Context context, Printer printer) {
        this.context = context;
        this.printer = printer;
    }
}
