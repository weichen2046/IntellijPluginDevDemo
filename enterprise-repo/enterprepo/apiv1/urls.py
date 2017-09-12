from django.conf.urls import include, url
from rest_framework import routers

from user import UserViewSet
from authview import LoginView, LogoutView
from uploadplugin import UploadPluginView

# Routers provide an easy way of automatically determining the URL conf.
router = routers.DefaultRouter()
router.register(r'users', UserViewSet)

urlpatterns = [
    url(r'^', include(router.urls)),
    url(r'^auth/login/', LoginView.as_view(), name='api_login'),
    url(r'^auth/logout/', LogoutView.as_view(), name='api_logout'),
    url(r'^auth/register/', UserViewSet.as_view({'post': 'create'}), name='api_register'),
    url(r'^uploadplugin/', UploadPluginView.as_view(), name='api_uploadplugin'),
    url(r'^api-auth/', include('rest_framework.urls', namespace='rest_framework')),
]
