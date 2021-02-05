package com.teinelund.jproject_info.commands;

import java.util.List;

public class ValidateCommandLineArgumentsProjectPathsException extends RuntimeException {

    private List<NonValidJavaProjectPath> nonValidJavaProjectPaths;

    public ValidateCommandLineArgumentsProjectPathsException(List<NonValidJavaProjectPath> nonValidJavaProjectPaths) {
        this.nonValidJavaProjectPaths = nonValidJavaProjectPaths;
    }

    public List<NonValidJavaProjectPath> getNonValidJavaProjectPaths() {
        return this.nonValidJavaProjectPaths;
    }
}
