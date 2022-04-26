package com.emerge.xml2axml.parser.axml;

import com.emerge.xml2axml.parser.axml.chunks.ValueChunk;

/**
 * Created by Roy on 15-10-5.
 */
public interface ReferenceResolver {
    int resolve(ValueChunk value, String ref);
}
