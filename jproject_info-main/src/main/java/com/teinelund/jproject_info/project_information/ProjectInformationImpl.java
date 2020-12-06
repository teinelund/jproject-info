package com.teinelund.jproject_info.project_information;

import com.teinelund.jproject_info.common.Project;
import com.teinelund.jproject_info.common.ProjectFactory;
import com.teinelund.jproject_info.common.ProjectInformationContext;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.NameFileFilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedList;

class ProjectInformationImpl implements ProjectInformation {

    private ProjectInformationContext context;

    public ProjectInformationImpl(ProjectInformationContext context) {
        this.context = context;
    }

    @Override
    public void fetchProjects() throws IOException {
        Collection<Project> projects = new LinkedList<>();
        for (Path path : context.getProjectPaths()) {
            fetchProject(path, projects);
        }
        context.setProjects(projects);
    }

    void fetchProject(Path path, Collection<Project> projects) throws IOException {
        if (isMavenProject(path)) {
            projects.add(ProjectFactory.createMavenProject(path));
        }
        else if (isSourceCodePath(path)) {
            context.addUnknownJavaProject(path);
            return;
        }
        else {
            Collection<File> files = FileUtils.listFilesAndDirs(path.toFile(), CanReadFileFilter.CAN_READ, DirectoryFileFilter.DIRECTORY);
            for (File file : files) {
                if (file.toPath().equals(path)) {
                    continue;
                }
                if (Files.isDirectory(file.toPath())) {
                    fetchProject(file.toPath(), projects);
                }
            }
        }
    }

    boolean isMavenProject(Path path) throws IOException {
        Collection<File> files = FileUtils.listFiles(path.toFile(), new NameFileFilter("pom.xml"), null);
        return !files.isEmpty();
    }

    boolean isSourceCodePath(Path path) {
        String[] filteExtenstionToLookFoor = {"java"};
        Collection<File> files = FileUtils.listFiles(path.toFile(), filteExtenstionToLookFoor, false);
        return !files.isEmpty();
    }
}
