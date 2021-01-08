package com.teinelund.jproject_info.commands;

import com.teinelund.jproject_info.context.Context;
import com.teinelund.jproject_info.controller.Controller;
import org.fusesource.jansi.Ansi;

import javax.inject.Inject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.fusesource.jansi.Ansi.ansi;


class ValidateCommandLineArgumentsCommandImpl extends AbstractCommand implements ValidateCommandLineArgumentsCommand {

    Controller controller;
    @Inject
    public ValidateCommandLineArgumentsCommandImpl(Context context, Controller controller) {
        super(context);
        this.controller = controller;
    }

    @Override
    public void execute() {

        if (this.context.getParameters().isVerbose()) {
            System.out.println( ansi().fg(Ansi.Color.MAGENTA).a("Verbose output is enabled.").toString());
        }

        if (!this.context.getParameters().isHelpOption() && !this.context.getParameters().isVersionOption()) {
            verboseOutput(this.context.getParameters(), "Help and Version options is not given in command line arguments.");

            verboseOutput(this.context.getParameters(), "Validate java project paths.");
            List<NonValidJavaProjectPath> nonValidJavaProjectPaths = validateJavaProjectPaths(this.context.getParameters().getJavaProjectPaths());
            if (!nonValidJavaProjectPaths.isEmpty()) {
                printErrorMessage(nonValidJavaProjectPaths);
                throw new ValidateCommandLineArgumentsException();
            }
            verboseOutput(this.context.getParameters(), "Java project paths exists and are valid directories.");
        }

        this.controller.execute();
    }

    List<NonValidJavaProjectPath> validateJavaProjectPaths(Set<Path> javaProjectPaths) {
        int index = 0;
        List<NonValidJavaProjectPath> nonValidJavaProjectPathList = new LinkedList<>();
        for (Path javaProjectPath : javaProjectPaths) {
            if (!Files.exists(javaProjectPath)) {
                nonValidJavaProjectPathList.add(new NonValidJavaProjectPath.Builder().setJavaProjectPath(javaProjectPath).
                        setIndex(index).
                        setErrorString("Path \'" + javaProjectPath.toString() + "\' does not exist. Check spelling.").build());
            } else if (!Files.isDirectory(javaProjectPath)) {
                nonValidJavaProjectPathList.add(new NonValidJavaProjectPath.Builder().setJavaProjectPath(javaProjectPath).
                        setIndex(index).
                        setErrorString("Path \'" + javaProjectPath.toString() + "\' is not a directory.").build());
            }
            index++;
        }
        return nonValidJavaProjectPathList;
    }

    void printErrorMessage(List<NonValidJavaProjectPath> nonValidJavaProjectPaths) {
        if (nonValidJavaProjectPaths.size() == 1) {
            System.err.println(nonValidJavaProjectPaths.get(0).getErrorString());
        } else {
            for (NonValidJavaProjectPath nonValidJavaProjectPath : nonValidJavaProjectPaths) {
                System.err.println("Option " + (nonValidJavaProjectPath.getIndex() + 1) + " : " + nonValidJavaProjectPath.getErrorString());
            }
        }
    }
}
