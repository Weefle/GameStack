# -*- coding: utf8 -*-
from time import *


def current_time_millis():
    """Returns the current time in milliseconds."""
    return int(round(time() * 1000))


def log_format_time():
    """Returns the current time in a log format."""
    return strftime('[%H:%M] ', gmtime())


def log_header_time():
    """Returns the time in the log header format."""
    return strftime('%A, %d %B %Y %H:%M:%S', gmtime())


def log_file_name():
    """Returns log file name."""
    return strftime('logs/log_%d-%m-%Y_%H-%M-%S.txt', gmtime())
