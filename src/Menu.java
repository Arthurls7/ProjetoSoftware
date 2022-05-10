import java.util.Scanner;

public class Menu {
    Scanner scan = new Scanner(System.in).useDelimiter("\n");
    Operations ops = new Operations();
    String choice;

    public void menu(){
        while(true) {
            System.out.println("Options: ");
            System.out.println("a: New Account");
            System.out.println("b: Login");
            System.out.println("q: Quit");
            System.out.print("Put your choice: ");
            choice = scan.next();

            switch (choice) {
                case "a":
                    if (ops.createAcc()) System.out.println("User created");
                    else System.out.println("Login already exists");
                    break;
                case "b":
                    System.out.print("Insert login: ");
                    String login = scan.next();
                    if (ops.login(login)) accountMenu(login);
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

    void enterFunc(){
        System.out.println("\nPress enter to proceed to menu");
        scan.nextLine();
    }

    public void accountMenu(String login){
        Account actualAcc = ops.findAcc(login);
        int loop = 0;

        while(!choice.equals("z")){

            if(loop != 0) enterFunc();

            System.out.println("\nWelcome " + actualAcc.getName());
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

            System.out.print("Your choice: "); choice = scan.next();
            System.out.println("-----------");
            loop = 1;

            switch (choice){
                case "a":
                    ops.modifyAccount(login);
                    break;
                case "b":
                    ops.showData(login);
                    break;
                case "c":
                    ops.sendMsg(login);
                    break;
                case "d":
                    ops.seeMsg(login);
                    break;
                case "e":
                    ops.addUser(login);
                    break;
                case "f":
                    ops.showFriends(login);
                    break;
                case "g":
                    actualAcc.invites.hasInvites();
                    break;
                case "h":
                    if(!actualAcc.invites.hasInvites()) break;
                    System.out.println("a -> to manage sent, b -> to manage received");
                    choice = scan.next();
                    if(choice.equals("a")) ops.manageSent(login);
                    else if(choice.equals("b")) ops.manageRec(login);
                    else System.out.println("Invalid option");
                    break;
                case "i":
                    ops.createComm(login);
                    break;
                case "j":
                    if(ops.existsAnyComm()){
                        System.out.println("Comm list:");
                        ops.listAllComm();
                        ops.joinComm(login);
                    } else System.out.println("There is no community");
                    break;
                case "k":
                    if(ops.userHasComm(login)){
                        ops.seeMyCommList(login);
                        ops.sentMsgComm(login);
                    } else System.out.println("You is not a member of any comm");

                    break;
                case "l":
                    if(ops.userHasComm(login)){
                        ops.seeMyCommList(login);
                        ops.seeMsgComm(login);
                    } else System.out.println("You is not a member of any comm");
                    break;
                case "m":
                    if(!ops.userIsCommHost(login)){
                        System.out.println("You arent host of any comm");
                        break;
                    }

                    System.out.println("a -> manage requests\nb -> remove member");
                    choice = scan.next();

                    if(choice.equals("a")) ops.manageComm(login, 1);
                    else if(choice.equals("b")) ops.manageComm(login, 2);
                    else System.out.println("Invalid choice");
                    break;
                case "n":
                    ops.postFeed(login);
                    break;
                case "o":
                    ops.seeFeed(login);
                    break;
                case "p":
                    ops.removeAcc(login);
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
}
