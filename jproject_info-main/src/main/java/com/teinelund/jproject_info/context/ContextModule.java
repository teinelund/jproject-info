package com.teinelund.jproject_info.context;

import com.teinelund.jproject_info.command_line_parameters_parser.Parameters;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ContextModule {

    @Singleton
    @Provides
    public Context provideContext(Parameters parameters) {
        return new ContextImpl(parameters);
    }
}
