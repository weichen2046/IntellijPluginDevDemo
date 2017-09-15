from django.conf.urls import url

from . import views

urlpatterns = [
    url(r'^(?P<plugin_id>.*)/(?P<plugin_version>.*)/(?P<plugin_file_name>.*)$',
        views.index, name="pluginjar")
]
