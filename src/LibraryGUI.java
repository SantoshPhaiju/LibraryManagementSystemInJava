import ui.AddBookForm;
import ui.SystemGUI;

import javax.swing.*;

public class LibraryGUI {
    public static void main(String[] args) {
        JFrame systemFrame = new SystemGUI();
        AddBookForm addBookForm = new AddBookForm();

        addBookForm.setBounds(0, 0, 400, 400);
        // Add to the frame
        systemFrame.setLayout(null);
        systemFrame.add(addBookForm);

        systemFrame.setVisible(true);
    }
}
