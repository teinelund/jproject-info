package com.teinelund.jproject_info.strategy;

import com.teinelund.jproject_info.context.Context;

import javax.inject.Inject;

public class PrintVersionStrategyImpl  extends AbstractStrategy implements PrintVersionStrategy {

    @Inject
    public PrintVersionStrategyImpl(Context context) {
        super(context);
    }

    @Override
    public void execute() {
        VersionProvider versionProvider = new VersionProvider();
        for (String line : versionProvider.getVersion()) {
            System.out.println(line);
        }
    }
}
