package Kalorienzähler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Mahlzeit_auswahl implements ActionListener {
    private JButton erstellen;
    private JPanel panel1;
    private JLabel mahl;
    private JButton zurück;
    private JComboBox dropname;
    private JTextField portionen;
    private JLabel anz_kalorien;
    private JLabel anz_carbs;
    private JLabel anz_protein;
    private JLabel anz_fat;
    private JLabel error_message;
    private JButton confirm;
    private JButton plus;
    private JButton minus;
    private JButton hinzufügen;
    private JFrame frame;

    private String mahlzeit;
    private String benutzername;
    private int userid;
    private String mahl_auswahl;
    private float anz_portionen;

    private Dimension size;
    private Point loc;

    public Mahlzeit_auswahl(Dimension size, Point loc, String mahlzeit, String benutzername){
        this.size = size;
        this.loc = loc;

        this.mahlzeit = mahlzeit;
        this.benutzername = benutzername;

        frame = new JFrame("Mahlzeit Auswählen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500,400));

        frame.add(panel1);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.setSize(this.size);
        frame.setLocation(this.loc);

        erstellen.addActionListener(this);
        zurück.addActionListener(this);
        dropname.addActionListener(this);
        confirm.addActionListener(this);
        plus.addActionListener(this);
        minus.addActionListener(this);
        hinzufügen.addActionListener(this);
    }

    public void content(){
            mahl.setText(this.mahlzeit);

            portionen.setText("1");
        try{
            //Verbindung um id zu erhalten
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM benutzer WHERE Benutzername = '" + this.benutzername + "'");
            while (resultSet.next()){
                int userid = resultSet.getInt("id");
                this.userid = userid;
            }
        }
        catch (Exception E){
            System.out.println("verbindung zu ID ist Fehlgeschlagen");
        }
        try{
            //Verbindung um Namen zu erhalten
            Connection new_connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
            Statement new_statement = new_connection.createStatement();
            ResultSet new_resultSet = new_statement.executeQuery("SELECT Name FROM mahlzeit WHERE ben = " + this.userid + " ORDER BY Name");
            while (new_resultSet.next()){
                //Namen werden abgerufen
                String benutzernamen = new_resultSet.getString("Name");
                //Namen werden in die Dropdown Liste eingetragen
                dropname.addItem(benutzernamen);
            }
        }
        catch (Exception E){
            System.out.println("verbindung zu Name ist Fehlgeschlagen");
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==erstellen){
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            new Mahlzeit(frame_size, frame_loc, this.mahlzeit, this.benutzername);
        }
        if (e.getSource()==zurück){
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Tagebuch n = new Tagebuch(frame_size, frame_loc, this.benutzername);
            n.content();
        }
        if (e.getSource()==dropname || e.getSource()==confirm){
            String mahl_name = String.valueOf(dropname.getSelectedItem());

            error_message.setText("");

            try {
                Connection new_connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
                Statement new_statement = new_connection.createStatement();
                ResultSet new_resultSet = new_statement.executeQuery("SELECT * FROM mahlzeit WHERE name = '" + mahl_name + "'");

                try {
                    this.anz_portionen = Float.parseFloat(portionen.getText());
                }
                catch (Exception E){
                    error_message.setText("Geben sie eine Zahl als Portion an!");
                }

                if (error_message.getText().isEmpty()){
                    while (new_resultSet.next()){
                        //Anzahl Kohlenhydrate werden abgerufen
                        String carb = new_resultSet.getString("carb");
                        int carb_int = Integer.parseInt(carb);
                        float carb_float = carb_int * this.anz_portionen;
                        double carb_double = Math.round(carb_float * 10d) / 10d;
                        String carb_final = String.valueOf(carb_double);
                        this.anz_carbs.setText(carb_final);
                        //Anzahl Protein wird abgerufen
                        String protein = new_resultSet.getString("protein");
                        int protein_int = Integer.parseInt(protein);
                        float protein_float = protein_int * this.anz_portionen;
                        double protein_double = Math.round(protein_float * 10d) / 10d;
                        String protein_final = String.valueOf(protein_double);
                        this.anz_protein.setText(protein_final);
                        //Anzahl Fett wird abgerufen
                        String fat = new_resultSet.getString("fat");
                        int fat_int = Integer.parseInt(fat);
                        float fat_float = fat_int * this.anz_portionen;
                        double fat_double = Math.round(fat_float * 10d) / 10d;
                        String fat_final = String.valueOf(fat_double);
                        this.anz_fat.setText(fat_final);
                        //Anzahl Kalorien werden abgerufen
                        double kalorien_double = (carb_double * 4) + (protein_double * 4) + (fat_double * 9);
                        long kalorien_long = Math.round(kalorien_double);
                        String kalorien_final = String.valueOf(kalorien_long);
                        this.anz_kalorien.setText(kalorien_final);
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource()==plus){
            error_message.setText("");
            try {
                this.anz_portionen = Float.parseFloat(portionen.getText());
            }
            catch (Exception E){
                error_message.setText("Geben sie eine Zahl als Portion an!");
            }
            if (error_message.getText().isEmpty()){
                if (this.anz_portionen < 1){
                    this.anz_portionen = (float)(this.anz_portionen + 0.1);
                    this.anz_portionen = (float)(Math.round(this.anz_portionen *10d) / 10d);
                }
                else {
                    this.anz_portionen++;
                }
                String str_portionen = String.valueOf(this.anz_portionen);
                portionen.setText(str_portionen);
            }
        }
        if (e.getSource()==minus){
            error_message.setText("");
            try {
                this.anz_portionen = Float.parseFloat(portionen.getText());
            }
            catch (Exception E){
                error_message.setText("Geben sie eine Zahl als Portion an!");
            }
            if (error_message.getText().isEmpty()){
                if (this.anz_portionen <= 1){
                    this.anz_portionen = (float)(this.anz_portionen - 0.1);
                    this.anz_portionen = (float)(Math.round(this.anz_portionen * 10d) / 10d);
                }
                else {
                    this.anz_portionen--;
                }
                if (this.anz_portionen >= 0){
                    String str_portionen = String.valueOf(this.anz_portionen);
                    portionen.setText(str_portionen);
                }
                else {
                    error_message.setText("Die Portion darf nicht kleiner als 0 sein!");
                }
            }
        }
        if (e.getSource() == hinzufügen){
            //Aktuelles Datum wird abgerufen
            Date date = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyy-MM-dd");

            //Ausgewählte Mahlzeit wird angeschaut
            int mahl_check = 0;
            if (this.mahlzeit.equals("Frühstück")){
                mahl_check = 1;
            }
            if (this.mahlzeit.equals("Mittagessen")){
                mahl_check = 2;
            }
            if (this.mahlzeit.equals("Abendessen")){
                mahl_check = 3;
            }
            if (this.mahlzeit.equals("Snacks")){
                mahl_check = 4;
            }
            try {
                //Verbindung um id der Mahlzeit zu erhalten
                int get_mahl_id = 0;
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT id FROM Mahlzeit WHERE Name = '" + dropname.getSelectedItem() + "'");
                while (resultSet.next()){
                    get_mahl_id = resultSet.getInt("id");
                }

                //Verbindung um id des Benutzer zu erhalten
                int get_ben_id = 0;
                Connection new_connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
                Statement new_statement = new_connection.createStatement();
                ResultSet new_resultSet = new_statement.executeQuery("SELECT id FROM Benutzer WHERE Benutzername = '" + this.benutzername + "'");
                while (new_resultSet.next()){
                    get_ben_id = new_resultSet.getInt("id");
                }

                //Data wird eingefügt
                Connection new_new_connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
                Statement new_new_statement = new_new_connection.createStatement();
                new_new_statement.execute("INSERT INTO mmm (mahl, port, kalorien, carb, protein, fat, datum, ben, mahlzeit) VALUES (" + get_mahl_id + ", " + portionen.getText() + ", " + this.anz_kalorien.getText() + ", " + this.anz_carbs.getText() + ", " + this.anz_protein.getText() + ", " + this.anz_fat.getText() + ", '" + ft.format(date) + "', " + get_ben_id + ", " + mahl_check + ")");

                frame.dispose();
                Dimension frame_size = frame.getSize();
                Point frame_loc = frame.getLocation();
                Tagebuch n = new Tagebuch(frame_size, frame_loc, this.benutzername);
                n.content();
            }
            catch (Exception E){
                System.out.println("ups...");
            }
        }
    }
}