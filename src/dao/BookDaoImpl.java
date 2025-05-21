package dao;

import Database.DatabaseConnection;
import models.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDAO {
    @Override
    public void addBook(Book book) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "INSERT INTO books (title, author, bookId, quantity) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getBookId());
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
    public List<Book> displayAllBooks() {
        List<Book> books = new ArrayList<>(); // Step 1

        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM books";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) { // Step 2
                Book book = new Book(); // Step 3
                book.setBookId("bookId");
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setQuantity(resultSet.getInt("quantity"));
                books.add(book); // Step 4
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return books; // Step 5
    }


    @Override
    public Book getSingleBookById(int bookId) {
        return null;
    }
}
