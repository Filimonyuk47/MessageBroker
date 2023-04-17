package by.messagetransport.consumer;

public class MessageConsumingException extends RuntimeException{
    public MessageConsumingException(){};
    MessageConsumingException (final String description) {
        super(description);
    };
    MessageConsumingException (final Exception cause) {
        super(cause);
    };
    MessageConsumingException (final String description, final Exception cause) {
        super(description, cause);
    };
}
