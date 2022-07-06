import java.util.ArrayList;
import java.util.Scanner;

public class OpCommunity extends GeneralOps{

    public OpCommunity(ArrayList<Account> users, ArrayList<Community> communities, ArrayList<FeedMessage> feed){
        super(users, communities, feed);
    }

    public void createComm(String login){
        Community newComm;
        newComm = new Community();

        try{
            String input;
            System.out.println("Give a name to the community (Len 6-30, No especial chars, No especial chars, only spaces):: ");
            input = scan.next();

            if(checkRegex("name", input)) throw new invalidFormatEx("Bad name");
            newComm.setName(input);

            for(Community comm : communities){
                if(comm.getName().equals(newComm.getName())){
                    System.out.println("Community already exists");
                    return;
                }
            }

            System.out.println("Give a description to the community (Len 6-40, All chars allowed):: ");
            input = scan.next();
            if(checkRegex("description", input)) throw new invalidFormatEx("Bad description");
            newComm.setDescription(input);
        } catch(invalidFormatEx e) {
            return;
        }

        try {
            newComm.setHost(login);
            newComm.getMemberList().add(login);
            communities.add(newComm);

            Account actualAcc = findAcc(login);
            actualAcc.getCommMember().add(newComm.getName());
            System.out.println("Community created");
        } catch(Exception e) {
            System.out.println("Problem in array or memory");
        }

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
        System.out.println("Insert comm name you want to see messages");
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
}
