package com.emerge.xml2axml.parser.encode

import com.emerge.xml2axml.parser.axml.IntWriter
import com.emerge.xml2axml.parser.axml.chunks.TagChunk
import com.emerge.xml2axml.parser.axml.chunks.XmlChunk
import com.emerge.xml2axml.parser.axml.resources.content.Context
import com.emerge.xml2axml.parser.axml.resources.content.res.Resources
import com.emerge.xml2axml.parser.util.toByteArray
import org.w3c.dom.Document
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.nio.file.Path
import kotlin.io.path.writeBytes

object AXmlEncoder : XmlEncoder {

    override fun encode(document: Document, outputPath: Path) {
        val byteArray = document.toByteArray()
        val documentInputStream = ByteArrayInputStream(byteArray)

        val context = Context()
        val parserFactory = XmlPullParserFactory.newInstance().apply {
            setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true)
        }

        val parser = parserFactory.newPullParser()
        parser.setInput(documentInputStream, "UTF-8")

        val chunk = XmlChunk(context)
        var current: TagChunk? = null
        val resources = Resources.instance
        var eventType = parser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_DOCUMENT -> {}
                XmlPullParser.START_TAG -> {
                    current = TagChunk(current ?: chunk, parser, resources)
                }
                XmlPullParser.END_TAG -> {
                    val parent = current?.parent
                    current = if (parent is TagChunk) parent else null
                }
                XmlPullParser.TEXT -> {}
                else -> {}
            }
            eventType = parser.next()
        }
        val os = ByteArrayOutputStream()
        val w = IntWriter(os)
        chunk.write(w)
        w.close()
        outputPath.writeBytes(os.toByteArray())
    }
}
