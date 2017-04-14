package com.fkulic.bugsy;

import org.simpleframework.xml.Attribute;
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
        return Enclosure.imgUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getCategory() {
        return mCategory;
    }

    @Root(strict = false)
    static class Enclosure{
        @Attribute(name = "url")
        private static String imgUrl;

    }
}
