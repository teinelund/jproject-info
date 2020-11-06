package com.teinelund.jproject_info.project_information;

import com.teinelund.jproject_info.common.ProjectInformationContext;

import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedList;

class ProjectInformationImpl implements ProjectInformation {

    private ProjectInformationContext context;

    public ProjectInformationImpl(ProjectInformationContext context) {
        this.context = context;
    }

    @Override
    public void fetchProjects() {
        Collection<Project> projects = new LinkedList<>();
        for (Path path : context.getProjectPaths()) {
            isMavenProject(path);
        }
    }

    boolean isMavenProject(Path path) {
        return true;
    }
}
