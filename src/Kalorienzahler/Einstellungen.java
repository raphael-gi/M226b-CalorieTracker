package Kalorienzahler;

import javax.swing.*;
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
    private JButton name_andern_button;
    private JButton gender_andern_button;
    private JButton age_andern_button;
    private JPasswordField name_andern_passwort;
    private JTextField name_andern_input;
    private JLabel name_andern_pas_label;
    private JButton name_andern_pas_best;
    private JButton name_andern_best;
    private JLabel name_andern_label;
    private JLabel gender_andern_pas_label;
    private JPasswordField gender_andern_passwort;
    private JButton gender_andern_pas_best;
    private JLabel age_andern_pas_label;
    private JPasswordField age_andern_passwort;
    private JButton age_andern_pas_best;

    private JFrame frame;

    private Dimension size;
    private Point loc;
    private String name;
    private boolean darkmode;

    private JButton[] all_buttons = {dark, zuruck, loeschen, bestaetigen, logout, name_andern_button, gender_andern_button, age_andern_button, name_andern_pas_best, name_andern_best};
    private JLabel[] all_labels = {benutzername, darkmode_label, benutzername_label, pass_eingeben, gender, age, age_label, gender_label, name_andern_pas_label, name_andern_label};

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

        name_andern_pas_best.setVisible(false);

        vis(name_andern_pas_label, name_andern_passwort, name_andern_label, name_andern_input, name_andern_pas_best, name_andern_best);

        dark.addActionListener(this);
        zuruck.addActionListener(this);
        loeschen.addActionListener(this);
        bestaetigen.addActionListener(this);
        logout.addActionListener(this);
        name_andern_button.addActionListener(this);
        gender_andern_button.addActionListener(this);
        age_andern_button.addActionListener(this);
    }
    public void vis(JLabel pas_label, JPasswordField pas_input, JLabel label, JTextField input, JButton pas_best, JButton best){
        pas_label.setVisible(false);
        pas_input.setVisible(false);
        label.setVisible(false);
        input.setVisible(false);
        pas_best.setVisible(false);
        best.setVisible(false);
    }
    public void andern(JButton andern_button){

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

        Darkmode n = new Darkmode(name, all_buttons, all_labels);
        this.darkmode = n.isDark();
        if (!this.darkmode){
            dark.setText("Aus");
        }
        else {
            dark.setText("An");

            panel1.setBackground(Color.DARK_GRAY);

            passwort_feld.setBackground(Color.lightGray);

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