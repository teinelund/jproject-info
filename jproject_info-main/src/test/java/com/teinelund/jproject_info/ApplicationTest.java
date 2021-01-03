package com.teinelund.jproject_info;

import static org.assertj.core.api.Assertions.assertThat;

import com.teinelund.jproject_info.commands.NonValidJavaProjectPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;


public class ApplicationTest {

    private static Application sut = null;


    @BeforeAll
    static void init() throws IOException {
        sut = new Application(null, null, null, null);
    }

    Set<Path> createSetOfPaths(Path ... paths) {
        Set<Path> pathSet = new LinkedHashSet<>();
        if (Objects.nonNull(paths)) {
            Stream.of(paths).forEach(path -> pathSet.add(path));
        }
        return pathSet;
    }


/*    @Test
    void verifyJavaProjectPathsWhereArgumentContainsOneDirectoryThatExist(@TempDir Path tempDir) throws IOException {
        // Initialize
        Set<Path> javaProjectPaths = createSetOfPaths(tempDir);
        // Test
        List<NonValidJavaProjectPath> result = sut.validateJavaProjectPaths(javaProjectPaths);
        // Verify
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void verifyJavaProjectPathsWhereArgumentContainsOneDirectoryThatDoesNotExist() throws IOException {
        // Initialize
        Path pathThatDoesNotExist = Paths.get("/Some/Path/That/Does/Not/Exist");
        Set<Path> javaProjectPaths = createSetOfPaths(pathThatDoesNotExist);
        // Test
        List<NonValidJavaProjectPath> result = sut.validateJavaProjectPaths(javaProjectPaths);
        // Verify
        assertThat(result.isEmpty()).isFalse();
        NonValidJavaProjectPath firstResultInList = result.get(0);
        assertThat(firstResultInList.getIndex()).isEqualTo(0);
        assertThat(firstResultInList.getJavaProjectPath()).isEqualTo(pathThatDoesNotExist);
        assertThat(firstResultInList.getErrorString()).contains("does not exist. Check spelling.");
    }

    @Test
    void verifyJavaProjectPathsWhereArgumentContainsOnePathToAFile(@TempDir Path tempDir) throws IOException {
        // Initialize
        Path pom_xml_file_path = Paths.get(tempDir.toAbsolutePath().toString() ,"pom.xml");
        if (!Files.exists(pom_xml_file_path)) {
            Files.createFile(pom_xml_file_path);
        }
        Set<Path> javaProjectPaths = createSetOfPaths(pom_xml_file_path);
        // Test
        List<NonValidJavaProjectPath> result = sut.validateJavaProjectPaths(javaProjectPaths);
        // Verify
        assertThat(result.isEmpty()).isFalse();
        assertThat(result.size()).isEqualTo(1);
        NonValidJavaProjectPath firstResultInList = result.get(0);
        assertThat(firstResultInList.getIndex()).isEqualTo(0);
        assertThat(firstResultInList.getJavaProjectPath()).isEqualTo(pom_xml_file_path);
        assertThat(firstResultInList.getErrorString()).contains("is not a directory.");
    }

    @Test
    void verifyJavaProjectPathsWhereArgumentContainsTwoDirectoriesThatExist(@TempDir Path tempDir) throws IOException {
        // Initialize
        Path project_1_path = Paths.get(tempDir.toAbsolutePath().toString() ,"Project1");
        if (!Files.exists(project_1_path)) {
            Files.createDirectory(project_1_path);
        }
        Path project_2_path = Paths.get(tempDir.toAbsolutePath().toString() ,"Project2");
        if (!Files.exists(project_2_path)) {
            Files.createDirectory(project_2_path);
        }
        Set<Path> javaProjectPaths = createSetOfPaths(project_1_path, project_2_path);
        // Test
        List<NonValidJavaProjectPath> result = sut.validateJavaProjectPaths(javaProjectPaths);
        // Verify
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void verifyJavaProjectPathsWhereArgumentContainsTwoDirectoriesThatDoesNotExist() throws IOException {
        // Initialize
        Path pathThatDoesNotExist_1 = Paths.get("/Some/Path/That/Does/Not/Exist_1");
        Path pathThatDoesNotExist_2 = Paths.get("/Some/Path/That/Does/Not/Exist_2");
        Set<Path> javaProjectPaths = createSetOfPaths(pathThatDoesNotExist_1, pathThatDoesNotExist_2);
        // Test
        List<NonValidJavaProjectPath> result = sut.validateJavaProjectPaths(javaProjectPaths);
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
                javaProject_3_path_found = nonValidJavaProjectPath.getJavaProjectPath().equals(pathThatDoesNotExist_1);
            }
            else if (nonValidJavaProjectPath.getJavaProjectPath().toString().contains("Exist_2")) {
                index_one_found = nonValidJavaProjectPath.getIndex() == 1;
                javaProject_4_path_found = nonValidJavaProjectPath.getJavaProjectPath().equals(pathThatDoesNotExist_2);
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
    void verifyJavaProjectPathsWhereArgumentContainsTwoDirectoriesWhereOneDoesNotExist(@TempDir Path tempDir) throws IOException {
        // Initialize
        Path project_1_path = Paths.get(tempDir.toAbsolutePath().toString() ,"Project1");
        if (!Files.exists(project_1_path)) {
            Files.createDirectory(project_1_path);
        }
        Path pathThatDoesNotExist = Paths.get("/Some/Path/That/Does/Not/Exist");
        Set<Path> javaProjectPaths = createSetOfPaths(project_1_path, pathThatDoesNotExist);
        // Test
        List<NonValidJavaProjectPath> result = sut.validateJavaProjectPaths(javaProjectPaths);
        // Verify
        assertThat(result.isEmpty()).isFalse();
        NonValidJavaProjectPath firstResultInList = result.get(0);
        assertThat(firstResultInList.getIndex()).isEqualTo(1);
        assertThat(firstResultInList.getJavaProjectPath()).isEqualTo(pathThatDoesNotExist);
        assertThat(firstResultInList.getErrorString()).contains("does not exist. Check spelling.");
    }*/
}
