package com.codyi.xml2axml

import android.content.res.AXmlResourceParser
import android.content.res.Resources
import android.util.TypedValue
import org.xmlpull.v1.XmlPullParser
import java.nio.file.Path
import kotlin.io.path.inputStream
import kotlin.io.path.writeLines

object AxmlDecoder {

    fun decodeFile(source: Path, dest: Path) {
        val resources = Resources.instance
        val parser = AXmlResourceParser()
        parser.open(source.inputStream())
        val indent = StringBuilder(10)

        val lines = mutableListOf<String>()

        var eventType = parser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_DOCUMENT -> lines.add("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
                XmlPullParser.START_TAG -> {
                    lines.add(String.format("%s<%s%s", indent, getNamespacePrefix(parser.prefix), parser.name))
                    indent.append(INDENT)
                    val namespaceCountBefore = parser.getNamespaceCount(parser.depth - 1)
                    val namespaceCount = parser.getNamespaceCount(parser.depth)

                    var i = namespaceCountBefore
                    while (i != namespaceCount) {
                        lines.add(String.format("%sxmlns:%s=\"%s\"",
                            indent,
                            parser.getNamespacePrefix(i),
                            parser.getNamespaceUri(i)))
                        ++i
                    }

                    (0 until parser.attributeCount).forEach { attributeIndex ->

                        val resource = parser.getAttributeNameResource(attributeIndex)
                        var name = parser.getAttributeName(attributeIndex)
                        if (name == null || name == "") {
                            name = resources.entries.firstOrNull { it.value == resource }?.key
                        }

                        var prefix = parser.getAttributePrefix(attributeIndex)
                        if (resource != 0) {
                            prefix = "android"
                        }

                        name?.let {
                            val line = String.format("%s%s%s=\"%s\"", indent,
                                getNamespacePrefix(prefix),
                                it,
                                getAttributeValue(parser, attributeIndex)
                            )
                            lines.add(line)
                        }
                    }
                    lines.add(String.format("%s>", indent))
                }
                XmlPullParser.END_TAG -> {
                    indent.setLength(indent.length - INDENT.length)
                    lines.add(String.format("%s</%s%s>", indent,
                        getNamespacePrefix(parser.prefix),
                        parser.name))
                }
                XmlPullParser.TEXT -> lines.add(String.format("%s%s", indent, parser.text))
                else -> {}
            }
            eventType = parser.next()
        }

        dest.writeLines(lines)
    }

    private fun getNamespacePrefix(prefix: String?) = if (prefix.isNullOrEmpty()) {
        ""
    } else "$prefix:"

    private fun getAttributeValue(
        parser: AXmlResourceParser,
        index: Int
    ): String {
        val type = parser.getAttributeValueType(index)
        val data = parser.getAttributeValueData(index)

        return when (type) {
            TypedValue.TYPE_STRING -> parser.getAttributeValue(index)
            TypedValue.TYPE_ATTRIBUTE -> String.format("?%s%08X", getPackage(data), data)
            TypedValue.TYPE_REFERENCE -> String.format("@%s%08X", getPackage(data), data)
            TypedValue.TYPE_FLOAT -> java.lang.Float.intBitsToFloat(data).toString()
            TypedValue.TYPE_INT_HEX -> String.format("0x%08X", data)
            TypedValue.TYPE_INT_BOOLEAN -> if (data != 0) "true" else "false"
            TypedValue.TYPE_DIMENSION -> complexToFloat(data).toString() +
                DIMENSION_UNITS[data and TypedValue.COMPLEX_UNIT_MASK]
            TypedValue.TYPE_FRACTION -> complexToFloat(data).toString() +
                FRACTION_UNITS[data and TypedValue.COMPLEX_UNIT_MASK]
            else -> {
                if (type >= TypedValue.TYPE_FIRST_COLOR_INT && type <= TypedValue.TYPE_LAST_COLOR_INT) {
                    return String.format("#%08X", data)
                }
                if (type >= TypedValue.TYPE_FIRST_INT && type <= TypedValue.TYPE_LAST_INT) {
                    data.toString()
                } else String.format("<0x%X, type 0x%02X>", data, type)
            }
        }
    }

    private fun getPackage(id: Int) = if (id ushr 24 == 1) {
        "android:"
    } else ""

    /////////////////////////////////// ILLEGAL STUFF, DONT LOOK :)

    private fun complexToFloat(complex: Int): Float {
        return (complex and -0x100).toFloat() * RADIX_MULTS[complex shr 4 and 3]
    }

    private const val INDENT = "	"
    private val RADIX_MULTS = floatArrayOf(0.00390625f, 3.051758E-005f, 1.192093E-007f, 4.656613E-010f)
    private val DIMENSION_UNITS = arrayOf("px", "dip", "sp", "pt", "in", "mm", "", "")
    private val FRACTION_UNITS = arrayOf("%", "%p", "", "", "", "", "", "")
}
