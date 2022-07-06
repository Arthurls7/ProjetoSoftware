public class PrivateMessage extends Message {
    String receiver;

    public PrivateMessage(String sender, String message, String receiver) {
        super(sender, message);
        this.receiver = receiver;
    }

    public String getReceiver() {
        return receiver;
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
        System.out.print("msg to" + receiver + ":" + message);
    }

    public void showReceiver(){
        System.out.print(receiver);
    }
}
