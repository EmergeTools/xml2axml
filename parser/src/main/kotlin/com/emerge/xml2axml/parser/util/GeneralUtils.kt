package com.emerge.xml2axml.parser.util

import com.android.aapt.Resources
import com.emerge.xml2axml.parser.encode.EncodingType
import org.apache.tika.Tika
import java.io.IOException
import java.nio.file.Path
import kotlin.io.path.inputStream
import kotlin.io.path.name

fun getEncodingType(inputPath: Path): EncodingType {
    // We needed to leverage Tika as a universal source of truth for mime-types,
    // as Files.probeContentType is system dependent.
    return when (val mimeType: String = Tika().detect(inputPath.inputStream())) {
        "application/xml",
        "text/xml" -> EncodingType.STANDARD
        "application/octet-stream" -> {
            // Both Proto XML and AXML have "application/octet-stream" mime types
            // So we'll try to parse the node as proto first, and if it fails fallback to AXML.
            return try {
                Resources.XmlNode.parseFrom(inputPath.inputStream())
                EncodingType.PROTO
            } catch (exception: IOException) {
                EncodingType.BINARY
            }
        }
        else -> throw IllegalArgumentException("Unsupported mime type $mimeType for input file ${inputPath.name}")
    }
}
