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
        while (this.messagesToBeConsumed.size() >= this.maxStoredMessages) {
            try {
                super.wait();
            }catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
            }
        }
        this.messagesToBeConsumed.add(message);
        super.notify();
    }
    public synchronized Message consume() {
        while (this.messagesToBeConsumed.isEmpty()) {
            try {
                super.wait();
            }catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
            }
        }
        Message message = this.messagesToBeConsumed.poll();
        super.notify();
        return message;
        //wdqw
    }
}
