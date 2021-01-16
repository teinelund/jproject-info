package com.teinelund.jproject_info.commands;

import com.teinelund.jproject_info.command_line_parameters_parser.Parameters;
import com.teinelund.jproject_info.context.Context;
import com.teinelund.jproject_info.context.JavaSourceProject;
import com.teinelund.jproject_info.context.MavenProject;
import com.teinelund.jproject_info.context.Project;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProjectInformationCommandTest {

    private static ProjectInformationCommandImpl sut = null;
    private static String path1Name = "Dir1";
    private static Path path1 = null;
    private static String path2Name = "Dir1/../Dir1";
    private static Path path2 = null;
    private static String path3Name = "/Users/cody/Develop/invoice";
    private static Path path3 = null;
    private static String path4Name = "/Users/cody/Develop/order";
    private static Path path4 = null;
    private static String path5Name = "/Users/cody/Develop";
    private static Path path5 = null;
    private static final String PROJECT = "project";
    private static final String PROJECT_1 = "project1";
    private static final String PROJECT_2 = "project2";

    @BeforeAll
    static void init() throws IOException {
        ContextTestComponent component = DaggerContextTestComponent.create();
        sut = new ProjectInformationCommandImplMock(component.buildContext());
        path1 = Paths.get(path1Name);
        path2 = Paths.get(path2Name);
        path3 = Paths.get(path3Name);
        path4 = Paths.get(path4Name);
        path5 = Paths.get(path5Name);
    }

    @Test
    public void normalizePathsWherePathContainsOnePath() {
        // Initialize
        Set<Path> paths = new LinkedHashSet<>();
        paths.add(path1);
        // Test
        Set<Path> result = sut.normalizePaths(paths);
        Path[] resultArray = result.toArray(Path[]::new);
        // Verify
        assertThat(result.isEmpty()).isFalse();
        assertThat(result.size()).isEqualTo(1);
        assertThat(resultArray[0].toString()).contains(path1Name);
        assertThat(resultArray[0].toString()).startsWith("/");
    }

    @Test
    public void normalizePathsWherePathContainsTwoPath() {
        // Initialize
        Set<Path> paths = new LinkedHashSet<>();
        paths.add(path1);
        paths.add(path2);
        // Test
        Set<Path> result = sut.normalizePaths(paths);
        Path[] resultArray = result.toArray(Path[]::new);
        // Verify
        assertThat(result.isEmpty()).isFalse();
        assertThat(result.size()).isEqualTo(1);
        assertThat(resultArray[0].toString()).contains(path1Name);
        assertThat(resultArray[0].toString()).startsWith("/");
    }

    @Test
    public void verifyNoSubPathsWithOnePath() {
        // Initialize
        Set<Path> paths = new LinkedHashSet<>();
        paths.add(path3);
        // Test
        sut.verifyNoSubPaths(paths);
        // Verify
    }

    @Test
    public void verifyNoSubPathsWhereTwoPathsAreDifferent() {
        // Initialize
        Set<Path> paths = new LinkedHashSet<>();
        paths.add(path3);
        paths.add(path4);
        // Test
        sut.verifyNoSubPaths(paths);
        // Verify
    }

    @Test
    public void verifyNoSubPathsWhereTwoPathsAreDubPath() {
        // Initialize
        Set<Path> paths = new LinkedHashSet<>();
        paths.add(path4);
        paths.add(path5);
        // Test
        Exception exception = assertThrows(SubPathException.class, () ->
                sut.verifyNoSubPaths(paths));
        // Verify
        assertThat(exception.getMessage()).contains("' is a sub path of '");
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
        boolean result = sut.isJavaSourceProject(path);
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
        boolean result = sut.isJavaSourceProject(path);
        // Verify
        assertThat(result).isFalse();
    }

    @Test
    void isSourceCodePathWherePathDoesNotContainAPomXmlFile(@TempDir Path path) throws IOException {
        // Initialize
        // Test
        boolean result = sut.isJavaSourceProject(path);
        // Verify
        assertThat(result).isFalse();
    }


    @Test
    void fetchProjectWherePathIsAMavenProject(@TempDir Path path) throws IOException {
        // Initialize
        Path project1Path = Paths.get(path.toString(), PROJECT_1);
        ContextTestComponent component = DaggerContextTestComponent.create();
        ProjectInformationCommandImplMock2 sutEx = new ProjectInformationCommandImplMock2(component.buildContext());
        sutEx.add(PROJECT_1, new ProjectTypes(true, false));
        List<Project> projects = new LinkedList<>();
        // Test
        sutEx.fetchProject(path, project1Path, projects);
        // Verify
        assertThat(projects.isEmpty()).isFalse();
        assertThat(projects.size()).isEqualTo(1);
        assertThat(projects.get(0)).isInstanceOf(MavenProject.class);
        assertThat(projects.get(0).getProjectPath()).isEqualTo(project1Path);
        assertThat(projects.get(0).getRootPath()).isEqualTo(path);
    }

    @Test
    void fetchProjectWherePathIsAJavaSourceProject(@TempDir Path path) throws IOException {
        // Initialize
        Path project1Path = Paths.get(path.toString(), PROJECT_1);
        ContextTestComponent component = DaggerContextTestComponent.create();
        ProjectInformationCommandImplMock2 sutEx = new ProjectInformationCommandImplMock2(component.buildContext());
        sutEx.add(PROJECT_1, new ProjectTypes(false, true));
        List<Project> projects = new LinkedList<>();
        // Test
        sutEx.fetchProject(path, project1Path, projects);
        // Verify
        assertThat(projects.isEmpty()).isFalse();
        assertThat(projects.size()).isEqualTo(1);
        assertThat(projects.get(0)).isInstanceOf(JavaSourceProject.class);
        assertThat(projects.get(0).getProjectPath()).isEqualTo(project1Path);
        assertThat(projects.get(0).getRootPath()).isEqualTo(path);
    }

    @Test
    void fetchProjectWherePathContainsBothMavenAndJavaSourceProject(@TempDir Path path) throws IOException {
        // Initialize
        Path projectPath = Paths.get(path.toAbsolutePath().toString(), PROJECT);
        if (!Files.exists(projectPath)) {
            Files.createDirectories(projectPath);
        }
        Path project1Path = Paths.get(projectPath.toString(), PROJECT_1);
        if (!Files.exists(project1Path)) {
            Files.createDirectories(project1Path);
        }
        Path project2Path = Paths.get(projectPath.toString(), PROJECT_2);
        if (!Files.exists(project2Path)) {
            Files.createDirectories(project2Path);
        }
        ContextTestComponent component = DaggerContextTestComponent.create();
        ProjectInformationCommandImplMock2 sutEx = new ProjectInformationCommandImplMock2(component.buildContext());
        sutEx.add(PROJECT, new ProjectTypes(false, false));
        sutEx.add(PROJECT_1, new ProjectTypes(true, false));
        sutEx.add(PROJECT_2, new ProjectTypes(false, true));
        List<Project> projects = new LinkedList<>();
        // Test
        sutEx.fetchProject(path, projectPath, projects);
        // Verify
        assertThat(projects.isEmpty()).isFalse();
        assertThat(projects.size()).isEqualTo(2);
        assertThat(projects.get(0)).isInstanceOf(MavenProject.class);
        assertThat(projects.get(0).getProjectPath()).isEqualTo(project1Path);
        assertThat(projects.get(0).getRootPath()).isEqualTo(path);
        assertThat(projects.get(1)).isInstanceOf(JavaSourceProject.class);
        assertThat(projects.get(1).getProjectPath()).isEqualTo(project2Path);
        assertThat(projects.get(1).getRootPath()).isEqualTo(path);
    }
}

class ProjectInformationCommandImplMock extends ProjectInformationCommandImpl {

    public ProjectInformationCommandImplMock(Context context) {
        super(context);
    }

    @Override
    public void verboseOutput(Parameters parameters, String text) {

    }
}

class ProjectInformationCommandImplMock2 extends ProjectInformationCommandImpl {

    private HashMap<String, ProjectTypes> map = new LinkedHashMap();

    public ProjectInformationCommandImplMock2(Context context) {
        super(context);
    }

    public void add(String key, ProjectTypes value) {
        this.map.put(key, value);
    }

    @Override
    public void verboseOutput(Parameters parameters, String text) {

    }

    @Override
    boolean isMavenProject(Path path) {
        String key = path.getFileName().toString();
        return this.map.get(key).isMavenProject();
    }

    @Override
    boolean isJavaSourceProject(Path path) {
        String key = path.getFileName().toString();
        return this.map.get(key).isSourceCodePath();
    }
}

class ProjectTypes {
    private boolean isMavenProject;
    private boolean isJavaSourceProject;

    public ProjectTypes(boolean isMavenProject, boolean isJavaSourceProject) {
        this.isMavenProject = isMavenProject;
        this.isJavaSourceProject = isJavaSourceProject;
    }

    boolean isMavenProject() {
        return this.isMavenProject;
    }

    boolean isSourceCodePath() {
        return this.isJavaSourceProject;
    }
}