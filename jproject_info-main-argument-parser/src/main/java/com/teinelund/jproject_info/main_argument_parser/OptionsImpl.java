package com.teinelund.jproject_info.main_argument_parser;

import com.teinelund.jproject_info.common.ContextFactory;
import com.teinelund.jproject_info.project_information.ProjectInformation;
import com.teinelund.jproject_info.project_information.ProjectInformationFactory;
import picocli.CommandLine;

import com.teinelund.jproject_info.common.Context;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Implementation class for command line argument options.
 * <p>
 * PicoCli:
 * The name attribute sets a name for the application when the help page is displayed.
 * The mixinStandardHelpOptions attribute adds --help and --version options to your application.
 * versionProvider store a class that implement a version string to be displayed when --version is given as option.
 */
@CommandLine.Command(name = "jproject_info", mixinStandardHelpOptions = true, versionProvider = VersionProvider.class)
class OptionsImpl implements Callable<Integer>, Options {

    @CommandLine.Parameters(description = "One or more paths to Java project folders (Maven project for instance). Required.",
            scope = CommandLine.ScopeType.INHERIT, arity = "1..*")
    Set<Path> javaProjectPaths;

    @Override
    public Integer call() throws Exception {
        int exitCode = verifyParsedOptions();
        if (exitCode == CommandLine.ExitCode.OK) {
            Context context = ContextFactory.getContext();
            context.setProjectPaths(this.javaProjectPaths);
            ProjectInformation projectInformation = ProjectInformationFactory.createProjectInformation(context);
            projectInformation.fetchProjects();
        }
        return exitCode;
    }

    int verifyParsedOptions() {
        List<NonValidJavaProjectPath> nonValidJavaProjectPaths = verifyJavaProjectPaths(javaProjectPaths);
        if (nonValidJavaProjectPaths.isEmpty()) {
            return CommandLine.ExitCode.OK;
        }
        return printErrorMessageAndCreateReturnCode(nonValidJavaProjectPaths);
    }

    List<NonValidJavaProjectPath> verifyJavaProjectPaths(Set<Path> javaProjectPaths) {
        int index = 0;
        List<NonValidJavaProjectPath> nonValidJavaProjectPathList = new LinkedList<>();
        for (Path javaProjectPath : javaProjectPaths) {
            if (!Files.exists(javaProjectPath)) {
                nonValidJavaProjectPathList.add(new NonValidJavaProjectPath.Builder().setJavaProjectPath(javaProjectPath).
                                setIndex(index).
                        setErrorString( "Path \'" + javaProjectPath.toString() + "\' does not exist. Check spelling.").build());
            } else if (!Files.isDirectory(javaProjectPath)) {
                nonValidJavaProjectPathList.add(new NonValidJavaProjectPath.Builder().setJavaProjectPath(javaProjectPath).
                        setIndex(index).
                        setErrorString( "Path \'" + javaProjectPath.toString() + "\' is not a directory.").build());
            }
            index++;
        }
        return nonValidJavaProjectPathList;
    }

    int printErrorMessageAndCreateReturnCode(List<NonValidJavaProjectPath> nonValidJavaProjectPaths) {
        if (nonValidJavaProjectPaths.size() == 1) {
            System.err.println(nonValidJavaProjectPaths.get(0).getErrorString());
        }
        else {
            for (NonValidJavaProjectPath nonValidJavaProjectPath : nonValidJavaProjectPaths) {
                System.err.println("Option " + (nonValidJavaProjectPath.getIndex() + 1) + " : " + nonValidJavaProjectPath.getErrorString());
            }
        }
        return CommandLine.ExitCode.USAGE;
    }
}