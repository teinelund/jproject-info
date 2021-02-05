package com.teinelund.jproject_info.strategy;

import com.teinelund.jproject_info.common.Printer;
import com.teinelund.jproject_info.context.Context;

abstract class AbstractStrategy implements Strategy {

    protected Context context;
    protected Printer printer;

    public AbstractStrategy(Context context, Printer printer) {
        this.context = context;
        this.printer = printer;
    }
}
