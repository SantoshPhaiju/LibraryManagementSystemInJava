package Service;

import entities.Book;
import entities.User;

import java.util.List;
import java.util.Map;

public interface LibraryService {
    public void addBook(Book book);

    public Book updateBook(int index, Book book);

    public void deleteBook(String bookName);

    public Book getBook(String bookName);

    public List<Book> fetchAllBooks();

    public boolean issueBook(String bookName, User user);

    public boolean returnBook(String bookName, User user);

    public void displayAllBooks();

    boolean searchBook(String bookName, int booksId);

    public void displayIssuedBook();

    List<Book> deleteBooks(String booksName);

}
