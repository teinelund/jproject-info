package com.teinelund.jproject_info.context;

import com.teinelund.jproject_info.command_line_parameters_parser.Parameters;

import java.nio.file.Path;
import java.util.Collection;

public interface Context extends OptionsContext, ProjectInformationContext {
    public Collection<Project> getProjects();
    Collection<Path> getUnknownJavaProjectPaths();

    void setCommandLineArguments(String[] args);

    String[] getCommandLineArguments();

    Parameters getParameters();
}
