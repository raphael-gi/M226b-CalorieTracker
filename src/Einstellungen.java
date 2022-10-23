import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Arrays;

public class Einstellungen extends Global implements ActionListener {
    private JPanel panel;
    private JLabel benutzername;
    private JButton dark;
    private JButton zuruck;
    private JButton loeschen;
    private JPasswordField passwort_feld;
    private JLabel pass_eingeben;
    private JButton bestaetigen;
    private JLabel error_message;
    private JButton logout;
    private JLabel gender;
    private JLabel age;
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
    private JButton groesse_andern_button;
    private JLabel new_groesse_label;
    private JButton new_groesse_best;
    SpinnerNumberModel groesse_model = new SpinnerNumberModel(180, 0.00, 1000.00, 1);
    private JSpinner new_groesse;
    private JButton gewicht_ander_button;
    SpinnerNumberModel gewicht_model = new SpinnerNumberModel(80, 0.00, 1000.00, 1);
    private JSpinner new_gewicht;
    private JLabel gewicht;
    private JLabel new_gewicht_label;
    private JButton new_gewicht_best;
    private JLabel einstellungen_label;
    private JButton pas_andern_button;
    private JLabel old_pas_label;
    private JPasswordField old_pas_input;
    private JButton old_pas_best;
    private JPasswordField new_pas_input;
    private JLabel new_pas_label;
    private JPasswordField new_pas_best_input;
    private JLabel new_pas_best_label;
    private JButton new_pas_best;
    private JCheckBox muskel_aufbau;
    private JRadioButton gew_zuh;
    private JRadioButton gew_halt;
    private JRadioButton gew_ver;

    private int new_loeschen_check;
    private int new_name_check;
    private int new_age_check;
    private int new_groesse_check;
    private int new_gewicht_check;
    private int new_pas_check;
    private int new_pas_check2;

    private final JButton[] all_buttons = {dark, zuruck, loeschen, bestaetigen, logout, name_andern_button, gender_andern_button, age_andern_button, new_name_best, new_alter_best, groesse_andern_button, new_groesse_best, gewicht_ander_button, new_gewicht_best, pas_andern_button, old_pas_best, new_pas_best};

    JButton[] un_vis_but = {new_name_best, new_alter_best, new_groesse_best, new_gewicht_best, old_pas_best, new_pas_best, new_pas_best, bestaetigen};
    JLabel[] un_vis_lab = {new_name_label, new_alter_label, new_groesse_label, new_gewicht_label, old_pas_label, new_pas_label, new_pas_best_label, pass_eingeben};
    JSpinner[] un_vis_spin = {new_alter, new_groesse, new_gewicht};
    JPasswordField[] un_vis_pas = {old_pas_input, new_pas_input, new_pas_best_input, passwort_feld};
    JRadioButton[] radios = {gew_halt, gew_zuh, gew_ver};

    public Einstellungen(){

        newPanel(panel);

        Font label_font = new Font("Arial", Font.BOLD, 15);
        einstellungen_label.setFont(label_font);

        new_alter.setModel(alter_model);
        new_groesse.setModel(groesse_model);
        new_gewicht.setModel(gewicht_model);

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
        //Error Message wird zurückgesetzt
        error_message.setText("");

        //Alles erhält einen Action Listener
        for (JButton but : all_buttons){
            but.addActionListener(this);
        }
        muskel_aufbau.addActionListener(this);
        gew_zuh.addActionListener(this);
        gew_halt.addActionListener(this);
        gew_ver.addActionListener(this);

        //Methoden werden ausgeführt
        ben();
        gend();
        age();
        groes();
        gew();
        musk();
        bulk();
        //dark();
    }
    public void ben(){
        benutzername.setText(username);
    }
    public void gend(){
        int gender = Global.gender;
        String[] gend;
        if (gender == 1){
            gend = new String[]{"Weiblich", "Feminine"};
        }
        else {
            gend = new String[]{"Männlich", "Masculine"};
        }
        this.gender.setText(gend[sprache]);
    }
    public void age() {
        this.age.setText(String.valueOf(Global.age));
    }
    public void groes() {
        this.groesse.setText(String.valueOf(Global.groesse));
    }
    public void gew() {
        this.gewicht.setText(String.valueOf(Global.gewicht));
    }
    public void musk() {
        int muskel = Global.muskel;
        if (muskel == 1){
            muskel_aufbau.setText("Ja");
            muskel_aufbau.setSelected(true);
        }
        else {
            muskel_aufbau.setText("Nein");
            muskel_aufbau.setSelected(false);
        }
    }
    public void bulk(){
        int bulk = Global.bulk;
        if (bulk == 0){
            gew_halt.setSelected(true);
        }
        if (bulk == 1){
            gew_zuh.setSelected(true);
        }
        if (bulk == 2){
            gew_ver.setSelected(true);
        }
    }
    public void dark(){

    }

    public void upd(String what, String to){
        try {
            statement.execute("UPDATE Benutzer SET " + what + " = '" + to + "' WHERE id = " + id + "");
        } catch (SQLException e) {
            e.printStackTrace();
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

    public void on_set_vis(JSpinner base, String get, String error_name, JLabel base_label, JButton base_but, int check) {
        if (base.getValue() == null){
            error_message.setText("" + error_name + " darf nicht Leer sein!");
        }
        else {
            upd(get, String.valueOf(base.getValue()));
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

    public void sendLogin() {
        frame.remove(panel);
        new Login();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dark){
            if (darkmode) upd("dark", "0");
            if (!darkmode) upd("dark", "1");

            darkmode = !darkmode;
            dark();
        }
        if (e.getSource() == zuruck){
            frame.remove(panel);
            Tagebuch tagebuch = new Tagebuch();
            tagebuch.content();
        }
        if (e.getSource() == loeschen){
            if (new_loeschen_check == 1){
                pass_eingeben.setVisible(false);
                passwort_feld.setVisible(false);
                bestaetigen.setVisible(false);
                passwort_feld.setText("");
                error_message.setText("");
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
            char[] passwort = passwort_feld.getPassword();
            Hash p = new Hash(passwort);

            if (!p.getHash().equals(password)){
                error_message.setText("Passwort stimmt nicht!");
            }
            else {
                new DBConnect("DELETE FROM benutzer WHERE id = " + id + "");
                sendLogin();
            }
        }
        if (e.getSource() == logout){
            sendLogin();
        }
        if (e.getSource() == name_andern_button){
            if (this.new_name_check == 1){
                new_name_label.setVisible(false);
                new_name.setVisible(false);
                new_name_best.setVisible(false);
                new_name.setText("");
                error_message.setText("");
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
                return;
            }
            if (new_name.getText().equals(username)){
                error_message.setText("Benutzername darf nicht gleich wie der Alte sein!");
                return;
            }
            DBConnect check = new DBConnect("SELECT Benutzername FROM Benutzer WHERE Benutzername = '" + new_name.getText() + "'");
            check.setSql_get("Benutzername");
            check.con();
            if (check.getResult() != null){
                error_message.setText("Benutzername wird bereits verwendet!");
                return;
            }

            upd("Benutzername", new_name.getText());
            username = new_name.getText();
            new_name.setVisible(false);
            new_name_best.setVisible(false);
            new_name_label.setVisible(false);
            new_name_check = 0;
            new_name.setText("");
            error_message.setText("");
            ben();
        }
        if (e.getSource() == age_andern_button){
            set_vis(new_age_check, 1, new_alter, new_alter_label, new_alter_best, age);
        }
        if (e.getSource() == new_alter_best){
            on_set_vis(new_alter,"age", "Alter", new_alter_label, new_alter_best, 1);
            Global.age = (int) new_alter.getValue();
            age();
        }
        if (e.getSource() == groesse_andern_button){
            set_vis(new_groesse_check, 2, new_groesse, new_groesse_label, new_groesse_best, groesse);
        }
        if (e.getSource() == new_groesse_best){
            on_set_vis(new_groesse,"groesse", "Grösse", new_groesse_label, new_groesse_best, 2);
            Global.groesse = (double) new_groesse.getValue();
            groes();
        }
        if (e.getSource() == gewicht_ander_button){
            set_vis(new_gewicht_check, 3, new_gewicht, new_gewicht_label, new_gewicht_best, gewicht);
        }
        if (e.getSource() == new_gewicht_best){
            on_set_vis(new_gewicht, "gewicht", "Gewicht", new_gewicht_label, new_gewicht_best, 3);
            Global.gewicht = (double) new_gewicht.getValue();
            gew();
        }
        if (e.getSource() == gender_andern_button){
            int gender = Global.gender;
            if (gender == 1){
                upd("gender", "2");
                gender = 2;
            }
            else {
                upd("gender", "1");
                gender = 1;
            }
            Global.gender = gender;
            gend();
        }
        if (e.getSource() == pas_andern_button){
            if (this.new_pas_check == 1){
                old_pas_label.setVisible(false);
                old_pas_input.setVisible(false);
                old_pas_best.setVisible(false);
                old_pas_input.setText("");
                this.new_pas_check = 0;
            }
            else {
                old_pas_label.setVisible(true);
                old_pas_input.setVisible(true);
                old_pas_best.setVisible(true);
                this.new_pas_check = 1;
            }
            if (this.new_pas_check2 == 1){
                new_pas_label.setVisible(false);
                new_pas_best_label.setVisible(false);
                new_pas_input.setVisible(false);
                new_pas_best_input.setVisible(false);
                new_pas_best.setVisible(false);
            }
            error_message.setText("");
        }
        if (e.getSource() == old_pas_best){
            char[] pas = old_pas_input.getPassword();
            Hash hash = new Hash(pas);

            if (password.equals(hash.getHash())){
                //Felder werden unsichtbar gemacht
                old_pas_label.setVisible(false);
                old_pas_input.setVisible(false);
                old_pas_best.setVisible(false);
                //Felder werden sichtbar gemacht
                new_pas_label.setVisible(true);
                new_pas_best_label.setVisible(true);
                new_pas_input.setVisible(true);
                new_pas_best_input.setVisible(true);
                new_pas_best.setVisible(true);
                //Input Felder werden reseted
                old_pas_input.setText("");
                error_message.setText("");
                new_pas_check2 = 1;
            }
            else {
                error_message.setText("Passwort stimmt nicht!");
            }
        }
        if (e.getSource() == new_pas_best){
            //Variabeln werden abgerufen
            char[] pas = new_pas_input.getPassword();
            char[] pas_best = new_pas_best_input.getPassword();
            //Passwort wird gehashed
            Hash hash = new Hash(pas);

            error_message.setText("");
            if (!Arrays.equals(pas, pas_best)){
                error_message.setText("Die Passwörter überstimmen nicht ein");
                return;
            }
            if (pas.length < 8){
                error_message.setText("Das Passwort ist zu kurz!");
                return;
            }
            if (pas.length > 90){
                error_message.setText("Das Passwort ist zu lange!");
                return;
            }

            if (hash.getHash().equals(password)){
                error_message.setText("Das neue Passwort darf nicht das gleiche wie das alte sein!");
                return;
            }

            upd("Passwort", hash.getHash());
            password = hash.getHash();
            //Felder werden unsichtbar gemacht
            new_pas_label.setVisible(false);
            new_pas_best_label.setVisible(false);
            new_pas_input.setVisible(false);
            new_pas_best_input.setVisible(false);
            new_pas_best.setVisible(false);
            //Input Felder werden reseted
            new_pas_input.setText("");
            new_pas_best_input.setText("");
            new_pas_check = 0;
            new_pas_check2 = 0;
        }
        if (e.getSource() == muskel_aufbau){
            int muskel = Global.muskel;
            if (muskel == 1){
                upd("muskel", "0");
                muskel = 2;
            }
            else {
                upd("muskel", "1");
                muskel = 1;
            }
            Global.muskel = muskel;
            musk();
        }
        if (e.getSource() == gew_halt){
            upd("bulk", "0");
            gew_zuh.setSelected(false);
            gew_ver.setSelected(false);
            bulk = 0;
            bulk();
        }
        if (e.getSource() == gew_zuh){
            upd("bulk", "1");
            gew_halt.setSelected(false);
            gew_ver.setSelected(false);
            bulk = 1;
            bulk();
        }
        if (e.getSource() == gew_ver){
            upd("bulk", "2");
            gew_halt.setSelected(false);
            gew_zuh.setSelected(false);
            bulk = 2;
            bulk();
        }
    }
}