package Kalorienzahler;

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
    private JRadioButton mann;
    private JRadioButton weib;
    SpinnerNumberModel alter_model = new SpinnerNumberModel(30, 0, 150, 1);
    private JSpinner alter;
    SpinnerNumberModel gewicht_model = new SpinnerNumberModel(75, 0.00, 1000.00, 1);
    private JSpinner gewicht;
    SpinnerNumberModel groesse_model = new SpinnerNumberModel(80, 0.00, 1000.00, 1);
    private JSpinner groesse;
    private JFrame frame;

    private Dimension size;
    private Point loc;

    public Registrierung(Dimension size, Point loc){
        frame = new JFrame("Registrierung");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(panel1);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        this.size = size;
        this.loc = loc;

        frame.setSize(this.size);
        frame.setLocation(loc);

        alter.setModel(alter_model);
        gewicht.setModel(gewicht_model);
        groesse.setModel(groesse_model);

        Registrieren.addActionListener(this);
        Login.addActionListener(this);
        mann.addActionListener(this);
        weib.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Registrieren) {
            String benutzer = Benutzer.getText();
            String passwort = Passwort.getText();
            String passwort_best = Passwort_Best.getText();
            int alter = (int)this.alter.getValue();
            int gender = 0;
            if (mann.isSelected()){
                gender = 1;
            }
            else {
                gender = 2;
            }
            double gewicht = (double) this.gewicht.getValue();
            double groesse = (double) this.groesse.getValue();

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
                                System.out.println("test");
                                RegistrierungSQL reg = new RegistrierungSQL(benutzer,passwort, gender, alter, gewicht, groesse);
                                try {
                                    reg.connect();
                                    if (reg.getResult().equals("being_used")) {
                                        error_message.setText("Benutzername wird bereits verwendet");
                                    } else {
                                        frame.dispose();
                                        Dimension frame_size = frame.getSize();
                                        Point frame_loc = frame.getLocation();
                                        new Login(frame_size, frame_loc);
                                    }
                                }
                                catch (Exception E){
                                    error_message.setText("Etwas ist schief gelaufen");
                                }
                            }
                        }
                    }
                }
            }
        }
        if (e.getSource() == Login) {
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            new Login(frame_size, frame_loc);
        }
        if (e.getSource() == mann){
            weib.setSelected(false);
        }
        if (e.getSource() == weib){
            mann.setSelected(false);
        }
    }
}