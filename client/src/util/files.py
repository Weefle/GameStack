# -*- coding: utf8 -*-
import os


def touch(path):
    """Creates the file and the directory if it does not exist."""
    if not path:
        return
    basedir = os.path.dirname(path)
    if basedir and not os.path.exists(basedir):
        os.makedirs(basedir)
    with open(path, 'a'):
        os.utime(path, None)
