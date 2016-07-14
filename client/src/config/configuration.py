# -*- coding: utf8 -*-
import os
import yaml


def load(path):
    if not path or not os.path.isfile(path):
        return
    f = open(path, 'r')
    return yaml.load(f)
