# -*- coding: utf8 -*-


# data-like class which contains important objects of the soft
class GameStack(object):
    def __init__(self, logger):
        self.logger = logger

    def get_logger(self):
        return self.logger
