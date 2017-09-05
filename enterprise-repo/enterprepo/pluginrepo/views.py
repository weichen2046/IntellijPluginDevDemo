# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.shortcuts import render

from models import Plugin

# Create your views here.
def index(request):
    context = {
        'plugins': Plugin.objects.all()
    }
    return render(request, "pluginrepo/updatePlugins.xml", context, content_type="text/xml")