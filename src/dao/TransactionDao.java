package dao;

import entities.Transactions;

import java.util.List;

public interface TransactionDao {
    public void issueBook(int bookId, int userId, int borrow_period);
    public void returnBook(int transactionId);

    public List<Transactions> showAllTransactions();
    public List<Transactions> displayIssuedBooks();
    public List<Transactions> displayReturnedBooks();
}
