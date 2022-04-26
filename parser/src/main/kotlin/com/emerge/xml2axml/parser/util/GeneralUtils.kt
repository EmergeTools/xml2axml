package com.emerge.xml2axml.parser.util

import com.android.aapt.Resources
import com.emerge.xml2axml.parser.encode.EncodingType
import org.apache.tika.Tika
import java.io.IOException
import java.io.InputStream
import java.nio.file.Path
import kotlin.io.path.inputStream

fun getEncodingType(inputPath: Path): EncodingType = getEncodingType(inputPath.inputStream())

fun getEncodingType(inputStream: InputStream): EncodingType {
    // We needed to leverage Tika as a universal source of truth for mime-types,
    // as Files.probeContentType is system dependent.
    return when (val mimeType: String = Tika().detect(inputStream)) {
        "application/xml",
        "text/xml" -> EncodingType.STANDARD
        "application/octet-stream" -> {
            // Both Proto XML and AXML have "application/octet-stream" mime types
            // So we'll try to parse the node as proto first, and if it fails fallback to AXML.
            return try {
                Resources.XmlNode.parseFrom(inputStream)
                EncodingType.PROTO
            } catch (exception: IOException) {
                EncodingType.BINARY
            }
        }
        else -> throw IllegalArgumentException("Unsupported mime type $mimeType for input.")
    }
}
