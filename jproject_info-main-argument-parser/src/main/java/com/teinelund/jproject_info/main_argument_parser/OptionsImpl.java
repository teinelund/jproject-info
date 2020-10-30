package com.teinelund.jproject_info.main_argument_parser;

import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * Implementation class for command line argument options.
 *
 * PicoCli:
 * The name attribute sets a name for the application when the help page is displayed.
 * The mixinStandardHelpOptions attribute adds --help and --version options to your application.
 * versionProvider store a class that implement a version string to be displayed when --version is given as option.
 */
@CommandLine.Command(name = "jproject_info", mixinStandardHelpOptions = true, versionProvider = VersionProvider.class)
class OptionsImpl implements Callable<Integer>, Options {

    @Override
    public Integer call() throws Exception {
        System.out.println("Hello World Picocli.");
        return 123; // exit code
    }
}