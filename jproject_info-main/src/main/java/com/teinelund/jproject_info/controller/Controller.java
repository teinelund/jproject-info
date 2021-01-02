package com.teinelund.jproject_info.controller;

import com.teinelund.jproject_info.command_line_parameters_parser.Parameters;

public interface Controller {
    void execute(Parameters parameters);
}
