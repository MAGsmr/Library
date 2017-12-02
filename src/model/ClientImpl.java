package model;

public class ClientImpl implements Client{

    //Обычный класс клиента для описания его структуры и работы с данными, полученными из базы

    private String id;
    private String login;
    private String pass;
    private String privilege;

    public ClientImpl(){ }

    public ClientImpl(String id, String login, String pass, String privilege){

        this.id = id;
        this.login = login;
        this.pass = pass;
        this.privilege = privilege;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getPass() {
        return pass;
    }

    @Override
    public String getPrivilege() {
        return privilege;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }
}
