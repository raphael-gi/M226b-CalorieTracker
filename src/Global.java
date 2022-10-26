import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

public class Global extends JFrame {
    public static JFrame frame;

    static Connection connection = null;
    static Statement statement = null;
    static ResultSet resultSet = null;

    static int id;
    static String username;
    static Date date;
    static int gender;
    static int age;
    static double gewicht;
    static double groesse;
    static int muskel;
    static int bulk;
    static int sprache;
    static String password;

    public void newPanel(JPanel panel) {
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }
}