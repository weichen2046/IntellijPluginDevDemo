# -*- coding: utf-8 -*-
from __future__ import unicode_literals

import mimetypes
import os

from django.http import FileResponse, HttpResponse

from pluginjar.models import PluginVersionedFile


# Create your views here.
def index(request, plugin_id, plugin_version, plugin_file_name):
    '''
    Return the requested versioned plugin file.
    '''

    final_file_path = _get_plugin_file_path(
        request, plugin_id, plugin_version, plugin_file_name)
    if final_file_path is not None and os.path.isfile(final_file_path):
        content_type = mimetypes.guess_type(final_file_path)[0]
        file_size = os.path.getsize(final_file_path)
        response = FileResponse(
            open(final_file_path, 'rb'), content_type=content_type)
        response['Content-Length'] = file_size
        response['Content-Disposition'] = "attachment; filename=%s" % os.path.basename(
            final_file_path)
        return response
    return HttpResponse('File not exist', status=404)


def _get_plugin_file_path(request, plugin_id, plugin_version, plugin_file_name):
    '''
    Get plugin file path according to plugin id and plugin version.
    Return None if not found.
    '''

    p_versioned = None
    try:
        p_versioned = PluginVersionedFile.objects.get(
            plugin__strId=plugin_id, version=plugin_version)
    except PluginVersionedFile.DoesNotExist:
        return None

    basename = os.path.basename(p_versioned.path)
    if basename != plugin_file_name:
        return None
    return p_versioned.path
