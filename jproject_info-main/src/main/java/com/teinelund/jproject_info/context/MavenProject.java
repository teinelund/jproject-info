package com.teinelund.jproject_info.context;

import java.nio.file.Path;

class MavenProject implements Project {

    private Path projectPath;

    public MavenProject(Path path) {
        this.projectPath = path;
    }

    @Override
    public Path getProjectPath() {
        return this.projectPath;
    }
}
