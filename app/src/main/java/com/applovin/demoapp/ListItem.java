package com.applovin.demoapp;

/**
 * Created by thomasso on 10/5/15.
 */
public class ListItem
{
    private final String title;
    private final String subtitle;

    ListItem(final String title, final String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }
}