package com.teinelund.jproject_info.context;

import java.nio.file.Path;

class MavenProject extends AbstractProject {

    public MavenProject(Path rootPath, Path projectPath) {
        super(rootPath, projectPath);
    }

}
