package com.fkulic.bugsy.rsshelper;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by Filip on 14.4.2017..
 */

@Root(strict = false)
public class Enclosure {
    @Attribute(name = "url")
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }
}