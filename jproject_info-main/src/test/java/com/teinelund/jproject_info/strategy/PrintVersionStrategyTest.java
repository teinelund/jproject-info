package com.teinelund.jproject_info.strategy;

import com.teinelund.jproject_info.common.PrinterMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.io.IOException;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class PrintVersionStrategyTest {

    private PrintVersionStrategy sut = null;
    private PrinterMock printer = null;

    @BeforeEach
    void init(TestInfo testInfo) throws IOException {
        this.printer = new PrinterMock();
        this.sut = new PrintVersionStrategyImpl(null, this.printer);
    }

    @Test
    void execute() {
        // Initialize
        // Test
        this.sut.execute();
        // Verify
        boolean versionFound = false;
        for (String line : this.printer.getInfo()) {
            if (line.toLowerCase(Locale.ROOT).contains("version")) {
                versionFound = true;
                break;
            }
        }
        assertThat(versionFound).isTrue();
    }
}
