package com.teinelund.jproject_info;

import com.teinelund.jproject_info.command_line_parameters_parser.ParametersModule;
import com.teinelund.jproject_info.controller.ControllerModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {ParametersModule.class, ControllerModule.class})
public interface ApplicationComponent {
    Application buildApplication();
}
