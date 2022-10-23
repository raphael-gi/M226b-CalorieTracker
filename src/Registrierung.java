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
            char[] passwortBest = Passwort_Best.getPassword();

            int alter = (int)this.alter.getValue();
            int gender = 1;
            if (weib.isSelected()) gender = 2;
            double gewicht = (double) this.gewicht.getValue();
            double groesse = (double) this.groesse.getValue();
            //Error handling
            error_message.setText("");
            if (benutzer.isEmpty() || passwort == null || passwortBest == null){
                error_message.setText("Füllen sie alle Felder aus!");
                return;
            }
            if (!Arrays.equals(passwort, passwortBest)){
                error_message.setText("Die Passwörter sind nicht gleich!");
                return;
            }
            if (passwort.length < 8){
                error_message.setText("Passwort zu kurz!");
                return;
            }
            if (alter < 0 || alter > 150){
                error_message.setText("Geben sie ihr richtiges Alter an!");
                return;
            }
            if (!mann.isSelected() && !weib.isSelected()){
                error_message.setText("Wählen eines der Gender aus!");
                return;
            }
            DBConnect check = new DBConnect("SELECT Benutzername FROM benutzer WHERE Benutzername = '" + benutzer + "'");
            check.setSql_get("Benutzername");
            check.con();
            if (check.getResult() != null) {
                error_message.setText("Benutzername wird bereits verwendet");
                return;
            }
            Hash p = new Hash(passwort);
            DBConnect dbConnect = new DBConnect("INSERT INTO benutzer (Benutzername, Passwort, gender, age, gewicht, groesse) VALUES ('" + benutzer + "', '" + p.getHash() + "', " + gender + ", " + alter + ", " + gewicht + ", " + groesse + ")");
            dbConnect.con();
            sendLogin();
        }
        if (e.getSource() == Login) {
            sendLogin();
        }
        if (e.getSource() == mann){
            weib.setSelected(false);
            mann.setSelected(true);
        }
        if (e.getSource() == weib){
            mann.setSelected(false);
            weib.setSelected(true);
        }
    }
}