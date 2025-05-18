import ui.SystemGUI;
import ui.SystemMenubar;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public class LibraryGUI {
    public static void main(String[] args) {
        JFrame systemFrame = new SystemGUI();

        JMenuBar systemMenu = new SystemMenubar();
        systemFrame.setJMenuBar(systemMenu);




        systemFrame.setVisible(true);
    }
}
