public class FeedMessage extends Message{
    protected boolean isPublic;

    public FeedMessage(String sender, String message, Boolean isPublic) {
        super(sender, message);
        this.isPublic = isPublic;
    }

    public boolean isPublic() {
        return isPublic;
    }
}
