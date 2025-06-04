package ui;

import javax.swing.*;

public class SystemMenubar extends JMenuBar {
    public SystemMenubar(JFrame systemFrame) {
        JMenu addBook = new JMenu("Add Book");
        JMenu deleteBook = new JMenu("Delete Book");
        JMenu updateBook = new JMenu("Update Book");
        JMenu displayAllBooks = new JMenu("Display All Books");
        JMenuItem displayBooks = new JMenuItem("Show All Books");

        add(addBook);
        add(deleteBook);
        add(updateBook);
        add(displayAllBooks);
        displayAllBooks.add(displayBooks);
    }
}
