import dao.BookDaoImpl;
import models.Book;
import ui.AddBookForm;
import ui.SystemGUI;
import utils.BookIdGenerator;

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

        updateBookButton.addActionListener(e -> {
            showUpdateBookDialog();
        });

        deleteBookButton.addActionListener(e -> {
            deleteBookDialog();
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

        dialog.setLayout(new GridLayout(4, 2));

        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField quantityField = new JTextField();

        dialog.add(new JLabel("Title:"));
        dialog.add(titleField);
        dialog.add(new JLabel("Author:"));
        dialog.add(authorField);
        dialog.add(new JLabel("Quantity:"));
        dialog.add(quantityField);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        dialog.add(saveButton);
        dialog.add(cancelButton);

        saveButton.addActionListener(e -> {
            boolean isSuccess = saveBookToDb(titleField, authorField, quantityField, bookDao);
            if(isSuccess) {
                loadData();

                JOptionPane.showMessageDialog(this, "New Book created successfully");
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Book creation failed");
                dialog.dispose();
            }

        });

        cancelButton.addActionListener(e -> dialog.dispose());


        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private static boolean saveBookToDb(JTextField titleField, JTextField authorField, JTextField quantityField, BookDaoImpl bookDao) {
        String title = titleField.getText();
        String author = authorField.getText();
        int quantity = Integer.parseInt(quantityField.getText());

        String bookId = BookIdGenerator.generate(title);
        Book newBook = new Book(title, author, bookId, quantity);
        return bookDao.addBook(newBook);
    }

    private void showUpdateBookDialog() {
        JDialog dialog = new JDialog(this, "Update Book", true);
        dialog.setLayout(new GridLayout(4, 2));
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to edit");
            return;
        }

        String title = table.getValueAt(selectedRow, 1).toString();
        String author = table.getValueAt(selectedRow, 2).toString();
        String bookId = table.getValueAt(selectedRow, 3).toString();
        int quantity = Integer.parseInt(table.getValueAt(selectedRow, 4).toString());


        JTextField titleField = new JTextField();
        titleField.setText(title);
        JTextField authorField = new JTextField();
        authorField.setText(author);
        JTextField quantityField = new JTextField();
        quantityField.setText(String.valueOf(quantity));
        dialog.add(new JLabel("Title:"));
        dialog.add(titleField);
        dialog.add(new JLabel("Author:"));
        dialog.add(authorField);
        dialog.add(new JLabel("Quantity:"));
        dialog.add(quantityField);
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        dialog.add(saveButton);
        dialog.add(cancelButton);

        saveButton.addActionListener(e -> {
            Book updatedBook = new Book(titleField.getText(), authorField.getText(), bookId, Integer.parseInt(quantityField.getText()));

            bookDao.updateBook(updatedBook);
            loadData();
            JOptionPane.showMessageDialog(null, "Your book has been updated");
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> {
            dialog.dispose();
        });

        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void deleteBookDialog() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure want to delete this book?");
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to delete");
            return;
        }
        int bookId = Integer.parseInt(booksModel.getValueAt(selectedRow, 0).toString());
        if (confirm == JOptionPane.YES_OPTION) {
            if (bookDao.deleteBook(bookId)) {
                JOptionPane.showMessageDialog(this, "Book deleted successfully!");
                loadBooks();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete book.");
            }
        }
    }

    public static void main(String[] args) {
        new LibraryGUI().setVisible(true);
    }
}
