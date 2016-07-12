# -*- coding: utf8 -*-
from util.timehelper import current_time_millis
import redis
from util import stackredis

version = "1.0.0"


def startup():
    r = redis.Redis()
    stack = stackredis.StackRedis(r)
    stack.connect()
    stack.register_listener('something')


def main():
    print('Starting up GameStack client version ', version)
    start_time = current_time_millis()
    startup()
    time_diff = current_time_millis() - start_time
    print('Done in {0}ms (~{1}s).'.format(time_diff, round(time_diff / 1000, 1)))

# Run
main()
