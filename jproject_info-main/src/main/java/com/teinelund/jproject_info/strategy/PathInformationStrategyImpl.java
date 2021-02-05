package com.teinelund.jproject_info.strategy;

import com.teinelund.jproject_info.commands.Command;
import com.teinelund.jproject_info.commands.ProjectInformationCommand;
import com.teinelund.jproject_info.commands.ValidateCommandLineArgumentsCommand;
import com.teinelund.jproject_info.common.Printer;
import com.teinelund.jproject_info.context.Context;
import com.teinelund.jproject_info.context.JavaSourceProject;
import com.teinelund.jproject_info.context.MavenProject;
import com.teinelund.jproject_info.context.Project;

import javax.inject.Inject;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class PathInformationStrategyImpl extends AbstractStrategy implements PathInformationStrategy {

    private Command command;

    @Inject
    public PathInformationStrategyImpl(Context context, Printer printer, ValidateCommandLineArgumentsCommand command) {
        super(context, printer);
        this.command = command;
    }

    @Override
    public void execute() {
        this.printer.verbose("PathInformationStrategy.execute()");
        this.command.execute();
        Map<Path, List<Project>> map = new HashMap();
        for (Project project : this.context.getProjects()) {
            List<Project> list;
            if (map.containsKey(project.getRootPath())) {
                list = map.get(project.getRootPath());
            }
            else {
                list = new LinkedList<>();
                map.put(project.getRootPath(), list);
            }
            list.add(project);
        }

        this.printer.infoWhite("Project information:");
        for (Path rootPath : map.keySet()) {
            this.printer.infoWhite("Path '" + rootPath.toString() + "' contains the projects:");
            List<Project> list = map.get(rootPath);
            for (Project project : list) {
                if (project instanceof MavenProject) {
                    this.printer.infoWhite("* Maven project at '" + project.getProjectPath().toString() + "'.");
                }
                else if (project instanceof JavaSourceProject) {
                    this.printer.infoWhite("* Unknown Java project at '" + project.getProjectPath().toString() + "'.");
                }
            }
        }
    }
}
