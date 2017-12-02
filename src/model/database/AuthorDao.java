package model.database;

import model.Author;
import model.AuthorImpl;

import java.util.List;

public interface AuthorDao {

    AuthorImpl getByID(String id);

    AuthorImpl getByName(String name);

    AuthorImpl getByBook(String name);

    boolean create(AuthorImpl author);

    void update(AuthorImpl author);

    void delete(String id);

    List<AuthorImpl> getAll();

}
