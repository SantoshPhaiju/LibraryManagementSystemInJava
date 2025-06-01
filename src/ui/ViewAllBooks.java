package ui;

import dao.BookDaoImpl;
import models.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.List;

public class ViewAllBooks extends JPanel {

    BookDaoImpl bookDao = new BookDaoImpl();

    public ViewAllBooks() {
        setLayout(null);

        JLabel allBooksLabel = new JLabel("All Books");
        allBooksLabel.setBounds(20, 20, 100, 30);
        add(allBooksLabel);

        // Get all books from DAO
        List<Book> books = bookDao.displayAllBooks(); // Make sure this method exists and returns List<Book>

        // Table column names
        String[] columns = {"ID", "Title", "Author", "Quantity"};

        // Table data
        String[][] data = new String[books.size()][columns.length];
        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            data[i][0] = String.valueOf(b.getBookId());
            data[i][1] = b.getTitle();
            data[i][2] = b.getAuthor();
            data[i][3] = String.valueOf(b.getQuantity());
        }
        System.out.println(Arrays.deepToString(data));

        // Create JTable
        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 60, 500, 300); // Adjust size and position

        add(scrollPane);
        add(table);
    }
}
