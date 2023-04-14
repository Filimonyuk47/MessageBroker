package by.messagetransport.consumer;

import by.messagetransport.Message;
import by.messagetransport.broker.MessageBroker;

import java.util.concurrent.TimeUnit;

public class MessageConsumingTask implements Runnable {
    private static final int SECONDS_SLEEP = 1;
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
                Message messageConsumed = this.messageBroker.consume();
                System.out.printf(MESSAGE,messageConsumed);
            }
        }catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }
}
