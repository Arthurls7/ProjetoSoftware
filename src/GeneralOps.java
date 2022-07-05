import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public abstract class GeneralOps {

    protected ArrayList<Account> users;
    protected ArrayList<Community> communities;
    protected ArrayList<FeedMessage> feed;
    Scanner scan = new Scanner(System.in).useDelimiter("\n");

    public GeneralOps(ArrayList<Account> users, ArrayList<Community> communities, ArrayList<FeedMessage> feed){
        this.users = users;
        this.communities = communities;
        this.feed = feed;
    }
    public Account findAcc(String login){
        for(Account user : this.users)
            if (user.getLogin().equals(login)) return user;
        return null;
    }

    public Community findComm(String commName){
        for(Community comm : communities)
            if (comm.getName().equals(commName)) return comm;
        return null;
    }

    public boolean checkRegex(String attribute, String input){
        String regex;
        switch (attribute){
            case "login":
                regex = "^[A-Za-z0-9_]{6,20}$";
                return !input.matches(regex);
            case "password":
                return input.length() < 6 || input.length() > 20;
            case "name":
                regex = "^[a-zA-z ]{6,30}$";
                return !input.matches(regex) || input.contains("  ");
            case "nickname":
                regex = "^[A-Za-z0-9 ]{6,20}$";
                return !input.matches(regex) || input.contains("  ");
            case "description":
                return input.length() < 6 || input.length() > 40;
            case "message":
                return input.length() < 1 || input.length() > 100;
            default:
                System.out.println("Invalid");
                return false;
        }
    }



}
