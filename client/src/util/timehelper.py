# -*- coding: utf8 -*-
import time


def current_time_millis():
    """Returns the current time in milliseconds."""
    return int(round(time.time() * 1000))
