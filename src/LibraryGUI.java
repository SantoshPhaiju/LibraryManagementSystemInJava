import dao.BookDaoImpl;
import models.Book;
import ui.AddBookForm;
import ui.SystemGUI;

import javax.swing.*;
import javax.swing.border.Border;
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
        initializeGui();
        loadData();
    }

    private void initializeGui() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 750);
        setLocationRelativeTo(null);
        tabbedPane = new JTabbedPane();

        JPanel booksPanel = createBooksPanel();
        tabbedPane.addTab("Books", booksPanel);

        add(tabbedPane);
    }

    private JPanel createBooksPanel() {
        JPanel booksPanel = new JPanel();

        booksPanel.setLayout(new BorderLayout());

        String[] columnNames = {"Title", "Author", "BookId", "Quantity"};
        booksModel = new DefaultTableModel(null, columnNames);
        table = new JTable(booksModel);

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JButton addBookButton = new JButton("Add Book");
        JButton updateBookButton = new JButton("Update Book");
        JButton deleteBookButton = new JButton("Delete Book");

        buttonsPanel.add(addBookButton);
        buttonsPanel.add(updateBookButton);
        buttonsPanel.add(deleteBookButton);

        booksPanel.add(scrollPane, BorderLayout.CENTER);
        booksPanel.add(buttonsPanel, BorderLayout.SOUTH);



        return  booksPanel;
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
