package com.teinelund.jproject_info.main_argument_parser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

public class OptionsImplTest {

    private OptionsImpl sut = null;


    @BeforeEach
    void beforeEach() {
        sut = new OptionsImpl();
    }


    /**
     * It should not be legal to use an empty argument. Expected to result in ExitCode.USAGE.
     */
    @Test
    void executeWhereArgumentsIsEmpty() {
        // Initialize
        String[] args = new String[0];
        // Test
        int result = (new CommandLine(sut).execute(args));
        // Verify
        assertThat(result).isEqualTo(CommandLine.ExitCode.USAGE);
        assertThat(sut.isVerbose()).isFalse();
        assertThat(sut.getJavaProjectPaths()).isNull();
        assertThat(sut.getProjectOption()).isNull();
    }

    @Test
    void executeWhereArgumentsContainsOptionVersion() {
        // Initialize
        String[] args = new String[1];
        args[0] = "-V";
        // Test
        int result = (new CommandLine(sut).execute(args));
        // Verify
        assertThat(result).isEqualTo(CommandLine.ExitCode.OK);
        assertThat(sut.isVerbose()).isFalse();
        assertThat(sut.getJavaProjectPaths()).isNull();
        assertThat(sut.getProjectOption()).isNull();
    }

    @Test
    void executeWhereArgumentsContainsOptionHelp() {
        // Initialize
        String[] args = new String[1];
        args[0] = "-h";
        // Test
        int result = (new CommandLine(sut).execute(args));
        // Verify
        assertThat(result).isEqualTo(CommandLine.ExitCode.OK);
        assertThat(sut.isVerbose()).isFalse();
        assertThat(sut.getJavaProjectPaths()).isNull();
        assertThat(sut.getProjectOption()).isNull();
    }

    private static final String PATH = "/Develop/Projects/SqlServerSqlConverter";

    @Test
    void executeWhereArgumentsContainsOptionPath() {
        // Initialize
        String[] args = new String[1];
        args[0] = PATH + "/";
        // Test
        int result = (new CommandLine(sut).execute(args));
        // Verify
        assertThat(result).isEqualTo(CommandLine.ExitCode.OK);
        assertThat(sut.isVerbose()).isFalse();
        assertThat(sut.getJavaProjectPaths()).isNotNull();
        assertThat(sut.getJavaProjectPaths().size()).isEqualTo(1);
        assertThat(sut.getJavaProjectPaths().toArray()[0].toString()).contains(PATH);
        assertThat(sut.getProjectOption()).isNull();
    }

    @Test
    void executeWhereArgumentsContainsOptionCommadProject() {
        // Initialize
        String[] args = new String[1];
        args[0] = "project";
        // Test
        int result = (new CommandLine(sut).execute(args));
        // Verify
        assertThat(result).isEqualTo(CommandLine.ExitCode.USAGE);
        assertThat(sut.isVerbose()).isFalse();
        assertThat(sut.getJavaProjectPaths()).isNull();
        assertThat(sut.getProjectOption()).isNull();
    }

    @Test
    void executeWhereArgumentsContainsOptionCommadProjectAndOptionPathInfo() {
        // Initialize
        String[] args = new String[2];
        args[0] = "project";
        args[1] = "-p";
        // Test
        int result = (new CommandLine(sut).execute(args));
        // Verify
        assertThat(result).isEqualTo(CommandLine.ExitCode.USAGE);
        assertThat(sut.isVerbose()).isFalse();
        assertThat(sut.getJavaProjectPaths()).isNull();
        assertThat(sut.getProjectOption()).isNull();
    }

    @Test
    void executeWhereArgumentsContainsOptionCommadProjectAndOptionPathInfoAndPath() {
        // Initialize
        String[] args = new String[3];
        args[0] = "project";
        args[1] = "-p";
        args[2] = PATH + "/";
        // Test
        int result = (new CommandLine(sut).execute(args));
        // Verify
        assertThat(result).isEqualTo(CommandLine.ExitCode.OK);
        assertThat(sut.isVerbose()).isFalse();
        assertThat(sut.getJavaProjectPaths()).isNotNull();
        assertThat(sut.getJavaProjectPaths().size()).isEqualTo(1);
        assertThat(sut.getJavaProjectPaths().toArray()[0].toString()).contains(PATH);
        assertThat(sut.getProjectOption()).isNotNull();
    }

    @Test
    void executeWhereArgumentsContainsOptionVerboseAndOptionPath() {
        // Initialize
        String[] args = new String[2];
        args[0] = "--verbose";
        args[1] = PATH + "/";
        // Test
        int result = (new CommandLine(sut).execute(args));
        // Verify
        assertThat(result).isEqualTo(CommandLine.ExitCode.OK);
        assertThat(sut.isVerbose()).isTrue();
        assertThat(sut.getJavaProjectPaths()).isNotNull();
        assertThat(sut.getJavaProjectPaths().size()).isEqualTo(1);
        assertThat(sut.getJavaProjectPaths().toArray()[0].toString()).contains(PATH);
        assertThat(sut.getProjectOption()).isNull();
    }

}
