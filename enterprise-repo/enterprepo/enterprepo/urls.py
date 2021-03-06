"""enterprepo URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.11/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import include, url
from django.contrib import admin
from django.views.generic.base import TemplateView

urlpatterns = [
    url(r'^pluginrepo/', include('pluginrepo.urls')),
    url(r'^pluginjar/', include('pluginjar.urls')),
    url(r'^admin/', admin.site.urls),
    url(r'^api/v1/', include('apiv1.urls')),
    # for static files
    url(r'^.*\.(woff2|woff|ttf|js|map|png|jpg|jpeg)', include('staticwrapper.urls')),
    url(r'^.*$', TemplateView.as_view(template_name="home.html"), name="home"),
]
