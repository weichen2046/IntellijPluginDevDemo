import os

from rest_framework import permissions, status, views
from rest_framework.response import Response

from django.conf import settings


class UploadPluginView(views.APIView):
    permission_classes = (permissions.IsAuthenticated,)

    def post(self, request, format=None):
        if request.FILES:
            return self._handle_upload_file(request, request.FILES['plugin_file'])
        return Response({'status': 'Ok'})

    def _handle_upload_file(self, request, file):
        '''
        Handle plugin file upload request.
        '''
        data = {}
        stat = status.HTTP_200_OK
        ok = True

        if ok and not self._simple_file_type_check(file):
            data['message'] = 'file extension should be .jar or .zip'
            stat = status.HTTP_400_BAD_REQUEST
            ok = False

        if ok and not self._file_size_check(file):
            data['message'] = 'file max size is 100M'
            stat = status.HTTP_400_BAD_REQUEST
            ok = False

        tmp_file = self._write_temp_file(file)
        if ok and not tmp_file:
            data['message'] = 'server write plugin file error'
            stat = status.HTTP_500_INTERNAL_SERVER_ERROR
            ok = False

        plugin_json = self._parse_plugin_file(tmp_file)
        if ok and not plugin_json:
            data['message'] = 'plugin file parse error'
            stat = status.HTTP_500_INTERNAL_SERVER_ERROR
            ok = False

        final_file = self._move_plugin_file(tmp_file, plugin_json)
        if ok and not final_file:
            data['message'] = 'store plugin file error'
            stat = status.HTTP_500_INTERNAL_SERVER_ERROR
            ok = False

        # TODO:
        # 1 if plugin file parse ok, update databases
        # 2 else return fail response

        return Response(data, status=stat)

    def _simple_file_type_check(self, file):
        '''
        Currently only check file extension, .jar or .zip will be valid.
        '''
        _, f_ext = os.path.splitext(file.name)
        if f_ext and f_ext.lower() in ('.jar', '.zip'):
            return True
        return False

    def _file_size_check(self, file):
        if file.size > 100 * 1024 * 1024:
            return False
        return True

    def _write_temp_file(self, file):
        '''
        Return the temp file absolute path or None.
        '''
        # TODO:
        return None

    def _parse_plugin_file(self, file_path):
        '''
        Use external tool pluginparser-0.1.jar to parse plugin file.
        Return a json object containing information of the plugin or None.
        '''
        # TODO:
        return None

    def _move_plugin_file(self, tmp_file_path, plugin_info):
        '''
        Move the temporary plugin file to final position according to the
        plugin information.
        Final directory like 'plugin_repo/plugin_id/plugin_version/'.
        Return final plugin path or None.
        '''
        # TODO:
        return None
