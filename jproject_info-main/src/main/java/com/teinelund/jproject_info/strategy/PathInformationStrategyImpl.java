package com.teinelund.jproject_info.strategy;

import com.teinelund.jproject_info.commands.ProjectInformationCommand;
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

public class PathInformationStrategyImpl extends AbstractStrategy implements PathInformationStrategy {

    private ProjectInformationCommand command;

    @Inject
    public PathInformationStrategyImpl(Context context, ProjectInformationCommand command) {
        super(context);
        this.command = command;
    }

    @Override
    public void execute() {
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

        System.out.println("Project information:");
        for (Path rootPath : map.keySet()) {
            System.out.println("Path '" + rootPath.toString() + "' contains the projects:");
            List<Project> list = map.get(rootPath);
            for (Project project : list) {
                if (project instanceof MavenProject) {
                    System.out.println("* Maven project at '" + project.getProjectPath().toString() + "'.");
                }
                else if (project instanceof JavaSourceProject) {
                    System.out.println("* Unknown Java project at '" + project.getProjectPath().toString() + "'.");
                }
            }
        }
    }
}
