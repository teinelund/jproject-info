package com.teinelund.jproject_info.main_argument_parser;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Unit test for simple App.
 */
public class OptionsImplTest {

    private static FileSystem fs = null;
    private static OptionsImpl sut = null;
    private static Path projectsPath = null;
    private static Path javaProject_1_Path = null;
    private static Path javaProject_2_Path = null;
    private static Path javaProject_1_pom_xml_file_path = null;
    private static Path javaProject_2_pom_xml_file_Path = null;
    private static Path javaProject_3_Path = null;
    private static Path javaProject_4_Path = null;


    @BeforeAll
    static void init() throws IOException {
        sut = new OptionsImpl();

        fs = Jimfs.newFileSystem(Configuration.unix());
        projectsPath = fs.getPath("/Users/Cody/Projects");
        if (!Files.exists(projectsPath)) {
            Files.createDirectories(projectsPath);
        }
        javaProject_1_Path = fs.getPath("/Users/Cody/Projects/JavaProject1");
        if (!Files.exists(javaProject_1_Path)) {
            Files.createDirectories(javaProject_1_Path);
        }
        javaProject_1_pom_xml_file_path = fs.getPath("/Users/Cody/Projects/JavaProject1/pom.xml");
        if (!Files.exists(javaProject_1_pom_xml_file_path)) {
            Files.createFile(javaProject_1_pom_xml_file_path);
        }
        javaProject_2_Path = fs.getPath("/Users/Cody/Projects/JavaProject2");
        if (!Files.exists(javaProject_2_Path)) {
            Files.createDirectories(javaProject_2_Path);
        }
        javaProject_2_pom_xml_file_Path = fs.getPath("/Users/Cody/Projects/JavaProject2/pom.xml");
        if (!Files.exists(javaProject_2_pom_xml_file_Path)) {
            Files.createFile(javaProject_2_pom_xml_file_Path);
        }
        javaProject_3_Path = Paths.get("/Some/Path/That/Does/Not/Exist_1");
        javaProject_4_Path = Paths.get("/Some/Other/Path/That/Does/Not/Exist_2");
    }

    Set<Path> createSetOfPaths(Path ... paths) {
        Set<Path> pathSet = new LinkedHashSet<>();
        if (Objects.nonNull(paths)) {
            Stream.of(paths).forEach(path -> pathSet.add(path));
        }
        return pathSet;
    }


    @Test
    void verifyJavaProjectPathsWhereArgumentContainsOneDirectoryThatExist() throws IOException {
        // Initialize
        Set<Path> javaProjectPaths = createSetOfPaths(javaProject_1_Path);
        // Test
        List<NonValidJavaProjectPath> result = sut.verifyJavaProjectPaths(javaProjectPaths);
        // Verify
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void verifyJavaProjectPathsWhereArgumentContainsOneDirectoryThatDoesNotExist() throws IOException {
        // Initialize
        Set<Path> javaProjectPaths = createSetOfPaths(javaProject_3_Path);
        // Test
        List<NonValidJavaProjectPath> result = sut.verifyJavaProjectPaths(javaProjectPaths);
        // Verify
        assertThat(result.isEmpty()).isFalse();
        NonValidJavaProjectPath firstResultInList = result.get(0);
        assertThat(firstResultInList.getIndex()).isEqualTo(0);
        assertThat(firstResultInList.getJavaProjectPath()).isEqualTo(javaProject_3_Path);
        assertThat(firstResultInList.getErrorString()).contains("does not exist. Check spelling.");
    }

    @Test
    void verifyJavaProjectPathsWhereArgumentContainsOnePathToAFile() throws IOException {
        // Initialize
        Set<Path> javaProjectPaths = createSetOfPaths(javaProject_1_pom_xml_file_path);
        // Test
        List<NonValidJavaProjectPath> result = sut.verifyJavaProjectPaths(javaProjectPaths);
        // Verify
        assertThat(result.isEmpty()).isFalse();
        assertThat(result.size()).isEqualTo(1);
        NonValidJavaProjectPath firstResultInList = result.get(0);
        assertThat(firstResultInList.getIndex()).isEqualTo(0);
        assertThat(firstResultInList.getJavaProjectPath()).isEqualTo(javaProject_1_pom_xml_file_path);
        assertThat(firstResultInList.getErrorString()).contains("is not a directory.");
    }

    @Test
    void verifyJavaProjectPathsWhereArgumentContainsTwoDirectoriesThatExist() throws IOException {
        // Initialize
        Set<Path> javaProjectPaths = createSetOfPaths(javaProject_2_Path, javaProject_1_Path);
        // Test
        List<NonValidJavaProjectPath> result = sut.verifyJavaProjectPaths(javaProjectPaths);
        // Verify
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void verifyJavaProjectPathsWhereArgumentContainsTwoDirectoriesThatDoesNotExist() throws IOException {
        // Initialize
        Set<Path> javaProjectPaths = createSetOfPaths(javaProject_3_Path, javaProject_4_Path);
        // Test
        List<NonValidJavaProjectPath> result = sut.verifyJavaProjectPaths(javaProjectPaths);
        // Verify
        assertThat(result.isEmpty()).isFalse();
        assertThat(result.size()).isEqualTo(2);
        boolean index_zero_found = false;
        boolean index_one_found = false;
        boolean javaProject_3_path_found = false;
        boolean javaProject_4_path_found = false;
        int number_of_string_does_not_exist_found = 0;
        // Order should not be important in result ..
        for (NonValidJavaProjectPath nonValidJavaProjectPath : result) {
            if (nonValidJavaProjectPath.getJavaProjectPath().toString().contains("Exist_1")) {
                index_zero_found = nonValidJavaProjectPath.getIndex() == 0;
                javaProject_3_path_found = nonValidJavaProjectPath.getJavaProjectPath().equals(javaProject_3_Path);
            }
            else if (nonValidJavaProjectPath.getJavaProjectPath().toString().contains("Exist_2")) {
                index_one_found = nonValidJavaProjectPath.getIndex() == 1;
                javaProject_4_path_found = nonValidJavaProjectPath.getJavaProjectPath().equals(javaProject_4_Path);
            }
            if (nonValidJavaProjectPath.getErrorString().contains("does not exist. Check spelling.")) {
                number_of_string_does_not_exist_found++;
            }
        }
        assertThat(index_zero_found).isTrue();
        assertThat(index_one_found).isTrue();
        assertThat(javaProject_3_path_found).isTrue();
        assertThat(javaProject_4_path_found).isTrue();
        assertThat(number_of_string_does_not_exist_found).isEqualTo(2);
    }

    @Test
    void verifyJavaProjectPathsWhereArgumentContainsTwoDirectoriesWhereOneDoesNotExist() throws IOException {
        // Initialize
        Set<Path> javaProjectPaths = createSetOfPaths(javaProject_3_Path, javaProject_2_Path);
        // Test
        List<NonValidJavaProjectPath> result = sut.verifyJavaProjectPaths(javaProjectPaths);
        // Verify
        assertThat(result.isEmpty()).isFalse();
        NonValidJavaProjectPath firstResultInList = result.get(0);
        assertThat(firstResultInList.getIndex()).isEqualTo(0);
        assertThat(firstResultInList.getJavaProjectPath()).isEqualTo(javaProject_3_Path);
        assertThat(firstResultInList.getErrorString()).contains("does not exist. Check spelling.");
    }
}
