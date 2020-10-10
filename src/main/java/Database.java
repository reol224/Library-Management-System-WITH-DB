import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class Database {
    private static String url;

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        Database.url = url;
    }

    public static void initDB(String dbFilename){
        setUrl("jdbc:sqlite:./" + dbFilename);
        createDbFile(dbFilename);
        createCardTableReader();
        createCardTableBook();
    }

    private static Connection openConnection(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(true);
        }
        catch (SQLException e){
            System.out.printf("An SQLException occurred: %s%n", e.getMessage());
        }
        return connection;
    }

    private static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.printf("An SQLException occurred: %s%n", e.getMessage());
        }
    }

    private static void createDbFile(String dbFilename) {
        Path dbPath = Paths.get(String.format("./%s", dbFilename));
        if (!Files.exists(dbPath)) {
            try {
                Files.createFile(dbPath);
            } catch (IOException e) {
                System.out.printf("An IOException occurred: %s%n", e.getMessage());
            }
        }
    }

    private static void createCardTableReader() {
        Connection connection = Database.openConnection();

        String query = "CREATE TABLE IF NOT EXISTS reader ("
                + "SSN INTEGER primary key,"
                + "name TEXT" +
                "email TEXT" +
                "password TEXT)";

        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(query);
        } catch (SQLException e){
            System.out.printf("An SQLException occurred: %s%n", e.getMessage());
        }

        Database.closeConnection(connection);
    }

    private static void createCardTableBook() {
        Connection connection = Database.openConnection();

        String query = "CREATE TABLE IF NOT EXISTS books ("
                + "title TEXT primary key,"
                + "author TEXT)";

        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(query);
        } catch (SQLException e){
            System.out.printf("An SQLException occurred: %s%n", e.getMessage());
        }

        Database.closeConnection(connection);
    }



    public static void closeAccount(Account account){
        Connection connection = Database.openConnection();
        String query = String.format("DELETE FROM library WHERE (name=%s)" +
                        "(password=%s)",
                Account.getName(),Account.getPassword());
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(query);
            connection.commit();
        }
        catch (SQLException e){
            System.out.printf("An SQLException occurred: %s%n", e.getMessage());
        }

        Database.closeConnection(connection);
    }

    public static void deleteBook(String title){
        Connection connection = Database.openConnection();
        String query = String.format("DELETE FROM library WHERE (title=%s)",
                title);
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(query);
            connection.commit();
        }
        catch (SQLException e){
            System.out.printf("An SQLException occurred: %s%n", e.getMessage());
        }

        Database.closeConnection(connection);

    }

    public static boolean doesBookExist(String title){
        Connection connection = Database.openConnection();
        String query = String.format("SELECT COUNT(*) FROM library WHERE (title = %s)", title);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){
            boolean exists = resultSet.getBoolean(1);
            if (exists) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            Database.closeConnection(connection);
        }
        return false;
    }

    public static void searchUserBySSN(String SSN){
        Connection connection = Database.openConnection();
        String query = String.format("SELECT name FROM library WHERE (SSN = %s)",
                SSN);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            connection.commit();
        } catch (SQLException e) {
            System.out.printf("An SQLException occurred: %s%n", e.getMessage());
        }
        Database.closeConnection(connection);
    }

    public static void searchUserByName(String name){
        Connection connection = Database.openConnection();
        String query = String.format("SELECT name FROM library WHERE (name = %s)",
                name);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            connection.commit();
        } catch (SQLException e) {
            System.out.printf("An SQLException occurred: %s%n", e.getMessage());
        }
        Database.closeConnection(connection);
    }

    public static void searchUserByEmail(String email){
        Connection connection = Database.openConnection();
        String query = String.format("SELECT name FROM library WHERE (email = %s)",
                email);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            connection.commit();
        } catch (SQLException e) {
            System.out.printf("An SQLException occurred: %s%n", e.getMessage());
        }
        Database.closeConnection(connection);
    }

    public static void saveAccount(Account account) {
        Connection connection = Database.openConnection();

        String query = String.format("INSERT INTO library (name, password) values (%s, %s)",
                Account.getName(), Account.getPassword());

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            connection.commit();
        } catch (SQLException e) {
            System.out.printf("An SQLException occurred: %s%n", e.getMessage());
        }

        Database.closeConnection(connection);
    }
    public static void addUser(String name, String password) {
        Connection connection = Database.openConnection();

        String query = String.format("INSERT INTO library (name, password) values (%s, %s)",
                name, password);

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            connection.commit();
        } catch (SQLException e) {
            System.out.printf("An SQLException occurred: %s%n", e.getMessage());
        }

        Database.closeConnection(connection);
    }


    public static Account loadAccount(String name, String password){
        Connection connection = Database.openConnection();

        String query = String.format("SELECT name, password FROM library WHERE name = %s AND password = %s",
                name, password);

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){
            boolean exists = resultSet.next();

            if (exists) {
                Account account = new Account();
                Account.setName(resultSet.getString("name"));
                Account.setEmail((resultSet.getString("email")));
                account.setPassword(resultSet.getString("password"));
                Account.setSSN(resultSet.getString("SSN"));
                return account;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Database.closeConnection(connection);
        return null;
    }
}
