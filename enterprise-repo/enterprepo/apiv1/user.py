from django.contrib.auth.models import User
from rest_framework import permissions, serializers, viewsets

from permissions import IsUserOwner

# Serializers define the API representation.
class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ('username', 'email')

# ViewSets define the view behavior.
class UserViewSet(viewsets.ModelViewSet):
    lookup_field = 'username'
    queryset = User.objects.all()
    serializer_class = UserSerializer

    def get_permissions(self):
        if self.request.method in permissions.SAFE_METHODS:
            return (permissions.IsAdminUser(), )

        # allow anyone to create new user
        if self.request.method == 'POST':
            return (permissions.AllowAny(), )

        # only owner can perform UPDATE or DELETE
        return (permissions.IsAuthenticated(), IsUserOwner())