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

from pluginrepo.models import Plugin, PluginVendor
from pluginjar.models import PluginVersionedFile


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

        # TODO:
        # 1 check file name can only contains ascii.
        # 2 url encode file name if needed, space will cause IDEA plugin
        # downloader error.

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

        final_file, error_info = self._move_plugin_file(
            file, tmp_file, plugin_json)
        if ok and not final_file:
            data['message'] = 'store plugin file error: %s' % error_info
            stat = status.HTTP_500_INTERNAL_SERVER_ERROR
            ok = False

        try:
            if ok:
                self._update_database(final_file, plugin_json)
        except Exception as ex:
            print ex
            data['message'] = 'store plugin to db error: %s' % ex.message
            stat = status.HTTP_500_INTERNAL_SERVER_ERROR
            ok = False
            # delete final_file to avoid prevent next upload
            os.remove(final_file)

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
        Use external tool pluginparser-<version>.jar to parse plugin file.
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
            dest_dir = os.path.join(
                settings.UPLOADED_PLUGIN_BASE_DIR, p_id, p_version)
            if not os.path.exists(dest_dir):
                os.makedirs(dest_dir)
            final_file = os.path.join(dest_dir, basename)
            if os.path.exists(final_file):
                os.remove(tmp_file)
                return (None, 'plugin file with name: %s already exist' % basename)
            os.rename(tmp_file, final_file)
            return (final_file, None)
        else:
            os.remove(tmp_file)
        return (None, 'plugin info format error')

    def _update_database(self, file, plugin_info):
        '''
        Insert or update plugin database record. Always returns True or throw
        Exception.
        '''

        p = plugin_info['plugin']

        # plugin vendor
        p_vendor_name = p['myPluginVendor']
        p_vendor_url = p['myVendorUrl']
        p_vendor_email = p['myVendorEmail']
        vendor = None
        try:
            vendor = PluginVendor.objects.get(name=p_vendor_name)
        except PluginVendor.DoesNotExist:
            vendor = PluginVendor(name=p_vendor_name)
        vendor.email = p_vendor_email
        vendor.url = p_vendor_url
        vendor.save()

        # plugin
        p_db = None
        p_id = p['myPluginId']
        p_name = p['myPluginName']
        p_description = p['myDescription']
        p_version = p['myPluginVersion']
        # dynamic generate download url
        p_url = self._generate_plugin_url(p_id, p_version, file)
        p_vendor = vendor
        p_last_updater = self.request.user
        try:
            p_db = Plugin.objects.get(strId=p_id)
            p_db.lastUpdater = p_last_updater
            p_db.vendor = p_vendor
            # check version exist or not
            try:
                PluginVersionedFile.objects.get(plugin=p_db, version=p_version)
                raise Exception(
                    'plugin with the version: %s already exist' % p_version)
                # TODO: do we need to check version downgrade?
            except PluginVersionedFile.DoesNotExist:
                pass
        except Plugin.DoesNotExist:
            p_author = self.request.user
            p_db = Plugin(strId=p_id, author=p_author,
                          lastUpdater=p_last_updater, vendor=p_vendor)
        p_db.name = p_name
        p_db.description = p_description
        p_db.version = p_version
        p_db.url = p_url
        p_db.save()

        # plugin versioned file
        p_versioned = None
        p_change_notes = p['myNotes']
        try:
            p_versioned = PluginVersionedFile.objects.get(path=file)
            p_versioned.plugin = p_db
            p_versioned.uploader = self.request.user
        except PluginVersionedFile.DoesNotExist:
            p_versioned = PluginVersionedFile(
                path=file, plugin=p_db, uploader=self.request.user)
        p_versioned.version = p_version
        p_versioned.changeNotes = p_change_notes
        p_versioned.save()

        return True

    def _generate_plugin_url(self, plugin_id, plugin_version, plugin_file):
        basename = os.path.basename(plugin_file)
        return '%s/%s/%s/%s' % (self._get_plugin_download_base_url(), plugin_id, plugin_version, basename)

    def _get_plugin_download_base_url(self):
        # TODO: can change if needed
        return settings.DOWNLOAD_PLUGIN_BASE_URL
