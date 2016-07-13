# -*- coding: utf8 -*-
from util.timehelper import current_time_millis, log_file_name
from util import log
import gamestack

version = "1.0.0"


def main():
    logger = log.Logger(log_file=log_file_name())
    logger.log('Starting up GameStack client version ', version)
    start_time = current_time_millis()

    # begin

    core = gamestack.GameStack(logger)

    # end

    time_diff = current_time_millis() - start_time
    logger.log('Done in {0}ms (~{1}s).'.format(time_diff, round(time_diff / 1000, 1)))


# Run
if __name__ == "__main__":
    main()
