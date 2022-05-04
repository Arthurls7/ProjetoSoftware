import java.util.ArrayList;

public abstract class Message {
    protected String sender, message;

    public Message(String sender, String message){
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }
}
