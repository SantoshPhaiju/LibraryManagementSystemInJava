package dao;

import models.Book;

public interface BookDAO {
    public void addBook(Book book);
    public void deleteBook(int bookId);
    public void updateBook(Book book);
    public void displayAllBooks();
    public Book getSingleBookById(int bookId);
}
