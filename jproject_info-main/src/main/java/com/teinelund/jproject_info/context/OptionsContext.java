package com.teinelund.jproject_info.context;

import java.nio.file.Path;
import java.util.Set;

public interface OptionsContext {
    public void setProjectPaths(Set<Path> projectPaths);
}
