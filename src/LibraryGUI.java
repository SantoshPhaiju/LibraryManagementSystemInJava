import dao.BookDaoImpl;
import models.Book;
import ui.AddBookForm;
import ui.SystemGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LibraryGUI extends JFrame {
    private BookDaoImpl bookDao;
    private JTabbedPane tabbedPane;
    private JTable table;
    private DefaultTableModel booksModel;

    public LibraryGUI() {
        bookDao = new BookDaoImpl();
        loadBooks();
        initializeGui();
    }

    private void initializeGui() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 750);
        setLocationRelativeTo(null);
        tabbedPane = new JTabbedPane();

        JPanel displayAllBooksPanel = createDisplayAllBooksPanel();
        if (tabbedPane != null) {
            tabbedPane.addTab("Display All Books", displayAllBooksPanel);
        }

        JPanel addBookPanel = createAddBookPanel();
        if (tabbedPane != null) {
            tabbedPane.addTab("Add Book", addBookPanel);
        }

        add(tabbedPane);
    }

    private JPanel createDisplayAllBooksPanel() {
        JPanel panel = new JPanel();

        String[] columns = {"Title", "Author", "BookId", "Quantity", "Id"};
        List<Book> data = bookDao.displayAllBooks();

        Object[][] tableData = new Object[data.size()][5];

        for (int i = 0; i < data.size(); i++) {
            Book book = data.get(i);
            tableData[i][0] = book.getTitle();
            tableData[i][1] = book.getAuthor();
            tableData[i][2] = book.getBookId();
            tableData[i][3] = book.getQuantity();
            tableData[i][4] = book.getBookId();
        }

        table = new JTable(tableData, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);

        return panel;
    }

    private JPanel createAddBookPanel() {
        return new AddBookForm();
    }

    private JPanel createUpdateBookPanel() {
        JPanel panel = new JPanel();
        JLabel pageLabel = new JLabel("Update Book Form");
        pageLabel.setBounds(0, 0, 750, 50);
        panel.add(pageLabel);

        return panel;
    }

    private void loadData() {
        loadBooks();
    }

    private void loadBooks() {
        booksModel.setRowCount(0);
        List<Book> data = bookDao.displayAllBooks();
        for (Book book : data) {
            Object[] row = {
                    book.getTitle(),
                    book.getAuthor(),
                    book.getBookId(),
                    book.getQuantity(),
            };
            booksModel.addRow(row);
        }
    }


    public static void main(String[] args) {
        new LibraryGUI().setVisible(true);
    }
}
