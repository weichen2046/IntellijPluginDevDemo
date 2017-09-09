# -*- coding: utf-8 -*-
from __future__ import unicode_literals

import os
import posixpath

from django.views.static import serve
from django.http import Http404
from django.contrib.staticfiles import finders
from django.utils.six.moves.urllib.parse import unquote

# Create your views here.
def index(request, suffix):
    path = os.path.join('ang', os.path.basename(request.path))
    normalized_path = posixpath.normpath(unquote(path)).lstrip('/')
    absolute_path = finders.find(normalized_path)
    if not absolute_path:
        if path.endswith('/') or path == '':
            raise Http404("Directory indexes are not allowed here.")
        raise Http404("'%s' could not be found" % path)
    document_root, path = os.path.split(absolute_path)
    return serve(request, path, document_root=document_root)