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
    url         = models.CharField(max_length=2083)
    version     = models.CharField(max_length=50)
    description = models.TextField(null=True, blank=True)
    createTime  = models.DateTimeField(auto_now_add=True)
    updateTime  = models.DateTimeField(auto_now=True)
    author      = models.ForeignKey(User, related_name='plugins', on_delete=models.PROTECT)
    lastUpdater = models.ForeignKey(User, related_name='+', on_delete=models.PROTECT)
    vendor      = models.ForeignKey(PluginVendor, related_name='plugins', on_delete=models.PROTECT)
