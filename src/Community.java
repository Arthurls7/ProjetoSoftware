import java.util.ArrayList;

public class Community{
    protected String name, host, description;
    protected ArrayList<String> memberList = new ArrayList<>();
    protected ArrayList<CommunityMessage> messages = new ArrayList<>();
    protected ArrayList<String> memberRequest = new ArrayList<>();

    private static Community instance = null;

    private Community(){

    }

    public Community(String name, String description, String host) {
        this.name = name;
        this.description = description;
        this.host = host;
        memberList = new ArrayList<>();
        messages = new ArrayList<>();
        memberRequest = new ArrayList<>();
    }

    public static Community getInstance() {
        if(instance == null) instance = new Community();
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getMemberList() {
        return memberList;
    }

    public void setMemberList(ArrayList<String> memberList) {
        this.memberList = memberList;
    }

    public ArrayList<CommunityMessage> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<CommunityMessage> messages) {
        this.messages = messages;
    }

    public ArrayList<String> getMemberRequest() {
        return memberRequest;
    }

    public void setMemberRequest(ArrayList<String> memberRequest) {
        this.memberRequest = memberRequest;
    }
}