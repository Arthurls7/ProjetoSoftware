import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class OpAccount extends GeneralOps{

    public OpAccount(ArrayList<Account> users, ArrayList<Community> communities, ArrayList<FeedMessage> feed){
        super(users, communities, feed);
    }

    //Profile
    public boolean createAcc(){
        Account newAcc;
        String input;

        try {
            System.out.print("Insert login (Len 6-20, No especial chars, only underscore): ");
            input = scan.next();
            //System.out.println(input);
            if(checkRegex("login", input)) throw new invalidFormatEx("Bad login");

            //Validating account
            newAcc = findAcc(input);
            if(newAcc != null) {
                System.out.println("Login already exists");
                return false;
            }

            newAcc = Account.getInstance();
            newAcc.setLogin(input);

            System.out.print("Insert password (Len 6-20): ");
            input = scan.next();

            if(checkRegex("password", input)) throw new invalidFormatEx("Bad password");
            newAcc.setPassword(input);

            System.out.print("Insert name (Len 6-30, No especial chars, No especial chars, only spaces): ");
            input = scan.next();

            if(checkRegex("name", input)) throw new invalidFormatEx("Bad name");
            newAcc.setName(input);

            System.out.print("Insert nickname (Len 6-20, No especial chars, only spaces): ");
            input = scan.next();

            if(checkRegex("nickname", input)) throw new invalidFormatEx("Bad nickname");
            newAcc.setNickname(input);

            System.out.print("Insert description (Len 6-40, All chars allowed): ");
            input = scan.next();

            if(checkRegex("description", input)) throw new invalidFormatEx("Bad description");
            newAcc.setDescription(input);

        } catch(invalidFormatEx e) {
            return false;
        }

        users.add(newAcc);
        System.out.println();
        return true;
    }

    public boolean login(String login){
        Account findAccount = findAcc(login);
        if(findAccount == null){
            System.out.println("Account nonexistent");
            return false;
        }

        System.out.print("Insert password: ");
        String password = scan.next();

        if(findAccount.getLogin().equals(login) && findAccount.getPassword().equals(password)){
            System.out.println("Successful login");
            return true;
        } else{
            System.out.println("Wrong password");
            return false;
        }
    }

    public void modifyAccount(String login){
        System.out.print("password\nname\nnickname\ndescription\n");
        System.out.println("What you want modify: ");
        String opt = scan.next();

        if(!(opt.equals("password") || opt.equals("name") || opt.equals("nickname") || opt.contentEquals("description"))) {
            System.out.println("Attribute unmodifiable or nonexistent");
            return;
        }

        try {
            Account actualAcc = findAcc(login);
            System.out.println("Insert new value: ");
            String newValue = scan.next();

            if(checkRegex(opt, newValue)) throw new invalidFormatEx("Bad input");

            switch (opt) {
                case "password" -> actualAcc.setPassword(newValue);
                case "name" -> actualAcc.setName(newValue);
                case "nickname" -> actualAcc.setNickname(newValue);
                case "description" -> actualAcc.setDescription(newValue);
                default -> System.out.println("Attribute unmodifiable or nonexistent");
            }

        } catch(Exception e) {
            return;
        }

        System.out.println("Account modified");
    }

    public void showData(String login){
        Account actualAcc = findAcc(login);

        System.out.println("Login: " + login);
        System.out.println("Password: " + actualAcc.getPassword());
        System.out.println("Name: " + actualAcc.getName());
        System.out.println("Nickname: " + actualAcc.getNickname());
        System.out.println("Description: " + actualAcc.getDescription());

        for (String s : actualAcc.getFriendList()) System.out.println("Friend of: " + s);
        for (String s : actualAcc.invites.getInviteSent()) System.out.println("Invite to: " + s);
        for (String s : actualAcc.invites.getInviteRec()) System.out.println("Invite from: " + s);
        for (String s : actualAcc.getCommMember()){
            Community loop = findComm(s);
            if(loop.getHost().equals(login)) System.out.println("Host of: " + s);
            else System.out.println("Member of: " + s);

            for(Message msg : loop.getMessages()){
                if(msg.getSender().equals(login)) System.out.println("Message to comm" + loop.getName() + ": " + msg.getMessage());
            }
        }

        for(Message actual : feed){
            if(actual.getSender().equals(login)) System.out.println("Feed post: " + actual.getMessage());
        }

        seeMsg(login);
    }

    public void seeMsg(String login) {
        Account actualAcc = findAcc(login);
        boolean result = false;
        for(PrivateMessage msg : actualAcc.getMessages()){
            System.out.println(msg.getSender() + " to " + msg.getReceiver() + ": " + msg.getMessage());
            result = true;
        }

        if(!result) System.out.println("You dont have messages");
    }

    //Friend area
    public void manageSent(String login){
        Account actualAcc = findAcc(login);

        if(!actualAcc.invites.hasInvSent()){
            System.out.println("No invites sent");
            return;
        }

        actualAcc.invites.showInvSent();
        System.out.println("You only can remove a sent invite");
        System.out.println("Proceed? y to -> Yes, any other to return");

        String opt = scan.next();
        if(!opt.equals("y")){
            System.out.println("Returning");
            return;
        }

        System.out.println("Insert id to REMOVE: ");
        String index = scan.next();
        int id;
        try{
            id = Integer.parseInt(index);
        } catch (NumberFormatException ex){
            System.out.println("Invalid id");
            return;
        }

        ArrayList<String> sent = actualAcc.invites.getInviteSent();
        Account newFriend;
        try{
            newFriend  = findAcc(sent.get(id-1));
        } catch(Exception e){
            System.out.println("Invalid choice");
            return;
        }

        ArrayList<String> recNewFriend = newFriend.invites.getInviteRec();
        recNewFriend.remove(actualAcc.getLogin());
        sent.remove(newFriend.getLogin());
        System.out.println("Removed");
    }

    public void manageRec(String login){
        Account actualAcc = findAcc(login);
        if(!actualAcc.invites.hasInvRec()){
            System.out.println("No invites received");
            return;
        }

        actualAcc.invites.showInvRec();
        System.out.println("Insert id to manage: ");
        String index = scan.next();
        int id;
        try{
            id = Integer.parseInt(index);
        } catch (NumberFormatException ex){
            System.out.println("Invalid id");
            return;
        }

        ArrayList<String> rec = actualAcc.invites.getInviteRec();
        Account newFriend;
        try{
            newFriend = findAcc(rec.get(id-1));
        } catch(IndexOutOfBoundsException err){
            System.out.println("Out of index");
            return;
        }

        System.out.println("a -> to Accept, b -> to Decline");
        String opt;
        opt = scan.next();
        if(!(opt.equals("a") || opt.equals("b"))){
            System.out.println("Returning");
            return;
        }

        ArrayList<String> sentNewFriend = newFriend.invites.getInviteSent();
        sentNewFriend.remove(actualAcc.getLogin());
        rec.remove(id-1);

        if ("a".equals(opt)) {
            ArrayList<String> actualFriendList = actualAcc.getFriendList();
            actualFriendList.add(newFriend.getLogin());
            ArrayList<String> newFriendList = newFriend.getFriendList();
            newFriendList.add(actualAcc.getLogin());
            System.out.println("Added");
        } else {
            System.out.println("Declined");
        }
    }

    public void addUser(String login){
        Account actualAcc = findAcc(login);

        System.out.println("Input user who u want to add: ");
        String loginToAdd = scan.next();

        if(loginToAdd.equals(actualAcc.getLogin())){
            System.out.println("You can't add yourself");
            return;
        }

        Account newFriend = findAcc(loginToAdd);
        if(newFriend == null){
            System.out.println("Invalid user");
            return;
        }

        //Invite already sent
        ArrayList<String> sent = actualAcc.invites.getInviteSent();
        for(String name : sent){
            if(newFriend.getLogin().equals(name)){
                System.out.println("Invited already sent");
                return;
            }
        }

        //Check if exists invite
        ArrayList<String> rec = actualAcc.invites.getInviteRec();
        for(String name : rec){
            if(newFriend.getLogin().equals(name)){
                System.out.println("Accepted friendship");

                ArrayList<String> fListActual = actualAcc.getFriendList();
                ArrayList<String> fListNewFriend = newFriend.getFriendList();
                ArrayList<String> sentListNewFriend = newFriend.invites.getInviteSent();

                fListActual.add(newFriend.getLogin());
                fListNewFriend.add(actualAcc.getLogin());

                sentListNewFriend.remove(actualAcc.getLogin());
                rec.remove(newFriend.getLogin());
                return;
            }
        }

        //Checking already friends
        ArrayList<String> friendList = actualAcc.getFriendList();
        for(String name : friendList){
            if(name.equals(newFriend.getLogin())){
                System.out.println("Already friends");
                return;
            }
        }

        //New invite
        ArrayList<String> recListNewFriend = newFriend.invites.getInviteRec();
        recListNewFriend.add(actualAcc.getLogin());
        sent.add(newFriend.getLogin());
        System.out.println("Invite sent");
    }

    public void showFriends(String login){
        Account actualAcc = findAcc(login);
        boolean result = false;
        for(String s : actualAcc.getFriendList()){
            System.out.println(s);
            result = true;
        }

        if(!result) System.out.println("You dont have friends");
    }

    public void sendMsg(String sender) {
        System.out.println("Insert the user who you want to send a message: ");
        Account receiverAcc = findAcc(scan.next());
        Account senderAcc = findAcc(sender);

        if(receiverAcc == null){
            System.out.println("Invalid user");
            return;
        }

        try{
            System.out.println("Put your message (Len 1-100, all chars allowed): ");
            String msgInput = scan.next();
            if(checkRegex("message", msgInput)) throw new invalidFormatEx("Bad msg input");

            PrivateMessage message = new PrivateMessage(sender, msgInput, receiverAcc.getLogin());
            receiverAcc.getMessages().add(message);
            senderAcc.getMessages().add(message);
            System.out.println("Message sent");
        } catch(Exception e){
            System.out.println("Returning");
        }
    }

    //Remove
    public void removeAcc(String login){
        Account actualAcc = findAcc(login);

        //Comm remove ok
        Iterator<Community> commIterator = communities.iterator();
        while(commIterator.hasNext()){
            Community actualComm = commIterator.next();

            if(actualComm.getMemberList().contains(login)){
                if(actualComm.getHost().equals(login)){
                    for(String member : actualComm.getMemberList()){
                        Account actualMember = findAcc(member);
                        actualMember.getCommMember().remove(actualComm.getName());
                    }

                    commIterator.remove();
                } else{
                    //actualAcc.getCommMember().remove(actualComm.getName());
                    actualComm.getMemberList().remove(login);
                    actualComm.getMessages().removeIf(message -> message.getSender().equals(login));
                }
            }
        }

        //Private messages
        for(PrivateMessage excludeMsg : actualAcc.getMessages()){
            Account friend;
            if(excludeMsg.getSender().equals(login)){
                friend = findAcc(excludeMsg.getReceiver());
                friend.getMessages().removeIf(message -> message.getSender().equals(login));
            }
            else{
                friend = findAcc(excludeMsg.getSender());
                friend.getMessages().removeIf(message -> message.getReceiver().equals(login));
            }
        }


        //FriendList
        for (Account actualFriend : users) actualFriend.getFriendList().remove(login);

        //Feed remove ok
        feed.removeIf(actualFeedPost -> actualFeedPost.getSender().equals(login));

        //Invite remove ok
        for(String inviteRec : actualAcc.getInvites().getInviteRec()){
            Account friendAcc = findAcc(inviteRec);
            friendAcc.getInvites().getInviteSent().remove(login);
        }
        for(String inviteSent : actualAcc.getInvites().getInviteSent()){
            Account friendAcc = findAcc(inviteSent);
            friendAcc.getInvites().getInviteRec().remove(login);
        }

        users.remove(actualAcc);
    }

}
