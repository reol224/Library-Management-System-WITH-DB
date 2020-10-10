import javax.xml.crypto.Data;
import java.util.Scanner;

public class Main {
    static Library library = new Library();
    static String dbFilename = "Library.db";
    private static boolean isLoggedIn;
    public static void main(String[] args){
        argsParse(args);
        if(dbFilename.equals("")){
            System.out.println("Database not available,Exiting...");
            System.exit(0);
        }
        Database.initDB(dbFilename);
        Scanner scanner = new Scanner(System.in);

        while(true){
            if(!isLoggedIn){
                library.initMenu();
                int action = Integer.parseInt(scanner.nextLine());
                chooseActionInitMenu(action);
            }
            else{
                library.loginMenu();
                int action = Integer.parseInt(scanner.nextLine());
                chooseActionLoginMenu(action);
            }
        }


    }
    private static void chooseActionLoginMenu(int action){
        Scanner scanner = new Scanner(System.in);
        switch (action){
            case 1:
                //booklist
            break;

            case 2:
                //readerlist
            break;

            case 3:
                //search book by title
            break;

            case 4:
                //search book by author
            break;

            case 5:
                //add book
            break;

            case 6:
                //modify book data
            break;

            case 7: {
                //remove book
                System.out.println("Enter name of book to remove: ");
                String name = scanner.nextLine();
                Database.deleteBook(name);
                System.out.println("Book " + name + "has been deleted!");
            }
            break;

            case 8: {
                //search user by name
                System.out.println("Enter name: ");
                String name = scanner.nextLine();
                Database.searchUserByName(name);
            }
            break;

            case 9:
                //search user by email
                System.out.println("Enter email: ");
                String email = scanner.nextLine();
                Database.searchUserByEmail(email);
            break;

            case 10:
                //search user by ssn
                System.out.println("Enter SSN: ");
                String SSN = scanner.nextLine();
                Database.searchUserBySSN(SSN);
            break;

            case 11: {
                //add user
                System.out.println("Enter the name of the new user: ");
                String name = scanner.nextLine();
                System.out.println("Enter the password of the new user: ");
                String password = scanner.nextLine();
                Database.addUser(name, password);
                System.out.println("User succesfully added!");
            }
            break;

            case 12:
                //modify user
            break;

            case 13:
                Database.closeAccount(Library.currentAccount);
                System.out.println("Account has been closed!");
            break;

            case 14:
                //borrow book
            break;

            case 15:
                //return book
            break;

            case 16:
                Library.logoutAccount();
            break;

            case 17:
                //does book exist
                System.out.println("Enter the name of the book: ");
                String name = scanner.nextLine();
                if(Database.doesBookExist(name)){
                    System.out.println(name + "exists!");
                }
                else{
                    System.out.println(name + "doesn't exist!");
                }
            break;

            case 0:
                Library.exit();


        }
    }
    private static void chooseActionInitMenu(int action) {
        switch (action){
            case 1:
                if(!isLoggedIn){
                    library.createAccount();
                }
                else{
                    System.out.println("hello!");
                }
                break;
            case 2:
                if (!isLoggedIn) {
                    isLoggedIn = library.loginAccount();
                } else {
                    isLoggedIn = library.logoutAccount();
                }
                break;
            case 0:
                library.exit();
                break;
            default:
                System.out.println("Wrong option");

        }
    }

    private static void argsParse(String[] args) {
        for (int i = 0; i < args.length; i++){
            if ("-fileName".equals(args[i])) {
                dbFilename = args[i + 1];
            }
        }
    }
}
