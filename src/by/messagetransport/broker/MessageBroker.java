package by.messagetransport.broker;

import by.messagetransport.Message;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.Queue;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class MessageBroker {
    private Queue<Message> messagesToBeConsumed;
    private int maxStoredMessages;

    public MessageBroker(int maxStoredMessages) {
        this.messagesToBeConsumed = new ArrayDeque<>(maxStoredMessages);
        this.maxStoredMessages = maxStoredMessages;
    }

    public synchronized void produce(Message message) {
        while (this.messagesToBeConsumed.size() >= this.maxStoredMessages) {
            try {
                super.wait();
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
            }
        }
        this.messagesToBeConsumed.add(message);
        super.notify();
    }

    public synchronized Optional<Message> consume() {
        try {
            while (this.messagesToBeConsumed.isEmpty()) {
                super.wait();
            }
            Message message = this.messagesToBeConsumed.poll();
            super.notify();
            return of(message);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            return empty();
        }
    }
}
