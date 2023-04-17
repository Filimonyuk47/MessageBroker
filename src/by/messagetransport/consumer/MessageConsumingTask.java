package by.messagetransport.consumer;

import by.messagetransport.Message;
import by.messagetransport.broker.MessageBroker;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class MessageConsumingTask implements Runnable {
    private static final int SECONDS_SLEEP = 3;
    private static final String MESSAGE = "Message '%s' is consumed. \n";
    private MessageBroker messageBroker;
    public MessageConsumingTask(MessageBroker messageBroker) {
        this.messageBroker = messageBroker;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                TimeUnit.SECONDS.sleep(SECONDS_SLEEP);
                Optional<Message> optionalConsumedMessage = this.messageBroker.consume();
                Message consumedMessage = optionalConsumedMessage.orElseThrow(MessageConsumingException :: new);
                System.out.printf(MESSAGE,consumedMessage);
            }
        }catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }
}
