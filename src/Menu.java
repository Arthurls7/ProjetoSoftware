import java.util.Scanner;

public class Menu {
    Scanner scan = new Scanner(System.in).useDelimiter("\n");
    Operations ops = new Operations();
    String choice;

    public void menu() throws invalidFormatEx{
        while(true) {
            menuText();
            choice = scan.next();

            switch (choice) {
                case "a":
                    if (ops.opAccount.createAcc()) System.out.println("User created");
                    break;
                case "b":
                    System.out.print("Insert login: ");
                    String login = scan.next();
                    if (ops.opAccount.login(login)) accountMenu(login);
                    break;
                case "q":
                    System.out.println("Bye bye :)");
                    return;
                default:
                    System.out.println("Unexpected value:" + choice);
                    break;
            }
        }
    }

    public void menuText(){
        System.out.println("Options: ");
        System.out.println("a: New Account");
        System.out.println("b: Login");
        System.out.println("q: Quit");
        System.out.print("Put your choice: ");
    }

    public void accountMenu(String login) throws invalidFormatEx{
        Account actualAcc = ops.opAccount.findAcc(login);

        while(!choice.equals("z")){
            System.out.println("\nWelcome " + actualAcc.getName());

            accountMenuText();

            System.out.print("Your choice: "); choice = scan.next();
            System.out.println("-----------");

            switch (choice){
                case "a":
                    ops.opAccount.modifyAccount(login);
                    break;
                case "b":
                    ops.opAccount.showData(login);
                    break;
                case "c":
                    ops.opAccount.sendMsg(login);
                    break;
                case "d":
                    ops.opAccount.seeMsg(login);
                    break;
                case "e":
                    ops.opAccount.addUser(login);
                    break;
                case "f":
                    ops.opAccount.showFriends(login);
                    break;
                case "g":
                    actualAcc.invites.hasInvites();
                    break;
                case "h":
                    if(!actualAcc.invites.hasInvites()) break;
                    System.out.println("a -> to manage sent, b -> to manage received");
                    choice = scan.next();
                    if(choice.equals("a")) ops.opAccount.manageSent(login);
                    else if(choice.equals("b")) ops.opAccount.manageRec(login);
                    else System.out.println("Invalid option");
                    break;
                case "i":
                    ops.opCommunity.createComm(login);
                    break;
                case "j":
                    if(ops.opCommunity.existsAnyComm()){
                        System.out.println("Comm list:");
                        ops.opCommunity.listAllComm();
                        ops.opCommunity.joinComm(login);
                    } else System.out.println("There is no community");
                    break;
                case "k":
                    if(ops.opCommunity.userHasComm(login)){
                        ops.opCommunity.seeMyCommList(login);
                        ops.opCommunity.sentMsgComm(login);
                    } else System.out.println("You is not a member of any comm");

                    break;
                case "l":
                    if(ops.opCommunity.userHasComm(login)){
                        ops.opCommunity.seeMyCommList(login);
                        ops.opCommunity.seeMsgComm(login);
                    } else System.out.println("You is not a member of any comm");
                    break;
                case "m":
                    if(!ops.opCommunity.userIsCommHost(login)){
                        System.out.println("You arent host of any comm");
                        break;
                    }

                    System.out.println("a -> manage requests\nb -> remove member");
                    choice = scan.next();

                    if(choice.equals("a")) ops.opCommunity.manageComm(login, 1);
                    else if(choice.equals("b")) ops.opCommunity.manageComm(login, 2);
                    else System.out.println("Invalid choice");
                    break;
                case "n":
                    ops.opFeed.postFeed(login);
                    break;
                case "o":
                    ops.opFeed.seeFeed(login);
                    break;
                case "p":
                    ops.opAccount.removeAcc(login);
                    System.out.println("Bye :( ");
                    return;
                case "q":
                    return;
                default:
                    System.out.println("Unexpected value: " + choice);
                    break;
            }
        }
    }

    public void accountMenuText(){
        System.out.println("\n** ACCOUNT AREA **");
        System.out.println("a: Edit info    | b: Show data");
        System.out.println("c: Sent message | d: See private msg's");

        System.out.println("\n** FRIEND AREA **");
        System.out.println("e: Add a friend | f: Show friends");
        System.out.println("g: List invites | h: Manage invites");

        System.out.println("\n** COMMUNITY AREA **");
        System.out.println("i: New Community    | j: Join community");
        System.out.println("k: Sent msg to comm | l: See community msg's");
        System.out.println("m: Manage community");

        System.out.println("\n** FEED SECTION **");
        System.out.println("n: Post in feed | o: See feed");

        System.out.println("\n** DEAD SECTION **");
        System.out.println("p: Remove acc | q: Quit (logoff)");
    }
}