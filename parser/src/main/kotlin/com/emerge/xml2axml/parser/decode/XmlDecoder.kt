package com.emerge.xml2axml.parser.decode

import com.android.aapt.Resources.XmlNode
import org.apache.tika.Tika
import org.w3c.dom.Document
import java.io.IOException
import java.nio.file.Path
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.io.path.inputStream
import kotlin.io.path.name

interface XmlDecoder {

    val documentBuilder: DocumentBuilder
        get() = DocumentBuilderFactory.newInstance().apply {
            isNamespaceAware = true
        }.newDocumentBuilder()

    fun toDocument(inputPath: Path): Document
}

fun decode(inputPath: Path): Document {
    // We needed to leverage Tika as a universal source of truth for mime-types,
    // as Files.probeContentType is system dependent.
    return when (val mimeType: String = Tika().detect(inputPath.inputStream())) {
        "application/xml",
        "text/xml" -> StandardXmlDecoder.toDocument(inputPath)
        "application/octet-stream" -> {
            // Both Proto XML and AXML have "application/octet-stream" mime types
            // So we'll try to parse the node as proto first, and if it fails fallback to AXML.
            return try {
                XmlNode.parseFrom(inputPath.inputStream())
                ProtoXmlDecoder.toDocument(inputPath)
            } catch (exception: IOException) {
                AXmlDecoder.toDocument(inputPath)
            }
        }
        else -> throw IllegalArgumentException("Unsupported mime type $mimeType for input file ${inputPath.name}")
    }
}
