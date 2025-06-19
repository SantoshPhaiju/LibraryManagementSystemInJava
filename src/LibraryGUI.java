import dao.BookDaoImpl;
import dao.BookNotAvailableException;
import dao.TransactionsDaoImpl;
import dao.UserDaoImpl;
import entities.Transactions;
import entities.User;
import models.Book;
import utils.BookIdGenerator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LibraryGUI extends JFrame {
    private final BookDaoImpl bookDao;
    private JTabbedPane tabbedPane;
    private final UserDaoImpl userDao;
    private final TransactionsDaoImpl transactionsDao;
    private JTable table;
    private JTable usersTable;
    private JTable transactionsTable;
    private DefaultTableModel transactionsModel;
    private DefaultTableModel usersModel;
    private DefaultTableModel booksModel;
    private JComboBox<Book> bookComboBox;
    private JComboBox<User> userComboBox;

    public LibraryGUI() {
        bookDao = new BookDaoImpl();
        userDao = new UserDaoImpl();
        transactionsDao = new TransactionsDaoImpl();
        initializeGui();
        loadData();
    }

    private void initializeGui() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 750);
        setLocationRelativeTo(null);
        tabbedPane = new JTabbedPane();

        transactionsDao.showAllTransactions();

        JPanel booksPanel = createBooksPanel();
        tabbedPane.addTab("Books", booksPanel);

        JPanel usersPanel = createUsersPanel();
        tabbedPane.addTab("Users", usersPanel);

        JPanel transactionsPanel = createTransactionsPanel();
        tabbedPane.addTab("Issued Books", transactionsPanel);

        add(tabbedPane);
    }

    private JPanel createUsersPanel() {
        JPanel usersPanel = new JPanel(new BorderLayout());
        String[] columnNames = {"ID", "Username", "Email", "created_at"};

        usersModel = new DefaultTableModel(null, columnNames);
        usersTable = new JTable(usersModel);

        JScrollPane scrollPane = new JScrollPane(usersTable);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JButton addUserButton = new JButton("Add User");
        JButton updateUserButton = new JButton("Update User");
        JButton deleteUserButton = new JButton("Delete User");

        buttonsPanel.add(addUserButton);
        buttonsPanel.add(updateUserButton);
        buttonsPanel.add(deleteUserButton);

        usersPanel.add(scrollPane, BorderLayout.CENTER);
        usersPanel.add(buttonsPanel, BorderLayout.SOUTH);

        addUserButton.addActionListener(e -> {
            showAddUserDialog();
        });

        updateUserButton.addActionListener(e -> {
            showUpdateUserDialog();
        });

        deleteUserButton.addActionListener(e -> {
            deleteUserDialog();
        });


        return usersPanel;
    }

    private JPanel createBooksPanel() {
        JPanel booksPanel = new JPanel();
        booksPanel.setLayout(new BorderLayout());

        String[] columnNames = {"Id", "Title", "Author", "BookId", "Quantity", "Available"};
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

        return booksPanel;
    }

    private JPanel createTransactionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        transactionsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(transactionsTable);
        String[] columnNames = {"Id", "Book Name", "Username", "Due Date"};
        transactionsModel = new DefaultTableModel(null, columnNames);
        transactionsTable.setModel(transactionsModel);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JButton issueBookButton = new JButton("Issue Book");
        JButton returnBookButton = new JButton("Return Book");
        buttonsPanel.add(issueBookButton);
        buttonsPanel.add(returnBookButton);

        issueBookButton.addActionListener(e -> {
            issueBookDialog();
        });

        returnBookButton.addActionListener(e -> {
            returnBookDialog();
        });


        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadData() {
        loadBooks();
        loadUsers();
        loadTransactions();
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
                    book.getAvailable()
            };
            booksModel.addRow(row);
        }
    }

    private void loadUsers() {
        usersModel.setRowCount(0);
        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            Object[] row = {
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getTimestamp(),
            };
            usersModel.addRow(row);
        }
    }

    private void loadTransactions() {
        transactionsModel.setRowCount(0);
//        List<Transactions> transactionsData = transactionsDao.showAllTransactions();
        List<Transactions> transactionsData = transactionsDao.displayIssuedBooks();
        for (Transactions transaction : transactionsData) {
            Object[] row = {
                    transaction.getId(),
                    transaction.getBookname(),
                    transaction.getUsername(),
                    transaction.getDueDate(),
            };
            transactionsModel.addRow(row);
        }
    }

    private void loadComboBoxes(JComboBox<Book> bookComboBox, JComboBox<User> userComboBox) {
        List<Book> books = bookDao.displayAllBooks();
        List<User> users = userDao.getAllUsers();

        bookComboBox.removeAllItems();
        userComboBox.removeAllItems();
        for (Book book : books) {
            System.out.println("testing to string" + book.toString());
            bookComboBox.addItem(book);
        }

        for (User user : users) {
            userComboBox.addItem(user);
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
            if (isSuccess) {
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

    private void showUpdateUserDialog() {
        JDialog dialog = new JDialog(this, "Update User", true);
        dialog.setLayout(new GridLayout(2, 2));
        dialog.add(new JLabel("Feature needs to be impleted here !!!"));
        dialog.add(new JLabel("blah:"));


        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        dialog.add(saveButton);
        dialog.add(cancelButton);

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void deleteUserDialog() {
        JDialog dialog = new JDialog(this, "Delete User", true);
        dialog.add(new JLabel("Feature needs to be impleted here !!!"));

        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void showAddUserDialog() {
        JDialog dialog = new JDialog(this, "Add User", true);
        dialog.setLayout(new GridLayout(3, 2));
        JTextField usernameField = new JTextField();
        JTextField emailField = new JTextField();

        JLabel usernameLabel = new JLabel("Username:");
        dialog.add(usernameLabel);
        dialog.add(usernameField);
        dialog.add(new JLabel("Email:"));
        dialog.add(emailField);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        dialog.add(saveButton);
        dialog.add(cancelButton);

        cancelButton.addActionListener(e -> dialog.dispose());
        saveButton.addActionListener(e -> {

            User newUser = new User(usernameField.getText());
            newUser.setEmail(emailField.getText());

            if (userDao.createUser(newUser)) {
                loadData();
                JOptionPane.showMessageDialog(this, "User added successfully");
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "User creation failed");
                dialog.dispose();
            }

        });

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

    private void issueBookDialog() {
        JDialog dialog = new JDialog(this, "Issue Book", true);
        dialog.setLayout(new GridLayout(3, 2));

        bookComboBox = new JComboBox<>();
        userComboBox = new JComboBox<>();

        loadComboBoxes(bookComboBox, userComboBox);

        dialog.add(new JLabel("Select Book:"));
        dialog.add(bookComboBox);

        dialog.add(new JLabel("Select User:"));
        dialog.add(userComboBox);

        JButton issueBookButton = new JButton("Issue Book");
        JButton cancelButton = new JButton("Cancel");

        issueBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Book selectedBook = (Book) bookComboBox.getSelectedItem();
                User selectedUser = (User) userComboBox.getSelectedItem();
                assert selectedBook != null;
                assert selectedUser != null;
                try {
                transactionsDao.issueBook(selectedBook.getId(), selectedUser.getId(), 5);
                } catch (BookNotAvailableException err) {
                    JOptionPane.showMessageDialog(null, err.getMessage());
                    System.out.println(err.getMessage());
                }
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.add(issueBookButton);
        dialog.add(cancelButton);

        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

    }

    private void returnBookDialog() {
        JDialog dialog = new JDialog(this, "Return Book", true);
        dialog.setLayout(new GridLayout(3, 2));

        JComboBox<Book> returnBookComboBox = new JComboBox<>();
        JComboBox<User> returnUserComboBox = new JComboBox<>();

        loadComboBoxes(returnBookComboBox, returnUserComboBox);

        dialog.add(new JLabel("Select Book:"));
        dialog.add(returnBookComboBox);
        dialog.add(new JLabel("Select User:"));
        dialog.add(returnUserComboBox);

        JButton returnButton = new JButton("Return");
        JButton cancelButton = new JButton("Cancel");

        returnButton.addActionListener(e -> {
            Book selectedBook = (Book) returnBookComboBox.getSelectedItem();
            User selectedUser = (User) returnUserComboBox.getSelectedItem();

            if (selectedBook == null || selectedUser == null) {
                JOptionPane.showMessageDialog(this, "Please select both Book and User.");
                return;
            }

            transactionsDao.returnBook(selectedBook.getId(), selectedUser.getId());
            loadData(); // Refresh table
            JOptionPane.showMessageDialog(this, "Book returned successfully!");
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.add(returnButton);
        dialog.add(cancelButton);

        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        new LibraryGUI().setVisible(true);
    }
}
