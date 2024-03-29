import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Date;

public class Login extends Global implements ActionListener {
    private JPanel panel;
    private JTextField Benutzer;
    private JPasswordField Passwort;
    private JButton Login;
    private JButton Registrieren;
    private JLabel error_message;

    private final Date date = new Date();

    public Login() {
        newPanel(panel);

        Login.addActionListener(this);
        Registrieren.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Login) {
            String benutzer = Benutzer.getText();
            char[] passwort = Passwort.getPassword();
            Hash hash = new Hash(passwort);

            error_message.setText("");
            if (benutzer.isEmpty() || Arrays.toString(passwort).length() < 3){
                error_message.setText("Füllen sie alle Felder aus!");
                return;
            }

            String username, password;
            username = password = null;
            int id, gender, age, muskel, bulk, sprache;
            id = gender = age = muskel = bulk = sprache = 0;
            double gewicht, groesse;
            gewicht = groesse = 0;

            try{
                resultSet = statement.executeQuery("SELECT id, Benutzername, gender, age, gewicht, groesse, muskel, bulk, sprache, Passwort FROM benutzer WHERE Benutzername = '" + benutzer + "' AND Passwort = '" + hash.getHash() + "'");
                while (resultSet.next()) {
                    id = resultSet.getInt("id");
                    username = resultSet.getString("Benutzername");
                    gender = resultSet.getInt("gender");
                    age = resultSet.getInt("age");
                    gewicht = resultSet.getDouble("gewicht");
                    groesse = resultSet.getDouble("groesse");
                    muskel = resultSet.getInt("muskel");
                    bulk = resultSet.getInt("bulk");
                    sprache = resultSet.getInt("sprache");
                    password = resultSet.getString("Passwort");
                }
                if (username == null || password == null) {
                    error_message.setText("Falsches Passwort oder Benutzername");
                    return;
                }
            }
            catch (Exception E) {
                    error_message.setText("Verbindung zu der Datenbank ist Fehlgeschlagen!");
                    return;
            }

            Global.id = id;
            Global.username = username;
            Global.date = date;
            Global.gender = gender;
            Global.age = age;
            Global.gewicht = gewicht;
            Global.groesse = groesse;
            Global.muskel = muskel;
            Global.bulk = bulk;
            Global.sprache = sprache;
            Global.password = password;
            frame.remove(panel);
            Tagebuch tagebuch = new Tagebuch();
            tagebuch.content();
        }

        if (e.getSource() == Registrieren) {
            frame.remove(panel);
            new Registrierung();
        }
    }
}