package Kalorienzähler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Login implements ActionListener {
    private JPanel panel1;
    private JTextField Benutzer;
    private JPasswordField Passwort;
    private JButton Login;
    private JButton Registrieren;
    private JLabel error_message;
    private JFrame frame;

    private Dimension size;
    private Point loc;

    public Login(Dimension size, Point loc){
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(panel1);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        this.size = size;
        this.loc = loc;

        frame.setSize(this.size);
        frame.setLocation(loc);

        Login.addActionListener(this);
        Registrieren.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==Login){
            String benutzer = Benutzer.getText();
            String passwort = Passwort.getText();

            error_message.setText("");
            LoginSQL log = new LoginSQL(benutzer,passwort);
            try{
                log.connect();
            }
            catch (Exception E){
                error_message.setText("Etwas ist schief gelaufen");
            }

            if (benutzer.isEmpty() || passwort.isEmpty()){
                error_message.setText("Füllen sie alle Felder aus!");
            }
            else {
                if (log.getResult() == null){
                    error_message.setText("Falsches Passwort oder Benutzername");
                }
                else {
                    frame.dispose();
                    Dimension frame_size = frame.getSize();
                    Point frame_loc = frame.getLocation();
                    Tagebuch n = new Tagebuch(frame_size, frame_loc, log.getResult());
                    n.content();
                }
            }
        }
        if (e.getSource()==Registrieren){
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            new Registrierung(frame_size, frame_loc);
        }
    }
}