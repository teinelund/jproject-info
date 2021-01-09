package com.teinelund.jproject_info.controller;

import com.teinelund.jproject_info.command_line_parameters_parser.ParametersModule;
import com.teinelund.jproject_info.commands.ContextTestComponent;
import com.teinelund.jproject_info.commands.DaggerContextTestComponent;
import com.teinelund.jproject_info.common.ParametersStub;
import com.teinelund.jproject_info.context.Context;
import com.teinelund.jproject_info.context.ContextModule;
import com.teinelund.jproject_info.strategy.PrintHelpStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ControllerTest {

    private ControllerModule module = new ControllerModule();
    private Context context = null;
    private ParametersStub parameters = null;
    private Controller sut = null;
    private PrintHelpStrategyMock controllerMock = null;
    private ContextModule contextModule = new ContextModule();

    @BeforeEach
    void beforeEach() {
        this.controllerMock = new PrintHelpStrategyMock();
    }

    @Test
    public void testExecuteWhereHelpOptionIsSet() {
        // Initialize
        this.parameters = new ParametersStub(true, false);
        this.context = this.contextModule.provideContext(this.parameters);
        this.sut = module.provideController(this.context, this.controllerMock);
        assertThat(this.controllerMock.getIsHelpInvoked()).isFalse();
        // Test
        this.sut.execute();
        // Verify
        assertThat(this.controllerMock.getIsHelpInvoked()).isTrue();
    }

    @Test
    public void testExecuteWhereNeitherHelpNorVersionIsSet() {
        // Initialize
        this.parameters = new ParametersStub(false, false);
        this.context = this.contextModule.provideContext(this.parameters);
        this.sut = module.provideController(this.context, this.controllerMock);
        assertThat(this.controllerMock.getIsHelpInvoked()).isFalse();
        // Test
        this.sut.execute();
        // Verify
        assertThat(this.controllerMock.getIsHelpInvoked()).isFalse();
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