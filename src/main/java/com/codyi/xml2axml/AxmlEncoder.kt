package com.codyi.xml2axml

import android.content.Context
import android.content.res.Resources
import com.codyi.xml2axml.chunks.TagChunk
import com.codyi.xml2axml.chunks.XmlChunk
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.ByteArrayOutputStream
import java.nio.file.Path
import kotlin.io.path.inputStream
import kotlin.io.path.writeBytes

object AxmlEncoder {

    private val context = Context()
    private val parserFactory = XmlPullParserFactory.newInstance().apply {
        setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true)
    }

    fun encodeFile(source: Path, dest: Path) {
        val parser = parserFactory.newPullParser()
        parser.setInput(source.inputStream(), "UTF-8")

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
        dest.writeBytes(os.toByteArray())
    }
}
