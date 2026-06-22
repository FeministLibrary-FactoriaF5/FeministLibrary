package com.femcoders;

import java.util.Scanner;

//import com.femcoders.config.DBManager;
import com.femcoders.controller.BookController;
import com.femcoders.repository.AuthorRepository;
import com.femcoders.repository.AuthorRepositoryImpl;
import com.femcoders.repository.BookRepository;
import com.femcoders.repository.BookRepositoryImpl;
import com.femcoders.view.BookView;

public class Main {
    public static void main(String[] args) {

        //DBManager.getConnection();
        //DBManager.closeConnection();

        BookRepository bookRepository = new BookRepositoryImpl();
        AuthorRepository authorRepository = new AuthorRepositoryImpl();

        BookController bookController = new BookController(bookRepository, authorRepository);
        BookView bookView = new BookView(bookController);
        Scanner scanner = new Scanner(System.in);

        bookView.createBook(scanner);

        }
    }