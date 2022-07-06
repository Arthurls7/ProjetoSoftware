public abstract class Message{
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

    abstract void showMessage();
    abstract void showSender();
    abstract void showData();

}
