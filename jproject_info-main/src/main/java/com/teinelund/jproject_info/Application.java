package com.teinelund.jproject_info;

import com.beust.jcommander.JCommander;
import com.teinelund.jproject_info.common.Context;
import com.teinelund.jproject_info.common.ContextFactory;
import com.teinelund.jproject_info.main_argument_parser.NonValidJavaProjectPath;
import com.teinelund.jproject_info.main_argument_parser.Options;
import com.teinelund.jproject_info.main_argument_parser.OptionsFactory;
import com.teinelund.jproject_info.project_information.ProjectInformation;
import com.teinelund.jproject_info.project_information.ProjectInformationFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Main class
 */
public class Application {
    public static void main(String[] args) throws IOException {
        Application application = new Application();
        application.execute(args);
    }

    void execute(String[] args) throws IOException {
        Options options = OptionsFactory.createOptions();
        // Parse command line arguments, and set prerequisite fields in object Options.
        JCommander jc = JCommander.newBuilder().
                addObject(options).
                programName("jproject_info").
                build();

        jc.parse(args);

        // Did user request usage help (--help)?
        if (options.isHelpOption()) {
            jc.usage();
            System.exit(0);
            // Did user request version help (--version)?
        } else if (options.isVersionOption()) {
            System.out.println("Version: x");
            System.exit(0);
        }

        // invoke the business logic

        if (options.isVerbose()) {
            System.out.println("Validate java project paths (from command line arguments)...");
        }
        List<NonValidJavaProjectPath> nonValidJavaProjectPaths = validateJavaProjectPaths(options.getJavaProjectPaths());
        if (!nonValidJavaProjectPaths.isEmpty()) {
            printErrorMessage(nonValidJavaProjectPaths);
            System.exit(2);
        }

        Context context = ContextFactory.getContext();
        context.setProjectPaths(options.getJavaProjectPaths());
        if (options.isVerbose()) {
            System.out.println("Investigate kind of Java project (Maven project, Ant project, and so on)...");
        }
        ProjectInformation projectInformation = ProjectInformationFactory.createProjectInformation(context);
        projectInformation.fetchProjects();


        if (options.isPathInfo()) {
            System.out.println("Shallow information about Java Project paths.");
            System.out.println("Maven project:");
            context.getProjects().forEach(project -> {
                System.out.println("* \'" + project.getProjectPath().toAbsolutePath().toString() + "\'");
            });
            if (!context.getUnknownJavaProjectPaths().isEmpty()) {
                System.out.println("Unknown Java project:");
                context.getUnknownJavaProjectPaths().forEach(unknownJavaProjectPaths -> {
                    System.out.println("* \'" + unknownJavaProjectPaths.toAbsolutePath().toString() + "\'");
                });
            }
        }

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
