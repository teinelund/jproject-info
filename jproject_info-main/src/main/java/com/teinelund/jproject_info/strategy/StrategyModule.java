package com.teinelund.jproject_info.strategy;

import com.teinelund.jproject_info.context.Context;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class StrategyModule {

    @Singleton
    @Provides
    public PrintHelpStrategy providePrintHelpStrategy(Context context) {
        return new PrintHelpStrategyImpl(context);
    }
}
