package com.teinelund.jproject_info.project_information;


import com.teinelund.jproject_info.common.Context;
import com.teinelund.jproject_info.common.Project;
import com.teinelund.jproject_info.common.ProjectInformationContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class ProjectInformationTest {
    private static ProjectInformationImpl sut = null;
    private static final String PROJECT_1 = "project1";
    private static final String PROJECT_2 = "project2";
    private static final String PROJECT_3 = "project3";
    private static final String PROJECT_4 = "project4";

    @BeforeAll
    static void init() throws IOException {
        Context context = null;
        sut = new ProjectInformationImpl(context);
    }

    @Test
    void isMavenProjectWherePathContainsAPomXmlFile(@TempDir Path path) throws IOException {
        // Initialize
        Path pom_xml_file_path = Paths.get(path.toAbsolutePath().toString() ,"pom.xml");
        if (!Files.exists(pom_xml_file_path)) {
            Files.createFile(pom_xml_file_path);
        }
        // Test
        boolean result = sut.isMavenProject(path);
        // Verify
        assertThat(result).isTrue();
    }

    @Test
    void isMavenProjectWherePathContainsAJavaSourceCodeFile(@TempDir Path path) throws IOException {
        // Initialize
        Path pom_xml_file_path = Paths.get(path.toAbsolutePath().toString() ,"customer.java");
        if (!Files.exists(pom_xml_file_path)) {
            Files.createFile(pom_xml_file_path);
        }
        // Test
        boolean result = sut.isMavenProject(path);
        // Verify
        assertThat(result).isFalse();
    }

    @Test
    void isMavenProjectWherePathDoesNotContainAPomXmlFile(@TempDir Path path) throws IOException {
        // Initialize
        // Test
        boolean result = sut.isMavenProject(path);
        // Verify
        assertThat(result).isFalse();
    }

    @Test
    void isSourceCodePathtWherePathContainsAJavaSourceCodeFile(@TempDir Path path) throws IOException {
        // Initialize
        Path pom_xml_file_path = Paths.get(path.toAbsolutePath().toString() ,"customer.java");
        if (!Files.exists(pom_xml_file_path)) {
            Files.createFile(pom_xml_file_path);
        }
        // Test
        boolean result = sut.isSourceCodePath(path);
        // Verify
        assertThat(result).isTrue();
    }

    @Test
    void isSourceCodePathtWherePathContainsAPomXmlFile(@TempDir Path path) throws IOException {
        // Initialize
        Path pom_xml_file_path = Paths.get(path.toAbsolutePath().toString() ,"pom.xml");
        if (!Files.exists(pom_xml_file_path)) {
            Files.createFile(pom_xml_file_path);
        }
        // Test
        boolean result = sut.isSourceCodePath(path);
        // Verify
        assertThat(result).isFalse();
    }

    @Test
    void isSourceCodePathWherePathDoesNotContainAPomXmlFile(@TempDir Path path) throws IOException {
        // Initialize
        // Test
        boolean result = sut.isSourceCodePath(path);
        // Verify
        assertThat(result).isFalse();
    }


    @Test
    void fetchProjectWherePathIsAMavenProject(@TempDir Path path) throws IOException {
        // Initialize
        Path project1Path = createDirectory(path, PROJECT_1);
        ProjectInformationImpl sutEx = new ProjectInformationImplMock(null);
        List<Project> projects = new LinkedList<>();
        // Test
        sutEx.fetchProject(project1Path, projects);
        // Verify
        assertThat(projects.isEmpty()).isFalse();
        assertThat(projects.size()).isEqualTo(1);
        assertThat(projects.get(0).getProjectPath()).isEqualTo(project1Path);
    }

    @Test
    void fetchProjectWherePathIsSourceCodePath(@TempDir Path path) throws IOException {
        // Initialize
        Path project3Path = createDirectory(path, PROJECT_3);
        ProjectInformationImpl sutEx = new ProjectInformationImplMock(null);
        List<Project> projects = new LinkedList<>();
        // Test
        sutEx.fetchProject(project3Path, projects);
        // Verify
        assertThat(projects.isEmpty()).isTrue();
    }

    @Test
    void fetchProjectWhereProject1AndProject2AreAMavenProjects(@TempDir Path path) throws IOException {
        // Initialize
        Path project1Path = createDirectory(path, PROJECT_1);
        Path project2Path = createDirectory(path, PROJECT_2);
        ProjectInformationImpl sutEx = new ProjectInformationImplMock(null);
        List<Project> projects = new LinkedList<>();
        // Test
        sutEx.fetchProject(path, projects);
        // Verify
        assertThat(projects.isEmpty()).isFalse();
        assertThat(projects.size()).isEqualTo(2);
    }

    @Test
    void fetchProjectWhereOneProjectIsAMavenProjects_1(@TempDir Path path) throws IOException {
        // Initialize
        Path project1Path = createDirectory(path, PROJECT_1);
        Path project3Path = createDirectory(path, PROJECT_3);
        ProjectInformationImpl sutEx = new ProjectInformationImplMock(null);
        List<Project> projects = new LinkedList<>();
        // Test
        sutEx.fetchProject(path, projects);
        // Verify
        assertThat(projects.isEmpty()).isFalse();
        assertThat(projects.size()).isEqualTo(1);
        assertThat(projects.get(0).getProjectPath()).isEqualTo(project1Path);
    }

    @Test
    void fetchProjectWhereOneProjectIsAMavenProjects_2(@TempDir Path path) throws IOException {
        // Initialize
        Path project3Path = createDirectory(path, PROJECT_4);
        Path project2Path = createDirectory(path, PROJECT_2);
        ProjectInformationImpl sutEx = new ProjectInformationImplMock(null);
        List<Project> projects = new LinkedList<>();
        // Test
        sutEx.fetchProject(path, projects);
        // Verify
        assertThat(projects.isEmpty()).isFalse();
        assertThat(projects.size()).isEqualTo(1);
        assertThat(projects.get(0).getProjectPath()).isEqualTo(project2Path);
    }

    @Test
    void fetchProjectWhereNoneIsAMavenProjects_2(@TempDir Path path) throws IOException {
        // Initialize
        Path project3Path = createDirectory(path, PROJECT_3);
        Path project4Path = createDirectory(path, PROJECT_4);
        ProjectInformationImpl sutEx = new ProjectInformationImplMock(null);
        List<Project> projects = new LinkedList<>();
        // Test
        sutEx.fetchProject(path, projects);
        // Verify
        assertThat(projects.isEmpty()).isTrue();
    }

    private Path createDirectory(Path path, String dirName) throws IOException {
        Path dirPath = Paths.get(path.toAbsolutePath().toString() ,dirName);
        if (!Files.exists(dirPath)) {
            Files.createDirectory(dirPath);
        }
        return dirPath;
    }
}

class ProjectInformationImplMock extends ProjectInformationImpl {

    private static final String PROJECT_1 = "project1";
    private static final String PROJECT_2 = "project2";
    private static final String PROJECT_3 = "project3";

    public ProjectInformationImplMock(ProjectInformationContext context) {
        super(context);
    }

    @Override
    boolean isMavenProject(Path path) throws IOException {
        return path.getFileName().toString().equals(PROJECT_1) || path.getFileName().toString().equals(PROJECT_2);
    }

    @Override
    boolean isSourceCodePath(Path path) {
        return path.getFileName().toString().equals(PROJECT_3);
    }
}
