package com.teinelund.jproject_info.context;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;

public interface ProjectInformationContext {
    public Set<Path> getProjectPaths();
    public void setProjects(Collection<Project> projects);
    public void addUnknownJavaProject(Path path);
}
