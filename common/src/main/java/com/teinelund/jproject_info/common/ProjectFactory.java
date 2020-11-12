package com.teinelund.jproject_info.common;

import java.nio.file.Path;

public class ProjectFactory {
    public static Project createMavenProject(Path path) {
        return new MavenProject(path);
    }
}
