package com.emerge.xml2axml.parser.decode

import org.w3c.dom.Document
import java.io.InputStream

object StandardXmlDecoder : XmlDecoder {

    override fun toDocument(inputStream: InputStream): Document = documentBuilder.parse(inputStream)
}
