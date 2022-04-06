package com.Iface;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    String choice = null;
    Scanner scan = new Scanner(System.in).useDelimiter("\n");

    public void startMenu(ArrayList<Account> users, ArrayList<Community> communities, Feed posts){

        boolean isLogged;
        do {
            System.out.println("Options: ");
            System.out.println("a: New Account");
            System.out.println("b: Login");
            System.out.println("q: Quit");
            System.out.print("Put your choice: ");
            choice = scan.next();

            switch (choice){
                case "a":
                    if(createAcc(users)) System.out.println("User created");
                    else System.out.println("Login already exists");
                    break;
                case "b":
                    Account actualAcc = login(users);
                    if(actualAcc == null) break;
                    isLogged = true;

                    while(isLogged){
                        System.out.println("Welcome " + actualAcc.getName());
                        System.out.println("a: Edit info | b: New Community");
                        System.out.println("c: Show data | d: Add a friend");
                        System.out.println("e: List invites | f: Manage invite");
                        System.out.println("g: Show friends | h: Sent message to another user");
                        System.out.println("i: See private msg's | j: See community msg's");
                        System.out.println("k: Join community | l: Sent msg to comm");
                        System.out.println("m: Post in feed | n: See feed");
                        System.out.println("o: Remove acc | p: Mgmt community");
                        System.out.println("q: Quit (logoff)");
                        System.out.println("Your choice: ");
                        choice = scan.next();

                        switch (choice){
                            case "a":
                                if(modifyAccount(actualAcc)) System.out.println("Account modified");
                                break;
                            case "b":
                                if(createComm(communities, actualAcc)) System.out.println("Community created");
                                break;
                            case "c":
                                actualAcc.showData(posts);
                                break;
                            case "d":
                                addUser(users, actualAcc);
                                break;
                            case "e":
                                if(!hasInvites(actualAcc)) System.out.println("You are lonely");
                                break;
                            case "f":
                                if(!hasInvites(actualAcc)) break;
                                System.out.println("a -> to manage sent, b -> to manage received");
                                choice = scan.next();
                                if(choice.equals("a")) manageSent(users, actualAcc);
                                else if(choice.equals("b")) manageRec(users, actualAcc);
                                else System.out.println("Invalid option");
                                break;
                            case "g":
                                actualAcc.showFriends();
                                break;
                            case "h":
                                sentMsg(users, actualAcc);
                                break;
                            case "i":
                                seeMsg(actualAcc);
                                break;
                            case "j":
                                if(seeMyCommList(actualAcc)) seeMsgComm(communities, actualAcc);
                                break;
                            case "k":
                                System.out.println("Comm list: ");
                                listAllComm(communities);
                                System.out.println("Insert comm you want to join:");
                                joinComm(communities, scan.next(), actualAcc);
                                break;
                            case "l":
                                if(seeMyCommList(actualAcc)) sentMsgComm(communities, actualAcc);
                                break;
                            case "m":
                                postFeed(posts, actualAcc);
                                break;
                            case "n":
                                seeFeed(posts, actualAcc);
                                break;
                            case "o":
                                removeAcc(users, communities, posts, actualAcc);
                                System.out.println("Bye :( ");
                                isLogged = false;
                                break;
                            case "p":
                                mgmtComm(communities, users, actualAcc);
                                break;
                            case "q":
                                isLogged = false;
                                choice = "z";
                                break;
                            default:
                                System.out.println("Unexpected value: " + choice);
                                break;
                        }
                    } break;
                case "q":
                    System.out.println("Bye bye :)");
                    return;
                default:
                    System.out.println("Unexpected value: " + choice);
                    break;
            }
        } while (!choice.equals("q"));
    }

    public Account findAcc(ArrayList<Account> users, String login){
        for(Account user : users)
            if (user.getLogin().equals(login)) return user;
        return null;
    }

    public Community findComm(ArrayList<Community> communities, String commName){
        for(Community comm : communities)
            if (comm.getName().equals(commName)) return comm;
        return null;
    }

    public void listAllComm(ArrayList<Community> communities){
        for(Community comm : communities) System.out.println(comm.getName());
    }

    public Account login(ArrayList<Account> users){
        System.out.print("Insert login: ");
        String login = scan.next();

        Account findAccount = findAcc(users, login);
        if(findAccount == null){
            System.out.println("Account nonexistent");
            return null;
        }

        System.out.print("Insert password: ");
        String password = scan.next();

        if(findAccount.signIn(login, password)) return findAccount;
        else{
            System.out.println("Wrong password");
            return null;
        }
    }

    public boolean createAcc(ArrayList<Account> users){
        System.out.print("Insert login: ");
        String login = scan.next();

        Account newAcc;
        newAcc = findAcc(users, login);
        if(newAcc != null) return false;

        //Receiving data
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
        return true;
    }

    public boolean modifyAccount(Account actualAcc){
        System.out.println("What you want modify: ");
        System.out.print("password\nname\nnickname\ndescription\n");
        String opt = scan.next();

        Field[] attributes =  actualAcc.getClass().getDeclaredFields();
        for (Field field : attributes) {
            if(opt.equals(field.getName())){
                System.out.println("Insert new value: ");
                if(!actualAcc.modifyData(opt)){
                    System.out.println("Attribute unmodifiable");
                    return false;
                } else return true;
            }
        }

        System.out.println("Attribute nonexistent");
        return false;
    }

    public void sentMsg(ArrayList<Account> users, Account actualAcc){
        System.out.println("Insert the user who you want to send a message: ");
        Account receiver = findAcc(users, scan.next());
        if(receiver == null){
            System.out.println("Invalid user");
            return;
        }

        System.out.println("Put your message: ");
        String msg = scan.next();

        Map<String, ArrayList<String>> receiverHash = receiver.getMsgRec();
        if(receiverHash.containsKey(actualAcc.getLogin())){
            //System.out.println("Entrou aqui 2");
            receiverHash.get(actualAcc.getLogin()).add(msg);
        } else{
            //System.out.println("Entrou aqui 1");
            receiverHash.put(actualAcc.getLogin(), new ArrayList<>());
            receiverHash.get(actualAcc.getLogin()).add(msg);
        }

        Map<String, ArrayList<String>> senderHash = actualAcc.getMsgSent();
        if(senderHash.containsKey(receiver.getLogin())){
            //System.out.println("Entrou aqui 4");
            senderHash.get(receiver.getLogin()).add(msg);
        } else{
            //System.out.println("Entrou aqui 3");
            senderHash.put(receiver.getLogin(), new ArrayList<>());
            senderHash.get(receiver.getLogin()).add(msg);
        }
    }

    public void sentMsgComm(ArrayList<Community> communities, Account actualAcc){
        System.out.println("Insert community who you want to send a message: ");
        Community receiver = findComm(communities, scan.next());
        if(receiver == null){
            System.out.println("Invalid comm");
            return;
        }

        //Se nao for membro da comunidade volta
        int status = 0;

        for(String members : receiver.getMembers()){
            if (members.equals(actualAcc.getLogin())) {
                status = 1;
                break;
            }
        }

        if(status == 0){
            System.out.println("You are not a member off comm");
            return;
        }

        System.out.println("Put your message: ");
        String msg = scan.next();

        Map<String, ArrayList<String>> commHashRec = receiver.getMsg();

        if(commHashRec.containsKey(actualAcc.getLogin())){
            commHashRec.get(actualAcc.getLogin()).add(msg);
        } else{
            commHashRec.put(actualAcc.getLogin(), new ArrayList<>());
            commHashRec.get(actualAcc.getLogin()).add(msg);
        }
    }

    public void mapPrint(Map<String, ArrayList<String>> msg){
        for(Map.Entry<String, ArrayList<String>> keys : msg.entrySet()){
            System.out.println(keys.getKey() + ":");
            ArrayList<String> values = keys.getValue();
            for(String arrMsg : values){
                System.out.println(arrMsg);
            }
        }
    }

    public void seeMsg(Account actualAcc){
        if(actualAcc.getMsgRec().isEmpty()){
            System.out.println("You have no msg's");
            return;
        }

        mapPrint(actualAcc.getMsgRec());
//        for(Map.Entry<String, ArrayList<String>> keys : msgRec.entrySet()){
//            System.out.println(keys.getKey() + ":");
//            ArrayList<String> values = keys.getValue();
//            for(String arrMsg : values){
//                System.out.println(arrMsg);
//            }
//        }
    }

    public boolean seeMyCommList(Account actualAcc){
        int result = 0;
        for(String comm : actualAcc.getCommunityMember()){
            if (result==0) System.out.println("My comms list:");
            System.out.println(comm);
            result++;
        }
        for(String comm : actualAcc.getCommunityHost()){
            if (result==0) System.out.println("My comms list:");
            System.out.println(comm);
            result++;
        }

        return result != 0;
    }

    public void seeMsgComm(ArrayList<Community> communities, Account actualAcc){
        System.out.println("Insert comm name you want to see msgs");
        Community comm = findComm(communities, scan.next());
        if(comm == null){
            System.out.println("Nonexistent comm");
            return;
        }

        int status = 0;
        for(String members : comm.getMembers()){
            if (members.equals(actualAcc.getLogin())) {
                status = 1;
                break;
            }
        }

        if(status == 0){
            System.out.println("You arent in this comm");
            return;
        }

        Map<String, ArrayList<String>> msg = comm.getMsg();
        mapPrint(msg);
//        for(Map.Entry<String, ArrayList<String>> keys : msg.entrySet()){
//            System.out.println(keys.getKey() + ":");
//            ArrayList<String> values = keys.getValue();
//            for(String arrMsg : values){
//                System.out.println(arrMsg);
//            }
//        }
    }

    public void joinComm(ArrayList<Community> communities, String commName, Account actualAcc){
        Community comm = findComm(communities, commName);
        if(comm == null){
            System.out.println("Comm nonexistent");
            return;
        }

        if(comm.getOwner().equals(actualAcc.getLogin())){
            System.out.println("You are the host of comm");
            return;
        }

        for(String member : comm.getMembers()){
            if(member.equals(actualAcc.getLogin())){
                System.out.println("Already a comm member");
                return;
            }
        }

        comm.getMembers().add(actualAcc.getLogin());
        actualAcc.getCommunityMember().add(comm.getName());
        System.out.println("Joined comm");
    }

    public void addUser(ArrayList<Account> users, Account actualAcc){
        System.out.println("Input user who u want to add: ");
        String login = scan.next();

        if(login.equals(actualAcc.getLogin())){
            System.out.println("You can't add yourself");
            return;
        }

        Account newFriend = findAcc(users, login);
        if(newFriend == null){
            System.out.println("Invalid user");
            return;
        }

        //Invite already sent
        ArrayList<String> sent = actualAcc.getInviteSent();
        for(String name : sent){
            if(newFriend.getLogin().equals(name)){
                System.out.println("Invited already sent");
                return;
            }
        }

        //Check if exists invite
        ArrayList<String> rec = actualAcc.getInviteRec();
        for(String name : rec){
            if(newFriend.getLogin().equals(name)){
                System.out.println("Accepted friendship");

                ArrayList<String> fListActual = actualAcc.getFriendList();
                ArrayList<String> fListNewFriend = newFriend.getFriendList();
                ArrayList<String> sentListNewFriend = newFriend.getInviteSent();

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
        ArrayList<String> recListNewFriend = newFriend.getInviteRec();
        recListNewFriend.add(actualAcc.getLogin());
        sent.add(newFriend.getLogin());
        System.out.println("Invite sent");
    }

    public boolean hasInvites(Account actualAcc){
        int result = 0;
        if(actualAcc.hasInvSent()){
            actualAcc.showInvSent();
            result++;
        } else System.out.println("No invites sent");

        if(actualAcc.hasInvRec()){
            actualAcc.showInvRec();
            result++;
        } else System.out.println("No invites received");

        return result != 0;
    }

    public void manageSent(ArrayList<Account> users, Account actualAcc){
        if(!actualAcc.hasInvSent()){
            System.out.println("No invites sent");
            return;
        }

        actualAcc.showInvSent();
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

        ArrayList<String> sent = actualAcc.getInviteSent();
        Account newFriend;
        try{
           newFriend  = findAcc(users, sent.get(id-1));
        } catch(Error err){
            System.out.println("Invalid choice");
            return;
        }

        ArrayList<String> recNewFriend = newFriend.getInviteRec();
        recNewFriend.remove(actualAcc.getLogin());
        sent.remove(newFriend.getLogin());
        System.out.println("Removed");
    }

    public void manageRec(ArrayList<Account> users, Account actualAcc){
        if(!actualAcc.hasInvRec()){
            System.out.println("No invites received");
            return;
        }

        actualAcc.showInvRec();
        System.out.println("Insert id to manage: ");
        String index = scan.next();
        int id;
        try{
            id = Integer.parseInt(index);
        } catch (NumberFormatException ex){
            System.out.println("Invalid id");
            return;
        }

        ArrayList<String> rec = actualAcc.getInviteRec();
        Account newFriend;
        try{
            newFriend = findAcc(users, rec.get(id-1));
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

        ArrayList<String> sentNewFriend = newFriend.getInviteSent();
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

    public boolean createComm(ArrayList<Community> communities, Account actualAcc){
        Community newComm = new Community();

        System.out.println("Give a name to the community: ");
        newComm.name = scan.next();

        //Adiantar testes
//        if(actualAcc.getLogin().equals("arthur")) newComm.name = "Zap";
//        else newComm.name = "Test";

        for(Community comm : communities){
            if(comm.getName().equals(newComm.getName())){
                System.out.println("Community already exists");
                return false;
            }
        }

        System.out.println("Give a description to the community: ");
        //newComm.description = "Test";
        newComm.description = scan.next();
        newComm.owner = actualAcc.getLogin();
        newComm.members.add(actualAcc.getLogin());
        communities.add(newComm);

        actualAcc.newCommHost(newComm.getName());
        return true;
    }

    public void seeFeed(Feed posts, Account actualAcc){
        int id = 0;
        for(String login : posts.getLogin()){
            //System.out.println(login);
            if(login.equals(actualAcc.getLogin())){
                id++;
                continue;
            }
            if(posts.getIsPrivate().get(id)){
                if(actualAcc.getFriendList().contains(login)){
                    System.out.print("Private->");
                    System.out.println(posts.getLogin().get(id) + ": " + posts.getScrap().get(id));
                }
            } else{
                System.out.print("Public->");
                System.out.println(posts.getLogin().get(id) + ": " + posts.getScrap().get(id));
            }

            id++;
        }
    }

    public void postFeed(Feed posts, Account actualAcc){
        posts.getLogin().add(actualAcc.getLogin());
        System.out.println("Insert your scrap:");
        posts.getScrap().add(scan.next());
        System.out.println("Is private? y -> yes, any other key -> no");
        String isPrivate = scan.next();

        if(isPrivate.equals("y")) posts.getIsPrivate().add(true);
        else posts.getIsPrivate().add(false);
    }

    public void removeAcc(ArrayList<Account> users, ArrayList<Community> communities, Feed posts, Account actualAcc){
        //Se for dono da comunidade, retira os membros, depois apaga ela
        for(String commHost : actualAcc.getCommunityHost()){
            for(Account memberLoop : users){
                memberLoop.getCommunityMember().remove(commHost);
            }
        }

        //Apago a comunidade, mas ainda fica o registro que ele eh host na conta
        Iterator<Community> comms = communities.iterator();
        for(String actualHost : actualAcc.getCommunityHost()){
            while(comms.hasNext()){
                Community actualComm = comms.next();
                if(actualComm.getName().equals(actualHost)){
                    comms.remove();
                }
            }
        }

        //System.out.println("Breakpoint 1");

        //Se for membro da comunidade so sai e apaga as msgs
        for(String commMember : actualAcc.getCommunityMember()){
            for(Community commLoop : communities){
                if(commLoop.getName().equals(commMember)){
                    commLoop.getMembers().remove(actualAcc.getLogin());
                    commLoop.getMsg().remove(actualAcc.getLogin());
                }
            }
        }

        //System.out.println("Breakpoint 2");

        //Saindo das outras listas de amigo
        for(Account friend : users){
            friend.getFriendList().remove(actualAcc.getLogin());
        }

        //Convites enviados retirando
        for(Account friend : users){
            friend.getInviteRec().remove(actualAcc.getLogin());
        }

        //Convites recebidos retirando
        for(Account friend : users){
            friend.getInviteSent().remove(actualAcc.getLogin());
        }

        //System.out.println("Breakpoint 3");
        //Map de enviados
        for(Map.Entry<String, ArrayList<String>> keys : actualAcc.getMsgSent().entrySet()){
            for(Account friend : users){
                if(keys.getKey().equals(friend.getLogin())){
                    //System.out.println("Excluindo msgs enviadas");
                    friend.getMsgRec().remove(actualAcc.getLogin());
                }
            }
        }

        //Map de recebidos
        for(Map.Entry<String, ArrayList<String>> keys : actualAcc.getMsgRec().entrySet()){
            for(Account friend : users){
                if(keys.getKey().equals(friend.getLogin())){
                    //System.out.println("Excluindo msgs recebidas");
                    friend.getMsgSent().remove(actualAcc.getLogin());
                }
            }
        }

        //System.out.println("Breakpoint 4");
        //Feed
        int lenPosts = posts.getLogin().size();
        for(int i=0; i<lenPosts; i++){
            if(posts.getLogin().get(i).equals(actualAcc.getLogin())){
                //System.out.println("Excluding: " + posts.getLogin().get(i) + posts.getScrap().get(i) + posts.isPrivate.get(i));
                posts.getLogin().remove(i);
                posts.getScrap().remove(i);
                posts.getIsPrivate().remove(i);
                i=i-1;
                lenPosts = lenPosts-1;
            }
        }

        //'Finally' cleaning account
        users.remove(actualAcc);
    }

    public void mgmtComm(ArrayList<Community> communities, ArrayList<Account> users, Account actualAcc){
        seeMyCommList(actualAcc);
        System.out.println("Insert comm name you want to manage:");
        Community comm = findComm(communities, scan.next());

        if(comm == null){
            System.out.println("Invalid comm");
            return;
        }

        if(!(comm.getOwner().equals(actualAcc.getLogin()))){
            System.out.println("You arent host of comm");
            return;
        }

        System.out.println("Memberlist: ");
        for(String member : comm.getMembers()){
            System.out.println("->" + member);
        }

        System.out.println("You want to remove any member? y -> yes, any other key to no");
        if(scan.next().equals("y")){
            System.out.println("Insert member you want to delete: ");
            String memberDelete = scan.next();

            if(memberDelete.equals(actualAcc.getLogin())){
                System.out.println("You cant delete yourself");
                return;
            }

            Account findMemberDelete = findAcc(users, memberDelete);
            if(findMemberDelete == null){
                System.out.println("Invalid member");
                return;
            }

            if(!(findMemberDelete.getCommunityMember().contains(comm.getName()))){
                System.out.println("User is not member of comm");
                return;
            }

            findMemberDelete.getCommunityMember().remove(comm.getName());
            comm.getMembers().remove(memberDelete);
            if(!(comm.getMsg().isEmpty())){
                try{
                    comm.getMsg().remove(memberDelete);
                }
                catch (Exception e){
                    System.out.println("Problem im member");
                }
            }

            System.out.println("Member removed");
        }
    }
}