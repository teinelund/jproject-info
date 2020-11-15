package com.teinelund.jproject_info;

import com.teinelund.jproject_info.common.Context;
import com.teinelund.jproject_info.common.ContextFactory;
import com.teinelund.jproject_info.main_argument_parser.NonValidJavaProjectPath;
import com.teinelund.jproject_info.main_argument_parser.Options;
import com.teinelund.jproject_info.main_argument_parser.OptionsFactory;
import com.teinelund.jproject_info.project_information.ProjectInformation;
import com.teinelund.jproject_info.project_information.ProjectInformationFactory;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
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
        CommandLine cmd = new CommandLine(options);
        try {
            CommandLine.ParseResult parseResult = cmd.parseArgs(args);

            // Did user request usage help (--help)?
            if (cmd.isUsageHelpRequested()) {
                cmd.usage(cmd.getOut());
                System.exit(cmd.getCommandSpec().exitCodeOnUsageHelp());

                // Did user request version help (--version)?
            } else if (cmd.isVersionHelpRequested()) {
                cmd.printVersionHelp(cmd.getOut());
                System.exit(cmd.getCommandSpec().exitCodeOnVersionHelp());
            }

            // invoke the business logic
            cmd = new CommandLine(options);
            int exitcode = cmd.execute(args);
            List<NonValidJavaProjectPath> nonValidJavaProjectPaths = verifyJavaProjectPaths(options.getJavaProjectPaths());
            if (!nonValidJavaProjectPaths.isEmpty()) {
                printErrorMessage(nonValidJavaProjectPaths);
                System.exit(2);
            }

            Context context = ContextFactory.getContext();
            context.setProjectPaths(options.getJavaProjectPaths());
            ProjectInformation projectInformation = ProjectInformationFactory.createProjectInformation(context);
            projectInformation.fetchProjects();


            if (Objects.nonNull(options.getProjectOption())) {
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
        catch (CommandLine.ParameterException ex) {
            // invalid user input: print error message and usage help
            cmd.getErr().println(ex.getMessage());
            if (!CommandLine.UnmatchedArgumentException.printSuggestions(ex, cmd.getErr())) {
                ex.getCommandLine().usage(cmd.getErr());
            }
            System.exit(cmd.getCommandSpec().exitCodeOnInvalidInput());
        }
        catch (Exception ex) {
            // exception occurred in business logic
            ex.printStackTrace(cmd.getErr());
            System.exit(cmd.getCommandSpec().exitCodeOnExecutionException());
        }
    }

    List<NonValidJavaProjectPath> verifyJavaProjectPaths(Set<Path> javaProjectPaths) {
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
