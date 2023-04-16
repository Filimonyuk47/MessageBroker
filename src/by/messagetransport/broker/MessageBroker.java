package by.messagetransport.broker;

import by.messagetransport.consumer.MessageConsumingTask;
import by.messagetransport.model.Message;
import by.messagetransport.producer.MessageProducingTask;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

import static java.util.Optional.*;

public class MessageBroker {
    private static final String MESSAGE_PRODUCE = "Message '%s' is produced by producer '%s'. " +
            "Amount of messages before producing: %d. \n";
    private static final String MESSAGE_CONSUME = "Message '%s' is consumed by consumer '%s'." +
            "Amount of messages before consuming: %d. \n";
    private Queue<Message> messagesToBeConsumed;
    private int maxStoredMessages;

    public MessageBroker(int maxStoredMessages) {
        this.messagesToBeConsumed = new ArrayDeque<>(maxStoredMessages);
        this.maxStoredMessages = maxStoredMessages;
    }

    public synchronized void produce(Message message, MessageProducingTask producingTask) {
        while (!this.isShouldProduce(producingTask)) {
            try {
                super.wait();
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
            }
        }
        this.messagesToBeConsumed.add(message);
        System.out.printf(MESSAGE_PRODUCE,message,producingTask.getName(), this.messagesToBeConsumed.size()-1);
        super.notify();
    }

    public synchronized Optional<Message> consume(MessageConsumingTask messageConsumingTask) {
        try {
            while (!this.isShouldConsume(messageConsumingTask)) {
                super.wait();
            }
            Message message = this.messagesToBeConsumed.poll();
            System.out.printf(MESSAGE_CONSUME,message,messageConsumingTask.getName(),this.messagesToBeConsumed.size()+1);
            super.notify();
            return ofNullable(message);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            return empty();
        }
    }
    public boolean isShouldConsume(MessageConsumingTask messageConsumingTask) {
        return !messagesToBeConsumed.isEmpty()
                && this.messagesToBeConsumed.size() >= messageConsumingTask.getMinimalAmountMessagesToConsume();
    }
    public boolean isShouldProduce(MessageProducingTask messageProducingTask) {
        return this.messagesToBeConsumed.size() < this.maxStoredMessages
                && this.messagesToBeConsumed.size() <= messageProducingTask.getMaximalAmountMessagesToConsume();
    }
}
