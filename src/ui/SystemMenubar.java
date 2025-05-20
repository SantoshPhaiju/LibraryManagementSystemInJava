package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SystemMenubar extends JMenuBar {
    public SystemMenubar(JFrame systemFrame) {
        JMenu addBook = new JMenu("Add Book");
        JMenu deleteBook = new JMenu("Delete Book");
        JMenu updateBook = new JMenu("Update Book");
        JMenu displayAllBooks = new JMenu("Display All Books");

        displayAllBooks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                systemFrame.getContentPane().removeAll();

            }
        });

        add(addBook);
        add(deleteBook);
        add(updateBook);
        add(displayAllBooks);
    }
}
