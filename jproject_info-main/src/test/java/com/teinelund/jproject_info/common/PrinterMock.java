package com.teinelund.jproject_info.common;

import java.util.LinkedList;
import java.util.List;

public class PrinterMock implements Printer {
    private String errorMessage = "";
    private boolean isPrintHelpInvoked = false;
    private List<String> info = new LinkedList<>();
    private List<String> infoWhite = new LinkedList<>();

    @Override
    public void verbose(String message) {

    }

    @Override
    public void error(String message) {
        this.errorMessage = message;
    }

    @Override
    public void infoWhite(String message) {
        this.infoWhite.add(message);
    }

    @Override
    public void info(String message) {
        this.info.add(message);
    }

    @Override
    public void printHelp() {
        this.isPrintHelpInvoked = true;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public boolean isPrintHelpInvoked() {
        return this.isPrintHelpInvoked;
    }

    public List<String> getInfo() {
        return this.info;
    }

    public List<String> getInfoWhite() {
        return this.infoWhite;
    }
}
