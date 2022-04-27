package com.emerge.xml2axml.parser.decode

import com.emerge.xml2axml.parser.encode.EncodingType
import org.w3c.dom.Document
import java.io.InputStream
import java.nio.file.Path
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.io.path.inputStream

interface XmlDecoder {

    val documentBuilder: DocumentBuilder
        get() = DocumentBuilderFactory.newInstance().apply {
            isNamespaceAware = true
        }.newDocumentBuilder()

    fun toDocument(inputPath: Path): Document = toDocument(inputPath.inputStream())

    fun toDocument(inputStream: InputStream): Document
}

fun decode(inputPath: Path, inputEncodingType: EncodingType): Document = when (inputEncodingType) {
    EncodingType.STANDARD -> StandardXmlDecoder.toDocument(inputPath)
    EncodingType.PROTO -> ProtoXmlDecoder.toDocument(inputPath)
    EncodingType.BINARY -> AXmlDecoder.toDocument(inputPath)
}

fun decode(inputStream: InputStream, inputEncodingType: EncodingType): Document = when (inputEncodingType) {
    EncodingType.STANDARD -> StandardXmlDecoder.toDocument(inputStream)
    EncodingType.PROTO -> ProtoXmlDecoder.toDocument(inputStream)
    EncodingType.BINARY -> AXmlDecoder.toDocument(inputStream)
}
