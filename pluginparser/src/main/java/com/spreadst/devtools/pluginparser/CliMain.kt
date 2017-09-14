package com.spreadst.devtools.pluginparser

import com.spreadst.devtools.pluginparser.json.JsonParser

fun main(args: Array<String>) {

    // TODO: use Apache Commons CLI to handle args
    // https://commons.apache.org/proper/commons-cli/

    if (args.size == 1) {
        // only one plugin jar/zip file provide
        val jsonStr = JsonParser.parsePlugin(args[0])
        println(jsonStr)
        return
    }

}
