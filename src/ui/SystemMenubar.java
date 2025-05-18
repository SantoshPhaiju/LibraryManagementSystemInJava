package ui;

import javax.swing.*;

public class SystemMenubar extends JMenuBar {
    public SystemMenubar() {
        JMenu addBook = new JMenu("Add Book");
        JMenu deleteBook = new JMenu("Delete Book");
        JMenu updateBook = new JMenu("Update Book");
        JMenu displayAllBooks = new JMenu("Display All Books");

        add(addBook);
        add(deleteBook);
        add(updateBook);
        add(displayAllBooks);
    }
}
