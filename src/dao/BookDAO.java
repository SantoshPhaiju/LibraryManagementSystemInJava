package dao;

import models.Book;

import java.util.List;

public interface BookDAO {
    public void addBook(Book book);
    public boolean deleteBook(int bookId);
    public void updateBook(Book book);
    public List<Book> displayAllBooks();
    public Book getSingleBookById(int bookId);
}
