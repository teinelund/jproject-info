package com.teinelund.jproject_info.context;

import java.nio.file.Path;

public interface Project {

    /**
     * Project root path.
     * @return Path is the root project path.
     */
    public Path getProjectPath();

    /**
     * This path is the path given from the command line. The path given at the
     * command line might not the same as the Project root path. This path may
     * contain a few projects.
     * @return Path is the path given from the command line.
     */
    public Path getRootPath();
}
