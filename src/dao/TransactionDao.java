package dao;

import java.util.List;

public interface TransactionDao {
    public void issueBook(int bookId, int userId, int borrow_period);
    public void returnBook(int bookId, int userId);

    public void showAllIssuedBooksList();
}
