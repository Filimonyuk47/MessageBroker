package by.messagetransport;

import by.messagetransport.broker.MessageBroker;
import by.messagetransport.consumer.MessageConsumingTask;
import by.messagetransport.producer.MessageFactory;
import by.messagetransport.producer.MessageProducingTask;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int brokerMax = 15;
        MessageBroker messageBroker = new MessageBroker(brokerMax);
        MessageFactory messageFactory = new MessageFactory();

        Thread firstProducingThread = new Thread(new MessageProducingTask(messageBroker, messageFactory,
                brokerMax, "PRODUCER 1"));
        Thread secondProducingThread = new Thread(new MessageProducingTask(messageBroker, messageFactory,
                10, "PRODUCER 2"));
        Thread thirdProducingThread = new Thread(new MessageProducingTask(messageBroker, messageFactory,
                5,"PRODUCER 3"));

        Thread firstConsumingThread = new Thread(new MessageConsumingTask(messageBroker,
                0, "CONSUMER 1"));
        Thread secondConsumingThread = new Thread(new MessageConsumingTask(messageBroker,
                6, "CONSUMER 2"));
        Thread thirdConsumingThread = new Thread(new MessageConsumingTask(messageBroker,
                11, "CONSUMER 3"));

        startThreads(firstProducingThread,secondProducingThread,thirdProducingThread,
                firstConsumingThread,secondConsumingThread,thirdConsumingThread);
    }
    public static void startThreads(Thread... threads) {
        Arrays.stream(threads).forEach(Thread::start);
    }
}