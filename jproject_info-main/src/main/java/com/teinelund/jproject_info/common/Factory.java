package com.teinelund.jproject_info.common;

import com.teinelund.jproject_info.Application;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Factory {

    public Object get(Class clazz) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ClassLoader classLoader = Application.class.getClassLoader();
        Class<?> cl = classLoader.loadClass(clazz.getName() + "Impl");
        Constructor<?>[] cs = cl.getConstructors();
        if (cs.length > 0) {
            Object o =cs[0].newInstance(null);
            return o;
        }
        return null;
    }
}
