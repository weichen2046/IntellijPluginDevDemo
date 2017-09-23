"""
Test pluginparser-<version>.jar.

Note: you should build the pluginparser and plugin before start test script.
"""

import json
import re
import os

try:
    import subprocess32 as subprocess
except ImportError:
    import subprocess

BASE_DIR = os.path.dirname(os.path.dirname(
    os.path.dirname(os.path.dirname(os.path.abspath(__file__)))))
PARSER_GRADLE_FILE = os.path.join(BASE_DIR, 'build.gradle')
PARSER_NAME_FORMAT = 'pluginparser-%s.jar'

PLUGIN_GRADLE_FILE = os.path.join(os.path.dirname(BASE_DIR), 'build.gradle')
PLUGIN_NAME_FORMAT = 'Demo Plugin-%s.jar'


def get_pluginparser_version():
    """
    Get pluginparser file version from build.gradle file.
    """
    output = subprocess.check_output(
        ['grep parserVersion %s' % PARSER_GRADLE_FILE], shell=True)
    m = re.match('def parserVersion = \'(.*)\'', output)
    if m is None:
        raise Exception('get parser version failed')
    return m.group(1)


def get_parser():
    """
    Get pluginparser file absolute path.
    """
    ver = get_pluginparser_version()
    return os.path.join(BASE_DIR, 'build/libs', PARSER_NAME_FORMAT % ver)


def get_plugin_versin():
    """
    Get plugin version.
    """
    output = subprocess.check_output(
        ['grep "^version" %s' % PLUGIN_GRADLE_FILE], shell=True)
    m = re.match('version \'(.*)\'', output)
    if m is None:
        raise Exception('get plugin version failed')
    return m.group(1)


def get_plugin():
    """
    Get plugin file absolute path.
    """
    ver = get_plugin_versin()
    return os.path.join(os.path.dirname(BASE_DIR), 'build/libs', PLUGIN_NAME_FORMAT % ver)


def test():
    """
    Start test and print output.
    """
    cmd = 'java -jar %s "%s"' % (get_parser(), get_plugin())
    print 'run:'
    print cmd
    print

    output = subprocess.check_output([cmd], shell=True)
    print 'output:'
    print output
    print

    pluign_info = json.loads(output)
    print 'json obj:'
    print pluign_info
    print

    print pluign_info['plugin']['myDescription']


if __name__ == '__main__':
    test()
