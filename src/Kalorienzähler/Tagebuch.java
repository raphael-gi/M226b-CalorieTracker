package Kalorienzähler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Tagebuch implements ActionListener {
    private JPanel panel1;
    private JButton Frühstück;
    private JButton Mittagessen;
    private JButton Abendessen;
    private JButton Snacks;
    private JLabel kalorien_count;
    private JFrame frame;

    private String benutzername;

    public Tagebuch(String benutzername){
        this.benutzername = benutzername;

        frame = new JFrame("Tagebuch");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500,400));

        frame.add(panel1);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Frühstück.addActionListener(this);
        Mittagessen.addActionListener(this);
        Abendessen.addActionListener(this);
        Snacks.addActionListener(this);
    }
    public void content(){
        System.out.println("test");
        java.util.Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyy-MM-dd");

        //Verbindung um id des Benutzer zu erhalten
        int get_ben_id = 0;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id FROM Benutzer WHERE Benutzername = '" + this.benutzername + "'");
            while (resultSet.next()){
                get_ben_id = resultSet.getInt("id");
            }

            //Verbindung um id des Benutzer zu erhalten
            //int get_ben_id = 0;
            Connection new_connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
            Statement new_statement = new_connection.createStatement();
            ResultSet new_resultSet = new_statement.executeQuery("SELECT * FROM mmm WHERE ben = '" + get_ben_id + "' AND datum = '" + ft.format(date) + "'");
            ArrayList<Integer> kalories = new ArrayList<>();
            while (new_resultSet.next()){
                int kalorien = new_resultSet.getInt("kalorien");
                kalories.add(kalorien);
            }
            System.out.println(kalories);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Frühstück){
            frame.dispose();
            Mahlzeit_auswahl mahl = new Mahlzeit_auswahl("Frühstück", this.benutzername);
            mahl.content();
        }
        if (e.getSource() == Mittagessen){
            frame.dispose();
            Mahlzeit_auswahl mahl = new Mahlzeit_auswahl("Mittagessen", this.benutzername);
            mahl.content();
        }
        if (e.getSource() == Abendessen){
            frame.dispose();
            Mahlzeit_auswahl mahl = new Mahlzeit_auswahl("Abendessen", this.benutzername);
            mahl.content();
        }
        if (e.getSource() == Snacks){
            frame.dispose();
            Mahlzeit_auswahl mahl = new Mahlzeit_auswahl("Snacks", this.benutzername);
            mahl.content();
        }
    }
}