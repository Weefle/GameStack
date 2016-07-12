# -*- coding: utf8 -*-


class InvalidArgumentException(Exception):

    def __init__(self, message):
        super(InvalidArgumentException, self).__init__(message)
