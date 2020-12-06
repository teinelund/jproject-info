package com.teinelund.jproject_info.project_information;

import com.teinelund.jproject_info.common.Context;

public class ProjectInformationFactory {
    public static ProjectInformation createProjectInformation(Context context) {
        return new ProjectInformationImpl(context);
    }
}
