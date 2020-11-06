package com.teinelund.jproject_info.common;

public class ContextFactory {

    private static Context context = null;

    public static Context getContext() {
        createContextIfNotExist();
        return context;
    }

    public static ProjectInformationContext getProjectInformationContext() {
        createContextIfNotExist();
        return context;
    }

    private static void createContextIfNotExist() {
        if (context == null) {
            context = new ContextImpl();
        }
    }
}
