package com.teinelund.jproject_info;

import javax.inject.Inject;
import javax.inject.Named;

import com.beust.jcommander.JCommander;
import com.teinelund.jproject_info.commands.Command;
import com.teinelund.jproject_info.commands.ParseCommandLineArgumentsCommand;
import com.teinelund.jproject_info.context.Context;
import com.teinelund.jproject_info.controller.Controller;
import com.teinelund.jproject_info.command_line_parameters_parser.NonValidJavaProjectPath;
import com.teinelund.jproject_info.command_line_parameters_parser.Parameters;
import com.teinelund.jproject_info.command_line_parameters_parser.VersionProvider;
import com.teinelund.jproject_info.project_information.ProjectInformation;
import com.teinelund.jproject_info.project_information.ProjectInformationFactory;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.fusesource.jansi.Ansi.ansi;


/**
 * Main class
 */
public class Application {
    public static void main(String[] args) throws IOException {
        ApplicationComponent ac = DaggerApplicationComponent.create();
        Application application = ac.buildApplication();
        application.execute(args);
    }

    private Parameters parameters;
    private Controller controller;
    private Context context;
    private ParseCommandLineArgumentsCommand command;

    @Inject
    public Application(Parameters parameters, Controller controller, Context context, ParseCommandLineArgumentsCommand command) {
        this.parameters = parameters;
        this.controller = controller;
        this.context = context;
        this.command = command;
    }

    void execute(String[] args) throws IOException {
        AnsiConsole.systemInstall();
        this.context.setCommandLineArguments(args);
        this.command.execute();
        System.exit(0);
        // Parse command line arguments, and set prerequisite fields in object Options.
        JCommander jc = JCommander.newBuilder().
                addObject(this.parameters).
                programName("jproject_info").
                build();

        jc.parse(args);

        if (this.parameters.isHelpOption()) {
            // Did user request usage help (--help)?
            System.out.println( ansi().fg(Ansi.Color.WHITE).toString() );
            jc.usage();
            System.exit(0);
        } else if (this.parameters.isVersionOption()) {
            // Did user request version help (--version)?
            VersionProvider versionProvider = new VersionProvider();
            for (String line : versionProvider.getVersion()) {
                System.out.println(line);
            }
            System.exit(0);
        }
        if (this.parameters.isVerbose()) {
            System.out.println( ansi().fg(Ansi.Color.MAGENTA).a("Verbose output is enabled.").toString());
        }

        // invoke the business logic

        this.controller.execute(this.parameters);

        verboseOutput(this.parameters, "Validate java project paths (from command line arguments)...");
        List<NonValidJavaProjectPath> nonValidJavaProjectPaths = validateJavaProjectPaths(this.parameters.getJavaProjectPaths());
        if (!nonValidJavaProjectPaths.isEmpty()) {
            printErrorMessage(nonValidJavaProjectPaths);
            System.exit(2);
        }

        this.context.setProjectPaths(this.parameters.getJavaProjectPaths());
        verboseOutput(this.parameters, "Investigate kind of Java project (Maven project, Ant project, and so on)...");
        ProjectInformation projectInformation = ProjectInformationFactory.createProjectInformation(this.context);
        projectInformation.fetchProjects();


        if (this.parameters.isPathInfo()) {
            System.out.println("Shallow information about Java Project paths.");
            System.out.println("Maven project:");
            this.context.getProjects().forEach(project -> {
                System.out.println("* \'" + project.getProjectPath().toAbsolutePath().toString() + "\'");
            });
            if (!this.context.getUnknownJavaProjectPaths().isEmpty()) {
                System.out.println("Unknown Java project:");
                this.context.getUnknownJavaProjectPaths().forEach(unknownJavaProjectPaths -> {
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

    static void verboseOutput(Parameters parameters, String text) {
        if (parameters.isVerbose()) {
            System.out.println(ansi().fg(Ansi.Color.MAGENTA).a("[VERBOSE] " + text).reset().toString());
        }
    }
}
