package com.example.libcommon.global;

import android.app.Application;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AppGlobal {

    private static Application mApplication;

    public static Application getApplication(){
        if (mApplication == null){
            try {
                Method method = Class.forName("android.app.ActivityThread").getDeclaredMethod("currentApplication");
                mApplication = (Application) method.invoke(null, (Object[]) null);
            } catch (NoSuchMethodException | ClassNotFoundException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return mApplication;
    }
} 