import java.util.ArrayList;

public class Account{
    protected String login, password, name, nickname, description;

    protected Invites invites = new Invites();
    protected ArrayList<String> friendList = new ArrayList<>();
    protected ArrayList<PrivateMessage> messages = new ArrayList<>();
    protected ArrayList<String> commMember = new ArrayList<>();

    public Account(){

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

    public ArrayList<String> getFriendList() {
        return friendList;
    }

    public ArrayList<PrivateMessage> getMessages() {
        return messages;
    }

    public ArrayList<String> getCommMember() {
        return commMember;
    }

    public Invites getInvites() {
        return invites;
    }

    public Account addLogin(String login){
        this.login = login;
        return this;
    }

    public Account addPassword(String password){
        this.password = password;
        return this;
    }

    public Account addName(String name){
        this.name = name;
        return this;
    }

    public Account addNickname(String nickname){
        this.nickname = nickname;
        return this;
    }

    public Account addDescription(String description){
        this.description = description;
        return this;
    }
}
