package com.teinelund.jproject_info.strategy;

import com.teinelund.jproject_info.commands.ProjectInformationCommand;
import com.teinelund.jproject_info.context.Context;

import javax.inject.Inject;

public class PathInformationStrategyImpl extends AbstractStrategy implements PathInformationStrategy {

    private ProjectInformationCommand command;

    @Inject
    public PathInformationStrategyImpl(Context context, ProjectInformationCommand command) {
        super(context);
        this.command = command;
    }

    @Override
    public void execute() {
        this.command.execute();
    }
}
