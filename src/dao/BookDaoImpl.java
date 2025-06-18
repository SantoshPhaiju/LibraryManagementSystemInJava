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
    public boolean addBook(Book book) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "INSERT INTO books (title, author, bookId, quantity, available) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getBookId());
            statement.setInt(4, book.getQuantity());
            statement.setInt(5, book.getQuantity());
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book added successfully!");
                connection.close();
                statement.close();
                return true;
            } else {
                System.out.println("Error adding book!");
                connection.close();
                statement.close();
                return false;
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteBook(int bookId) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM books WHERE id = ?");
            statement.setInt(1, bookId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book deleted successfully with id " + bookId);
                return true;
            } else {
                System.out.println("Error deleting book with id " + bookId);
                return false;
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public void updateBook(Book book) {
        try {
            Connection connection = DatabaseConnection.getConnection();

            String query = "UPDATE books SET title = ?, author = ?, quantity = ? WHERE bookId = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getQuantity());
            stmt.setString(4, book.getBookId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book updated successfully!");
            } else {
                System.out.println("Error updating book!");
            }
        }
        catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Book> displayAllBooks() {
        List<Book> books = new ArrayList<>(); // Step 1

        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM books";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book();
                book.setBookId(resultSet.getString("bookId"));
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setQuantity(resultSet.getInt("quantity"));
                book.setAvailable(resultSet.getInt("available"));
                books.add(book);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return books;
    }


    @Override
    public Book getSingleBookById(int bookId) {
        return null;
    }
}
