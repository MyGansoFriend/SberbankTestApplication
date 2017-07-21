package com.gusev.elisium.sberbanktestapplication.data.model;

import android.support.annotation.Nullable;

/**
 * Created by elisiumGusev
 *
 * @Date 21/07/2017
 * @Author Andrei Gusev
 */

public class Channel extends BaseXmlModel{
    @Nullable
    private String title;
    @Nullable
    private String description;
    @Nullable
    private String language;
    @Nullable
    private String pubDate;


    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Nullable
    public String getLanguage() {
        return language;
    }

    public void setLanguage(@Nullable String language) {
        this.language = language;
    }

    @Nullable
    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(@Nullable String pubDate) {
        this.pubDate = pubDate;
    }
}
