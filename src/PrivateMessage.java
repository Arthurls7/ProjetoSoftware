public class PrivateMessage extends Message{
    String receiver;

    public PrivateMessage(String sender, String message, String receiver) {
        super(sender, message);
        this.receiver = receiver;
    }

    public String getReceiver() {
        return receiver;
    }
}
