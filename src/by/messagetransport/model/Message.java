package by.messagetransport;

import java.util.Objects;

public class Message {
    private String data;
     public Message(String data) {
        this.data = data;
    }

    public String getData() {
        return this.data;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Message o = (Message) obj;
        return Objects.equals(this.data, o.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.data);
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "[data = " + this.data + "]";
    }
}
