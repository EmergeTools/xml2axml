package com.emerge.xml2axml.parser.encode

import com.emerge.xml2axml.parser.util.toByteArray
import org.w3c.dom.Document
import java.nio.file.Path
import kotlin.io.path.writeBytes

object StandardXmlEncoder : XmlEncoder {

    override fun encode(document: Document, outputPath: Path) {
        val byteArray = document.toByteArray()
        outputPath.writeBytes(byteArray)
    }
}
