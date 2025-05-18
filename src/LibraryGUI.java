import models.Book;
import ui.SystemGUI;
import ui.SystemMenubar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryGUI {
    public static void main(String[] args) {
        JFrame systemFrame = new SystemGUI();

        JMenuBar systemMenu = new SystemMenubar();
        systemFrame.setJMenuBar(systemMenu);

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

                Book book = new Book(bookName, author, 1, bookQuantity);

            }
        });

        // Adding to frame
        systemFrame.add(submitButton);
        systemFrame.add(bookNameLabel);
        systemFrame.add(bookNameField);
        systemFrame.add(authorLabel);
        systemFrame.add(authorField);
        systemFrame.add(bookQuantityLabel);
        systemFrame.add(bookQuantityField);


        systemFrame.setVisible(true);
    }
}
