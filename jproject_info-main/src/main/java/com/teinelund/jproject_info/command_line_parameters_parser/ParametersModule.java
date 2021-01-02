package com.teinelund.jproject_info.command_line_parameters_parser;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ParametersModule {

    @Singleton
    @Provides
    public Parameters provideParameters() {
        return new ParametersImpl();
    }
}
