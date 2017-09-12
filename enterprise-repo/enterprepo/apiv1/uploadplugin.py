from rest_framework import views
from rest_framework.response import Response


class UploadPluginView(views.APIView):

    def post(self, request, format=None):
        if request.FILES:
            return self._handle_upload_file(request, request.FILES['plugin_file'])
        return Response({'status': 'Ok'})

    def _handle_upload_file(self, request, file):
        # TODO:
        # 1 file type check
        # 2 file size check, max is 100M
        # 3 store file to local disk
        # 4 update databases
        return Response({'status': 'Ok'})
