package com.teinelund.jproject_info.controller;

public class ControllerFactory {

    private static ControllerFactory instance = null;

    public static ControllerFactory getInstance() {
        createInstanceIfNotExist();
        return instance;
    }

    private static void createInstanceIfNotExist() {
        if (instance == null) {
            instance = new ControllerFactory();
        }
    }

    private ControllerFactory() {}

    public Controller getConttroller() {
        return new ControllerImpl();
    }
}
