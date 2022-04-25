package com.emerge.xml2axml.parser.decode

import com.android.aapt.Resources
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNode
import com.android.tools.build.bundletool.xml.XmlProtoToXmlConverter
import org.w3c.dom.Document
import java.nio.file.Path
import kotlin.io.path.inputStream

object ProtoXmlDecoder : XmlDecoder {

    override fun toDocument(inputPath: Path): Document {
        val xmlNode = Resources.XmlNode.parseFrom(inputPath.inputStream())
        return XmlProtoToXmlConverter.convert(XmlProtoNode(xmlNode))
    }
}
