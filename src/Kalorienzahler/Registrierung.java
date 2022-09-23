package Kalorienzahler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Registrierung extends Global implements ActionListener {
    private JPanel panel;
    private JTextField Benutzer;
    private JPasswordField Passwort;
    private JPasswordField Passwort_Best;
    private JButton Login;
    private JButton Registrieren;
    private JLabel error_message;
    private JRadioButton mann;
    private JRadioButton weib;
    SpinnerNumberModel alter_model = new SpinnerNumberModel(30, 0, 150, 1);
    private JSpinner alter;
    SpinnerNumberModel gewicht_model = new SpinnerNumberModel(75, 0.00, 1000.00, 1);
    private JSpinner gewicht;
    SpinnerNumberModel groesse_model = new SpinnerNumberModel(180, 0.00, 1000.00, 1);
    private JSpinner groesse;

    public Registrierung() {
        newPanel(panel);

        alter.setModel(alter_model);
        gewicht.setModel(gewicht_model);
        groesse.setModel(groesse_model);

        Registrieren.addActionListener(this);
        Login.addActionListener(this);
        mann.addActionListener(this);
        weib.addActionListener(this);
    }

    public void sendLogin() {
        frame.remove(panel);
        new Login();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Registrieren) {
            String benutzer = Benutzer.getText();
            char[] passwort = Passwort.getPassword();
            char[] passwort_best = Passwort_Best.getPassword();

            int alter = (int)this.alter.getValue();
            int gender;
            if (mann.isSelected()){
                gender = 1;
            }
            else {
                gender = 2;
            }
            double gewicht = (double) this.gewicht.getValue();
            double groesse = (double) this.groesse.getValue();
            //Error handling
            error_message.setText("");
            if (benutzer.isEmpty() || passwort == null || passwort_best == null){
                error_message.setText("Füllen sie alle Felder aus!");
            }
            else {
                if (!Arrays.equals(passwort, passwort_best)){
                    error_message.setText("Die Passwörter sind nicht gleich!");
                }
                else {
                    if (passwort.length < 8){
                        error_message.setText("Passwort zu kurz!");
                    }
                    else {
                        if (alter < 0 || alter > 150){
                            error_message.setText("Geben sie ihr richtiges Alter an!");
                        }
                        else {
                            if (!mann.isSelected() && !weib.isSelected()){
                                error_message.setText("Wählen eines der Gender aus!");
                            }
                            else {
                                DBConnect check = new DBConnect("SELECT Benutzername FROM benutzer WHERE Benutzername = '" + benutzer + "'");
                                check.setSql_get("Benutzername");
                                check.con();
                                if (check.getResult() != null) {
                                    error_message.setText("Benutzername wird bereits verwendet");
                                } else {
                                    Hash p = new Hash(passwort);
                                    new DBConnect("INSERT INTO benutzer (Benutzername, Passwort, gender, age, gewicht, groesse) VALUES ('" + benutzer + "', '" + p.getHash() + "', " + gender + ", " + alter + ", " + gewicht + ", " + groesse + ")");
                                    sendLogin();
                                }
                            }
                        }
                    }
                }
            }
        }
        if (e.getSource() == Login) {
            sendLogin();
        }
        if (e.getSource() == mann){
            if (weib.isSelected()){
                weib.setSelected(false);
            }
            else {
                mann.setSelected(true);
            }
        }
        if (e.getSource() == weib){
            if (mann.isSelected()){
                mann.setSelected(false);
            }
            else {
                weib.setSelected(true);
            }
        }
    }
}