package Service;

import entities.Book;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BooksManagement {
    private static final String FILE_NAME = "books.txt";

    public static void saveBook(Book book) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(book.toFileString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving book: " + e.getMessage());
        }
    }

    public static List<Book> readBooks() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                books.add(Book.fromFileString(line));
            }
        } catch (IOException e) {
            System.out.println("Error reading books: " + e.getMessage());
        }
        return books;
    }

    public static void deleteBookById(int bookId) {
        List<Book> books = readBooks();
        boolean found = false;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Book book : books) {
                if (book.getBookId() != bookId) {
                    bw.write(book.toFileString());
                    bw.newLine();
                } else {
                    found = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error deleting book: " + e.getMessage());
        }

        if (found) {
            System.out.println("Book deleted successfully.");
        } else {
            System.out.println("Book ID not found.");
        }
    }

    public static void updateBookById(int bookId, Book updatedBook) {
        List<Book> books = readBooks();
        boolean found = false;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Book book : books) {
                if (book.getBookId() == bookId) {
                    bw.write(updatedBook.toFileString()); // write updated data
                    found = true;
                } else {
                    bw.write(book.toFileString()); // keep old data
                }
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating book: " + e.getMessage());
        }

        if (found) {
            System.out.println("Book updated successfully.");
        } else {
            System.out.println("Book ID not found.");
        }
    }
}