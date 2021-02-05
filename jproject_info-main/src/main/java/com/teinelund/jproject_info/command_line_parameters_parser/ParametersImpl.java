package com.teinelund.jproject_info.command_line_parameters_parser;

import com.beust.jcommander.Parameter;

import javax.inject.Inject;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Implementation class for command line parameters.
 */
class ParametersImpl implements Parameters {

    @Inject
    public ParametersImpl() {}

    private Set<Path> javaProjectPaths = new LinkedHashSet<>();
    @Parameter(description = "One or more paths to Java project folders (Maven project for instance). Required.", order = 0)
    private List<String> javaProjectPathsNames = new LinkedList<>();

    @Parameter(names = {"--path-info", "-p"}, description = "Display shallow project information for each input path.", order = 1)
    private boolean pathInfo = false;

    @Parameter(names = {"--maven-project-info", "-m"}, description = "Display Maven project information.", order = 2)
    private boolean mavenInfo = false;

    @Parameter(names = {"--verbose", "-v"}, description = "Verbose output to console.", order = 50)
    private boolean verbose = false;

    @Parameter(names = {"--help", "-h"}, help = true, order = 51)
    private boolean help = false;

    @Parameter(names = {"--version", "-V"}, order = 52)
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
    public boolean isVerboseOption() {
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
    public boolean isPathInfoOption() {
        return this.pathInfo;
    }

    @Override
    public boolean isMavenProjectInfoOption() {
        return this.mavenInfo;
    }
}
