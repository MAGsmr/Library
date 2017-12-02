package control;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.AuthorImpl;
import model.Book;
import model.BookImpl;
import model.ClientImpl;
import model.database.ClientDao;
import model.database.DaoFactory;
import model.database.DaoFactoryImpl;
import model.database.sql.SqlClientDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main /*extends Application */{


    public static void main(String[] args) throws Exception {

        DaoFactory daoFactory = new DaoFactoryImpl();
        Scanner sc = new Scanner(System.in);
        String log, pass;
        int select;
        char assepting;
        boolean run = true;
        ClientImpl client;
        BookImpl book;
        AuthorImpl author;
        List<BookImpl> books;
        List<ClientImpl> clients;
        List<AuthorImpl> authors;

        while(run) {
            System.out.print("Введите ваш логин: ");
            log = sc.next();

            System.out.print("Введите ваш пароль: ");
            pass = sc.next();

            client = daoFactory.client().getByName(log);

            if (client != null) {
                if (client.getPass().equals(pass)) {
                    //тело программы. работа с клиентом
                    System.out.println("Здравствуй, " + client.getLogin());
                    switch (client.getPrivilege()){
                        case "Client":
                            while(run) {
                                System.out.println("Что бы вы хотели сделать?\nПосмотреть список ваших книг? Введите '1'.\nПосмотреть список всех книг? Введите '2'.\nПосмотреть информацию о книге? Введите '3'.\nВзять новую книгу? Введите '4'.\nВернуть книгу? Введите '5'.\nВыйти? Введите '6'.");
                                select = sc.nextInt();
                                String bookName;
                                switch (select) {
                                    case 1:
                                        books = daoFactory.book().getByClientID(client.getId());
                                        for (BookImpl book1 : books) {
                                            System.out.println(book1.getTitle());
                                        }
                                        break;
                                    case 2:
                                        books = daoFactory.book().getAll();
                                        for (BookImpl book1 : books) {
                                            System.out.println(book1.getTitle());
                                        }
                                        break;
                                    case 3:
                                        System.out.println("Введите название книги: ");
                                        sc.nextLine();
                                        bookName = sc.nextLine();
                                        book = daoFactory.book().getByName(bookName);
                                        if (book!=null) {
                                            System.out.println(book.getTitle() + " " + book.getYear() + " " + book.getGenre());
                                        }else {
                                            System.out.println("Книги с таким названием нет");
                                        }
                                        break;
                                    case 4:
                                        System.out.println("Введите название книги: ");
                                        sc.nextLine();
                                        bookName = sc.nextLine();
                                        book = daoFactory.book().getByName(bookName);
                                        if(book!=null) {
                                            daoFactory.client().getBook(client.getId(), book.getId());
                                        }else {
                                            System.out.println("Книги с таким названием нет");
                                        }
                                        break;
                                    case 5:
                                        System.out.println("Введите название книги: ");
                                        sc.nextLine();
                                        bookName = sc.nextLine();
                                        book = daoFactory.book().getByName(bookName);
                                        if(book!=null) {
                                            daoFactory.client().returnBook(client.getId(), book.getId());
                                        }else {
                                            System.out.println("Книги с таким названием нет");
                                        }
                                        break;
                                    case 6:
                                        run=false;
                                        break;
                                    default:
                                        System.out.println("Неверный ввод. Попробуйте снова");
                                }
                            }
                            break;
                        case "Admin":
                            while(run) {
                                System.out.println("Что бы вы хотели сделать?\nПолучить список всех пользователей? Введите '1'.\nУдалить пользователя? Введите '2'.\nДать привелегии пользователю? Введите '3'.\nВыйти? Введите '4'.");
                                select = sc.nextInt();
                                String clientName;
                                String clientPriv;
                                switch (select) {
                                    case 1:
                                        clients = daoFactory.client().getAll();
                                        for (ClientImpl client3 : clients) {
                                            System.out.println(client3.getLogin() + " - " + client3.getPrivilege());
                                        }
                                        break;
                                    case 2:
                                        System.out.println("Введите логин пользователя: ");
                                        sc.nextLine();
                                        clientName = sc.nextLine();
                                        ClientImpl client1 = daoFactory.client().getByName(clientName);
                                        if (client1!=null) {
                                            if (!client1.getLogin().equals(client.getLogin())) {
                                                daoFactory.client().delete(client1.getId());
                                            }else {
                                                System.out.println("Это не выход");
                                            }
                                        }else {
                                            System.out.println("Нет клиента с таким логином");
                                        }
                                        break;
                                    case 3:
                                        System.out.println("Введите логин пользователя: ");
                                        sc.nextLine();
                                        clientName = sc.nextLine();
                                        ClientImpl client2 = daoFactory.client().getByName(clientName);
                                        if (client2!=null) {
                                            if (!client2.getLogin().equals(client.getLogin())) {
                                                System.out.println("Какие привилегии будут у пользователя?(Client, Moder, Admin)");
                                                clientPriv = sc.nextLine();
                                                if(clientPriv.equals("Client") || clientPriv.equals("Moder") || clientPriv.equals("Admin")) {
                                                    daoFactory.client().setPriv(client2.getId(), clientPriv);
                                                }else {
                                                    System.out.println("Таких привилегий нет");
                                                }
                                            }else {
                                                System.out.println("У вас и так все хорошо");
                                            }
                                        }else {
                                            System.out.println("Нет клиента с таким логином");
                                        }
                                        break;
                                    case 4:
                                        run=false;
                                        break;
                                    default:
                                        System.out.println("Неверный ввод. Попробуйте снова");
                                }
                            }
                            break;
                        case "Moder":
                            while(run) {
                                System.out.println("Что бы вы хотели сделать?\nПолучить список всех книг? Введите '1'.\nПолучить список всех авторов? Введите '2'.\nУзнать автора книги?  Введите '3'.\nДобавить автора?  Введите '4'.\nДобавить автору книгу?  Введите '5'.\nВыйти? Введите '6'.");
                                String bookName;
                                select = sc.nextInt();
                                switch (select) {
                                    case 1:
                                        books = daoFactory.book().getAll();
                                        for (BookImpl book1 : books) {
                                            System.out.println(book1.getTitle());
                                        }
                                        break;
                                    case 2:
                                        authors = daoFactory.author().getAll();
                                        for (AuthorImpl author1 : authors) {
                                            System.out.println(author1.getName());
                                        }
                                        break;
                                    case 3:
                                        System.out.println("Введите название книги: ");
                                        sc.nextLine();
                                        bookName = sc.nextLine();
                                        book = daoFactory.book().getByName(bookName);
                                        if(book!=null) {
                                            System.out.println(daoFactory.author().getByBook(book.getTitle()).getName());
                                        }else {
                                            System.out.println("Книги с таким названием нет");
                                        }
                                        break;
                                    case 4:
                                        break;
                                    case 5:
                                        break;
                                    case 6:
                                        run=false;
                                        break;
                                    default:
                                        System.out.println("Неверный ввод. Попробуйте снова");
                                }
                            }
                            break;
                    }
                } else {
                    System.out.println("Неверный пароль");
                }
            } else {
                System.out.println("Такого пользователя нет");
            }
            System.out.print("Попробуете войти снова?(y): ");
            assepting = sc.next().charAt(0);
            if(assepting!='y'){
                run=false;
            }
        }
    }


    //Пока отложим вывод в формы и сделаем консольный интерфейс. Позже уже пойдем во фронт
    /*@Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/sample.fxml"));
        primaryStage.setTitle("Логин");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }*/


}
