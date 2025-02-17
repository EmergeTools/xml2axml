package com.emerge.xml2axml.parser.axml;

import com.emerge.xml2axml.parser.axml.chunks.ValueChunk;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Roy on 16-4-27.
 */
public class DefaultReferenceResolver implements ReferenceResolver {
    static Pattern pat = Pattern.compile("^@\\+?(?:(\\w+):)?(?:(\\w+)/)?(\\w+)$");

    public int resolve(ValueChunk value, String ref) {
        Matcher m=pat.matcher(ref);
        if (!m.matches()) throw new RuntimeException("invalid reference");
        String pkg=m.group(1);
        String type=m.group(2);
        String name=m.group(3);
        try {
            return Integer.parseInt(name, 16);
        }catch (Exception e){
            e.printStackTrace();
        }
        int id=value.getContext().getResources().getId(name,type,pkg);
        return id;

    }
}
