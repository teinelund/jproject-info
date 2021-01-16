package com.teinelund.jproject_info.context;

import java.nio.file.Path;

public class ProjectFactory {
    public static Project createMavenProject(Path rootPath, Path projectPath) {
        return new MavenProjectImpl(rootPath, projectPath);
    }

    public static Project createJavaSourceProject(Path rootPath, Path projectPath) {
        return new JavaSourceProjectImpl(rootPath, projectPath);
    }
}
