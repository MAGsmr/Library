package model.database;

import model.Book;
import model.BookImpl;

import java.util.List;

public interface BookDao {

    BookImpl getByID(String id);

    BookImpl getByName(String title);

    boolean create(BookImpl book);

    void update(BookImpl book);

    void delete(String id);

    List<BookImpl> getByClientID(String id);

    List<BookImpl> getAll();

}
