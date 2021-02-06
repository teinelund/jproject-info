package com.teinelund.jproject_info.strategy;

import com.teinelund.jproject_info.command_line_parameters_parser.Parameters;
import com.teinelund.jproject_info.commands.Command;
import com.teinelund.jproject_info.commands.ValidateCommandLineArgumentsCommand;
import com.teinelund.jproject_info.common.PrinterMock;
import com.teinelund.jproject_info.context.Context;
import com.teinelund.jproject_info.context.ContextModule;
import com.teinelund.jproject_info.context.MavenProject;
import com.teinelund.jproject_info.context.Project;
import com.teinelund.jproject_info.context.ProjectFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class PathInformationStrategyTest {

    private ValidateCommandLineArgumentsCommand command = new ValidateCommandLineArgumentsCommandMock();
    private PathInformationStrategy sut = null;
    private PrinterMock printer = null;
    private Context context = null;
    private ContextModule contextModule = new ContextModule();
    private Parameters parameters = null;

    @BeforeEach
    void init(TestInfo testInfo) throws IOException {
        this.context = this.contextModule.provideContext(parameters);
        this.printer = new PrinterMock();
        this.sut = new PathInformationStrategyImpl(this.context, this.printer, this.command);
    }

    @Test
    void execute(@TempDir Path dir) {
        // Initialize
        List<Project> projects = new LinkedList<>();
        projects.add(ProjectFactory.createMavenProject(Paths.get(dir.toString(), "root1"),
                Paths.get(dir.toString(), "root1", "mavenProject")));
        projects.add(ProjectFactory.createJavaSourceProject(Paths.get(dir.toString(), "root2"),
                Paths.get(dir.toString(), "root2", "mavenProject")));
        this.context.setProjects(projects);
        // Test
        this.sut.execute();
        // Verify
        int projectInformationFound = 0;
        int pathFound = 0;
        int mavenProjectFound = 0;
        int unknownJavaProjectFound = 0;
        for (String line : this.printer.getInfoWhite()) {
            if (line.startsWith("Project information:")) {
                projectInformationFound++;
            }
            else if (line.startsWith("Path '")) {
                pathFound++;
            }
            else if (line.startsWith("* Maven project at '")) {
                mavenProjectFound++;
            }
            else if (line.startsWith("* Unknown Java project at '")) {
                unknownJavaProjectFound++;
            }
        }
        assertThat(projectInformationFound).isEqualTo(1);
        assertThat(pathFound).isEqualTo(2);
        assertThat(mavenProjectFound).isEqualTo(1);
        assertThat(unknownJavaProjectFound).isEqualTo(1);
    }
}

class ValidateCommandLineArgumentsCommandMock implements ValidateCommandLineArgumentsCommand {

    @Override
    public void execute() {

    }
}