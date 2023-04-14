package by.messagetransport.broker;

import by.messagetransport.Message;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class MessageBroker {
    private Queue<Message> messagesToBeConsumed;
    private int maxStoredMessages;
    public MessageBroker(int maxStoredMessages) {
        this.messagesToBeConsumed = new ArrayDeque<>(maxStoredMessages);
        this.maxStoredMessages = maxStoredMessages;
    }
    public synchronized void produce (Message message) {
        this.messagesToBeConsumed.add(message);
    }
    public synchronized Message consume() {
        return this.messagesToBeConsumed.poll();
    }
}
