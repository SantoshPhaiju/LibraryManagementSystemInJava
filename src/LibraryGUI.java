import dao.BookDaoImpl;
import models.Book;
import ui.AddBookForm;
import ui.SystemGUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        String[] columnNames = {"Id", "Title", "Author", "BookId", "Quantity"};
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

        // Add book button action listener
        addBookButton.addActionListener(e -> {

            showAddBookDialog();

        });

        return  booksPanel;
    }

    private void loadData() {
        loadBooks();
    }

    private void loadBooks() {
        booksModel.setRowCount(0);
        List<Book> data = bookDao.displayAllBooks();
        for (Book book : data) {
            Object[] row = {
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getBookId(),
                    book.getQuantity(),
            };
            booksModel.addRow(row);
        }
    }

    private void showAddBookDialog() {
        JDialog dialog = new JDialog(this, "Add Book", true);

        dialog.setLayout(new GridLayout(6, 2));

        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField isbnField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField copiesField = new JTextField();

        dialog.add(new JLabel("Title:"));
        dialog.add(titleField);
        dialog.add(new JLabel("Author:"));
        dialog.add(authorField);
        dialog.add(new JLabel("ISBN:"));
        dialog.add(isbnField);
        dialog.add(new JLabel("Category:"));
        dialog.add(categoryField);
        dialog.add(new JLabel("Total Copies:"));
        dialog.add(copiesField);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        dialog.add(saveButton);
        dialog.add(cancelButton);

        dialog.setSize(400, 400);

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

    }


    public static void main(String[] args) {
        new LibraryGUI().setVisible(true);
    }
}
