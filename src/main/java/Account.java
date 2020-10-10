import net.andreinc.mockneat.MockNeat;

public class Account {
    private static String name;
    private static String email;
    private static String password;
    private static String SSN;


    public static Account createNewAccount(){
        MockNeat mock = MockNeat.secure();
        Account account = new Account();
        name = mock.names().full().val();
        email = mock.emails().val();
        password = mock.passwords().val();
        SSN = mock.sscs().val();
        return account;
    }

    public static String getSSN() {
        return SSN;
    }

    public static void setSSN(String SSN) {
        Account.SSN = SSN;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Account.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Account.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        Account.password = password;
    }
}
