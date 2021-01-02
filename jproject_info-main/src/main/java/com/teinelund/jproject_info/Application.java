package com.teinelund.jproject_info;

import javax.inject.Inject;
import com.beust.jcommander.JCommander;
import com.teinelund.jproject_info.context.Context;
import com.teinelund.jproject_info.context.ContextFactory;
import com.teinelund.jproject_info.controller.Controller;
import com.teinelund.jproject_info.controller.ControllerFactory;
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

    @Inject
    public Application(Parameters parameters) {
        this.parameters = parameters;
    }

    void execute(String[] args) throws IOException {
        AnsiConsole.systemInstall();
        // Parse command line arguments, and set prerequisite fields in object Options.
        JCommander jc = JCommander.newBuilder().
                addObject(this.parameters).
                programName("jproject_info").
                build();

        jc.parse(args);

        if (parameters.isHelpOption()) {
            // Did user request usage help (--help)?
            System.out.println( ansi().fg(Ansi.Color.WHITE).toString() );
            jc.usage();
            System.exit(0);
        } else if (parameters.isVersionOption()) {
            // Did user request version help (--version)?
            VersionProvider versionProvider = new VersionProvider();
            for (String line : versionProvider.getVersion()) {
                System.out.println(line);
            }
            System.exit(0);
        }
        if (parameters.isVerbose()) {
            System.out.println( ansi().fg(Ansi.Color.MAGENTA).a("Verbose output is enabled.").toString());
        }

        // invoke the business logic

        Controller controller = ControllerFactory.getInstance().getConttroller();
        controller.execute(parameters);

        verboseOutput(parameters, "Validate java project paths (from command line arguments)...");
        List<NonValidJavaProjectPath> nonValidJavaProjectPaths = validateJavaProjectPaths(parameters.getJavaProjectPaths());
        if (!nonValidJavaProjectPaths.isEmpty()) {
            printErrorMessage(nonValidJavaProjectPaths);
            System.exit(2);
        }

        Context context = ContextFactory.getContext();
        context.setProjectPaths(parameters.getJavaProjectPaths());
        verboseOutput(parameters, "Investigate kind of Java project (Maven project, Ant project, and so on)...");
        ProjectInformation projectInformation = ProjectInformationFactory.createProjectInformation(context);
        projectInformation.fetchProjects();


        if (parameters.isPathInfo()) {
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

    static void verboseOutput(Parameters parameters, String text) {
        if (parameters.isVerbose()) {
            System.out.println(ansi().fg(Ansi.Color.MAGENTA).a("[VERBOSE] " + text).reset().toString());
        }
    }
}
