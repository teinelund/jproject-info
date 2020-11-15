package com.teinelund.jproject_info.common;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;

public interface ProjectInformationContext {
    public Set<Path> getProjectPaths();
    void setProjects(Collection<Project> projects);
    void addUnknownJavaProject(Path path);
}
