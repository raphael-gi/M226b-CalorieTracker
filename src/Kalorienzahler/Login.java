package Kalorienzahler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Date;

public class Login implements ActionListener {
    private JPanel panel1;
    private JTextField Benutzer;
    private JPasswordField Passwort;
    private JButton Login;
    private JButton Registrieren;
    private JLabel error_message;
    private final JFrame frame;

    private final java.util.Date date_now = new Date();

    public Login(Dimension size, Point loc){
        frame = new JFrame();
        new StarterPack(frame, panel1, "Login", size, loc);
        frame.setLocationRelativeTo(null);

        Login.addActionListener(this);
        Registrieren.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==Login){
            String benutzer = Benutzer.getText();
            char[] passwort = Passwort.getPassword();
            Hash p = new Hash(passwort);

            //Error Message wird reseted
            error_message.setText("");
            //Error Handling beginnt
            try{
                DBConnect log = new DBConnect("SELECT Benutzername FROM benutzer WHERE Benutzername = '"+ benutzer +"' AND Passwort = '"+ p.getHash() +"'","Benutzername",0);
                if (benutzer.isEmpty() || Arrays.toString(passwort).length() < 3){
                    error_message.setText("Füllen sie alle Felder aus!");
                }
                else {
                    if (!benutzer.equals(log.getResult())){
                        error_message.setText("Falsches Passwort oder Benutzername");
                    }
                    else {
                        //Frame wird geschlossen und Tagebuch geöffnet
                        frame.dispose();
                        Dimension frame_size = frame.getSize();
                        Point frame_loc = frame.getLocation();
                        Tagebuch n = new Tagebuch(frame_size, frame_loc, log.getResult(), date_now);
                        n.content();
                    }
                }
            }
            catch (Exception E){
                error_message.setText("Verbindung zu der Datenbank ist Fehlgeschlagen!");
            }
        }
        if (e.getSource()==Registrieren){
            //Schliesst Fenster und öffnet das Registrierungsfenster
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            new Registrierung(frame_size, frame_loc);
        }
    }
}