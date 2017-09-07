# -*- coding: utf-8 -*-
from __future__ import unicode_literals

import mimetypes
import os

from django.http import FileResponse, HttpResponse

BASE_PATH = '/home/chenwei/IdeaProjects/DemoPlugin/enterprise-repo/uploaded-plugins'

# Create your views here.
def index(request):
    '''
    Return the requested versioned plugin file.
    '''

    final_file_path = _get_plugin_file_path(request)
    if os.path.isfile(final_file_path):
        content_type = mimetypes.guess_type(final_file_path)[0]
        file_size = os.path.getsize(final_file_path)
        response = FileResponse(open(final_file_path, 'rb'), content_type=content_type)
        response['Content-Length'] = file_size
        response['Content-Disposition'] = "attachment; filename=%s" % os.path.basename(final_file_path)
        return response
    return HttpResponse('File not exist', status=404)

def upload(request):
    '''
    Handle upload of plugin jar file or plugin zip file.
    '''

    #TODO:
    # 1 check file name can only contains ascii.
    # 2 url encode file name if needed
    # 3 store uploaded file to disk
    # 4 update table Plugin and PluginVersionedFile

    return None

def _get_plugin_file_path(request):
    #TODO: get plugin file path from database according to request information
    # version, versioned file id, etc.

    # simple approach
    filename = os.path.basename(request.path)
    final_file_path = os.path.join(BASE_PATH, filename)
    return final_file_path
