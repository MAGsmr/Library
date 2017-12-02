package model.database.sql;

import model.Author;
import model.AuthorImpl;
import model.BookImpl;
import model.database.AuthorDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SqlAuthorDao implements AuthorDao {

    private String url = "jdbc:oracle:thin:@localhost:1521:XE";
    private String name = "GAV_6308";
    private String password = "qwerty";
    private static String driver = "oracle.jdbc.driver.OracleDriver";

    private static Connection connection = null;

    SqlAuthorDao(){
        try {
            Locale.setDefault(Locale.ENGLISH);
            Class.forName(driver);
            connection = DriverManager.getConnection(url, name, password);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public AuthorImpl getByID(String id) {

        String sql = "SELECT * FROM Author WHERE ID = ?";
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
    public AuthorImpl getByName(String name) {

        String sql = "SELECT * FROM Author WHERE Name = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);

            ResultSet result = statement.executeQuery();

            return getResult(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public AuthorImpl getByBook(String name) {
        String sql = "SELECT Author.id FROM Author, Author_book, Book WHERE Author.id = Author_book.Author_id and Book.id=Author_book.Book_id and book.title=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return getByID(result.getString("ID"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean create(AuthorImpl author) {
        return false;
    }

    @Override
    public void update(AuthorImpl author) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<AuthorImpl> getAll() {
        String sql = "SELECT * FROM Author";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet result = statement.executeQuery();

            return getResults(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static List<AuthorImpl> getResults(ResultSet result) throws SQLException {
        List<AuthorImpl> authors = new ArrayList<>();
        while (result.next()) {
            AuthorImpl author = new AuthorImpl();

            author.setId(result.getString("ID"));
            author.setName(result.getString("Name"));

            authors.add(author);
        }

        connection.close();

        return authors;
    }

    private static AuthorImpl getResult(ResultSet result) throws SQLException{
        if(result.next()){
            AuthorImpl author = new AuthorImpl();

            author.setId(result.getString("ID"));
            author.setName(result.getString("Name"));
            connection.close();

            return author;
        }else {
            return null;
        }
    }
}
