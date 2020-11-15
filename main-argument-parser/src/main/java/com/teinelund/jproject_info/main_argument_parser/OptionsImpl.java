package com.teinelund.jproject_info.main_argument_parser;

import picocli.CommandLine;

import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Implementation class for command line argument options.
 * <p>
 * PicoCli:
 * The name attribute sets a name for the application when the help page is displayed.
 * The mixinStandardHelpOptions attribute adds --help and --version options to your application.
 * versionProvider store a class that implement a version string to be displayed when --version is given as option.
 */
@CommandLine.Command(
        name = "jproject_info",
        mixinStandardHelpOptions = true,
        versionProvider = VersionProvider.class,
        subcommands = {ProjectOptionImpl.class})
class OptionsImpl implements Options {

    @CommandLine.Parameters(description = "One or more paths to Java project folders (Maven project for instance). Required.",
            scope = CommandLine.ScopeType.INHERIT, arity = "1..*")
    private Set<Path> javaProjectPaths;

    private ProjectOption projectOption;

    @Override
    public Integer call() throws Exception {
        return CommandLine.ExitCode.OK;
    }

    @Override
    public Set<Path> getJavaProjectPaths() {
        return this.javaProjectPaths;
    }

    @Override
    public ProjectOption getProjectOption() {
        return this.projectOption;
    }

    public void setProjectOption(ProjectOption projectOption) {
        this.projectOption = projectOption;
    }
}

@CommandLine.Command(name = "project", description = "Display information about the project/projects.", mixinStandardHelpOptions = true)
class ProjectOptionImpl implements Callable<Integer>, ProjectOption {

    @CommandLine.Option(names = {"-p", "--path-info"}, description = "Display shallow project information for each input path. Required.", required = true)
    boolean typeOfProject = false;

    @CommandLine.ParentCommand
    private OptionsImpl parent;

    @Override
    public Integer call() throws Exception {
        this.parent.setProjectOption(this);
        return CommandLine.ExitCode.OK;
    }

    public boolean isTypeOfProject() {
        return this.typeOfProject;
    }
}