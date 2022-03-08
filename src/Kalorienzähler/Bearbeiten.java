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
    private String portion;
    private String mahl_name;

    private int userid;

    public Bearbeiten(Dimension size, Point loc, String benutzername, String mahl_name, String portion){
        this.benutzername = benutzername;
        this.portion = portion;
        this.mahl_name = mahl_name;

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
            System.out.println("hi");
        }
        if (e.getSource()==zurück){
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Tagebuch n = new Tagebuch(frame_size, frame_loc, this.benutzername);
            n.content();
        }
    }
}