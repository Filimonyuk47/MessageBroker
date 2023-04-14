package by.messagetransport;

import by.messagetransport.broker.MessageBroker;
import by.messagetransport.consumer.MessageConsumingTask;
import by.messagetransport.producer.MessageProdusingTask;

import java.util.Arrays;
import java.util.concurrent.ThreadFactory;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        int brokerMax = 5;
        MessageBroker messageBroker = new MessageBroker(brokerMax);
        Thread producingThread = new Thread(new MessageProdusingTask(messageBroker));
        Thread consumingThread = new Thread(new MessageConsumingTask(messageBroker));

        producingThread.start();
        consumingThread.start();
    }
}