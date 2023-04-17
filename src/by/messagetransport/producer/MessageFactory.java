package by.messagetransport.producer;

import by.messagetransport.Message;

public class MessageFactory {
    private static final int INITIAL_MESSAGE = 1;
    private static final String CREATED_MESSAGE_DATA = "Message#%d";
    private int nextMessageIndex;
    public MessageFactory() {
        this.nextMessageIndex = INITIAL_MESSAGE;
    }
    public Message create () {
        return new Message(String.format(CREATED_MESSAGE_DATA,incrementMessage()));
    }
    private synchronized int incrementMessage () {
        return this.nextMessageIndex++;
    }
}
