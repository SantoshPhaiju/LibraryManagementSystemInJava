package Service;

import entities.Book;
import entities.User;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryServiceImpl implements LibraryService {

    List<Book> books = new ArrayList<>();

    @Override
    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public Book updateBook(int index, Book book) {
        books.set(index, book);
        return book;
    }

    @Override
    public void deleteBook(String bookName) {
        Book bookToRemove = null;
        for (Book book : books) {
            if (book.getTitle().equals(bookName)) {
                bookToRemove = book;
                break;
            }
        }
        if (bookToRemove != null) {
            books.remove(bookToRemove);
            System.out.println("Book removed: " + bookName);
        } else {
            System.out.println("Book not found: " + bookName);
        }
    }


    @Override
    public Book getBook(String bookName) {
        Book book = null;
        for (Book book1 : books) {
            if (book1.getTitle().equals(bookName)) {
                book = book1;
            }
        }
        return book;
    }

    @Override
    public List<Book> fetchAllBooks() {
       return books;
    }

    @Override
    public void issueBook(String bookName, User user) {
        Book newBook = null;
        for (Book book : books) {
            if (book.getTitle().equals(bookName)) {
                newBook = book;
                break;
            }
        }
        if (newBook == null) {
            System.out.println("Book not found: " + bookName);
        } else {
            Map<String, List<String>> issuedList = new HashMap<>();
            List<String> issuedToUser = new ArrayList<>();
            issuedToUser.add(user.getUsername());
            issuedList.put(newBook.getTitle(), issuedToUser);
            newBook.setIssuedList(issuedList);
            System.out.println("Book Issued: " + newBook.getTitle() + " to user: " + user.getUsername());
        }
    }

    @Override
    public Book returnBook(String bookName, User user) {
        return null;
    }
}
