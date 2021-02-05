package com.teinelund.jproject_info.command_line_parameters_parser;

import java.nio.file.Path;
import java.util.Set;

public interface Parameters {
    public Set<Path> getJavaProjectPaths();
    public boolean isVerboseOption();
    public boolean isHelpOption();
    public boolean isVersionOption();
    public boolean isPathInfoOption();
    public boolean isMavenProjectInfoOption();
}
