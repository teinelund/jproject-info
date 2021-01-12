package com.teinelund.jproject_info.context;

import com.beust.jcommander.JCommander;
import com.teinelund.jproject_info.command_line_parameters_parser.Parameters;

import java.nio.file.Path;
import java.util.Collection;

public interface Context extends OptionsContext, ProjectInformationContext {
    public Collection<Project> getProjects();

    void setCommandLineArguments(String[] args);

    String[] getCommandLineArguments();

    Parameters getParameters();

    void setJCommander(JCommander jc);

    JCommander getJCommander();
}
