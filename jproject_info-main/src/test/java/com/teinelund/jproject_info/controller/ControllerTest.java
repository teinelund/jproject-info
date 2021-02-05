package com.teinelund.jproject_info.controller;

import com.teinelund.jproject_info.common.ParametersStub;
import com.teinelund.jproject_info.common.Printer;
import com.teinelund.jproject_info.common.PrinterMock;
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
    private PrinterMock printer = null;
    private ParametersStub parameters = null;
    private Controller sut = null;
    private PrintHelpStrategyMock printHelpStrategyMock = null;
    private PrintVersionStrategyMock printVersionStrategyMock = null;
    private PathInformationStrategyMock pathInformationStrategyMock = null;
    private ContextModule contextModule = new ContextModule();

    @BeforeEach
    void beforeEach() {
        this.printer = new PrinterMock();
        this.printHelpStrategyMock = new PrintHelpStrategyMock();
        this.printVersionStrategyMock = new PrintVersionStrategyMock();
        this.pathInformationStrategyMock = new PathInformationStrategyMock();
    }

    @Test
    public void testExecuteWhereHelpOptionIsSet() {
        // Initialize
        this.parameters = new ParametersStub(true, false, false, false);
        this.context = this.contextModule.provideContext(this.parameters);
        this.sut = module.provideController(this.context, this.printer, this.printHelpStrategyMock, this.printVersionStrategyMock, this.pathInformationStrategyMock);
        assertThat(this.printHelpStrategyMock.getIsHelpInvoked()).isFalse();
        assertThat(this.printVersionStrategyMock.getIsVersionInvoked()).isFalse();
        assertThat(this.pathInformationStrategyMock.getIsExecuteInvoked()).isFalse();
        // Test
        this.sut.execute();
        // Verify
        assertThat(this.printHelpStrategyMock.getIsHelpInvoked()).isTrue();
        assertThat(this.printVersionStrategyMock.getIsVersionInvoked()).isFalse();
        assertThat(this.pathInformationStrategyMock.getIsExecuteInvoked()).isFalse();
        assertThat(this.printer.getErrorMessage()).isEmpty();
    }

    @Test
    public void testExecuteWhereVersionpOptionIsSet() {
        // Initialize
        this.parameters = new ParametersStub(false, true, false, false);
        this.context = this.contextModule.provideContext(this.parameters);
        this.sut = module.provideController(this.context, this.printer, this.printHelpStrategyMock, this.printVersionStrategyMock, this.pathInformationStrategyMock);
        assertThat(this.printHelpStrategyMock.getIsHelpInvoked()).isFalse();
        assertThat(this.printVersionStrategyMock.getIsVersionInvoked()).isFalse();
        assertThat(this.pathInformationStrategyMock.getIsExecuteInvoked()).isFalse();
        // Test
        this.sut.execute();
        // Verify
        assertThat(this.printHelpStrategyMock.getIsHelpInvoked()).isFalse();
        assertThat(this.printVersionStrategyMock.getIsVersionInvoked()).isTrue();
        assertThat(this.pathInformationStrategyMock.getIsExecuteInvoked()).isFalse();
        assertThat(this.printer.getErrorMessage()).isEmpty();
    }

    @Test
    public void testExecuteWherePathInfoOptionIsSet() {
        // Initialize
        this.parameters = new ParametersStub(false, false, true, false);
        this.context = this.contextModule.provideContext(this.parameters);
        this.sut = module.provideController(this.context, this.printer, this.printHelpStrategyMock, this.printVersionStrategyMock, this.pathInformationStrategyMock);
        assertThat(this.printHelpStrategyMock.getIsHelpInvoked()).isFalse();
        assertThat(this.printVersionStrategyMock.getIsVersionInvoked()).isFalse();
        assertThat(this.pathInformationStrategyMock.getIsExecuteInvoked()).isFalse();
        // Test
        this.sut.execute();
        // Verify
        assertThat(this.printHelpStrategyMock.getIsHelpInvoked()).isFalse();
        assertThat(this.printVersionStrategyMock.getIsVersionInvoked()).isFalse();
        assertThat(this.pathInformationStrategyMock.getIsExecuteInvoked()).isTrue();
        assertThat(this.printer.getErrorMessage()).isEmpty();
    }

    @Test
    public void testExecuteWhereNoOptionIsSet() {
        // Initialize
        this.parameters = new ParametersStub(false, false, false, false);
        this.context = this.contextModule.provideContext(this.parameters);
        this.sut = module.provideController(this.context, this.printer, this.printHelpStrategyMock, this.printVersionStrategyMock, this.pathInformationStrategyMock);
        assertThat(this.printHelpStrategyMock.getIsHelpInvoked()).isFalse();
        assertThat(this.printVersionStrategyMock.getIsVersionInvoked()).isFalse();
        assertThat(this.pathInformationStrategyMock.getIsExecuteInvoked()).isFalse();
        // Test
        this.sut.execute();
        // Verify
        assertThat(this.printHelpStrategyMock.getIsHelpInvoked()).isFalse();
        assertThat(this.printVersionStrategyMock.getIsVersionInvoked()).isFalse();
        assertThat(this.pathInformationStrategyMock.getIsExecuteInvoked()).isFalse();
        assertThat(this.printer.getErrorMessage()).contains("No option selected.");
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