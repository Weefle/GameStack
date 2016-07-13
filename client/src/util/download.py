# -*- coding: utf8 -*-
import urllib2
import sys
import re
from util import console


def dl(gamestack, file_url, file_destination="."):
    """"Downloads the demanded file and moves it to the destination"""
    if not file_url:
        return None
    file_name = file_url.split('/')[-1]
    u = urllib2.urlopen(url=file_url)
    if file_destination and not re.match(r'.*(.[a-zA-Z]*)[1]', file_destination):
        file_name = sys.path.join(file_destination, file_name)
    f = open(file_name, 'wb')
    meta = u.info()
    file_size = int(meta.getheaders("Content-Length"))
    gamestack.get_logger().log('Began download of file {} ({} kb)'.format(file_name, int(file_size / 1024)))
    downloaded = 0
    block_sz = 8192
    prog = console.Progress(title="Downloading file \"{}\"".format(file_name))
    while True:
        buf = u.read(block_sz)
        if not buf:
            break
        downloaded += len(buf)
        f.write(buf)
        prog.do_progress(int(downloaded / file_size * 100))
    prog.end_progress()
    return f
