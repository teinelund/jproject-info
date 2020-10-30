package com.teinelund.jproject_info;

import com.teinelund.jproject_info.main_argument_parser.Options;
import picocli.CommandLine;

/**
 * Main class
 */
public class Application {
    public static void main(String[] args) {
        System.exit(new CommandLine(new Options()).execute(args));
    }
}
