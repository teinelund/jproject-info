package com.teinelund.jproject_info.main_argument_parser;

import picocli.CommandLine;

import java.util.concurrent.Callable;

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
