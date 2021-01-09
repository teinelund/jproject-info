package com.teinelund.jproject_info.commands;

import com.teinelund.jproject_info.command_line_parameters_parser.ParametersModule;
import com.teinelund.jproject_info.context.Context;
import com.teinelund.jproject_info.context.ContextModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {ContextModule.class, ParametersModule.class})
public interface ContextTestComponent {
    public Context buildContext();
}
