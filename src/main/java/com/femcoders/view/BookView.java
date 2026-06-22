package com.femcoders.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.femcoders.controller.BookController;
import com.femcoders.model.Author;
import com.femcoders.model.Book;
import com.femcoders.model.Format;
import com.femcoders.model.Genre;

public class BookView {
    private BookController bookController;

    public BookView (BookController bookController){
        this.bookController = bookController;
    }

    public void createBook(Scanner scanner){
        System.out.println("===Create book===");

        System.out.println("Introduce title: ");
        String title = scanner.nextLine();

        //System.out.println("Introduce Author:");
        //String author = scanner.nextLine();

        // Author fijo de prueba (id=1 ya existe en la base de datos)
        Author author = new Author(1, "Autor de prueba");

        //System.out.println("Introduce Publisher:");
        //String publisher = scanner.nextLine();

        System.out.println("Introduce ISBN (13 characters): ");
        String isbn = scanner.nextLine();

        System.out.println("Introduce year of publishing: ");
        Integer publishedYear = Integer.parseInt(scanner.nextLine());

        System.out.println("Introduce summary: (max 200 characters) ");
        String summary = scanner.nextLine();

        System.out.println("Introduce format: (paperback / hardcover / ebook / audiobook) ");
        Format format = Format.valueOf(scanner.nextLine().toUpperCase());

        System.out.println("Introduce genre (separated by comma, ex: horror, satire): ");
        String genresInput = scanner.nextLine();
        List<Genre> genres = new ArrayList<>();
        for (String genreName : genresInput.split(",")) {
            // De momento usamos id=1 como placeholder.
            // Pendiente: buscar el id real del género en la base de datos.
            genres.add(new Genre(1, genreName.trim()));
        }

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublisher(null); // sin editorial, para probar el caso null
        book.setIsbn(isbn);
        book.setPublishedYear(publishedYear);
        book.setSummary(summary);
        book.setFormat(format);
        book.setGenres(genres);

        bookController.createBook(book);

    }
}
