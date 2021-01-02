package com.teinelund.jproject_info;

import com.teinelund.jproject_info.command_line_parameters_parser.ParametersModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = ParametersModule.class)
public interface ApplicationComponent {
    Application buildApplication();
}
