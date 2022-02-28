package Kalorienzähler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login implements ActionListener {
    private JPanel panel1;
    private JTextField Benutzer;
    private JPasswordField Passwort;
    private JButton Login;
    private JButton Registrieren;
    private JLabel error_message;
    private JFrame frame;

    public Login(){
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500,400));

        frame.add(panel1);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

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

            if (log.getResult() == null){
                System.out.println(log.getResult());
                error_message.setText("Falsches Passwort oder Benutzername");
            }
            else {
                frame.dispose();
                new Tagebuch(log.getResult());
            }
            if (benutzer.isEmpty() || passwort.isEmpty()){
                error_message.setText("Füllen sie alle Felder aus!");
            }
        }
        if (e.getSource()==Registrieren){
            frame.dispose();
            new Registrierung();
        }
    }
}