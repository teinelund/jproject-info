package com.teinelund.jproject_info.main_argument_parser;

import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.Callable;

public interface Options extends Callable<Integer> {
    public Set<Path> getJavaProjectPaths();
    public ProjectOption getProjectOption();
    public boolean isVerbose();
}
