package Kalorienzähler;

import com.mysql.cj.log.Log;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import javax.swing.text.html.HTML;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class Einstellungen implements ActionListener {
    private JPanel panel1;
    private JLabel benutzername;
    private JButton dark;
    private JLabel darkmode_label;
    private JLabel benutzername_label;
    private JButton zuruck;
    private JButton loeschen;
    private JPasswordField passwort_feld;
    private JLabel pass_eingeben;
    private JButton bestaetigen;
    private JLabel error_message;
    private JButton logout;
    private JLabel gender;
    private JLabel age;
    private JLabel age_label;
    private JLabel gender_label;

    private JFrame frame;

    private Dimension size;
    private Point loc;
    private String name;
    private boolean darkmode;

    private JButton[] all_buttons = {dark, zuruck, loeschen, bestaetigen, logout};
    private JLabel[] all_labels = {benutzername, darkmode_label, benutzername_label, pass_eingeben, gender, age, age_label, gender_label};

    public Einstellungen(Dimension size, Point loc, String name){
        this.size = size;
        this.loc = loc;
        this.name = name;

        frame = new JFrame("Einstellungen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(panel1);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.setSize(this.size);
        frame.setLocation(loc);

        pass_eingeben.setVisible(false);
        passwort_feld.setVisible(false);
        bestaetigen.setVisible(false);

        error_message.setText("");

        DBConnect darkcon = new DBConnect("SELECT dark FROM benutzer WHERE Benutzername = '" + this.name + "'", "dark", 0);
        darkcon.con();
        int numb = Integer.parseInt(darkcon.getResult());
        if (numb == 0){
            this.darkmode = false;
        }
        else {
            this.darkmode = true;

            panel1.setBackground(Color.DARK_GRAY);
            benutzername.setForeground(Color.WHITE);
            darkmode_label.setForeground(Color.WHITE);
            benutzername_label.setForeground(Color.WHITE);
        }

        dark.addActionListener(this);
        zuruck.addActionListener(this);
        loeschen.addActionListener(this);
        bestaetigen.addActionListener(this);
        logout.addActionListener(this);
    }
    public void content(){
        benutzername.setText(this.name);

        DBConnect get_gender = new DBConnect("SELECT gender FROM benutzer WHERE Benutzername = '" + this.name + "'", "gender", 0);
        get_gender.con();
        int gender = Integer.parseInt(get_gender.getResult());
        if (gender == 0){
            this.gender.setText("Weiblich");
        }
        else {
            this.gender.setText("Männlich");
        }

        DBConnect get_age = new DBConnect("SELECT age FROM benutzer WHERE Benutzername = '" + this.name + "'", "age", 0);
        get_age.con();
        String age = get_age.getResult();
        this.age.setText(age);

        if (!this.darkmode){
            dark.setText("Aus");
        }
        else {
            dark.setText("An");
            passwort_feld.setBackground(Color.lightGray);

            Darkmode n = new Darkmode(name, all_buttons, all_labels);
            all_buttons = n.getAll_buttons();
            all_labels = n.getAll_labels();

            logout.addMouseListener(new MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    logout.setForeground(Color.CYAN);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    logout.setForeground(Color.CYAN);
                }
            });
            loeschen.addMouseListener(new MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    loeschen.setForeground(Color.RED);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    loeschen.setForeground(Color.RED);
                }
            });
            bestaetigen.addMouseListener(new MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    bestaetigen.setForeground(Color.RED);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    bestaetigen.setForeground(Color.RED);
                }
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dark){
            if (!this.darkmode){
                DBConnect turn_light = new DBConnect("UPDATE benutzer SET dark = " + 1 + " WHERE Benutzername = '" + this.name + "'", " ", 1);
                turn_light.con();
            }
            else {
                DBConnect turn_dark = new DBConnect("UPDATE benutzer SET dark = " + 0 + " WHERE Benutzername = '" + this.name + "'", " ", 1);
                turn_dark.con();
            }
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Einstellungen n = new Einstellungen(frame_size, frame_loc, this.name);
            n.content();
        }
        if (e.getSource() == zuruck){
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Tagebuch n = new Tagebuch(frame_size, frame_loc,this.name);
            n.content();
        }
        if (e.getSource() == loeschen){
            pass_eingeben.setVisible(true);
            passwort_feld.setVisible(true);
            bestaetigen.setVisible(true);
        }
        if (e.getSource() == bestaetigen){
            error_message.setText("");
            String passwort = passwort_feld.getText();
            DBConnect pass = new DBConnect("SELECT Passwort FROM benutzer WHERE Benutzername = '" + this.name + "'", "Passwort", 0);
            pass.con();
            String data_passwort = pass.getResult();
            if (!passwort.equals(data_passwort)){
                error_message.setText("Passwort stimmt nicht!");
            }
            else {
                DBConnect delete = new DBConnect("DELETE FROM benutzer WHERE Benutzername = '" + this.name + "'", " ", 1);
                delete.con();
                frame.dispose();
                Dimension frame_size = frame.getSize();
                Point frame_loc = frame.getLocation();
                new Login(frame_size, frame_loc);
            }
        }
        if (e.getSource() == logout){
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            new Login(frame_size, frame_loc);
        }
    }
}