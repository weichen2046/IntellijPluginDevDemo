from django.contrib.auth.models import User
from rest_framework import permissions, serializers, viewsets

from permissions import IsUserOwner


# Serializers define the API representation.
class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ('username', 'email', 'password')
        # this will omit password field when serialize
        extra_kwargs = {'password': {'write_only': True}}

    def create(self, validated_data):
        instance = User.objects.create(**validated_data)
        # TODO: why User.objects.create(**validated_data) can't hash password
        # for us?
        # we use the following two line to save the hashed password or it will
        # keep the original format from client and will cause authentication
        # fail.
        instance.set_password(validated_data['password'])
        instance.save()
        return instance


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
