package Kalorienzähler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Registrierung implements ActionListener {
    private JPanel panel1;
    private JTextField Benutzer;
    private JPasswordField Passwort;
    private JPasswordField Passwort_Best;
    private JButton Login;
    private JButton Registrieren;
    private JLabel error_message;
    private JFrame frame;

    public Registrierung(){
        frame = new JFrame("Registrierung");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500,400));

        frame.add(panel1);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Registrieren.addActionListener(this);
        Login.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==Registrieren) {
            String benutzer = Benutzer.getText();
            String passwort = Passwort.getText();
            String passwort_best = Passwort_Best.getText();

            error_message.setText("");
            if (benutzer.isEmpty() || passwort.isEmpty() || passwort_best.isEmpty()){
                error_message.setText("Füllen sie alle Felder aus!");
            }
            else {
                if (!passwort.equals(passwort_best)){
                    error_message.setText("Die Passwörter sind nicht gleich!");
                }
                else {
                    if (passwort.length() < 8){
                        error_message.setText("Passwort zu kurz");
                    }
                    else {
                        RegistrierungSQL reg = new RegistrierungSQL(benutzer,passwort);
                        try {
                            reg.connect();
                            if (reg.getResult().equals("being_used")) {
                                error_message.setText("Benutzername wird bereits verwendet");
                            } else {
                                frame.dispose();
                                new Login();
                            }
                        }
                        catch (Exception E){
                            error_message.setText("Etwas ist schief gelaufen");
                        }
                    }
                }
            }
        }
        if (e.getSource()==Login) {
            frame.dispose();
            new Login();
        }
    }
}