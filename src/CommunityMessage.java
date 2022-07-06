public class CommunityMessage extends Message {
    public CommunityMessage(String sender, String message) {
        super(sender, message);
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
        System.out.print(sender + ":" + message);
    }

}
