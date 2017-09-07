# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.contrib.auth.models import User
from django.db import models

# Create your models here.
class PluginVendor(models.Model):
    name    = models.CharField(max_length=200)
    email   = models.EmailField()
    url     = models.URLField()

class Plugin(models.Model):
    name        = models.CharField(max_length=50)
    strId       = models.CharField(max_length=256)
    createTime  = models.DateTimeField(auto_now_add=True)
    # description can only has one copy, maybe we can provide entry to allow
    # modify this field.
    description = models.TextField(null=True, blank=True)
    # version here represent the newest version of this plugin, should updated
    # when a new version of the plugin uploaded.
    version     = models.CharField(max_length=50)
    # the newest url to download this plugin, usually url is dynamic generated
    # when user uploaded this plugin.
    url         = models.CharField(max_length=2083)
    # who created the plugin, always equal to the first uploader
    author      = models.ForeignKey(User, related_name='plugins', on_delete=models.PROTECT)
    # vendor information
    vendor      = models.ForeignKey(PluginVendor, related_name='plugins', on_delete=models.PROTECT)
    # last time the plugin got updated, including description or version or
    # other field
    updateTime  = models.DateTimeField(auto_now=True)
    # use PluginVersionedFile.uploader instead
    lastUpdater = models.ForeignKey(User, related_name='+', on_delete=models.PROTECT)
