package com.teinelund.jproject_info.strategy;

import com.beust.jcommander.JCommander;
import com.teinelund.jproject_info.command_line_parameters_parser.ParametersModule;
import com.teinelund.jproject_info.common.PrinterMock;
import com.teinelund.jproject_info.context.Context;
import com.teinelund.jproject_info.context.ContextModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class PrintHelpStrategyTest {

    private PrintHelpStrategy sut = null;
    private PrinterMock printer = null;

    @BeforeEach
    void init(TestInfo testInfo) throws IOException {
        this.printer = new PrinterMock();
        this.sut = new PrintHelpStrategyImpl(null, this.printer);
    }

    @Test
    void execute() {
        // Initialize
        // Test
        this.sut.execute();
        // Verify
        assertThat(this.printer.isPrintHelpInvoked()).isTrue();
    }

}
