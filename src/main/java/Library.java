import java.util.Scanner;

public class Library {
    static Account currentAccount;

    public void initMenu(){
        System.out.println("1. Create an account\n" +
                "2. Log into account" +
                "0. Exit");
    }

    public void loginMenu(){
        System.out.println("---MAIN MENU---" +
                "1. Book list\n" +
                "2. Reader list\n" +
                "3. Search book by title\n" +
                "4. Search book by author\n" +
                "5. Add book\n" +
                "6. Modify book data\n" +
                "7. Remove book\n" +
                "8. Search user by name\n" +
                "9. Search user by email\n" +
                "10. Search user by SSN\n" +
                "11. Add user\n" +
                "12. Modify user\n" +
                "13. Delete user\n" +
                "14. Borrow book\n" +
                "15. Return book" +
                "16. Log out\n" +
                "0. Exit");
    }

    public void createAccount(){
        Account account = Account.createNewAccount();
        Database.saveAccount(account);
        System.out.printf("Your account has been created" +
                "Your name is %s\n" +
                "Your email is %s\n" +
                "Your password is %s\n" +
                "Your SSN is %s\n",
                Account.getName(),Account.getEmail(),Account.getPassword(),Account.getSSN());
    }

    public boolean loginAccount(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter name: ");
        String name = scan.nextLine();
        System.out.println("Enter password: ");
        String password = scan.nextLine();

        Account account = Database.loadAccount(name,password);
        if(account == null || Account.getPassword()!=password){
            System.out.println("Wrong name or password!");
            return false;
        }

        System.out.println("You have succesfully logged in!");
        currentAccount = account;
        return true;
    }

    public static boolean logoutAccount(){
        currentAccount = null;
        System.out.println("You have succesfully logged out!");
        return false;
    }
    public static void exit(){
        System.out.println("Bye!");
        System.exit(0);
    }


}
