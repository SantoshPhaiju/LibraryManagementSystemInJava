package Database;

import entities.Book;

public interface BookService {

    public void addBook(String title, String author, int bookId, int quantity);
    public void updateBook(int bookId, String title, String author, int quantity);
    public void deleteBook(int bookId);
    public void displayAllBooks();
    public Book getBook(int bookId);
    public boolean searchBook(String bookName, int booksId);
    public void displayIssuedBook();
    public void deleteBooks(String bookName);
}
