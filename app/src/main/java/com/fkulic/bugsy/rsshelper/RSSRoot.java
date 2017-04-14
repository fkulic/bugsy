package com.fkulic.bugsy.rsshelper;

import com.fkulic.bugsy.Article;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Filip on 14.4.2017..
 */

@Root(name = "rss", strict = false)
public class RSSRoot {
    @Element(name = "channel") private Channel mChannel;

    public List<Article> getArticles() {
        return Channel.mArticles;
    }

    @Root(strict = false)
    static class Channel {
        @ElementList(name = "item", inline = true)
        private static List<Article> mArticles;
    }
}
