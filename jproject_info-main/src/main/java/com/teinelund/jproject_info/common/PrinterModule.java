package com.teinelund.jproject_info.common;

import com.teinelund.jproject_info.command_line_parameters_parser.Parameters;
import com.teinelund.jproject_info.context.Context;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class PrinterModule {

    @Singleton
    @Provides
    public Printer providePrinter(Parameters parameters, Context context) {
        return new PrinterImpl(parameters, context);
    }
}
