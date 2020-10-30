package com.teinelund.jproject_info.main_argument_parser;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "jproject_info", mixinStandardHelpOptions = true, version = "1.0.0-SNAPSHOT")
public class Options implements Callable<Integer>
{

    @Override
    public Integer call() throws Exception {
        System.out.println("Hello World Picocli.");
        return 123; // exit code
    }
}
