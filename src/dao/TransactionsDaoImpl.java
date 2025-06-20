package dao;

import Database.DatabaseConnection;
import entities.Transactions;
import models.Book;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class TransactionsDaoImpl implements TransactionDao {

    @Override
    public void issueBook(int bookId, int userId, int borrow_period) throws BookNotAvailableException {
        try (Connection connection = DatabaseConnection.getConnection();) {
            Book book = new Book();
            String fetchBookQuery = "SELECT * FROM books WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(fetchBookQuery);
            preparedStatement.setInt(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setAvailable(resultSet.getInt("available"));
                book.setQuantity(resultSet.getInt("quantity"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setBookId(resultSet.getString("bookId"));
            }

            if (book.getQuantity() != 0) {

            String query = "INSERT INTO transactions (user_id, book_id, transaction_type, due_date) VALUES (?, ?, ?, ?)";

            // Calculate due date
            LocalDate dueDate = LocalDate.now().plusDays(borrow_period);
            Date sqlDueDate = Date.valueOf(dueDate); // Convert to java.sql.Date


            PreparedStatement preparedStatement2 = connection.prepareStatement(query);
            preparedStatement2.setInt(1, userId);
            preparedStatement2.setInt(2, bookId);
            preparedStatement2.setString(3, "Borrow");
            preparedStatement2.setDate(4, sqlDueDate);

            int rowsAffected = preparedStatement2.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book Issued");
                book.setAvailable(book.getAvailable() - 1);
                String query2 = "UPDATE books SET available = ? WHERE id = ?";
                PreparedStatement preparedStatement3 = connection.prepareStatement(query2);
                preparedStatement3.setInt(1, book.getAvailable());
                preparedStatement3.setInt(2, bookId);
                preparedStatement3.executeUpdate();
            } else {
                throw new BookNotAvailableException("Book is not available for borrowing.");
            }

            } else {
                System.out.println("Book is not available");
                throw new BookNotAvailableException("Book is not available for borrowing.");
            }


        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void returnBook(int bookId, int userId) {
        try (Connection connection = DatabaseConnection.getConnection();) {

            String query1 = "";

            String query = "INSERT INTO transactions (user_id, book_id, transaction_type, returned_date) VALUES (?, ?, ?, ?)";

            Date returnDate = Date.valueOf(LocalDate.now());

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, bookId);
            preparedStatement.setString(3, "Return");
            preparedStatement.setDate(4, returnDate);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book Returned");
            } else {
                System.out.println("Book Not Returned");
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Transactions> showAllTransactions() {
        List<Transactions> transactions = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();) {

            String query = "SELECT * FROM transactions t JOIN users u ON t.user_id = u.id JOIN books b ON t.book_id = b.id";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                int bookId = resultSet.getInt("book_id");
                String transactionType = resultSet.getString("transaction_type");
                String dueDate = resultSet.getString("due_date");
                String returnDate = resultSet.getString("returned_date");
                Transactions transaction = new Transactions();
                transaction.setId(id);
                transaction.setUserId(userId);
                transaction.setBookId(bookId);
                transaction.setUsername(resultSet.getString("username"));
                transaction.setBookname(resultSet.getString("title"));
                transaction.setTransactionType(transactionType);
                transaction.setDueDate(dueDate);
                transaction.setReturnedDate(returnDate);
                transactions.add(transaction);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

            return transactions;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Transactions> displayIssuedBooks() {
        try (Connection connection = DatabaseConnection.getConnection();) {
            String query = "SELECT * FROM transactions t " +
                    "JOIN users u ON t.user_id = u.id " +
                    "JOIN books b ON t.book_id = b.id " +
                    "WHERE t.transaction_type = 'Borrow' AND t.status = 'issued'";

            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            List<Transactions> borrowedBooks = new ArrayList<>();
            while (resultSet.next()) {
                Transactions transactions = new Transactions();
                int id = resultSet.getInt("id");
                String dueDate = resultSet.getString("due_date");
                transactions.setId(id);
                transactions.setUsername(resultSet.getString("username"));
                transactions.setBookname(resultSet.getString("title"));
                transactions.setStatus(resultSet.getString("status"));
                transactions.setDueDate(dueDate);
                borrowedBooks.add(transactions);
            }

            return borrowedBooks;

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Transactions> displayReturnedBooks() {
        try (Connection connection = DatabaseConnection.getConnection();) {
            String query = "SELECT * FROM transactions t " +
                    "JOIN users u ON t.user_id = u.id " +
                    "JOIN books b ON t.book_id = b.id " +
                    "WHERE t.transaction_type = 'Return' AND t.status = 'returned'";

            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            List<Transactions> returnedBooks = new ArrayList<>();
            while (resultSet.next()) {
                Transactions transactions = new Transactions();
                int id = resultSet.getInt("id");
                String returnedDate = resultSet.getString("returned_date");
                transactions.setId(id);
                transactions.setUsername(resultSet.getString("username"));
                transactions.setBookname(resultSet.getString("title"));
                transactions.setStatus(resultSet.getString("status"));
                transactions.setReturnedDate(returnedDate);
                returnedBooks.add(transactions);
            }

            return returnedBooks;

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
