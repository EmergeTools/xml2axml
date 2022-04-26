package com.emerge.xml2axml.parser.axml.resources.content;

import com.emerge.xml2axml.parser.axml.resources.content.res.Resources;

/**
 * Created by Roy on 15-10-6.
 */
public class Context {
    private Resources resources=new Resources();
    public Resources getResources() {
        return resources;
    }

    @Deprecated
    public String getPackageName() {
        return "com.example.reforceapp";
    }
}
