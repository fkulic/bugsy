package com.fkulic.bugsy;

import com.fkulic.bugsy.rsshelper.Enclosure;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Filip on 14.4.2017..
 */

@Root(name = "item", strict = false)
public class Article {
    @Element(name = "title") private String mTitle;
    @Element(name = "link") private String mLink;
    @Element(name = "enclosure") private Enclosure mEnclosure;
    @Element(name = "description") private String mDescription;
    @Element(name = "category") private String mCategory;

    public String getTitle() {
        return mTitle;
    }

    public String getLink() {
        return mLink;
    }

    public String getImgUrl() {
        return mEnclosure.getImgUrl();
    }

    public String getDescription() {
        return mDescription;
    }

    public String getCategory() {
        return mCategory;
    }
}
