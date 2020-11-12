package com.teinelund.jproject_info.common;

import java.util.Collection;

public interface Context extends OptionsContext, ProjectInformationContext {
    public Collection<Project> getProjects();
}
