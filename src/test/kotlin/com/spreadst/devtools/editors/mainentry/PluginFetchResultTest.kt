package com.spreadst.devtools.editors.mainentry

import com.google.gson.Gson
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PluginFetchResultTest {
    private var resultPlugin: PluginFetchResult? = null

    @Before
    fun setUp() {
        val plugins = ArrayList<PluginBeanSimpleVersion>()
        var bean = PluginBeanSimpleVersion("com.spreadst.devtools.demoplugin", "Demo Plugin", "1.0",
                "http://127.0.0.1:8000/pluginjar/com.spreadst.devtools.demoplugin/1.0/Demo+Plugin-1.0.jar", null)
        plugins.add(bean)
        bean = PluginBeanSimpleVersion("mobi.hsz.idea.gitignore", ".ignore", "2.2.1",
                "http://127.0.0.1:8000/pluginjar/mobi.hsz.idea.gitignore/2.2.1/idea-gitignore-2.2.1.jar", null)
        plugins.add(bean)
        resultPlugin = PluginFetchResult("Ok", plugins)
    }

    @After
    fun tearDown() {
        resultPlugin = null
    }

    @Test
    fun testEquals() {
        val plugins = ArrayList<PluginBeanSimpleVersion>()
        var bean = PluginBeanSimpleVersion("com.spreadst.devtools.demoplugin", "Demo Plugin", "1.0",
                "http://127.0.0.1:8000/pluginjar/com.spreadst.devtools.demoplugin/1.0/Demo+Plugin-1.0.jar", null)
        plugins.add(bean)
        bean = PluginBeanSimpleVersion("mobi.hsz.idea.gitignore", ".ignore", "2.2.1",
                "http://127.0.0.1:8000/pluginjar/mobi.hsz.idea.gitignore/2.2.1/idea-gitignore-2.2.1.jar", null)
        plugins.add(bean)
        var result1 = PluginFetchResult("Ok", plugins)
        assertEquals("PluginFetchResult.equals method failed", resultPlugin, result1)
    }

    @Test
    fun testToJson() {
        val gson = Gson()
        val json = gson.toJson(resultPlugin)
        val expected =
                """{"status":"Ok","plugins":[{"id":"com.spreadst.devtools.demoplugin","name":"Demo Plugin","version":"1.0","url":"http://127.0.0.1:8000/pluginjar/com.spreadst.devtools.demoplugin/1.0/Demo+Plugin-1.0.jar"},{"id":"mobi.hsz.idea.gitignore","name":".ignore","version":"2.2.1","url":"http://127.0.0.1:8000/pluginjar/mobi.hsz.idea.gitignore/2.2.1/idea-gitignore-2.2.1.jar"}]}"""
        assertEquals("", expected, json)
    }

    @Test
    fun testFromJson() {
        val json =
                """{"status":"Ok","plugins":[{"id":"com.spreadst.devtools.demoplugin","name":"Demo Plugin","version":"1.0","url":"http://127.0.0.1:8000/pluginjar/com.spreadst.devtools.demoplugin/1.0/Demo+Plugin-1.0.jar"},{"id":"mobi.hsz.idea.gitignore","name":".ignore","version":"2.2.1","url":"http://127.0.0.1:8000/pluginjar/mobi.hsz.idea.gitignore/2.2.1/idea-gitignore-2.2.1.jar"}]}"""
        val gson = Gson()
        val resultObj = gson.fromJson(json, PluginFetchResult::class.java)
        assertEquals("PluginFetchResult object from json not equal to pre-defined", resultPlugin, resultObj)
    }
}