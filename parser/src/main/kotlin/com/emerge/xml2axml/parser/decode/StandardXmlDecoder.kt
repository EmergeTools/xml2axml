package com.emerge.xml2axml.parser.decode

import org.w3c.dom.Document
import java.nio.file.Path

object StandardXmlDecoder : XmlDecoder {

    override fun toDocument(inputPath: Path): Document = documentBuilder.parse(inputPath.toFile())
}
