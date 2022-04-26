package com.emerge.xml2axml.parser.encode

import org.w3c.dom.Document
import java.nio.file.Path

interface XmlEncoder {

    fun encode(document: Document, outputPath: Path)
}

fun encode(source: Document, dest: Path, encodingType: EncodingType) {
    when (encodingType) {
        EncodingType.STANDARD -> StandardXmlEncoder.encode(source, dest)
        EncodingType.PROTO -> ProtoXmlEncoder.encode(source, dest)
        EncodingType.BINARY -> AXmlEncoder.encode(source, dest)
    }
}

enum class EncodingType {
    STANDARD,
    PROTO,
    BINARY,
}
