package com.teinelund.jproject_info;

import static org.assertj.core.api.Assertions.assertThat;

import com.teinelund.jproject_info.commands.ParseCommandLineArgumentsCommand;
import com.teinelund.jproject_info.common.PrinterMock;
import com.teinelund.jproject_info.context.ContextModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.io.IOException;


public class ApplicationTest {

    private ParseCommandLineArgumentsCommandStub stub = null;
    private ContextModule contextModule = new ContextModule();
    private PrinterMock printer = null;
    private Application sut = null;


    @BeforeEach
    void init(TestInfo testInfo) throws IOException {
        this.printer = new PrinterMock();
        if (testInfo.getDisplayName().contains("executeWhereCommandRaiseException")) {
            this.stub = new ParseCommandLineArgumentsCommandStub(true);
        }
        else {
            this.stub = new ParseCommandLineArgumentsCommandStub(false);
        }
        this.sut = new Application(contextModule.provideContext(null), this.printer, this.stub);
    }

    @Test
    void executeWhereArgsContainsHelpOption() {
        // Initialize
        String[] args = {"--help"};
        // Test
        this.sut.execute(args);
        // Verify
        assertThat(this.stub.getIsExecuteInvoked()).isTrue();
        assertThat(this.printer.getErrorMessage()).isEmpty();
    }

    @Test
    void executeWhereCommandRaiseException() {
        // Initialize
        String[] args = {"--help"};
        // Test
        this.sut.execute(args);
        // Verify
        assertThat(this.stub.getIsExecuteInvoked()).isTrue();
        assertThat(this.printer.getErrorMessage()).contains("FAULT");
    }

}

class ParseCommandLineArgumentsCommandStub implements ParseCommandLineArgumentsCommand {

    private boolean isExecuteInvoked = false;
    private boolean raiseException;

    public ParseCommandLineArgumentsCommandStub(boolean raiseException) {
        this.raiseException = raiseException;
    }

    @Override
    public void execute() {
        this.isExecuteInvoked = true;
        if (raiseException) {
            throw new RuntimeException("FAULT");
        }
    }

    public boolean getIsExecuteInvoked() {
        return this.isExecuteInvoked;
    }
}
