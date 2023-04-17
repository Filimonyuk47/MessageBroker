package by.messagetransport.consumer;

import by.messagetransport.model.Message;
import by.messagetransport.broker.MessageBroker;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class MessageConsumingTask implements Runnable {
    private static final int SECONDS_SLEEP = 3;
    private final int minimalAmountMessagesToConsume;
    private MessageBroker messageBroker;
    private final String name;

    public int getMinimalAmountMessagesToConsume() {
        return minimalAmountMessagesToConsume;
    }

    public String getName() {
        return name;
    }

    public MessageConsumingTask(MessageBroker messageBroker, int minimalAmountMessagesToConsume, String name) {
        this.name = name;
        this.minimalAmountMessagesToConsume = minimalAmountMessagesToConsume;
        this.messageBroker = messageBroker;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                TimeUnit.SECONDS.sleep(SECONDS_SLEEP);
                Optional<Message> optionalConsumedMessage = this.messageBroker.consume(this);
                optionalConsumedMessage.orElseThrow(MessageConsumingException :: new);
            }
        }catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }
}
