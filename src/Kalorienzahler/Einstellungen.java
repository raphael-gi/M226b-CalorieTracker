package Kalorienzahler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Date;

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
    private JTextField new_name;
    private JButton new_name_best;
    private JLabel new_name_label;
    SpinnerNumberModel alter_model = new SpinnerNumberModel(30, 0, 150, 1);
    private JSpinner new_alter;
    private JLabel new_alter_label;
    private JButton new_alter_best;
    private JLabel groesse;
    private JLabel groesse_label;
    private JButton groesse_andern_button;
    private JLabel new_groesse_label;
    private JButton new_groesse_best;
    SpinnerNumberModel groesse_model = new SpinnerNumberModel(180, 0.00, 1000.00, 1);
    private JSpinner new_groesse;
    private JLabel gewicht_label;
    private JButton gewicht_ander_button;
    SpinnerNumberModel gewicht_model = new SpinnerNumberModel(80, 0.00, 1000.00, 1);
    private JSpinner new_gewicht;
    private JLabel gewicht;
    private JLabel new_gewicht_label;
    private JButton new_gewicht_best;

    private JFrame frame;

    private Dimension size;
    private Point loc;
    private String name;
    private boolean darkmode;
    private Date date_select;

    private int new_loeschen_check;
    private int new_name_check;
    private int new_age_check;
    private int new_groesse_check;
    private int new_gewicht_check;

    private JButton[] all_buttons = {dark, zuruck, loeschen, bestaetigen, logout, name_andern_button, gender_andern_button, age_andern_button, new_name_best, new_alter_best, groesse_andern_button, new_groesse_best, gewicht_ander_button, new_gewicht_best};
    private JLabel[] all_labels = {benutzername, darkmode_label, benutzername_label, pass_eingeben, gender, age, age_label, gender_label, new_name_label, new_alter_label, groesse_label, groesse, new_groesse_label, gewicht, gewicht_label, new_gewicht_label};

    public Einstellungen(Dimension size, Point loc, String name, Date datum){
        this.size = size;
        this.loc = loc;
        this.name = name;
        this.date_select = datum;

        frame = new JFrame("Einstellungen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(panel1);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.setSize(this.size);
        frame.setLocation(loc);

        new_alter.setModel(alter_model);
        new_groesse.setModel(groesse_model);
        new_gewicht.setModel(gewicht_model);

        pass_eingeben.setVisible(false);
        passwort_feld.setVisible(false);
        bestaetigen.setVisible(false);

        new_name_label.setVisible(false);
        new_name.setVisible(false);
        new_name_best.setVisible(false);
        new_alter_label.setVisible(false);
        new_alter.setVisible(false);
        new_alter_best.setVisible(false);
        new_groesse_label.setVisible(false);
        new_groesse.setVisible(false);
        new_groesse_best.setVisible(false);
        new_gewicht_label.setVisible(false);
        new_gewicht.setVisible(false);
        new_gewicht_best.setVisible(false);

        error_message.setText("");

        for (int i = 0; all_buttons.length > i; i++){
            JButton but = all_buttons[i];
            but.addActionListener(this);
        }
    }

    public void content(){
        benutzername.setText(this.name);

        DBConnect get_gender = new DBConnect("SELECT gender FROM benutzer WHERE Benutzername = '" + this.name + "'", "gender", 0);
        get_gender.con();
        int gender = Integer.parseInt(get_gender.getResult());
        if (gender == 1){
            this.gender.setText("Weiblich");
        }
        else {
            this.gender.setText("Männlich");
        }

        DBConnect get_age = new DBConnect("SELECT age FROM benutzer WHERE Benutzername = '" + this.name + "'", "age", 0);
        String age = get_age.getResult();
        this.age.setText(age);

        DBConnect get_groesse = new DBConnect("SELECT groesse FROM benutzer WHERE Benutzername = '" + this.name + "'", "groesse", 0);
        String groesse = get_groesse.getResult();
        this.groesse.setText(groesse);

        DBConnect get_gewicht = new DBConnect("SELECT gewicht FROM benutzer WHERE Benutzername = '" + this.name + "'", "gewicht", 0);
        String gewicht = get_gewicht.getResult();
        this.gewicht.setText(gewicht);

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

    public void set_vis(int check, int check2 , JSpinner base, JLabel base_label, JButton base_best, JLabel base_base){
        if (check == 1){
            base_label.setVisible(false);
            base.setVisible(false);
            base_best.setVisible(false);
            if (check2 == 1){
                new_age_check = 0;
            }
            if (check2 == 2){
                new_groesse_check = 0;
            }
            if (check2 == 3){
                new_gewicht_check = 0;
            }
        }
        else {
            double groesse_value = Double.parseDouble(base_base.getText());
            base.setValue(groesse_value);
            base_label.setVisible(true);
            base.setVisible(true);
            base_best.setVisible(true);
            if (check2 == 1){
                new_age_check = 1;
            }
            if (check2 == 2){
                new_groesse_check = 1;
            }
            if (check2 == 3){
                new_gewicht_check = 1;
            }
        }
    }

    public void on_set_vis(JSpinner base, String get, String error_name){
        if (base.getValue() == null){
            error_message.setText("" + error_name + " darf nicht Leer sein!");
        }
        else {
            new DBConnect("UPDATE Benutzer SET " + get + " = '" + base.getValue() + "' WHERE Benutzername = '" + this.name + "'", " ", 1);
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Einstellungen n = new Einstellungen(frame_size, frame_loc, this.name, date_select);
            n.content();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dark){
            if (!this.darkmode){
                new DBConnect("UPDATE benutzer SET dark = " + 1 + " WHERE Benutzername = '" + this.name + "'", " ", 1);
            }
            else {
                new DBConnect("UPDATE benutzer SET dark = " + 0 + " WHERE Benutzername = '" + this.name + "'", " ", 1);
            }
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Einstellungen n = new Einstellungen(frame_size, frame_loc, this.name, date_select);
            n.content();
        }

        if (e.getSource() == zuruck){
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Tagebuch n = new Tagebuch(frame_size, frame_loc,this.name, date_select);
            n.content();
        }
        if (e.getSource() == loeschen){
            if (new_loeschen_check == 1){
                pass_eingeben.setVisible(false);
                passwort_feld.setVisible(false);
                bestaetigen.setVisible(false);
                new_loeschen_check = 0;
            }
            else {
                pass_eingeben.setVisible(true);
                passwort_feld.setVisible(true);
                bestaetigen.setVisible(true);
                new_loeschen_check = 1;
            }
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
        if (e.getSource() == name_andern_button){
            if (this.new_name_check == 1){
                new_name_label.setVisible(false);
                new_name.setVisible(false);
                new_name_best.setVisible(false);
                this.new_name_check = 0;
            }
            else {
                new_name_label.setVisible(true);
                new_name.setVisible(true);
                new_name_best.setVisible(true);
                this.new_name_check = 1;
            }
        }
        if (e.getSource() == new_name_best){
            if (new_name.getText().isEmpty()){
                error_message.setText("Benutzername darf nicht Leer sein!");
            }
            else {
                DBConnect check = new DBConnect("SELECT Benutzername FROM Benutzer WHERE Benutzername = '" + new_name.getText() + "'", "Benutzername", 0);
                if (check.getResult() != null){
                    error_message.setText("Benutzername wird bereits verwendet!");
                }
                else {
                    new DBConnect("UPDATE Benutzer SET Benutzername = '" + new_name.getText() + "' WHERE Benutzername = '" + this.name + "'", " ", 1);
                    frame.dispose();
                    Dimension frame_size = frame.getSize();
                    Point frame_loc = frame.getLocation();
                    Einstellungen n = new Einstellungen(frame_size, frame_loc, new_name.getText(), date_select);
                    n.content();
                }
            }
        }
        if (e.getSource() == age_andern_button){
            set_vis(new_age_check, 1, new_alter, new_alter_label, new_alter_best, age);
        }
        if (e.getSource() == new_alter_best){
            on_set_vis(new_alter,"age", "Alter");
        }
        if (e.getSource() == groesse_andern_button){
            set_vis(new_groesse_check, 2, new_groesse, new_groesse_label, new_groesse_best, groesse);
        }
        if (e.getSource() == new_groesse_best){
            on_set_vis(new_groesse,"groesse", "Grösse");
        }
        if (e.getSource() == gewicht_ander_button){
            set_vis(new_gewicht_check, 3, new_gewicht, new_gewicht_label, new_gewicht_best, gewicht);
        }
        if (e.getSource() == new_gewicht_best){
            on_set_vis(new_gewicht, "gewicht", "Gewicht");
        }
        if (e.getSource() == gender_andern_button){
            DBConnect gend = new DBConnect("SELECT gender FROM Benutzer WHERE Benutzername = '" + this.name + "'", "gender", 0);
            int gender = Integer.parseInt(gend.getResult());
            if (gender == 1){
                new DBConnect("UPDATE Benutzer SET gender = " + 2 + " WHERE Benutzername = '" + this.name + "'", " ", 1);
            }
            else {
                new DBConnect("UPDATE Benutzer SET gender = " + 1 + " WHERE Benutzername = '" + this.name + "'", " ", 1);
            }
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Einstellungen n = new Einstellungen(frame_size, frame_loc, this.name, date_select);
            n.content();
        }
    }
}