package ui;

import dao.BookDaoImpl;
import models.Book;
import utils.BookIdGenerator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBookForm extends JPanel {
    BookDaoImpl bookDao = new BookDaoImpl();

    public AddBookForm() {

        setLayout(null);
        JLabel bookNameLabel = new JLabel("Book Name:");
        bookNameLabel.setBounds(20, 20, 100, 30);

        JTextField bookNameField = new JTextField();
        bookNameField.setBounds(20, 50, 250, 30);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setBounds(20, 90, 100, 30);

        JTextField authorField = new JTextField();
        authorField.setBounds(20, 120, 250, 30);

        JLabel bookQuantityLabel = new JLabel("Book Quantity:");
        bookQuantityLabel.setBounds(20, 160, 100, 30);

        JTextField bookQuantityField = new JTextField();
        bookQuantityField.setBounds(20, 190, 250, 30);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(20, 230, 100, 30);


        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Submit button clicked");

                String bookName = bookNameField.getText();
                String author = authorField.getText();
                int bookQuantity = Integer.parseInt(bookQuantityField.getText());
                String bookId = BookIdGenerator.generate(bookName);
                Book book = new Book(bookName, author, bookId, bookQuantity);

                bookDao.addBook(book);
                System.out.println("book created.");
            }
        });


        add(submitButton);
        add(bookNameLabel);
        add(bookNameField);
        add(authorLabel);
        add(authorField);
        add(bookQuantityLabel);
        add(bookQuantityField);


    }

}
