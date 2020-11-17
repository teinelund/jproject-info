package com.teinelund.jproject_info.main_argument_parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import com.beust.jcommander.JCommander;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OptionsImplTest {

    private OptionsImpl sut = null;
    private JCommander jc = null;


    @BeforeEach
    void beforeEach() {
        this.sut = new OptionsImpl();
        this.jc = JCommander.newBuilder().
                addObject(this.sut).
                programName("jproject_info").
                build();
    }


    @Test
    void executeWhereArgumentsIsEmpty() {
        // Initialize
        String[] args = new String[0];
        // Test
        this.jc.parse(args);
        // Verify
        assertThat(sut.isHelpOption()).isFalse();
        assertThat(sut.isVersionOption()).isFalse();
        assertThat(sut.isVerbose()).isFalse();
        assertThat(sut.isPathInfo()).isFalse();
        assertThat(sut.getJavaProjectPaths()).isNotNull();
        assertThat(sut.getJavaProjectPaths().isEmpty()).isTrue();
    }

    @Test
    void executeWhereArgumentsContainsOptionVersion() {
        // Initialize
        String[] args = new String[1];
        args[0] = "-V";
        // Test
        this.jc.parse(args);
        // Verify
        assertThat(sut.isHelpOption()).isFalse();
        assertThat(sut.isVersionOption()).isTrue();
        assertThat(sut.isVerbose()).isFalse();
        assertThat(sut.isPathInfo()).isFalse();
        assertThat(sut.getJavaProjectPaths()).isNotNull();
        assertThat(sut.getJavaProjectPaths().isEmpty()).isTrue();
    }

    @Test
    void executeWhereArgumentsContainsOptionHelp() {
        // Initialize
        String[] args = new String[1];
        args[0] = "-h";
        // Test
        this.jc.parse(args);
        // Verify
        assertThat(sut.isHelpOption()).isTrue();
        assertThat(sut.isVersionOption()).isFalse();
        assertThat(sut.isVerbose()).isFalse();
        assertThat(sut.isPathInfo()).isFalse();
        assertThat(sut.getJavaProjectPaths()).isNotNull();
        assertThat(sut.getJavaProjectPaths().isEmpty()).isTrue();
    }

    private static final String PATH = "/Develop/Projects/SqlServerSqlConverter";

    @Test
    void executeWhereArgumentsContainsOptionPath() {
        // Initialize
        String[] args = new String[1];
        args[0] = PATH + "/";
        // Test
        this.jc.parse(args);
        // Verify
        assertThat(sut.isHelpOption()).isFalse();
        assertThat(sut.isVersionOption()).isFalse();
        assertThat(sut.isVerbose()).isFalse();
        assertThat(sut.isPathInfo()).isFalse();
        assertThat(sut.getJavaProjectPaths()).isNotNull();
        assertThat(sut.getJavaProjectPaths().size()).isEqualTo(1);
        assertThat(sut.getJavaProjectPaths().toArray()[0].toString()).contains(PATH);
    }

    @Test
    void executeWhereArgumentsContainsOptionCommadProjectAndOptionPathInfo() {
        // Initialize
        String[] args = new String[1];
        args[0] = "-p";
        // Test
        this.jc.parse(args);
        // Verify
        assertThat(sut.isHelpOption()).isFalse();
        assertThat(sut.isVersionOption()).isFalse();
        assertThat(sut.isVerbose()).isFalse();
        assertThat(sut.isPathInfo()).isTrue();
        assertThat(sut.getJavaProjectPaths()).isNotNull();
        assertThat(sut.getJavaProjectPaths().isEmpty()).isTrue();
    }

    @Test
    void executeWhereArgumentsContainsOptionCommadProjectAndOptionPathInfoAndPath() {
        // Initialize
        String[] args = new String[2];
        args[0] = "-p";
        args[1] = PATH + "/";
        // Test
        this.jc.parse(args);
        // Verify
        assertThat(sut.isHelpOption()).isFalse();
        assertThat(sut.isVersionOption()).isFalse();
        assertThat(sut.isVerbose()).isFalse();
        assertThat(sut.isPathInfo()).isTrue();
        assertThat(sut.getJavaProjectPaths()).isNotNull();
        assertThat(sut.getJavaProjectPaths().size()).isEqualTo(1);
        assertThat(sut.getJavaProjectPaths().toArray()[0].toString()).contains(PATH);
    }

    @Test
    void executeWhereArgumentsContainsOptionVerboseAndOptionPath() {
        // Initialize
        String[] args = new String[2];
        args[0] = "--verbose";
        args[1] = PATH + "/";
        // Test
        this.jc.parse(args);
        // Verify
        assertThat(sut.isHelpOption()).isFalse();
        assertThat(sut.isVersionOption()).isFalse();
        assertThat(sut.isVerbose()).isTrue();
        assertThat(sut.isPathInfo()).isFalse();
        assertThat(sut.getJavaProjectPaths()).isNotNull();
        assertThat(sut.getJavaProjectPaths().size()).isEqualTo(1);
        assertThat(sut.getJavaProjectPaths().toArray()[0].toString()).contains(PATH);
    }

}
