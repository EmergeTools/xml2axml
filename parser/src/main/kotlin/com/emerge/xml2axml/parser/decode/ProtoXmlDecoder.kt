package com.emerge.xml2axml.parser.decode

import com.android.aapt.Resources
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNode
import com.android.tools.build.bundletool.xml.XmlProtoToXmlConverter
import org.w3c.dom.Document
import java.io.InputStream

object ProtoXmlDecoder : XmlDecoder {

    override fun toDocument(inputStream: InputStream): Document {
        val xmlNode = Resources.XmlNode.parseFrom(inputStream)
        return XmlProtoToXmlConverter.convert(XmlProtoNode(xmlNode))
    }
}
