package com.teinelund.jproject_info.commands;

import static org.assertj.core.api.Assertions.assertThat;

import com.teinelund.jproject_info.context.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParseCommandLineArgumentsCommandTest {

    private CommandModule module = new CommandModule();
    private Context context = null;
    private ParseCommandLineArgumentsCommand sut = null;
    private ValidateCommandLineArgumentsCommandMock commandMock = null;

    @BeforeEach
    void beforeEach() {
        ContextTestComponent component = DaggerContextTestComponent.create();
        this.context = component.buildContext();
        this.commandMock = new ValidateCommandLineArgumentsCommandMock(this.context);
        sut = module.provideParseCommandLineArguments(this.context, this.commandMock);
    }
    
    @Test
    void executeWhereArgumentsIsEmpty() {
        // Initialize
        String[] args = new String[0];
        this.context.setCommandLineArguments(args);
        assertThat(this.commandMock.isExecutedInvoked()).isFalse();
        // Test
        this.sut.execute();
        // Verify
        assertThat(this.context.getParameters().isHelpOption()).isFalse();
        assertThat(this.context.getParameters().isVersionOption()).isFalse();
        assertThat(this.context.getParameters().isVerbose()).isFalse();
        assertThat(this.context.getParameters().isPathInfo()).isFalse();
        assertThat(this.context.getParameters().getJavaProjectPaths()).isNotNull();
        assertThat(this.context.getParameters().getJavaProjectPaths().isEmpty()).isTrue();
        assertThat(this.commandMock.isExecutedInvoked()).isTrue();
    }

    @Test
    void executeWhereArgumentsContainsOptionVersion() {
        // Initialize
        String[] args = new String[1];
        args[0] = "-V";
        this.context.setCommandLineArguments(args);
        assertThat(this.commandMock.isExecutedInvoked()).isFalse();
        // Test
        this.sut.execute();
        // Verify
        assertThat(this.context.getParameters().isHelpOption()).isFalse();
        assertThat(this.context.getParameters().isVersionOption()).isTrue();
        assertThat(this.context.getParameters().isVerbose()).isFalse();
        assertThat(this.context.getParameters().isPathInfo()).isFalse();
        assertThat(this.context.getParameters().getJavaProjectPaths()).isNotNull();
        assertThat(this.context.getParameters().getJavaProjectPaths().isEmpty()).isTrue();
        assertThat(this.commandMock.isExecutedInvoked()).isTrue();
    }

    @Test
    void executeWhereArgumentsContainsOptionHelp() {
        // Initialize
        String[] args = new String[1];
        args[0] = "-h";
        this.context.setCommandLineArguments(args);
        assertThat(this.commandMock.isExecutedInvoked()).isFalse();
        // Test
        this.sut.execute();
        // Verify
        assertThat(this.context.getParameters().isHelpOption()).isTrue();
        assertThat(this.context.getParameters().isVersionOption()).isFalse();
        assertThat(this.context.getParameters().isVerbose()).isFalse();
        assertThat(this.context.getParameters().isPathInfo()).isFalse();
        assertThat(this.context.getParameters().getJavaProjectPaths()).isNotNull();
        assertThat(this.context.getParameters().getJavaProjectPaths().isEmpty()).isTrue();
        assertThat(this.commandMock.isExecutedInvoked()).isTrue();
    }

    private static final String PATH = "/Develop/Projects/SqlServerSqlConverter";

    @Test
    void executeWhereArgumentsContainsOptionPath() {
        // Initialize
        String[] args = new String[1];
        args[0] = PATH + "/";
        this.context.setCommandLineArguments(args);
        assertThat(this.commandMock.isExecutedInvoked()).isFalse();
        // Test
        this.sut.execute();
        // Verify
        assertThat(this.context.getParameters().isHelpOption()).isFalse();
        assertThat(this.context.getParameters().isVersionOption()).isFalse();
        assertThat(this.context.getParameters().isVerbose()).isFalse();
        assertThat(this.context.getParameters().isPathInfo()).isFalse();
        assertThat(this.context.getParameters().getJavaProjectPaths()).isNotNull();
        assertThat(this.context.getParameters().getJavaProjectPaths().size()).isEqualTo(1);
        assertThat(this.context.getParameters().getJavaProjectPaths().toArray()[0].toString()).contains(PATH);
        assertThat(this.commandMock.isExecutedInvoked()).isTrue();
    }

    @Test
    void executeWhereArgumentsContainsOptionCommadProjectAndOptionPathInfo() {
        // Initialize
        String[] args = new String[1];
        args[0] = "-p";
        this.context.setCommandLineArguments(args);
        assertThat(this.commandMock.isExecutedInvoked()).isFalse();
        // Test
        this.sut.execute();
        // Verify
        assertThat(this.context.getParameters().isHelpOption()).isFalse();
        assertThat(this.context.getParameters().isVersionOption()).isFalse();
        assertThat(this.context.getParameters().isVerbose()).isFalse();
        assertThat(this.context.getParameters().isPathInfo()).isTrue();
        assertThat(this.context.getParameters().getJavaProjectPaths()).isNotNull();
        assertThat(this.context.getParameters().getJavaProjectPaths().isEmpty()).isTrue();
        assertThat(this.commandMock.isExecutedInvoked()).isTrue();
    }

    @Test
    void executeWhereArgumentsContainsOptionCommadProjectAndOptionPathInfoAndPath() {
        // Initialize
        String[] args = new String[2];
        args[0] = "-p";
        args[1] = PATH + "/";
        this.context.setCommandLineArguments(args);
        assertThat(this.commandMock.isExecutedInvoked()).isFalse();
        // Test
        this.sut.execute();
        // Verify
        assertThat(this.context.getParameters().isHelpOption()).isFalse();
        assertThat(this.context.getParameters().isVersionOption()).isFalse();
        assertThat(this.context.getParameters().isVerbose()).isFalse();
        assertThat(this.context.getParameters().isPathInfo()).isTrue();
        assertThat(this.context.getParameters().getJavaProjectPaths()).isNotNull();
        assertThat(this.context.getParameters().getJavaProjectPaths().size()).isEqualTo(1);
        assertThat(this.context.getParameters().getJavaProjectPaths().toArray()[0].toString()).contains(PATH);
        assertThat(this.commandMock.isExecutedInvoked()).isTrue();
    }

    @Test
    void executeWhereArgumentsContainsOptionVerboseAndOptionPath() {
        // Initialize
        String[] args = new String[2];
        args[0] = "--verbose";
        args[1] = PATH + "/";
        this.context.setCommandLineArguments(args);
        assertThat(this.commandMock.isExecutedInvoked()).isFalse();
        // Test
        this.sut.execute();
        // Verify
        assertThat(this.context.getParameters().isHelpOption()).isFalse();
        assertThat(this.context.getParameters().isVersionOption()).isFalse();
        assertThat(this.context.getParameters().isVerbose()).isTrue();
        assertThat(this.context.getParameters().isPathInfo()).isFalse();
        assertThat(this.context.getParameters().getJavaProjectPaths()).isNotNull();
        assertThat(this.context.getParameters().getJavaProjectPaths().size()).isEqualTo(1);
        assertThat(this.context.getParameters().getJavaProjectPaths().toArray()[0].toString()).contains(PATH);
        assertThat(this.commandMock.isExecutedInvoked()).isTrue();
    }
}

