package Kalorienzahler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.*;
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
    private JLabel einstellungen_label;
    private JButton pas_andern_button;
    private JLabel pas_label;
    private JLabel pas_no_input;
    private JLabel old_pas_label;
    private JPasswordField old_pas_input;
    private JButton old_pas_best;
    private JPasswordField new_pas_input;
    private JLabel new_pas_label;
    private JPasswordField new_pas_best_input;
    private JLabel new_pas_best_label;
    private JButton new_pas_best;
    private JLabel muskel_aufbau_label;
    private JCheckBox muskel_aufbau;
    private int sprache;
    private String[] sprachen = {"Deutsch","English"};
    private JComboBox sprachW;
    private JLabel Sprache_Label;
    private String wahl;
    private int wahlnum;

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
    private int new_pas_check;

    String [] zuruck_list = {"Zurück","Back"};
    String [] benutzername_list = {"Benutzername:", "Username:"};
    String [] loeschen_list = {"Account löschen","Delete account"};
    String [] new_benutzername_list = {"Neuer Benutzername:", "New username:"};
    String [] gender_list = {"Geschlecht:","Gender:"};
    String [] age_list = {"Alter:", "Age:"};
    String [] new_age_list = {"Neues Alter:", "New age:"};
    String [] einstellungen_list = {"Einstellungen","Settings"};
    String [] Sprache_list = {"Sprache:","Language:"};
    String [] andern_list = {"Ändern","Change"};
    String [] groesse_list = {"Körpergrösse(Cm):","Body size(Cm):"};
    String [] new_groesse_list = {"Neue Körpergrösse(Cm):","New body size(Cm):"};
    String [] gewicht_list = {"Gewicht(Kg):","Weight(Kg):"};
    String [] new_gewicht_list = {"Neues Gewicht(Kg):","New weight(Kg):"};
    String [] password_list = {"Passwort:","Password:"};
    String [] old_password_list = {"Altes Passwort:","Old password:"};
    String [] new_password_list = {"Neues Passwort:","New password:"};
    String [] conf_password_list = {"Neues Passwort bestätigen:","Confirm New password:"};
    String [] eingeb_password_list = {"Passwort eingeben:","Enter password:"};
    String [] muskeln_list = {"Muskel Aufbauen","Build muscle"};
    String [] bestaetigen_list = {"Bestätigen","Confirm"};

    private JButton[] all_buttons = {dark, zuruck, loeschen, bestaetigen, logout, name_andern_button, gender_andern_button, age_andern_button, new_name_best, new_alter_best, groesse_andern_button, new_groesse_best, gewicht_ander_button, new_gewicht_best, pas_andern_button, old_pas_best, new_pas_best};
    private JLabel[] all_labels = {benutzername, darkmode_label, benutzername_label, pass_eingeben, gender, age, age_label, gender_label, new_name_label, new_alter_label, groesse_label, groesse, new_groesse_label, gewicht, gewicht_label, new_gewicht_label, einstellungen_label, pas_label, pas_no_input, old_pas_label, new_pas_label, new_pas_best_label, muskel_aufbau_label, Sprache_Label};

    JButton[] un_vis_but = {new_name_best, new_alter_best, new_groesse_best, new_gewicht_best, old_pas_best, new_pas_best, new_pas_best, bestaetigen};
    JLabel[] un_vis_lab = {new_name_label, new_alter_label, new_groesse_label, new_gewicht_label, old_pas_label, new_pas_label, new_pas_best_label, pass_eingeben};
    JSpinner[] un_vis_spin = {new_alter, new_groesse, new_gewicht};
    JPasswordField[] un_vis_pas = {old_pas_input, new_pas_input, new_pas_best_input, passwort_feld};

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public Einstellungen(Dimension size, Point loc, String name, Date datum){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        this.size = size;
        this.loc = loc;
        this.name = name;
        this.date_select = datum;

        frame = new JFrame("Einstellungen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon image = new ImageIcon(getClass().getResource("calories-logo.png"));
        frame.setIconImage(image.getImage());

        frame.add(panel1);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.setSize(this.size);
        frame.setLocation(loc);

        Font label_font = new Font("Arial", Font.BOLD, 15);
        einstellungen_label.setFont(label_font);

        new_alter.setModel(alter_model);
        new_groesse.setModel(groesse_model);
        new_gewicht.setModel(gewicht_model);

        for (String nam : sprachen) {
            sprachW.addItem(nam);

        }
        DBConnect get_sprache = new DBConnect("SELECT sprache FROM benutzer WHERE Benutzername = '" + this.name + "'", "sprache", 0);
        int b = Integer.parseInt(get_sprache.getResult());
        
        sprachW.setSelectedIndex(b);

        for (JButton but : un_vis_but) {
            but.setVisible(false);
        }
        for (JLabel lab : un_vis_lab) {
            lab.setVisible(false);
        }
        for (JSpinner spin : un_vis_spin) {
            spin.setVisible(false);
        }
        for (JPasswordField pas : un_vis_pas) {
            pas.setVisible(false);
        }

        new_name.setVisible(false);

        error_message.setText("");

        for (int i = 0; all_buttons.length > i; i++){
            JButton but = all_buttons[i];
            but.addActionListener(this);
        }
        muskel_aufbau.addActionListener(this);
        sprachW.addActionListener(this);





        //Methoden werden ausgeführt
        sprach();
        gend();
        age();
        groes();
        gew();
        musk();
        dark();
    }


    public void sprach(){
        DBConnect get_sprache = new DBConnect("SELECT sprache FROM benutzer WHERE Benutzername = '" + this.name + "'", "sprache", 0);
        sprache = Integer.parseInt(get_sprache.getResult());
        benutzername_label.setText(benutzername_list[this.sprache]);
        new_name_label.setText(new_benutzername_list[this.sprache]);
        gender_label.setText(gender_list[this.sprache]);
        age_label.setText(age_list[this.sprache]);
        new_alter_label.setText(new_age_list[this.sprache]);
        einstellungen_label.setText(einstellungen_list[this.sprache]);
        Sprache_Label.setText(Sprache_list[this.sprache]);
        groesse_label.setText(groesse_list[this.sprache]);
        new_groesse_label.setText(new_groesse_list[this.sprache]);
        pas_label.setText(password_list[this.sprache]);
        old_pas_label.setText(old_password_list[this.sprache]);
        pass_eingeben.setText(eingeb_password_list[this.sprache]);
        new_pas_label.setText(new_password_list[this.sprache]);
        new_pas_best_label.setText(conf_password_list[this.sprache]);
        zuruck.setText(zuruck_list[this.sprache]);
        loeschen.setText(loeschen_list[this.sprache]);
        muskel_aufbau_label.setText(muskeln_list[this.sprache]);
        bestaetigen.setText(bestaetigen_list[this.sprache]);
        new_pas_best.setText(bestaetigen_list[this.sprache]);
        old_pas_best.setText(bestaetigen_list[this.sprache]);
        new_name_best.setText(bestaetigen_list[this.sprache]);
        new_alter_best.setText(bestaetigen_list[this.sprache]);
        new_gewicht_best.setText(bestaetigen_list[this.sprache]);
        new_groesse_best.setText(bestaetigen_list[this.sprache]);
        name_andern_button.setText(andern_list[this.sprache]);
        gender_andern_button.setText(andern_list[this.sprache]);
        age_andern_button.setText(andern_list[this.sprache]);
        groesse_andern_button.setText(andern_list[this.sprache]);
        gewicht_ander_button.setText(andern_list[this.sprache]);
        pas_andern_button.setText(andern_list[this.sprache]);
        gewicht_label.setText(gewicht_list[this.sprache]);
        new_gewicht_label.setText(new_gewicht_list[this.sprache]);
    }

    public void age(){
        DBConnect get_age = new DBConnect("SELECT age FROM benutzer WHERE Benutzername = '" + this.name + "'", "age", 0);
        String age = get_age.getResult();
        this.age.setText(age);
    }

    public void gend(){
        DBConnect get_gender = new DBConnect("SELECT gender FROM benutzer WHERE Benutzername = '" + this.name + "'", "gender", 0);
        int gender = Integer.parseInt(get_gender.getResult());
        if (gender == 1){
            this.gender.setText("Weiblich");
        }
        else {
            this.gender.setText("Männlich");
        }
    }

    public void groes(){
        DBConnect get_groesse = new DBConnect("SELECT groesse FROM benutzer WHERE Benutzername = '" + this.name + "'", "groesse", 0);
        String groesse = get_groesse.getResult();
        this.groesse.setText(groesse);
    }
    public void gew(){
        DBConnect get_gewicht = new DBConnect("SELECT gewicht FROM benutzer WHERE Benutzername = '" + this.name + "'", "gewicht", 0);
        String gewicht = get_gewicht.getResult();
        this.gewicht.setText(gewicht);
    }
    public void musk(){
        DBConnect get_muskel = new DBConnect("SELECT muskel FROM benutzer WHERE Benutzername = '" + this.name + "'", "muskel", 0);
        int muskel = Integer.parseInt(get_muskel.getResult());
        if (muskel == 1){
            muskel_aufbau.setText("Ja");
            muskel_aufbau.setSelected(true);
        }
        else {
            muskel_aufbau.setText("Nein");
            muskel_aufbau.setSelected(false);
        }
    }
    public void dark(){
        Darkmode n = new Darkmode(name, all_buttons, all_labels);
        this.darkmode = n.isDark();
        if (!this.darkmode){
            String aus [] = {"Aus","Off"};
            dark.setText(aus[this.sprache]);
            panel1.setBackground(Color.WHITE);
            muskel_aufbau.setBackground(Color.WHITE);
            muskel_aufbau.setForeground(Color.BLACK);
        }
        else {
            String an [] = {"An","On"};
            dark.setText(an[this.sprache]);

            panel1.setBackground(Color.DARK_GRAY);

            passwort_feld.setBackground(Color.LIGHT_GRAY);

            muskel_aufbau.setBackground(Color.DARK_GRAY);
            muskel_aufbau.setForeground(Color.WHITE);

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

    public void content(){
        benutzername.setText(this.name);
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

    public void on_set_vis(JSpinner base, String get, String error_name, JLabel base_label, JButton base_but, int check){
        if (base.getValue() == null){
            error_message.setText("" + error_name + " darf nicht Leer sein!");
        }
        else {
            new DBConnect("UPDATE Benutzer SET " + get + " = '" + base.getValue() + "' WHERE Benutzername = '" + this.name + "'", " ", 1);
        }
        base.setVisible(false);
        base_label.setVisible(false);
        base_but.setVisible(false);
        if (check == 1){
            new_age_check = 0;
        }
        if (check == 2){
            new_groesse_check = 0;
        }
        if (check == 3){
            new_gewicht_check = 0;
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
            dark();
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
            on_set_vis(new_alter,"age", "Alter", new_alter_label, new_alter_best, 1);
            age();
        }
        if (e.getSource() == groesse_andern_button){
            set_vis(new_groesse_check, 2, new_groesse, new_groesse_label, new_groesse_best, groesse);
        }
        if (e.getSource() == new_groesse_best){
            on_set_vis(new_groesse,"groesse", "Grösse", new_groesse_label, new_groesse_best, 2);
            groes();
        }
        if (e.getSource() == gewicht_ander_button){
            set_vis(new_gewicht_check, 3, new_gewicht, new_gewicht_label, new_gewicht_best, gewicht);
        }
        if (e.getSource() == new_gewicht_best){
            on_set_vis(new_gewicht, "gewicht", "Gewicht", new_gewicht_label, new_gewicht_best, 3);
            gew();
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
            gend();
        }
        if (e.getSource() == pas_andern_button){
            if (this.new_pas_check == 1){
                old_pas_label.setVisible(false);
                old_pas_input.setVisible(false);
                old_pas_best.setVisible(false);
                this.new_pas_check = 0;
            }
            else {
                old_pas_label.setVisible(true);
                old_pas_input.setVisible(true);
                old_pas_best.setVisible(true);
                this.new_pas_check = 1;
            }
            error_message.setText("");
        }
        if (e.getSource() == old_pas_best){
            String pas = old_pas_input.getText();
            DBConnect check = new DBConnect("SELECT passwort FROM benutzer WHERE Benutzername = '" + this.name + "'", "passwort", 0);
            String real_pas = check.getResult();
            if (pas.equals(real_pas)){
                old_pas_label.setVisible(false);
                old_pas_input.setVisible(false);
                old_pas_best.setVisible(false);
                new_pas_label.setVisible(true);
                new_pas_best_label.setVisible(true);
                new_pas_input.setVisible(true);
                new_pas_best_input.setVisible(true);
                new_pas_best.setVisible(true);
            }
            else {
                error_message.setText("Passwort stimmt nicht!");
            }
        }
        if (e.getSource() == new_pas_best){
            String pas = new_pas_input.getText();
            String pas_best = new_pas_best_input.getText();
            error_message.setText("");
            if (!pas.equals(pas_best)){
                error_message.setText("Die Passwörter überstimmen nicht ein");
            }
            else {
                if (pas.length() < 8){
                    error_message.setText("Das Passwort ist zu kurz!");
                }
                else {
                    if (pas.length() > 90){
                        error_message.setText("Das Passwort ist zu lange!");
                    }
                    else {
                        DBConnect check = new DBConnect("SELECT passwort FROM benutzer WHERE Benutzername = '" + this.name + "'", "passwort", 0);
                        String old = check.getResult();
                        if (pas.equals(old)){
                            error_message.setText("Das neue Passwort darf nicht das gleiche wie das alte sein!");
                        }
                        else {
                            new DBConnect("UPDATE benutzer SET Passwort = '" + pas + "' WHERE Benutzername = '" + this.name + "'", "", 1);
                            new_pas_label.setVisible(false);
                            new_pas_best_label.setVisible(false);
                            new_pas_input.setVisible(false);
                            new_pas_best_input.setVisible(false);
                            new_pas_best.setVisible(false);
                        }
                    }
                }
            }
        }
        if (e.getSource() == muskel_aufbau){
            DBConnect get_muskel = new DBConnect("SELECT muskel FROM benutzer WHERE Benutzername = '" + this.name + "'", "muskel", 0);
            int muskel = Integer.parseInt(get_muskel.getResult());
            if (muskel == 1){
                new DBConnect("UPDATE benutzer SET muskel = 0 WHERE Benutzername = '" + this.name + "'", "", 1);
                musk();
            }
            else {
                new DBConnect("UPDATE benutzer SET muskel = 1 WHERE Benutzername = '" + this.name + "'", "", 1);
                musk();
            }
        }
        if (e.getSource() == sprachW){

            wahl = (String) sprachW.getSelectedItem();
            if (wahl.equals("Deutsch")){
                new DBConnect("UPDATE benutzer SET sprache = 0 WHERE Benutzername = '" + this.name + "'", "", 1);
            }
            if (wahl.equals("English")){
                new DBConnect("UPDATE benutzer SET sprache = 1 WHERE Benutzername = '" + this.name + "'", "", 1);
            }
            sprach();
        }
    }
}

