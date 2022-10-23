import javax.swing.*;
import java.awt.*;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Global {
    public static void main(String[] args) {
        com.formdev.flatlaf.FlatDarkLaf.install();

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        frame = new JFrame();
        frame.setSize(new Dimension(900, 700));
        frame.setLocation(new Point(0,0));
        frame.setLocationRelativeTo(null);
        new Login();
        frame.setVisible(true);
    }
}