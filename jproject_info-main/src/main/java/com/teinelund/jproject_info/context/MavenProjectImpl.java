package com.teinelund.jproject_info.context;

import java.nio.file.Path;

class MavenProjectImpl extends AbstractProject implements MavenProject {

    public MavenProjectImpl(Path rootPath, Path projectPath) {
        super(rootPath, projectPath);
    }

}
