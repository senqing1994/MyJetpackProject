package com.example.myjetpackapplication.utils;

import android.content.ComponentName;
import android.util.Log;

import androidx.navigation.ActivityNavigator;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.fragment.FragmentNavigator;

import com.example.libcommon.global.AppGlobal;
import com.example.myjetpackapplication.model.Destination;

import java.util.HashMap;

public class NavGraphBuilder {
    private static final String TAG = "NavGraphBuilder";

    public static void build(NavController controller){
        NavigatorProvider navigatorProvider = controller.getNavigatorProvider();

        FragmentNavigator fragmentNavigator = navigatorProvider.getNavigator(FragmentNavigator.class);
        ActivityNavigator activityNavigator = navigatorProvider.getNavigator(ActivityNavigator.class);

        NavGraph navGraph = new NavGraph(new NavGraphNavigator(navigatorProvider));

        HashMap<String, Destination> destConfig = AppConfig.getDestConfig();
        for (Destination value : destConfig.values()) {
            Log.d(TAG, "build: values: " + value.toString());
            if (value.isIsFragment()){
                FragmentNavigator.Destination destination = fragmentNavigator.createDestination();
                destination.setClassName(value.getClazzName());
                destination.setId(value.getId());
                destination.addDeepLink(value.getPageUrl());

                navGraph.addDestination(destination);
            }else {
                ActivityNavigator.Destination destination = activityNavigator.createDestination();
                destination.setId(value.getId());
                destination.setComponentName(new ComponentName(AppGlobal.getApplication().getPackageName(),value.getClazzName()));
                destination.addDeepLink(value.getPageUrl());

                navGraph.addDestination(destination);
            }

            if (value.isAsStarter()){
                navGraph.setStartDestination(value.getId());
            }

            Log.d(TAG, "build: navGraph: " + navGraph.toString());
        }
        controller.setGraph(navGraph);
    }
} 