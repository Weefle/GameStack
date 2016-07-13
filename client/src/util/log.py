# -*- coding: utf8 -*-
from util.timehelper import log_format_time, log_header_time
import os
from util.files import touch


def prefix():
    return log_format_time()


class Logger(object):

    def __init__(self, log_file):
        self.log_file = log_file
        self.log_stream = None

    def log(self, message, to_file=True, console=True):
        """Logs to file and to console."""
        if not message:
            return
        if not to_file and not console:
            return
        output = prefix() + message
        if to_file:
            self.put_to_file(output)
        if console:
            print(output)

    def put_to_file(self, output):
        if self.log_stream is None:
            if os.path.isfile(self.log_file):
                os.remove(self.log_file)  # should not happen
            else:
                touch(self.log_file)
            self.log_stream = open(self.log_file, 'w')
            self.write_to_stream('###=== GAMESTACK CLIENT LOG ({}) ===###'.format(log_header_time()))
        self.write_to_stream(output)
        self.log_stream.flush()

    def write_to_stream(self, message):
        self.log_stream.write(message + '\n')

    def close(self):
        self.log_stream.close()
