package com.emerge.xml2axml.parser.util

import org.w3c.dom.Document
import java.io.ByteArrayOutputStream
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

fun Document.toByteArray(): ByteArray {
    val outputStream = ByteArrayOutputStream()

    val xmlSource = DOMSource(this)
    val outputTarget = StreamResult(outputStream)

    val transformer = TransformerFactory.newInstance().newTransformer()
    transformer.transform(xmlSource, outputTarget)
    return outputStream.toByteArray()
}
