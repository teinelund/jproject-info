package com.teinelund.jproject_info.main_argument_parser;

import java.util.concurrent.Callable;

public class OptionsFactory {
    public Callable<Integer> getOptions() {
        return new OptionsImpl();
    }
}
