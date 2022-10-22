import javax.swing.*;
import java.awt.*;

public class Main extends Global {
    public static void main(String[] args) {
        frame = new JFrame();
        frame.setSize(new Dimension(900, 700));
        frame.setLocation(new Point(0,0));
        frame.setLocationRelativeTo(null);
        new Login();
        frame.setVisible(true);
    }
}