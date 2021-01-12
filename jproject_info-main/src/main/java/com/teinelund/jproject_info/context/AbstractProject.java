package com.teinelund.jproject_info.context;

import java.nio.file.Path;

public abstract class AbstractProject implements Project {

    private Path rootPath;
    private Path projectPath;

    public AbstractProject(Path rootPath, Path projectPath) {
        this.rootPath = rootPath;
        this.projectPath = projectPath;
    }

    public Path getRootPath() {
        return this.rootPath;
    }

    public Path getProjectPath() {
        return this.projectPath;
    }

}
