package com.femcoders.view;

import java.util.Scanner;

public class MenuView {

    private Scanner scanner;
    private BookView bookView;

    public MenuView(Scanner scanner, BookView bookView) {
        this.scanner = scanner;
        this.bookView = bookView;
    }

    public void start() {

        boolean bExit = false;

        System.out.println("Welcome to the Feminist Library");

        while (!bExit) {

            System.out.println("================================");

            System.out.println("1.- Create book");
            System.out.println("2.- View all books");
            System.out.println("3.- Find book by title");
            System.out.println("4.- Find book by author");
            System.out.println("5.- Find book by genre");
            System.out.println("6.- Update book info");
            System.out.println("7.- Delete a book");
            System.out.println("0.- Exit");

            System.out.println("Select a number between 0 and 7: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 0:
                    bExit = true;
                    System.out.println("See you soon!");
                    break;
                case 1:
                    bookView.createBook(scanner);
                    break;
                case 2:
                    break;
                case 4:
                    bookView.searchByTitle(scanner);
                    break;

                default:
                    System.out.println("Invalid option. Please enter a number from 0 to 7.");

            }

        }

    }

}
