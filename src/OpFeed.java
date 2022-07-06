import java.util.ArrayList;

public class OpFeed extends GeneralOps{

    public OpFeed(ArrayList<Account> users, ArrayList<Community> communities, ArrayList<FeedMessage> feed){
        super(users, communities, feed);
    }

    public void postFeed(String login) {
        try{
            System.out.println("Insert your scrap (Len 1-100):");
            String scrap = scan.next();

            if(checkRegex("message", scrap)) throw new invalidFormatEx("Bad scrap");

            System.out.println("Is public? y -> yes, any other key -> no");
            String inputPublic = scan.next();
            boolean isPublic;
            isPublic = inputPublic.equals("y");
            FeedMessage newPost = new FeedMessage(login, scrap, isPublic);

            feed.add(newPost);
            System.out.println("Successful post");
        } catch(Exception e){
            System.out.println("Returning");
        }
    }

    public void seeFeed(String login){
        if(feed.isEmpty()){
            System.out.println("Empty feed");
            return;
        }

        for(FeedMessage actual : feed){
            //System.out.println(actual.isPublic());
            if(actual.getSender().equals(login)) continue;
            if(!actual.isPublic()){
                Account verifyFriend = findAcc(login);
                if(verifyFriend.getFriendList().contains(actual.getSender())) actual.showData();
                    //System.out.println(actual.getSender() + ": " + actual.getMessage());
            } else System.out.println(actual.getSender() + ": " + actual.getMessage());
        }
    }
}
