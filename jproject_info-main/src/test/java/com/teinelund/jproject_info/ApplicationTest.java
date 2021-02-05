package com.teinelund.jproject_info;

import static org.assertj.core.api.Assertions.assertThat;

import com.teinelund.jproject_info.commands.ParseCommandLineArgumentsCommand;
import com.teinelund.jproject_info.common.Printer;
import com.teinelund.jproject_info.common.PrinterMock;
import com.teinelund.jproject_info.context.ContextModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;


public class ApplicationTest {

    private ParseCommandLineArgumentsCommandStub stub = null;
    private ContextModule contextModule = new ContextModule();
    private Printer printer = null;
    private Application sut = null;


    @BeforeEach
    void init() throws IOException {
        this.printer = new PrinterMock();
        this.stub = new ParseCommandLineArgumentsCommandStub();
        this.sut = new Application(contextModule.provideContext(null), this.printer, this.stub);
    }

    @Test
    void executeWhereArgumentsIsEmpty() {
        // Initialize
        String[] args = {"--help"};
        // Test
        this.sut.execute(args);
        // Verify
        assertThat(this.stub.getIsExecuteInvoked()).isTrue();
    }

}

class ParseCommandLineArgumentsCommandStub implements ParseCommandLineArgumentsCommand {

    private boolean isExecuteInvoked = false;

    @Override
    public void execute() {
        this.isExecuteInvoked = true;
    }

    public boolean getIsExecuteInvoked() {
        return this.isExecuteInvoked;
    }
}
