# -*- coding: utf8 -*-
from util.timehelper import current_time_millis, log_file_name
from util import log
import gamestack
from rmq import rabbitstack
import sys


version = "1.0.0"


class MyListener(rabbitstack.RabbitListener):
    def call(self, channel, body):
        print 'Received {} on channel {}'.format(channel, body)


def main():
    logger = log.Logger(log_file=log_file_name())
    logger.log('Starting up GameStack client version {}'.format(version))
    start_time = current_time_millis()

    # begin

    core = gamestack.GameStack(logger)

    # end

    time_diff = current_time_millis() - start_time
    logger.log('Done in {0}ms (~{1}s).'.format(time_diff, round(time_diff / 1000, 1)))


def test():
    rmq = rabbitstack.RabbitStack(gamestack.GameStack(log.Logger(log_file_name())), host='192.168.99.100', port=5672)
    rmq.connect()
    rmq.register_listener(MyListener('test'))
    i = 0
    while i < 1000:
        rmq.publish(rabbitstack.RabbitPacket('test', 'message' + str(i)))
        i += 1
    import time
    time.sleep(2)
    rmq.close()
    sys.exit(0)

# Run
if __name__ == "__main__":
    test()
