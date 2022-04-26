package com.codyi.xml2axml

import com.emerge.xml2axml.parser.decode.decode
import com.emerge.xml2axml.parser.encode.EncodingType
import com.emerge.xml2axml.parser.encode.StandardXmlEncoder.encode
import com.emerge.xml2axml.parser.encode.encode
import com.emerge.xml2axml.parser.util.getEncodingType
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.types.enum
import com.github.ajalt.clikt.parameters.types.path
import kotlin.io.path.pathString

fun main(args: Array<String>) = Xml2AXml()
    .subcommands(Encode(), Decode())
    .main(args)

class Xml2AXml : CliktCommand() {
    override fun run() = Unit
}

class Encode : CliktCommand(help = "Encode utf-8 xml to binary xml") {

    private val source by argument().path(mustExist = true)
    private val dest by argument().path()
    private val encodingType by argument()
        .enum<EncodingType>()
        .default(EncodingType.BINARY)

    override fun run() {
        if (getEncodingType(source) == encodingType) {
            echo("Encoding of ${source.pathString} matches desired output encoding type $encodingType, skipping.")
            return
        }
        encode(decode(source), dest, encodingType)
    }
}

class Decode : CliktCommand(help = "Decode from binary xml to standard utf-8 xml") {

    private val source by argument().path(mustExist = true)
    private val dest by argument().path()

    override fun run() {
        val document = decode(source)
        encode(document, dest)
    }
}
