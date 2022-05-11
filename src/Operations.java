import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Operations implements Messages{
    protected ArrayList<Account> users;
    protected ArrayList<Community> communities;
    protected ArrayList<FeedMessage> feed;
    Scanner scan = new Scanner(System.in).useDelimiter("\n");

    public Operations(){
        this.users = new ArrayList<>();
        this.communities = new ArrayList<>();
        this.feed = new ArrayList<>();
    }

    //Account area
    public Account findAcc(String login){
        for(Account user : this.users)
            if (user.getLogin().equals(login)) return user;
        return null;
    }

    public boolean createAcc(){
        System.out.print("Insert login: ");
        String login = scan.next();

        Account newAcc;
        newAcc = findAcc(login);
        if(newAcc != null) return false;

        newAcc = new Account();
        newAcc.setLogin(login);

        System.out.print("Insert password: ");
        newAcc.setPassword(scan.next());

        System.out.print("Insert name: ");
        newAcc.setName(scan.next());

        System.out.print("Insert nickname: ");
        newAcc.setNickname(scan.next());

        System.out.print("Insert description: ");
        newAcc.setDescription(scan.next());

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
        System.out.println("What you want modify: ");
        System.out.print("password\nname\nnickname\ndescription\n");
        String opt = scan.next();

        Account actualAcc = findAcc(login);
        Field[] attributes =  actualAcc.getClass().getDeclaredFields();
        for (Field field : attributes) {
            if(opt.equals(field.getName())){
                System.out.println("Insert new value: ");
                String newValue = scan.next();

                switch (opt) {
                    case "password": 
                        actualAcc.setPassword(newValue);
                        break;
                    case "name":
                        actualAcc.setName(newValue);
                        break;
                    case "nickname":
                        actualAcc.setNickname(newValue);
                        break;
                    case "description":
                        actualAcc.setDescription(newValue);
                        break;
                    default: 
                        System.out.println("Attribute unmodifiable or nonexistent");
                        break;
                }
            }
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
        for(FeedMessage actual : feed){
            if(actual.getSender().equals(login)) System.out.println("Feed post: " + actual.getMessage());;
        }

        seeMsg(login);
    }

    //Friend Area
    public void manageSent(String login){
        Account actualAcc = findAcc(login);

        if(!actualAcc.invites.hasInvSent()){
            System.out.println("No invites sent");
            return;
        }

        actualAcc.invites.showInvSent();
        System.out.println("You only can remove a sent invite");
        System.out.println("Proceed? y to -> Yes, n to -> No");

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
        } catch(Error err){
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

    //Messages
    @Override
    public void seeMsg(String login) {
        Account actualAcc = findAcc(login);
        boolean result = false;
        for(PrivateMessage msg : actualAcc.getMessages()){
            System.out.println(msg.getSender() + " to " + msg.getReceiver() + ": " + msg.getMessage());
            result = true;
        }

        if(!result) System.out.println("You dont have messages");
    }

    @Override
    public void sendMsg(String sender) {
        System.out.println("Insert the user who you want to send a message: ");
        Account receiverAcc = findAcc(scan.next());
        Account senderAcc = findAcc(sender);

        if(receiverAcc == null){
            System.out.println("Invalid user");
            return;
        }

        System.out.println("Put your message: ");
        String msgInput = scan.next();
        PrivateMessage message = new PrivateMessage(sender, msgInput, receiverAcc.getLogin());
        receiverAcc.getMessages().add(message);
        senderAcc.getMessages().add(message);
        System.out.println("Message sent");
    }

    //Community area
    public void createComm(String login){
        Community newComm = new Community();

        System.out.println("Give a name to the community: ");
        newComm.setName(scan.next());

        for(Community comm : communities){
            if(comm.getName().equals(newComm.getName())){
                System.out.println("Community already exists");
                return;
            }
        }

        System.out.println("Give a description to the community: ");
        newComm.setDescription(scan.next());
        newComm.setHost(login);
        newComm.getMemberList().add(login);
        communities.add(newComm);

        Account actualAcc = findAcc(login);
        actualAcc.getCommMember().add(newComm.getName());
        System.out.println("Community created");
    }

    public Community findComm(String commName){
        for(Community comm : communities)
            if (comm.getName().equals(commName)) return comm;
        return null;
    }

    public void joinComm(String login){
        System.out.println("Put comm you want to join: ");
        String commName = scan.next();
        Community comm = findComm(commName);
        if(comm == null){
            System.out.println("Comm nonexistent");
            return;
        }

        Account actualAcc = findAcc(login);
        if(comm.getHost().equals(login)){
            System.out.println("You are the host of comm");
            return;
        }

        for(String member : comm.getMemberList()){
            if(member.equals(actualAcc.getLogin())){
                System.out.println("Already a comm member");
                return;
            }
        }

        for(String request : comm.getMemberRequest()){
            if(request.equals(login)){
                System.out.println("You already request this comm");
                return;
            }
        }

        comm.getMemberRequest().add(login);
        System.out.println("Waiting for host to accept");
    }

    public void manageComm(String login, int opt){
        seeMyCommHosted(login);

        System.out.println("Input comm you want to manage: ");

        Community commManage = findComm(scan.next());

        if(commManage == null){
            System.out.println("Invalid comm");
            return;
        }

        if(!commManage.getHost().equals(login)){
            System.out.println("You arent the host of this comm");
            return;
        }

        if(opt == 1) acceptMembers(commManage);
        if(opt == 2) removeMember(commManage);
    }

    public void acceptMembers(Community commManage){
        if(commManage.getMemberRequest().size() == 0){
            System.out.println("No requests");
            return;
        }

        for(int i=0; i<commManage.getMemberRequest().size(); i++){
            System.out.println(i+1 + ":" + commManage.getMemberRequest().get(i));
        }

        int id;
        System.out.println("Input ID you want to manage: ");

        try{
            id = Integer.parseInt(scan.next());
        } catch (NumberFormatException ex){
            System.out.println("Invalid id");
            return;
        }

        System.out.println("a -> accept, b -> decline: ");
        String choice = scan.next();

        try{
            if(choice.equals("a")){
                Account member = findAcc(commManage.getMemberRequest().get(id-1));
                member.getCommMember().add(commManage.getName());

                commManage.getMemberList().add(commManage.getMemberRequest().get(id-1));
                commManage.getMemberRequest().remove(id-1);

                System.out.println("Member accepted");
            } else if(choice.equals("b")){
                commManage.getMemberRequest().remove(id-1);
                System.out.println("Member declined");
            }
        } catch(IndexOutOfBoundsException err){
            System.out.println("Out of index");
        }
    }

    public void removeMember(Community commManage){
        if(commManage.getMemberList().size() == 1){
            System.out.println("You are the only member of comm");
            return;
        }

        for(int i=0; i<commManage.getMemberList().size(); i++){
            System.out.println(i+1 + ":" + commManage.getMemberList().get(i));
        }

        int id;
        System.out.println("Input ID you want to remove: ");
        System.out.println("Insert random id if you dont want to remove");

        try{
            id = Integer.parseInt(scan.next());
            if(id-1 == 0){
                System.out.println("You cant remove you");
                return;
            }

            Account member = findAcc(commManage.getMemberList().get(id-1));
            member.getCommMember().remove(commManage.getName());
            commManage.getMemberList().remove(id-1);
            System.out.println("Removed");
        } catch (Exception ex){
            System.out.println("Invalid id");
        }
    }

    public void sentMsgComm(String login){
        System.out.println("Insert community who you want to send a message: ");
        Community receiver = findComm(scan.next());
        if(receiver == null){
            System.out.println("Invalid comm");
            return;
        }

        Account actualAcc = findAcc(login);
        if(!actualAcc.getCommMember().contains(receiver.getName())){
            System.out.println("You are not a member off comm");
            return;
        }

        System.out.println("Put your message: ");
        String msg = scan.next();

        //See here later
        CommunityMessage objMsg = new CommunityMessage(login, msg);
        receiver.getMessages().add(objMsg);
    }

    public void seeMsgComm(String login){
        System.out.println("Insert comm name you want to see msgs");
        Community comm = findComm(scan.next());
        if(comm == null){
            System.out.println("Nonexistent comm");
            return;
        }

        Account actualAcc = findAcc(login);
        if(!actualAcc.getCommMember().contains(comm.getName())){
            System.out.println("You are not a member off comm");
            return;
        }

        boolean result = false;
        for(Message msg : comm.getMessages()){
            System.out.println(msg.getSender() + ": " + msg.getMessage());
            result = true;
        }

        if(!result) System.out.println("Comm dont have messages");
    }

    //Checks comm
    public boolean existsAnyComm(){
        return !communities.isEmpty();
    }

    public boolean userHasComm(String login){
        Account actualAcc = findAcc(login);
        return !actualAcc.getCommMember().isEmpty();
    }

    public boolean userIsCommHost(String login){
        for(Community comm : communities){
            if(comm.getHost().equals(login)) return true;
        }

        return false;
    }

    public void listAllComm(){
        for(Community comm : communities) System.out.println(comm.getName());
    }

    public void seeMyCommHosted(String login){
        Account actualAcc = findAcc(login);
        System.out.println("My communities list:");
        for (String s : actualAcc.getCommMember()){
            Community loop = findComm(s);
            if(loop.getHost().equals(login)) System.out.println("Host of: " + s);
        }
    }

    public void seeMyCommList(String login){
        Account actualAcc = findAcc(login);
        System.out.println("My communities list:");
        for (String s : actualAcc.getCommMember()) System.out.println("Member of: " + s);
    }

    //Feed
    public void postFeed(String login){
        System.out.println("Insert your scrap:");
        String scrap = scan.next();

        System.out.println("Is public? y -> yes, any other key -> no");
        String inputPublic = scan.next();
        boolean isPublic;
        isPublic = inputPublic.equals("y");

        FeedMessage newPost = new FeedMessage(login, scrap, isPublic);

        feed.add(newPost);
        System.out.println("Successful post");
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
                if(verifyFriend.getFriendList().contains(actual.getSender())) System.out.println(actual.getSender() + ": " + actual.getMessage());
            } else System.out.println(actual.getSender() + ": " + actual.getMessage());
        }
    }

    //Remove
    public void removeAcc(String login){
        Account actualAcc = findAcc(login);

        //Comm remove ok
        Iterator<Community> comms = communities.iterator();
        while(comms.hasNext()){
            Community actualComm = comms.next();

            if(actualComm.getMemberList().contains(login)){
                if(actualComm.getHost().equals(login)){
                    for(String member : actualComm.getMemberList()){
                        Account actualMember = findAcc(member);
                        actualMember.getCommMember().remove(actualComm.getName());
                    }

                    comms.remove();
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