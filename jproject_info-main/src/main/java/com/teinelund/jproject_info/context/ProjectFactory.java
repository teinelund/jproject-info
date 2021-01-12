package com.teinelund.jproject_info.context;

import java.nio.file.Path;

public class ProjectFactory {
    public static Project createMavenProject(Path rootPath, Path projectPath) {
        return new MavenProject(rootPath, projectPath);
    }

    public static Project createUnknownJavaProject(Path rootPath, Path projectPath) {
        return new UnknowJavaProject(rootPath, projectPath);
    }
}
