package com.teinelund.jproject_info.commands;

import java.util.List;

public class ValidateCommandLineArgumentsException extends RuntimeException {

    private List<NonValidJavaProjectPath> nonValidJavaProjectPaths;

    public ValidateCommandLineArgumentsException(List<NonValidJavaProjectPath> nonValidJavaProjectPaths) {
        this.nonValidJavaProjectPaths = nonValidJavaProjectPaths;
    }

    public List<NonValidJavaProjectPath> getNonValidJavaProjectPaths() {
        return this.nonValidJavaProjectPaths;
    }
}
