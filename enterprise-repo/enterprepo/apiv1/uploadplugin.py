import json
import os
import tempfile

try:
    import subprocess32 as subprocess
except:
    import subprocess

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
            os.remove(tmp_file)
            data['message'] = 'plugin file parse error'
            stat = status.HTTP_500_INTERNAL_SERVER_ERROR
            ok = False

        final_file, error_info = self._move_plugin_file(file, tmp_file, plugin_json)
        if ok and not final_file:
            data['message'] = 'store plugin file error: %s' % error_info
            stat = status.HTTP_500_INTERNAL_SERVER_ERROR
            ok = False

        if ok:
            # TODO:
            # 1 if plugin file parse ok, update databases
            # 2 else return fail response
            pass

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
        basename = os.path.basename(file.name)
        _, f_ext = os.path.splitext(basename)
        file_no, tmp_file = tempfile.mkstemp(suffix=f_ext)
        # copy file content to temp file
        with os.fdopen(file_no, 'w') as dest:
            for chunk in file.chunks():
                dest.write(chunk)
        return tmp_file if tmp_file else None

    def _parse_plugin_file(self, file_path):
        '''
        Use external tool pluginparser-0.1.jar to parse plugin file.
        Return a json object containing information of the plugin or None.
        '''
        plugin_parser = settings.EXTERNAL_TOOLS['plugin_parser']
        try:
            output = subprocess.check_output(
                ['java -jar %s %s' % (plugin_parser, file_path)], shell=True)
            pluign_info = json.loads(output)
            return pluign_info
        except Exception as ex:
            print ex
            return None

    def _move_plugin_file(self, file, tmp_file, plugin_info):
        '''
        Move the temporary plugin file to final position according to the
        plugin information.
        Final directory like 'plugin_repo/plugin_id/plugin_version/'.
        Return tuple combine with final plugin path and error.
        '''
        basename = os.path.basename(file.name)
        if plugin_info['status']:
            p = plugin_info['plugin']
            p_id = p['myPluginId']
            p_version = p['myPluginVersion']
            dest_dir = os.path.join(settings.UPLOADED_PLUGIN_BASE_DIR, p_id, p_version)
            if not os.path.exists(dest_dir):
                os.makedirs(dest_dir)
            final_file = os.path.join(dest_dir, basename)
            if os.path.exists(final_file):
                os.remove(tmp_file)
                return (None, 'plugin file already exist')
            os.rename(tmp_file, final_file)
            return (final_file, None)
        else:
            os.remove(tmp_file)
        return (None, 'plugin info format error')
