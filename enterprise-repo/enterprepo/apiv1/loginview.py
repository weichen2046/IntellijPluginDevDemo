import json

from django.contrib.auth import authenticate, login

from rest_framework import status, views
from rest_framework.response import Response

from user import UserSerializer


class LoginView(views.APIView):

    def post(self, request, format=None):
        if request.user and request.user.is_authenticated:
            return Response(UserSerializer(request.user).data)

        data = json.loads(request.body)

        username = data.get('username', None)
        password = data.get('password', None)

        user = authenticate(username=username, password=password)

        if user is None:
            return Response({
                'status': 'Unauthorized',
                'message': 'Username/password combination invalid.'
            }, status=status.HTTP_401_UNAUTHORIZED)

        if user.is_active:
            login(request, user)
            return Response(UserSerializer(user).data)
        else:
            return Response({
                'status': 'Unauthorized',
                'message': 'This account has been disabled.'
            }, status=status.HTTP_401_UNAUTHORIZED)
