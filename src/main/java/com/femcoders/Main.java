package com.femcoders;

import java.util.Scanner;

//import com.femcoders.config.DBManager;
import com.femcoders.controller.BookController;
import com.femcoders.repository.*;
import com.femcoders.view.BookView;
import com.femcoders.view.MenuView;

public class Main {
    public static void main(String[] args) {

        BookRepository bookRepository = new BookRepositoryImpl();
        AuthorRepository authorRepository = new AuthorRepositoryImpl();
        PublisherRepository publisherRepository = new PublishRepositoryImpl();

        BookController bookController = new BookController(bookRepository, authorRepository, publisherRepository);
        
        BookView bookView = new BookView(bookController);
        Scanner scanner = new Scanner(System.in);

        MenuView menuView = new MenuView(scanner,bookView);

        menuView.start();

        //bookView.createBook(scanner);

        scanner.close();
        }
    }