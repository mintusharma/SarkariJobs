package com.geekdroid.bottom_navigation;

import android.graphics.Typeface;

import com.geekdroid.bottom_navigation.listener.BubbleBottomNavigationChangeListener;

public interface IBubbleNavigation {
    void setNavigationChangeListener(BubbleBottomNavigationChangeListener navigationChangeListener);

    void setTypeface(Typeface typeface);

    int getCurrentActiveItemPosition();

    void setCurrentActiveItem(int position);

    void setBadgeValue(int position, String value);
}
