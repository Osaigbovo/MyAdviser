package com.osaigbovo.myadviser.util;

import android.app.Application;
import android.content.Context;

import androidx.test.runner.AndroidJUnitRunner;

import com.osaigbovo.myadviser.TestApp;

/**
 * Custom runner to disable dependency injection.
 */
public class CustomTestRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return super.newApplication(cl, TestApp.class.getName(), context);
    }

}
