package com.teinelund.jproject_info.controller;

import com.teinelund.jproject_info.commands.NonValidJavaProjectPath;
import com.teinelund.jproject_info.commands.ValidateCommandLineArgumentsOptionsException;
import com.teinelund.jproject_info.commands.ValidateCommandLineArgumentsProjectPathsException;
import com.teinelund.jproject_info.common.Printer;
import com.teinelund.jproject_info.context.Context;
import com.teinelund.jproject_info.strategy.PathInformationStrategy;
import com.teinelund.jproject_info.strategy.PrintHelpStrategy;
import com.teinelund.jproject_info.strategy.PrintVersionStrategy;
import com.teinelund.jproject_info.strategy.Strategy;
import javax.inject.Inject;

import java.util.List;

public class ControllerImpl implements Controller {

    private Context context;
    private Printer printer;
    private Strategy printHelpStrategy;
    private Strategy printVersionStrategy;
    private Strategy pathInformationStrategy;

    @Inject
    public ControllerImpl(
            Context context,
            Printer printer,
            PrintHelpStrategy printHelpStrategy,
            PrintVersionStrategy printVersionStrategy,
            PathInformationStrategy pathInformationStrategy) {
        this.context = context;
        this.printer = printer;
        this.printHelpStrategy = printHelpStrategy;
        this.printVersionStrategy = printVersionStrategy;
        this.pathInformationStrategy = pathInformationStrategy;
    }

    @Override
    public void execute() {
        this.printer.verbose("Controller.execute()");
        Strategy strategy = null;
        if (this.context.getParameters().isHelpOption()) {
            strategy = this.printHelpStrategy;
        }
        else if (this.context.getParameters().isVersionOption()) {
            strategy = this.printVersionStrategy;
        }
        else if (this.context.getParameters().isPathInfoOption()) {
            strategy = this.pathInformationStrategy;
        }

        if (strategy != null) {
            try {
                strategy.execute();
            }
            catch (ValidateCommandLineArgumentsProjectPathsException e) {
                printErrorMessage(e.getNonValidJavaProjectPaths());
            }
            catch (ValidateCommandLineArgumentsOptionsException e) {
                this.printer.error(e.getMessage());
            }
            catch(Exception e) {
                this.printer.error(e.getMessage());
            }
        }
        else {
            this.printer.error("No option selected.");
        }
    }

    void printErrorMessage(List<NonValidJavaProjectPath> nonValidJavaProjectPaths) {
        if (nonValidJavaProjectPaths.size() == 1) {
            this.printer.error(nonValidJavaProjectPaths.get(0).getErrorString());
        } else {
            for (NonValidJavaProjectPath nonValidJavaProjectPath : nonValidJavaProjectPaths) {
                this.printer.error((nonValidJavaProjectPath.getIndex() + 1) + " : " + nonValidJavaProjectPath.getErrorString());
            }
        }
    }

}
