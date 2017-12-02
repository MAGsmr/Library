package model.database.sql;

import model.ClientImpl;
import model.database.ClientDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class SqlClientDao implements ClientDao {

    private String url = "jdbc:oracle:thin:@localhost:1521:XE";
    private String name = "GAV_6308";
    private String password = "qwerty";
    private static String driver = "oracle.jdbc.driver.OracleDriver";

    private static Connection connection = null;

    SqlClientDao(){
        try {
            Locale.setDefault(Locale.ENGLISH);
            Class.forName(driver);
            connection = DriverManager.getConnection(url, name, password);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public ClientImpl getByID(String id){

        String sql = "SELECT * FROM Client WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, id);

            ResultSet result = statement.executeQuery();

            return getResult(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ClientImpl getByName(String login){
        String sql = "SELECT * FROM Client WHERE Login = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, login);

            ResultSet result = statement.executeQuery();

            return getResult(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void getBook(String clientID, String bookID) {
        String sql = "INSERT INTO Client_book VALUES (?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,UUID.randomUUID().toString());
            statement.setString(2,clientID);
            statement.setString(3,bookID);

            statement.executeUpdate();

            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void returnBook(String clientID, String bookID) {
        String sql = "DELETE FROM Client_book WHERE Client_ID = ? AND Book_ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,clientID);
            statement.setString(2,bookID);

            statement.executeUpdate();

            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean create(ClientImpl client) {
        return false;
    }

    @Override
    public void setPriv(String id, String priv) {
        String sql = "UPDATE Client SET Privilege = ? WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,priv);
            statement.setString(2,id);

            statement.executeUpdate();

            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM Client WHERE ID = ?";
        String sql1 = "DELETE FROM Client_book WHERE Client_ID = ?";
        try {

            PreparedStatement statement1 = connection.prepareStatement(sql1);
            statement1.setString(1,id);

            statement1.executeUpdate();

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,id);

            statement.executeUpdate();

            statement.close();
            statement1.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<ClientImpl> getAll(){
        String sql = "SELECT * FROM Client";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            List<ClientImpl> clients = new ArrayList<>();
            while (result.next()) {
                ClientImpl client = new ClientImpl();

                client.setId(result.getString("ID"));
                client.setLogin(result.getString("Login"));
                client.setPass(result.getString("Pass"));
                client.setPrivilege(result.getString("Privilege"));

                clients.add(client);
            }

            statement.close();

            return clients;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static ClientImpl getResult(ResultSet result) throws SQLException {
        if(result.next()){
            ClientImpl client = new ClientImpl();

            client.setId(result.getString("ID"));
            client.setLogin(result.getString("Login"));
            client.setPass(result.getString("Pass"));
            client.setPrivilege(result.getString("Privilege"));

            connection.close();

            return client;
        }else {
            return null;
        }
    }
}
