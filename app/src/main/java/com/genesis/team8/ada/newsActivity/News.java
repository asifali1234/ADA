package com.genesis.team8.ada.newsActivity;

/**
 * Created by asif ali on 14/01/17.
 */

public class News {
    private String mSection;
    private String mTitle;
    private String mWebUrl;
    private String mThumbNailUrl;

    public News(String section, String title, String WebUrl,String ThumbNaialUrl) {
        mSection = section;
        mTitle = title;
        mWebUrl = WebUrl;
       mThumbNailUrl = ThumbNaialUrl;
    }

    public String getSection()
    {
        return mSection;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public String getWebUrl()
    {
        return mWebUrl;
    }

    public String getThumbNailUrl()
    {
       return mThumbNailUrl;
    }
}
