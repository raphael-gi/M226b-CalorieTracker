package Kalorienzähler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Bearbeiten implements ActionListener {
    private JPanel panel1;
    private JButton bearbeiten;
    private JComboBox dropname;
    private JButton plus;
    private JButton minus;
    private JButton confirm;
    private JLabel anz_kalorien;
    private JLabel anz_carbs;
    private JLabel anz_protein;
    private JLabel anz_fat;
    private JLabel error_message;
    private JTextField portionen;
    private JButton zurück;
    private JFrame frame;

    private Dimension size;
    private Point loc;

    private String benutzername;
    private String mahl_name;
    private String portion;
    private int mmm_id;

    private int userid;
    private float anz_portionen;

    public Bearbeiten(Dimension size, Point loc, String benutzername, String mahl_name, String portion, int mmm_id){
        this.benutzername = benutzername;
        this.portion = portion;
        this.mahl_name = mahl_name;
        this.mmm_id = mmm_id;

        frame = new JFrame("Bearbeiten");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(panel1);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        this.size = size;
        this.loc = loc;

        frame.setSize(this.size);
        frame.setLocation(loc);

        zurück.addActionListener(this);
        dropname.addActionListener(this);
        confirm.addActionListener(this);
        plus.addActionListener(this);
        minus.addActionListener(this);
        bearbeiten.addActionListener(this);
        zurück.addActionListener(this);
    }
    public void content(){
        portionen.setText(portion);
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
                String benutzernamen = new_resultSet.getString("Name");
                dropname.addItem(benutzernamen);
            }
            dropname.setSelectedItem(this.mahl_name);
        }
        catch (Exception E){
            System.out.println("verbindung zu Name ist Fehlgeschlagen");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bearbeiten){
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
        if (e.getSource() == bearbeiten){
            String drop_selected = (String)dropname.getSelectedItem();
            String portion = portionen.getText();
            int kalorien = Integer.parseInt(anz_kalorien.getText());
            float carb = Float.parseFloat(anz_carbs.getText());
            float protein = Float.parseFloat(anz_protein.getText());
            float fat = Float.parseFloat(anz_fat.getText());

            DBConnect get_ben = new DBConnect("SELECT id FROM benutzer WHERE Benutzername = '" + this.benutzername + "'", "id", 0);
            get_ben.con();
            String ben_id = get_ben.getResult();

            DBConnect get_mahl = new DBConnect("SELECT id FROM mahlzeit WHERE Name = '" + drop_selected + "' AND ben = " + ben_id + "", "id", 0);
            get_mahl.con();
            String mahl = get_mahl.getResult();

            DBConnect update = new DBConnect("UPDATE mmm SET mahl = " + mahl + ", port = " + portion + ", kalorien = " + kalorien + ", carb = " + carb + ", protein = " + protein + ", fat = " + fat + " WHERE id = " + this.mmm_id + "", " ", 1);
            update.con();

            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Tagebuch n = new Tagebuch(frame_size, frame_loc, this.benutzername);
            n.content();
        }
    }
}