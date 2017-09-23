from rest_framework import status, views
from rest_framework.response import Response

from pluginrepo.models import Plugin


class PluginQueryView(views.APIView):

    def get(self, request, format=None):
        if not request.GET.has_key('type'):
            return Response({
                'status': 'Fail',
                'message': 'must specify type parameter'
            }, status=status.HTTP_400_BAD_REQUEST)

        max_record = 10
        if request.GET.has_key('max'):
            try:
                max_record = int(request.GET['max'])
            except ValueError:
                pass
        query_type = request.GET['type']

        if query_type == 'search':
            if not request.GET.has_key('term'):
                return Response({
                    'status': 'Fail',
                    'message': 'must specify search term'
                }, status=status.HTTP_400_BAD_REQUEST)
            search_term = request.GET['term']
            return self._handle_search_query(search_term, max_record)
        if query_type == 'hot':
            return self._handle_hot_query(max_record)
        if query_type == 'new_added':
            return self._handle_new_added_query(max_record)
        if query_type == 'update_available':
            if not request.GET.has_key('plugins') or not request.GET['plugins']:
                return Response({
                    'status': 'Fail',
                    'message': 'must specify plugins and versions'
                }, status=status.HTTP_400_BAD_REQUEST)
            plugins_str = request.GET['plugins']
            return self._handle_update_available_query(plugins_str, max_record)

        return Response({
            'status': 'Fail',
            'message': 'unsupport query type "%s"' % query_type
        }, status=status.HTTP_400_BAD_REQUEST)

    def _handle_search_query(self, term, max_record):
        # TODO:
        return Response({'status': 'Ok'})

    def _handle_hot_query(self, max_record):
        # TODO:
        return Response({'status': 'Ok'})

    def _handle_new_added_query(self, max_record):
        plugin_objs = Plugin.objects.all().order_by('-updateTime')[:max_record]
        plugins = []
        for plugin_obj in plugin_objs:
            plugin = {
                'id': plugin_obj.strId,
                'name': plugin_obj.name,
                'version': plugin_obj.version,
                'url': plugin_obj.url,
                'icon': plugin_obj.iconUrl,
            }
            plugins.append(plugin)

        return Response({'status': 'Ok', 'plugins': plugins})

    def _handle_update_available_query(self, plugins_str, max_record):
        pv_dict = {}
        pvs = plugins_str.split(':')
        pv_len = len(pvs)
        if pv_len % 2 != 0:
            return Response({
                'status': 'Fail',
                'message': 'error format with plugin and version "%s"' % plugins_str
            }, status=status.HTTP_400_BAD_REQUEST)
        for i in xrange(0, pv_len, 2):
            pv_dict[pvs[i]] = pvs[i + 1]
        # TODO: query all plugin and it's update version
        return Response({'status': 'Ok'})
