# -*- coding: utf8 -*-
import sys


class Progress(object):
    def __init__(self, title):
        self.title = title

        sys.stdout.write(title + ": [" + " "*40 + "]" + chr(8)*41)
        sys.stdout.flush()
        self.progress = 0

    def do_progress(self, x):
        x = int(x * 40 // 100)
        sys.stdout.write("=" * (x - self.progress))
        sys.stdout.flush()
        self.progress = x

    def end_progress(self):
        sys.stdout.write(">" * (40 - self.progress) + "] Done.\n")
        sys.stdout.flush()
