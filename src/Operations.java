import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Operations{
    protected ArrayList<Account> users;
    protected ArrayList<Community> communities;
    protected ArrayList<FeedMessage> feed;
    protected OpAccount opAccount;
    protected OpCommunity opCommunity;
    protected OpFeed opFeed;

    public Operations(){
        this.users = new ArrayList<>();
        this.communities = new ArrayList<>();
        this.feed = new ArrayList<>();
        this.opAccount = new OpAccount(users, communities, feed);
        this.opCommunity = new OpCommunity(users, communities, feed);
        this.opFeed = new OpFeed(users, communities, feed);
    }

}