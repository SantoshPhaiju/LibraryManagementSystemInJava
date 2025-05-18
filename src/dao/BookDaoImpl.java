package dao;

import Database.DatabaseConnection;
import models.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookDaoImpl implements BookDAO {
    @Override
    public void addBook(Book book) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "INSERT INTO books (title, author, bookId, quantity) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getBookId());
            statement.setInt(4, book.getQuantity());
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {

                System.out.println("Book added successfully!");
            } else {
                System.out.println("Error adding book!");
            }
            connection.close();
            statement.close();


        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteBook(int bookId) {

    }

    @Override
    public void updateBook(Book book) {

    }

    @Override
    public void displayAllBooks() {

    }

    @Override
    public Book getSingleBookById(int bookId) {
        return null;
    }
}
