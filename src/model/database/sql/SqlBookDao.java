package model.database.sql;

import model.Book;
import model.BookImpl;
import model.ClientImpl;
import model.database.BookDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SqlBookDao implements BookDao {

    private String url = "jdbc:oracle:thin:@localhost:1521:XE";
    private String name = "GAV_6308";
    private String password = "qwerty";
    private static String driver = "oracle.jdbc.driver.OracleDriver";

    private static Connection connection = null;

    SqlBookDao(){
        try {
            Locale.setDefault(Locale.ENGLISH);
            Class.forName(driver);
            connection = DriverManager.getConnection(url, name, password);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public BookImpl getByID(String id) {
        String sql = "SELECT * FROM Book WHERE ID = ?";
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
    public BookImpl getByName(String title) {
        String sql = "SELECT * FROM Book WHERE Title = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, title);

            ResultSet result = statement.executeQuery();

            return getResult(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean create(BookImpl book) {
        return false;
    }

    @Override
    public void update(BookImpl book) {}

    @Override
    public void delete(String id) {}

    @Override
    public List<BookImpl> getByClientID(String id) {
        String sql = "SELECT book.id, book.title, book.year, book.genre FROM Book,Client_book WHERE Book.ID=Client_book.Book_ID AND Client_book.Client_ID=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,id);

            ResultSet result = statement.executeQuery();

            return getResults(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BookImpl> getAll() {
        String sql = "SELECT * FROM Book";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet result = statement.executeQuery();

            return getResults(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static List<BookImpl> getResults(ResultSet result) throws SQLException {
        List<BookImpl> books = new ArrayList<>();
        while (result.next()) {
            BookImpl book = new BookImpl();

            book.setId(result.getString("ID"));
            book.setTitle(result.getString("Title"));
            book.setYear(Integer.parseInt(result.getString("Year")));
            book.setGenre(result.getString("Genre"));

            books.add(book);
        }

        connection.close();

        return books;
    }

    private static BookImpl getResult(ResultSet result) throws SQLException{
        if(result.next()){
            BookImpl book = new BookImpl();

            book.setId(result.getString("ID"));
            book.setTitle(result.getString("Title"));
            book.setYear(Integer.parseInt(result.getString("Year")));
            book.setGenre(result.getString("Genre"));

            connection.close();

            return book;
        }else {
            return null;
        }
    }
}
