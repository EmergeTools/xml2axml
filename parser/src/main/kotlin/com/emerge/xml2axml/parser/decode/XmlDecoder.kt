package com.emerge.xml2axml.parser.decode

import com.emerge.xml2axml.parser.encode.EncodingType
import com.emerge.xml2axml.parser.util.getEncodingType
import org.w3c.dom.Document
import java.nio.file.Path
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

interface XmlDecoder {

    val documentBuilder: DocumentBuilder
        get() = DocumentBuilderFactory.newInstance().apply {
            isNamespaceAware = true
        }.newDocumentBuilder()

    fun toDocument(inputPath: Path): Document
}

fun decode(inputPath: Path): Document = when (getEncodingType(inputPath)) {
    EncodingType.STANDARD -> StandardXmlDecoder.toDocument(inputPath)
    EncodingType.PROTO -> ProtoXmlDecoder.toDocument(inputPath)
    EncodingType.BINARY -> AXmlDecoder.toDocument(inputPath)
}
