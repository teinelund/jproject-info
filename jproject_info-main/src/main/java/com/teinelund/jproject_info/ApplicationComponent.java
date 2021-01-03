package com.teinelund.jproject_info;

import com.teinelund.jproject_info.command_line_parameters_parser.ParametersModule;
import com.teinelund.jproject_info.commands.CommandModule;
import com.teinelund.jproject_info.context.ContextModule;
import com.teinelund.jproject_info.controller.ControllerModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {ParametersModule.class, ControllerModule.class, ContextModule.class, CommandModule.class})
public interface ApplicationComponent {
    Application buildApplication();
}
