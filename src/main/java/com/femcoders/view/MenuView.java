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

        System.out.println(Colors.PURPLE + Colors.BOLD +
                "\n╔═══════════════════════════════════╗" + Colors.RESET);
        System.out.println(Colors.PURPLE + Colors.BOLD +
                "       📚 FEMINIST LIBRARY 📚        " + Colors.RESET);
        System.out.println(Colors.PURPLE + Colors.BOLD +
                "╚═══════════════════════════════════╝" + Colors.RESET);

        System.out.println(Colors.CYAN + "   Welcome to the Feminist Library" + Colors.RESET);
        while (!bExit) {

            System.out.println(Colors.PURPLE + Colors.BOLD +
                    "═════════════════════════════════════" + Colors.RESET);
            System.out.println(Colors.YELLOW + "  1. ➕ Create book" + Colors.RESET);
            System.out.println(Colors.CYAN + "  2. 📖 View all books" + Colors.RESET);
            System.out.println(Colors.CYAN + "  3. 🔍 Find book by id" + Colors.RESET);
            System.out.println(Colors.CYAN + "  4. 🔍 Find book by title" + Colors.RESET);
            System.out.println(Colors.BLUE + "  5. ✏️ Update book info" + Colors.RESET);
            System.out.println(Colors.RED + "  6. 🗑️ Delete a book" + Colors.RESET);
            System.out.println(Colors.WHITE +  "  0. 👋 Exit" + Colors.RESET);
            System.out.println(Colors.PURPLE + Colors.BOLD +
                    "═════════════════════════════════════" + Colors.RESET);
            System.out.print(Colors.YELLOW + Colors.BOLD +
                    "  Select a number between 0 and 6: " + Colors.RESET);

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 0:
                    bExit = true;
                    System.out.println(Colors.YELLOW + "\n👋 See you soon!" + Colors.RESET);
                    break;
                case 1:
                    bookView.createBook(scanner);
                    break;
                case 2:
                    bookView.searchAllBooks();
                    break;
                case 3:
                    bookView.searchById(scanner);
                    break;
                case 4:
                    bookView.searchByTitle(scanner);
                    break;
                case 5:
                    bookView.updateBookById(scanner);
                    break;
                case 6:
                    bookView.deleteBook(scanner);
                    break;

                default:
                    System.out.println(Colors.RED + "❌Invalid option. Please enter a number from 0 to 6." + Colors.RESET);
            }
        }
    }
}