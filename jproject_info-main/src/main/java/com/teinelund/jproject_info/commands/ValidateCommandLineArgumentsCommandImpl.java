package com.teinelund.jproject_info.commands;

import com.teinelund.jproject_info.context.Context;
import org.fusesource.jansi.Ansi;

import javax.inject.Inject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.fusesource.jansi.Ansi.ansi;


class ValidateCommandLineArgumentsCommandImpl extends AbstractCommand implements ValidateCommandLineArgumentsCommand {

    private Command command;

    @Inject
    public ValidateCommandLineArgumentsCommandImpl(Context context, ProjectInformationCommand command) {
        super(context);
        this.command = command;
    }

    @Override
    public void execute() {

        if (this.context.getParameters().isVerboseOption()) {
            System.out.println( ansi().fg(Ansi.Color.MAGENTA).a("Verbose output is enabled.").toString());
        }

        verboseOutput(this.context.getParameters(), "Validate java project paths.");
        List<NonValidJavaProjectPath> nonValidJavaProjectPaths = validateJavaProjectPaths(this.context.getParameters().getJavaProjectPaths());
        if (!nonValidJavaProjectPaths.isEmpty()) {
            throw new ValidateCommandLineArgumentsProjectPathsException(nonValidJavaProjectPaths);
        }
        verboseOutput(this.context.getParameters(), "Java project paths exists and are valid directories.");

        int nrOfOptions = 0;
        if (this.context.getParameters().isPathInfoOption()) {
            nrOfOptions++;
        }
        if (this.context.getParameters().isMavenProjectInfoOption()) {
            nrOfOptions++;
        }
        if (nrOfOptions > 1) {
            throw new ValidateCommandLineArgumentsOptionsException("Select one of the options.");
        }

        this.command.execute();
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
}
