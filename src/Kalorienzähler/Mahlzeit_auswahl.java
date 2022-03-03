package Kalorienzähler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Mahlzeit_auswahl implements ActionListener {
    private JButton erstellen;
    private JPanel panel1;
    private JLabel mahl;
    private JButton zurück;
    private JComboBox dropname;
    private JTextField portionen_text;
    private JFrame frame;

    private String mahlzeit;
    private String benutzername;
    private int userid;

    public Mahlzeit_auswahl(String mahlzeit, String benutzername){
        this.mahlzeit = mahlzeit;
        this.benutzername = benutzername;

        frame = new JFrame("Mahlzeit Auswählen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500,400));

        frame.add(panel1);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        erstellen.addActionListener(this);
        zurück.addActionListener(this);
    }

    public void content(){
            mahl.setText(this.mahlzeit);

            portionen_text.setText("1");
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
        }
        catch (Exception E){
            System.out.println("verbindung zu Name ist Fehlgeschlagen");
        }
        Date date = new Date();
        System.out.printf("%1$tY %1$tm %1$td", date);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==erstellen){
            frame.dispose();
            new Mahlzeit(this.mahlzeit, this.benutzername);
        }
        if (e.getSource()==zurück){
            frame.dispose();
            new Tagebuch(this.benutzername);
        }
    }
}