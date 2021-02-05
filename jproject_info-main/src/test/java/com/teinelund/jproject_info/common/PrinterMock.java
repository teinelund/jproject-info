package com.teinelund.jproject_info.common;

public class PrinterMock implements Printer {
    private String errorMessage = "";

    @Override
    public void verbose(String message) {

    }

    @Override
    public void error(String message) {
        this.errorMessage = message;
    }

    @Override
    public void infoWhite(String message) {
        
    }

    @Override
    public void info(String message) {

    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
