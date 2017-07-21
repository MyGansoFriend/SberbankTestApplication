package com.gusev.elisium.sberbanktestapplication.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gusev.elisium.sberbanktestapplication.tools.StringParser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by elisiumGusev
 *
 * @Date 21/07/2017
 * @Autor Andrei Gusev
 */
public class Hab extends BaseXmlModel {
    @Nullable
    private String guid;
    @Nullable
    private String link;
    @Nullable
    private String description;
    @Nullable
    private String pubDate;
    @Nullable
    private String enclosure;
    @Nullable
    private String dc_creator;
    @NonNull
    private List<String> categories;

    @NonNull
    private UUID id;
    @Nullable
    private String title;


    public Hab() {
        id = UUID.randomUUID();
        categories = new ArrayList<>();
    }

    @Nullable
    public String getGuid() {
        return guid;
    }

    public void setGuid(@Nullable String guid) {
        this.guid = guid;
    }

    @Nullable
    public String getLink() {
        return link;
    }

    public void setLink(@Nullable String link) {
        this.link = link;
    }

    @Nullable
    public String getDescription() {
        return description;
    }


    @Nullable
    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(@Nullable String pubDate) {
        this.pubDate = pubDate;
    }

    @Nullable
    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(@Nullable String enclosure) {
        this.enclosure = enclosure;
    }

    @Nullable
    public String getDc_creator() {
        return dc_creator;
    }

    public void setDc_creator(@Nullable String dc_creator) {
        this.dc_creator = dc_creator;
    }

    public void setCategories(@NonNull List<String> categories) {
        this.categories = categories;
    }

    @NonNull
    public UUID getId() {
        return id;
    }

    public void setId(@NonNull UUID id) {
        this.id = id;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    public void addCategory(String category) {
        categories.add(category);
    }

    public List<String> getCategories() {
        return categories;
    }

    public void updateDescription() {
        enclosure = StringParser.getImgFromString(description);
        description = StringParser.removeHtmlTags(description);
    }
}
