package com.spreadst.devtools.editors.mainentry

import com.google.gson.Gson
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PluginBeanSimpleVersionTest {
    private var bean: PluginBeanSimpleVersion? = null

    @Before
    fun setUp() {
        bean = PluginBeanSimpleVersion("com.spreadst.devtools.demoplugin", "Demo Plugin", "1.0",
                "http://127.0.0.1:8000/pluginjar/com.spreadst.devtools.demoplugin/1.0/Demo+Plugin-1.0.jar", null)
    }

    @After
    fun tearDown() {
        bean = null
    }

    @Test
    fun testEquals() {
        val bean1 = PluginBeanSimpleVersion("com.spreadst.devtools.demoplugin", "Demo Plugin", "1.0",
                "http://127.0.0.1:8000/pluginjar/com.spreadst.devtools.demoplugin/1.0/Demo+Plugin-1.0.jar", null)
        assertEquals("PluginBeanSimpleVersion.equals method failed", bean, bean1)
    }

    @Test
    fun testToJson() {
        val gson = Gson()
        val json = gson.toJson(bean)
        val expected =
                """{"id":"com.spreadst.devtools.demoplugin","name":"Demo Plugin","version":"1.0","url":"http://127.0.0.1:8000/pluginjar/com.spreadst.devtools.demoplugin/1.0/Demo+Plugin-1.0.jar"}"""
        assertEquals("Json representation error", expected, json)
    }

    @Test
    fun testFromJson() {
        val json =
                """{"id":"com.spreadst.devtools.demoplugin","name":"Demo Plugin","version":"1.0","url":"http://127.0.0.1:8000/pluginjar/com.spreadst.devtools.demoplugin/1.0/Demo+Plugin-1.0.jar"}"""
        val gson = Gson()
        val beanObj = gson.fromJson(json, PluginBeanSimpleVersion::class.java)
        assertEquals("PluginBeanSimpleVersion object from json not equal to pre-defined", bean, beanObj)
    }
}