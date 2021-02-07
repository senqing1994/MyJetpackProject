package com.example.myjetpackapplication.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myjetpackapplication.R;
import com.example.myjetpackapplication.model.BottomBar;
import com.example.myjetpackapplication.model.Destination;
import com.example.myjetpackapplication.utils.AppConfig;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import java.util.List;

public class AppBottomBar extends BottomNavigationView {
    private static int[] mIcons = new int[]{R.drawable.icon_tab_home, R.drawable.icon_tab_sofa, R.drawable.icon_tab_publish, R.drawable.icon_tab_find, R.drawable.icon_tab_mine};
    public AppBottomBar(@NonNull Context context) {
        this(context, null);
    }

    public AppBottomBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("RestrictedApi")
    public AppBottomBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        BottomBar bottomBar = AppConfig.getBottomBar();
        List<BottomBar.Tabs> tabs = bottomBar.getTabs();

        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_selected};
        states[1] = new int[]{};

        int[] colors = new int[]{Color.parseColor(bottomBar.getActiveColor()), Color.parseColor(bottomBar.getActiveColor())};
        ColorStateList colorStateList = new ColorStateList(states, colors);

        setItemIconTintList(colorStateList);
        setItemTextColor(colorStateList);

        setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        setSelectedItemId(bottomBar.getSelectTab());

        for (int i = 0; i < tabs.size(); i++) {
            BottomBar.Tabs tab = tabs.get(i);
            if (!tab.isEnable()) {
                return;
            }
            int id = getId(tab.getPageUrl());
            if (id < 0) {
                return;
            }
            MenuItem item = getMenu().add(0, id, tab.getIndex(), tab.getTitle());
            item.setIcon(mIcons[tab.getIndex()]);
        }

        for (int i = 0; i < tabs.size(); i++) {
            BottomBar.Tabs tab = tabs.get(i);
            int iconSize = dp2px(tab.getSize());

            BottomNavigationMenuView menuView = (BottomNavigationMenuView) getChildAt(0);
            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(tab.getIndex());
            itemView.setIconSize(iconSize);

            if (TextUtils.isEmpty(tab.getTitle())){
                itemView.setIconTintList(ColorStateList.valueOf(Color.parseColor(tab.getTintColor())));
                itemView.setShifting(false);
            }
        }

    }

    private int dp2px(int size) {
        float value = getContext().getResources().getDisplayMetrics().density * size + 0.5f;
        return (int) value;
    }

    private int getId(String pageUrl) {
        Destination destination = AppConfig.getDestConfig().get(pageUrl);
        if (destination == null) {
            return -1;
        }
        return destination.getId();
    }
}