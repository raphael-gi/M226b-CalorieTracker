package Kalorienzähler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Mahlzeit implements ActionListener {
    private JFrame frame;
    private JPanel panel1;
    private JButton Erstellen;
    private JTextField Name;
    private JTextField Kohlenhydrate;
    private JTextField Protein;
    private JTextField Fett;
    private JLabel error_message;

    private String mahlzeit;
    private String benutzername;

    public Mahlzeit(String mahlzeit, String benutzername){

        this.mahlzeit = mahlzeit;
        this.benutzername = benutzername;

        frame = new JFrame("Mahlzeit Erstellen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500,400));

        frame.add(panel1);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Erstellen.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==Erstellen){
            String name = Name.getText();
            String kohlenhydrate = Kohlenhydrate.getText();
            String protein = Protein.getText();
            String fett = Fett.getText();

            error_message.setText("");

            if (name.isEmpty() || kohlenhydrate.isEmpty() || protein.isEmpty() || fett.isEmpty()){
                error_message.setText("Füllen sie alle Felder aus!");
            }
            else {
                if (name.length() > 90){
                    error_message.setText("Der Name ist zu lange!");
                }
                else {
                    DBConnect check_name = new DBConnect("SELECT Name FROM mahlzeit WHERE Name = '" + name + "'", "Name", 0);
                    check_name.con();
                    if (check_name.getResult() != null){
                        error_message.setText("Mahlzeit exestiert bereits. Wählen sie einen anderen Namen!");
                    }
                    else {
                        try{
                            int int_kohlenhydrate = Integer.parseInt(kohlenhydrate);
                            int int_protein = Integer.parseInt(protein);
                            int int_fett = Integer.parseInt(fett);
                            int kalorien = int_kohlenhydrate*4 + int_protein*4 + int_fett*9;

                            DBConnect get_id = new DBConnect("SELECT id FROM benutzer WHERE Benutzername = '" + this.benutzername + "'","id",0);
                            get_id.con();
                            int userid = Integer.parseInt(get_id.getResult());

                            DBConnect make_meal = new DBConnect("INSERT INTO mahlzeit (Name, kalorien, carb, protein, fat, ben) VALUES ('" + name +"', '" + kalorien +"', '" + int_kohlenhydrate + "', '" + int_protein + "', '" + int_fett + "', '" + userid + "')", "Benutzername", 1);
                            make_meal.con();

                            frame.dispose();
                            Mahlzeit_auswahl mahl = new Mahlzeit_auswahl(this.mahlzeit, this.benutzername);
                            mahl.content();
                        }
                        catch (Exception E){
                            error_message.setText("Geben sie als Wert Zahlen an!");
                        }
                    }
                }
            }
        }
    }
}