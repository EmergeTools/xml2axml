package com.emerge.xml2axml.parser.encode

import org.w3c.dom.Document
import java.nio.file.Path

// TODO: Impl can encode a document to any other form of XML
interface XmlEncoder {

    fun Document.encode(outputPath: Path)
}
