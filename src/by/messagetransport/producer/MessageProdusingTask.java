package by.messagetransport.producer;

import by.messagetransport.Message;
import by.messagetransport.broker.MessageBroker;

import java.util.concurrent.TimeUnit;

public class MessageProdusingTask implements Runnable {
    private static final String MESSAGE = "Message '%s' is produced \n";
    private static final int SECONDS_SLEEP = 3;
    private MessageFactory messageFactory;
    private MessageBroker messageBroker;
    public MessageProdusingTask(MessageBroker messageBroker) {
        this.messageBroker = messageBroker;
        this.messageFactory = new MessageFactory();
    }
    private static final class MessageFactory{
        private static final int INITIAL_MESSAGE = 1;
        private static final String CREATED_MESSAGE_DATA = "Message#%d";
        private int nextMessageIndex;
        public MessageFactory() {
            this.nextMessageIndex = INITIAL_MESSAGE;
        }
        public Message create () {
            return new Message(String.format(CREATED_MESSAGE_DATA,this.nextMessageIndex++));
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Message produsedMessage = this.messageFactory.create();
                TimeUnit.SECONDS.sleep(SECONDS_SLEEP);
                this.messageBroker.produce(produsedMessage);
                System.out.printf(MESSAGE,produsedMessage);
            }
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }
}
