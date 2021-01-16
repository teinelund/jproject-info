package com.teinelund.jproject_info.context;

import java.nio.file.Path;

class JavaSourceProjectImpl extends AbstractProject implements JavaSourceProject {
    public JavaSourceProjectImpl(Path rootPath, Path projectPath) {
        super(rootPath, projectPath);
    }
}
