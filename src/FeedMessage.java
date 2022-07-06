public class FeedMessage extends Message {
    protected boolean isPublic;

    public FeedMessage(String sender, String message, Boolean isPublic) {
        super(sender, message);
        this.isPublic = isPublic;
    }

    public boolean isPublic() {
        return isPublic;
    }

    @Override
    public void showMessage(){
        System.out.print(message);
    }

    @Override
    public void showSender(){
        System.out.print(sender);
    }

    @Override
    public void showData(){
        if(isPublic) System.out.print("Public feed- ");
        else System.out.print("Private feed - ");
        System.out.println(sender + ":" + message);
    }
}
