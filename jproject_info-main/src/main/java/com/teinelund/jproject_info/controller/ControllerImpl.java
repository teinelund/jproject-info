package com.teinelund.jproject_info.controller;

import com.teinelund.jproject_info.context.Context;

import javax.inject.Inject;

public class ControllerImpl implements Controller {

    private Context context;
    @Inject
    public ControllerImpl(Context context) {
        this.context = context;
    }

    @Override
    public void execute() {

    }
}
