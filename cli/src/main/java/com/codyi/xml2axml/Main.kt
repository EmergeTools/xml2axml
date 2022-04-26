package com.codyi.xml2axml

import com.emerge.xml2axml.parser.decode.decode
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.path
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

fun main(args: Array<String>) = Xml2AXml()
    .subcommands(Encode(), Decode())
    .main(args)

class Xml2AXml : CliktCommand() {
    override fun run() = Unit
}

class Encode : CliktCommand(help = "Encode utf-8 xml to binary xml") {

    private val source by argument().path(mustExist = true)
    private val dest by argument().path()

    override fun run() {
        // TODO: Implement
        // AxmlEncoder.encodeFile(source, dest)
    }
}

class Decode : CliktCommand(help = "Decode from binary xml to standard utf-8 xml") {

    private val source by argument().path(mustExist = true)
    private val dest by argument().path()

    override fun run() {
        val document = decode(source)
        val source = DOMSource(document)
        val result = StreamResult(dest.toFile())

        val transformer = TransformerFactory.newInstance().newTransformer()
        transformer.transform(source, result)
    }
}

