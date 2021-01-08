package com.teinelund.jproject_info;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;


public class ApplicationTest {

    private static Application sut = null;


    @BeforeAll
    static void init() throws IOException {
        sut = new Application(null, null, null, null);
    }

}
