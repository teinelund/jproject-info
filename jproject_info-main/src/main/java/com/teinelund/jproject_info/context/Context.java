package com.teinelund.jproject_info.context;

import java.nio.file.Path;
import java.util.Collection;

public interface Context extends OptionsContext, ProjectInformationContext {
    public Collection<Project> getProjects();
    Collection<Path> getUnknownJavaProjectPaths();
}
