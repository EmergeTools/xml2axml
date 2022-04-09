package com.codyi.xml2axml

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.path

fun main(args: Array<String>) = Xml2Axml()
    .subcommands(Encode(), Decode())
    .main(args)

class Xml2Axml : CliktCommand() {
    override fun run() = Unit
}

class Encode : CliktCommand(help = "Encode utf-8 xml to binary xml") {

    private val source by argument().path(mustExist = true)
    private val dest by argument().path()

    override fun run() {
        AxmlEncoder.encodeFile(source, dest)
    }
}

class Decode : CliktCommand(help = "Decode from binary xml to utf-8 xml") {

    private val source by argument().path(mustExist = true)
    private val dest by argument().path()

    override fun run() {
        AxmlDecoder.decodeFile(source, dest)
    }
}

