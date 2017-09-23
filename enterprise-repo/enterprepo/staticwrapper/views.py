# -*- coding: utf-8 -*-
from __future__ import unicode_literals

import os
import re
import posixpath

from django.views.static import serve
from django.http import Http404
from django.contrib.staticfiles import finders
from django.utils.six.moves.urllib.parse import unquote


plugin_icon_prog = re.compile('^/plugin/icons/')


def index(request, suffix):
    base_dir = 'ang'
    if plugin_icon_prog.match(request.path):
        base_dir = 'plugin/icons'
    path = os.path.join(base_dir, os.path.basename(request.path))
    normalized_path = posixpath.normpath(unquote(path)).lstrip('/')
    absolute_path = finders.find(normalized_path)
    if not absolute_path:
        if path.endswith('/') or path == '':
            raise Http404("Directory indexes are not allowed here.")
        raise Http404("'%s' could not be found" % path)
    document_root, path = os.path.split(absolute_path)
    return serve(request, path, document_root=document_root)


def _is_picture_ext(ext):
    return ext in ['png', 'jpeg', 'jpg', 'gif']
