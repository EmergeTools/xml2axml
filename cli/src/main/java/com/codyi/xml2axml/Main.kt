package com.codyi.xml2axml

import com.emerge.xml2axml.parser.decode.decode
import com.emerge.xml2axml.parser.encode.EncodingType
import com.emerge.xml2axml.parser.encode.encode
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.enum
import com.github.ajalt.clikt.parameters.types.path

fun main(args: Array<String>) = Xml2AXml()
    .subcommands(Encode(), Decode())
    .main(args)

class Xml2AXml : CliktCommand() {
    override fun run() = Unit
}

class Encode : CliktCommand(help = "Encode utf-8 xml to binary xml") {

    private val source by argument().path(mustExist = true)
    private val sourceEncodingType by option()
        .enum<EncodingType>()
        .default(EncodingType.STANDARD)
    private val dest by argument().path()
    private val destEncodingType by option()
        .enum<EncodingType>()
        .default(EncodingType.BINARY)

    override fun run() {
        if (sourceEncodingType == destEncodingType) {
            echo("Source encoding $sourceEncodingType matches desired dest encoding type $destEncodingType, skipping.")
            return
        }
        encode(decode(source, sourceEncodingType), dest, destEncodingType)
    }
}

class Decode : CliktCommand(help = "Decode from binary xml to standard utf-8 xml") {

    private val source by argument().path(mustExist = true)
    private val sourceEncodingType by option()
        .enum<EncodingType>()
        .default(EncodingType.BINARY)
    private val dest by argument().path()
    private val destEncodingType by option()
        .enum<EncodingType>()
        .default(EncodingType.STANDARD)

    override fun run() {
        val document = decode(source, sourceEncodingType)
        encode(document, dest, destEncodingType)
    }
}
