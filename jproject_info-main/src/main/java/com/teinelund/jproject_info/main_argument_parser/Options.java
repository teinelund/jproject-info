package com.teinelund.jproject_info.main_argument_parser;

import java.nio.file.Path;
import java.util.Set;

public interface Options {
    public Set<Path> getJavaProjectPaths();
    public boolean isVerbose();
    public boolean isHelpOption();
    public boolean isVersionOption();
    public boolean isPathInfo();
}
