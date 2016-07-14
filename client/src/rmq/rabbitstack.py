# -*- coding: utf8 -*-
import pika
from threading import Thread
import Queue


class RabbitStack(object):
    def __init__(self, gamestack, host, port, user=None, password=None, virtual_host=None):
        self.gamestack = gamestack
        self.host = host
        self.port = port
        self.user = user
        self.password = password
        self.virtual_host = virtual_host
        self.connection = None
        self.channel = None
        self.main_listener = None
        self.publisher = None
        self.listeners = {}
        self.consumer_tags = list()
        self.threads = list()

    def connect(self):
        self.connection = pika.BlockingConnection(pika.ConnectionParameters(host=self.host, port=self.port,
                                                                            virtual_host=self.virtual_host))
        self.gamestack.get_logger().info('Successfully connected to the RabbitMQ server.')
        self.channel = self.connection.channel()

    def register_listener(self, listener):
        if listener is None or not isinstance(listener, RabbitListener):
            return False
        if not listener.get_listening_topic():
            return False
        topic = listener.get_listening_topic()
        if self.main_listener is None:
            self.main_listener = ListeningThread(self, self.channel)
            self.main_listener.setName('RabbitMQ Listener')
            self.main_listener.add_queue(callback=self.all_call, queue=topic)
            self.main_listener.start()
            self.threads.append(self.main_listener)
        if topic not in self.listeners.keys():
            self.listeners[topic] = list()
        self.listeners[topic].append(listener)
        return True

    def close(self):
        if self.publisher:
            self.publisher.finish()
            self.publisher.join()
        if self.consumer_tags:
            for tag in self.consumer_tags:
                self.channel.basic_cancel(tag)
        if self.main_listener:
            self.main_listener.finish()
            self.main_listener.join()
        self.connection.close()
        self.gamestack.get_logger().info('Closed connection to the RabbitMQ server.')

    def publish(self, packet):
        if packet is None or not isinstance(packet, RabbitPacket):
            return
        if self.publisher is None:
            self.publisher = PublisherThread(self.channel)
            self.publisher.setName('RabbitMQ Publisher')
            self.publisher.start()
            self.threads.append(self.publisher)
        self.publisher.enqueue(packet)

    def add_consumer_tag(self, tag):
        if not tag or tag in self.consumer_tags:
            return
        self.consumer_tags.append(tag)

    def all_call(self, ch, method, properties, body):
        if body is None:
            return
        if method.exchange is None:
            return
        if method.exchange not in self.listeners.keys():
            return
        to_call = self.listeners[method.exchange]
        if to_call is None or not to_call:
            return
        for listener in to_call:
            listener.call(channel=method.exchange, body=body)


class RabbitListener(object):
    def __init__(self, topic):
        self.topic = topic

    def get_listening_topic(self):
        return self.topic

    def call(self, channel, body):
        """To implement."""
        return


class RabbitPacket(object):
    def __init__(self, topic, message):
        self.topic = topic
        self.message = message

    def get_topic(self):
        return self.topic

    def get_message(self):
        return self.message

    def __str__(self):
        return "RabbitPacket[topic:{},message:{}]".format(self.topic, self.message)


class PublisherThread(Thread):
    def __init__(self, channel):
        self.packets = Queue.Queue()
        Thread.__init__(self)
        self.channel = channel
        self.do_run = True

    def enqueue(self, packet):
        self.packets.put_nowait(packet)

    def finish(self):
        self.do_run = False

    def run(self):
        while self.do_run:
            try:
                packet = self.packets.get(False, 500)
                self.channel.basic_publish(exchange=packet.get_topic(), routing_key='', body=packet.get_message())
            except Queue.Empty:
                # we don't care, we just need to loop through and checkout if the publisher is still running
                continue


class ListeningThread(Thread):
    def __init__(self, rabbit, channel):
        Thread.__init__(self)
        self.rabbit = rabbit
        self.channel = channel
        self.channels = list()
        self.do_run = True

    def add_queue(self, callback, queue):
        if queue not in self.channels:
            self.channel.exchange_declare(exchange=queue, type='fanout')
            result = self.channel.queue_declare(exclusive=True)
            queue_name = result.method.queue
            self.channel.queue_bind(exchange=queue, queue=queue_name)
            self.channel.basic_consume(callback, queue=queue_name, no_ack=True)

    def finish(self):
        self.do_run = False

    def run(self):
        while self.do_run:
            self.channel.connection.process_data_events(time_limit=1)
