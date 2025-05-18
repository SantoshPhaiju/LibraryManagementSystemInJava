import models.Book;
import ui.AddBookForm;
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

        AddBookForm addBookForm = new AddBookForm();

        addBookForm.setBounds(0, 0, 400, 400);
        // Add to the frame
        systemFrame.setLayout(null);
        systemFrame.add(addBookForm);

        systemFrame.setVisible(true);
    }
}
