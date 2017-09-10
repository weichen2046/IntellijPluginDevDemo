from rest_framework import permissions

class IsUserOwner(permissions.BasePermission):
    def has_object_permission(self, request, view, user):
        if request.user:
            return request.user == user
        return False