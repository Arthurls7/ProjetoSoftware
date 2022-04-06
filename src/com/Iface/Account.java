package com.Iface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Account {
    String login;
    String password;
    String name;
    String nickname;
    String description;

    //Listas
    ArrayList<String> communityHost = new ArrayList<String>();
    ArrayList<String> communityMember = new ArrayList<String>();
    ArrayList<String> inviteSent = new ArrayList<String>();
    ArrayList<String> inviteRec = new ArrayList<String>();
    ArrayList<String> friendList = new ArrayList<String>();
    //ArrayList<Integer> feedPosts = new ArrayList<>();
    Map<String, ArrayList<String>> msgSent = new HashMap<String, ArrayList<String>>();
    Map<String, ArrayList<String>> msgRec = new HashMap<String, ArrayList<String>>();
    //Map<String, ArrayList<String>> msgToComm = new HashMap<String, ArrayList<String>>();

    //boolean publicFeed = true;

    public Account(){

    }

    public Account(String login, String password, String name, String nickname, String description) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.description = description;
    }

    public boolean signIn(String login, String password){
        return this.login.equals(login) && this.password.equals(password);
    }

    public void showInvRec(){
        int i = 1;
        System.out.println("Received: ");
        for(String name : inviteRec){
            System.out.println(i + ":" + name);
            i++;
        }
    }

    public void showData(Feed posts){
        System.out.println("Login: " + login);
        System.out.println("Password: " + password);
        System.out.println("Name: " + name);
        System.out.println("Nickname: " + nickname);
        System.out.println("Description: " + description);

        for (String s : communityHost) System.out.println("Host of community: " + s);
        for (String s : communityMember) System.out.println("Member of community: " + s);
        for (String s : friendList) System.out.println("Friend of: " + s);
        for (String s : inviteSent) System.out.println("Invite to: " + s);
        for (String s : inviteRec) System.out.println("Invite from: " + s);
        System.out.println("Msg's sent: " + msgSent);
        System.out.println("Msg's rec: " + msgRec);

        int lenPosts = posts.getLogin().size();
        for(int i=0; i<lenPosts; i++){
            if(posts.getLogin().get(i).equals(login)) System.out.println("Feed post (" + (posts.getIsPrivate().get(i) ? "Private" : "Public") + "):" + posts.getScrap().get(i));
        }
    }

    public void showInvSent(){
        int i = 1;
        System.out.println("Invited: ");
        for(String name : inviteSent){
            System.out.println(i + ":" + name);
            i++;
        }
    }

    public void showFriends(){
        int i = 1;
        for(String name : friendList){
            if(i == 1) System.out.println("FriendList: ");
            System.out.println(i + ":" + name);
            i++;
        }
        if(i == 1) System.out.println("You have no friends");
    }

    public boolean hasInvSent(){
        for(String name : inviteSent) return true;
        return false;
    }

    public boolean hasInvRec(){
        for(String name : inviteRec) return true;
        return false;
    }

    public boolean modifyData(String attribute){
        Scanner scan = new Scanner(System.in).useDelimiter("\n");
        String newValue = scan.next();

        switch (attribute) {
            case "password":
                this.password = newValue;
                break;
            case "name":
                this.name = newValue;
                break;
            case "nickname":
                this.nickname = newValue;
                break;
            case "description":
                this.description = newValue;
                break;
            default:
                return false;
        }

        return true;
    }

    public void newCommHost(String commName){
        this.communityHost.add(commName);
    }

    public void removeCommHost(String commName){
        this.communityHost.remove(commName);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getCommunityHost() {
        return communityHost;
    }

    public void setCommunityHost(ArrayList<String> communityHost) {
        this.communityHost = communityHost;
    }

    public ArrayList<String> getCommunityMember() {
        return communityMember;
    }

    public void setCommunityMember(ArrayList<String> communityMember) {
        this.communityMember = communityMember;
    }

    public ArrayList<String> getInviteSent() {
        return inviteSent;
    }

    public void setInviteSent(ArrayList<String> inviteSent) {
        this.inviteSent = inviteSent;
    }

    public ArrayList<String> getInviteRec() {
        return inviteRec;
    }

    public void setInviteRec(ArrayList<String> inviteRec) {
        this.inviteRec = inviteRec;
    }

    public ArrayList<String> getFriendList() {
        return friendList;
    }

    public void setFriendList(ArrayList<String> friendList) {
        this.friendList = friendList;
    }

    public Map<String, ArrayList<String>> getMsgSent() {
        return msgSent;
    }

    public void setMsgSent(Map<String, ArrayList<String>> msgSent) {
        this.msgSent = msgSent;
    }

    public Map<String, ArrayList<String>> getMsgRec() {
        return msgRec;
    }

    public void setMsgRec(Map<String, ArrayList<String>> msgRec) {
        this.msgRec = msgRec;
    }

//    public Map<String, ArrayList<String>> getMsgToComm() {
//        return msgToComm;
//    }
//
//    public void setMsgToComm(Map<String, ArrayList<String>> msgToComm) {
//        this.msgToComm = msgToComm;
//    }
}