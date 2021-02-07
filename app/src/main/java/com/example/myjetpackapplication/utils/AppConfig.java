package com.example.myjetpackapplication.utils;

import android.content.res.AssetManager;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.libcommon.global.AppGlobal;
import com.example.myjetpackapplication.model.BottomBar;
import com.example.myjetpackapplication.model.Destination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class AppConfig {
    private static final String TAG = "AppConfig";

    private static HashMap<String, Destination> mDestConfig;

    private static BottomBar mBottomBar;

    public static HashMap<String, Destination> getDestConfig() {
        if (mDestConfig == null) {
            String content = parseFile("destination.json");
            mDestConfig = JSON.parseObject(content, new TypeReference<HashMap<String, Destination>>() {
            });
        }
        Log.d(TAG, "getDestConfig: " + mDestConfig.toString());
        return mDestConfig;
    }

    public static BottomBar getBottomBar(){
        if (mBottomBar == null){
            String content = parseFile("main_tabs_config.json");
            mBottomBar = JSON.parseObject(content, BottomBar.class);
        }
        return mBottomBar;
    }

    private static String parseFile(String fileName) {
        AssetManager assetManager = AppGlobal.getApplication().getResources().getAssets();

        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder builder = new StringBuilder();
        try {
            inputStream = assetManager.open(fileName);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        Log.d(TAG, "parseFile: " + builder.toString());
        return builder.toString();
    }
} 