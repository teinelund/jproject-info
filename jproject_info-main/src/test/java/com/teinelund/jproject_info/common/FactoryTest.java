package com.teinelund.jproject_info.common;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;

class FactoryTest {

    @Test
    void test() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // Initialize
        Factory factory = new Factory();
        // Test
        Object o = factory.get(TestClass.class);
        // Verify
        assertThat(o).isInstanceOfAny(TestClassImpl.class);
    }
}

interface TestClass {}

class TestClassImpl implements TestClass {
    public TestClassImpl() {}
}