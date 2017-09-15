# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.contrib.auth.models import User
from django.db import models

from pluginrepo.models import Plugin

# Create your models here.
class PluginVersionedFile(models.Model):
    plugin      = models.ForeignKey(Plugin, related_name='versioned_files', on_delete=models.CASCADE)
    uploader    = models.ForeignKey(User, related_name='+', on_delete=models.PROTECT)
    uploadTime  = models.DateTimeField(auto_now_add=True)
    version     = models.CharField(max_length=100)
    changeNotes = models.TextField(null=True, blank=True)
    # path which the file stored on disk
    path        = models.CharField(max_length=2000, unique=True)
