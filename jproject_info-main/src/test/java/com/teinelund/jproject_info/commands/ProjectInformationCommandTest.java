package com.teinelund.jproject_info.commands;

import com.teinelund.jproject_info.command_line_parameters_parser.Parameters;
import com.teinelund.jproject_info.context.Context;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
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
}

class ProjectInformationCommandImplMock extends ProjectInformationCommandImpl {

    public ProjectInformationCommandImplMock(Context context) {
        super(context);
    }

    @Override
    public void verboseOutput(Parameters parameters, String text) {

    }
}