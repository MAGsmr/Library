package model;

public class AuthorImpl implements Author{

    private String id;
    private String name;

    public AuthorImpl(){}

    public AuthorImpl(String id, String name){
        this.id = id;
        this. name = name;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
