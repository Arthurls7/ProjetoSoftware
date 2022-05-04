import java.lang.reflect.Array;
import java.util.ArrayList;

public class Invites {
    protected ArrayList<String> inviteSent = new ArrayList<String>();
    protected ArrayList<String> inviteRec = new ArrayList<String>();

    public boolean hasInvSent(){
        for(String name : inviteSent) return true;
        return false;
    }

    public boolean hasInvRec(){
        for(String name : inviteRec) return true;
        return false;
    }

    public void showInvSent(){
        int i = 1;
        System.out.println("Invited: ");
        for(String name : inviteSent){
            System.out.println(i + ":" + name);
            i++;
        }
    }

    public void showInvRec(){
        int i = 1;
        System.out.println("Received: ");
        for(String name : inviteRec){
            System.out.println(i + ":" + name);
            i++;
        }
    }

    public boolean hasInvites(){
        int result = 0;
        if(hasInvSent()){
            showInvSent();
            result++;
        } else System.out.println("No invites sent");

        if(hasInvRec()){
            showInvRec();
            result++;
        } else System.out.println("No invites received");

        return result != 0;
    }

    public ArrayList<String> getInviteSent() {
        return inviteSent;
    }

    public ArrayList<String> getInviteRec() {
        return inviteRec;
    }
}
