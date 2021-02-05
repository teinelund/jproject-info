package com.teinelund.jproject_info.common;

import com.teinelund.jproject_info.command_line_parameters_parser.Parameters;

import java.nio.file.Path;
import java.util.Set;

public class ParametersStub implements Parameters {

    private boolean help = false;
    private boolean version = false;

    public ParametersStub(boolean help, boolean version) {
        this.help = help;
        this.version = version;
    }

    @Override
    public Set<Path> getJavaProjectPaths() {
        return null;
    }

    @Override
    public boolean isVerboseOption() {
        return false;
    }

    @Override
    public boolean isHelpOption() {
        return this.help;
    }

    @Override
    public boolean isVersionOption() {
        return this.version;
    }

    @Override
    public boolean isPathInfoOption() {
        return false;
    }

    @Override
    public boolean isMavenProjectInfoOption() {
        return false;
    }
}
