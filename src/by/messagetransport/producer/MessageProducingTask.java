package by.messagetransport.producer;

import by.messagetransport.model.Message;
import by.messagetransport.broker.MessageBroker;

import java.util.concurrent.TimeUnit;

public class MessageProducingTask implements Runnable {
    private static final int SECONDS_SLEEP = 1;
    private final int maximalAmountMessagesToConsume;
    private MessageFactory messageFactory;
    private MessageBroker messageBroker;
    private final String name;
    public MessageProducingTask(MessageBroker messageBroker, MessageFactory messageFactory,
                                int maximalAmountMessagesToConsume, String name) {
        this.messageBroker = messageBroker;
        this.maximalAmountMessagesToConsume = maximalAmountMessagesToConsume;
        this.messageFactory = messageFactory;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getMaximalAmountMessagesToConsume() {
        return maximalAmountMessagesToConsume;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Message produsedMessage = this.messageFactory.create();
                TimeUnit.SECONDS.sleep(SECONDS_SLEEP);
                this.messageBroker.produce(produsedMessage, this);
            }
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }
}
