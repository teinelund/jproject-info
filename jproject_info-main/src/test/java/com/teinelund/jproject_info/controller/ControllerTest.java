package com.teinelund.jproject_info.controller;

import com.teinelund.jproject_info.common.ParametersStub;
import com.teinelund.jproject_info.context.Context;
import com.teinelund.jproject_info.context.ContextModule;
import com.teinelund.jproject_info.strategy.PathInformationStrategy;
import com.teinelund.jproject_info.strategy.PrintHelpStrategy;
import com.teinelund.jproject_info.strategy.PrintVersionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ControllerTest {

    private ControllerModule module = new ControllerModule();
    private Context context = null;
    private ParametersStub parameters = null;
    private Controller sut = null;
    private PrintHelpStrategyMock printHelpStrategyMock = null;
    private PrintVersionStrategyMock printVersionStrategyMock = null;
    private PathInformationStrategyMock pathInformationStrategyMock = null;
    private ContextModule contextModule = new ContextModule();

    @BeforeEach
    void beforeEach() {
        this.printHelpStrategyMock = new PrintHelpStrategyMock();
        this.printVersionStrategyMock = new PrintVersionStrategyMock();
        this.pathInformationStrategyMock = new PathInformationStrategyMock();
    }

    @Test
    public void testExecuteWhereHelpOptionIsSet() {
        // Initialize
        this.parameters = new ParametersStub(true, false);
        this.context = this.contextModule.provideContext(this.parameters);
        this.sut = module.provideController(this.context, this.printHelpStrategyMock, this.printVersionStrategyMock, this.pathInformationStrategyMock);
        assertThat(this.printHelpStrategyMock.getIsHelpInvoked()).isFalse();
        assertThat(this.printVersionStrategyMock.getIsVersionInvoked()).isFalse();
        // Test
        this.sut.execute();
        // Verify
        assertThat(this.printHelpStrategyMock.getIsHelpInvoked()).isTrue();
        assertThat(this.printVersionStrategyMock.getIsVersionInvoked()).isFalse();
    }

    @Test
    public void testExecuteWhereVersionpOptionIsSet() {
        // Initialize
        this.parameters = new ParametersStub(false, true);
        this.context = this.contextModule.provideContext(this.parameters);
        this.sut = module.provideController(this.context, this.printHelpStrategyMock, this.printVersionStrategyMock, this.pathInformationStrategyMock);
        assertThat(this.printHelpStrategyMock.getIsHelpInvoked()).isFalse();
        assertThat(this.printVersionStrategyMock.getIsVersionInvoked()).isFalse();
        // Test
        this.sut.execute();
        // Verify
        assertThat(this.printHelpStrategyMock.getIsHelpInvoked()).isFalse();
        assertThat(this.printVersionStrategyMock.getIsVersionInvoked()).isTrue();
    }

    @Test
    public void testExecuteWhereNeitherHelpNorVersionIsSet() {
        // Initialize
        this.parameters = new ParametersStub(false, false);
        this.context = this.contextModule.provideContext(this.parameters);
        this.sut = module.provideController(this.context, this.printHelpStrategyMock, this.printVersionStrategyMock, this.pathInformationStrategyMock);
        assertThat(this.printHelpStrategyMock.getIsHelpInvoked()).isFalse();
        assertThat(this.printVersionStrategyMock.getIsVersionInvoked()).isFalse();
        // Test
        this.sut.execute();
        // Verify
        assertThat(this.printHelpStrategyMock.getIsHelpInvoked()).isFalse();
        assertThat(this.printVersionStrategyMock.getIsVersionInvoked()).isFalse();
    }
}

class PrintHelpStrategyMock implements PrintHelpStrategy {

    private boolean isHelpInvoked = false;
    @Override
    public void execute() {
        this.isHelpInvoked = true;
    }

    public boolean getIsHelpInvoked() {
        return this.isHelpInvoked;
    }
}

class PrintVersionStrategyMock implements PrintVersionStrategy {

    private boolean isVersionInvoked = false;
    @Override
    public void execute() {
        this.isVersionInvoked = true;
    }

    public boolean getIsVersionInvoked() {
        return this.isVersionInvoked;
    }
}

class PathInformationStrategyMock implements PathInformationStrategy {

    private boolean isExecuteInvoked = false;
    @Override
    public void execute() {
        this.isExecuteInvoked = true;
    }

    public boolean getIsExecuteInvoked() {
        return this.isExecuteInvoked;
    }
}