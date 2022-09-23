package Kalorienzahler;

import javax.swing.*;
import java.util.Date;

public class Global {
    public static JFrame frame;

    public static int id;
    public static String username;
    public static Date date;
    public static int gender;
    public static int age;
    public static double gewicht;
    public static double groesse;
    public static int muskel;
    public static int bulk;
    public static int sprache;
    public static boolean darkmode;
    public static String password;

    public void newPanel(JPanel panel) {
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }
}