# -*- coding: utf8 -*-
import os


def touch(path):
    if not path:
        return
    basedir = os.path.dirname(path)
    print basedir
    if basedir and not os.path.exists(basedir):
        os.makedirs(basedir)
    with open(path, 'a'):
        os.utime(path, None)
