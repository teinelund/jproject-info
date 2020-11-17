package com.teinelund.jproject_info.main_argument_parser;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParametersDelegate;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation class for command line argument options.
 */
//@CommandLine.Command(
//        name = "jproject_info",
//        mixinStandardHelpOptions = true,
//        versionProvider = VersionProvider.class,
//        subcommands = {ProjectOptionImpl.class})
class OptionsImpl implements Options {

//    @CommandLine.Parameters(description = "One or more paths to Java project folders (Maven project for instance). Required.",
//            scope = CommandLine.ScopeType.INHERIT, arity = "1..*")
    private Set<Path> javaProjectPaths = new LinkedHashSet<>();
    @Parameter(description = "One or more paths to Java project folders (Maven project for instance). Required.", order = 0)
    private List<String> javaProjectPathsNames = new LinkedList<>();

    @Parameter(names = {"-p", "--path-info"}, description = "Display shallow project information for each input path.")
    boolean pathInfo = false;

    //@CommandLine.Option(names = {"--verbose", "-v"}, description = "Verbose output to console.")
    @Parameter(names = {"--verbose", "-v"}, description = "Verbose output to console.", order = 1)
    private boolean verbose = false;

    @Parameter(names = {"--help", "-h"}, help = true, order = 2)
    private boolean help = false;

    @Parameter(names = {"--version", "-V"}, order = 3)
    private boolean version = false;

    @Override
    public Set<Path> getJavaProjectPaths() {
        if (!this.javaProjectPathsNames.isEmpty()) {
            for (String pathName : this.javaProjectPathsNames) {
                Path path = Paths.get(pathName);
                this.javaProjectPaths.add(path);
            }
        }
        return this.javaProjectPaths;
    }

    @Override
    public boolean isVerbose() {
        return this.verbose;
    }

    @Override
    public boolean isHelpOption() {
        return this.help;
    }

    @Override
    public boolean isVersionOption() {
        return this.version;
    }

    @Override
    public boolean isPathInfo() {
        return this.pathInfo;
    }
}
