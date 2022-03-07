package Kalorienzähler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    private JList fruhstuck_list;
    DefaultListModel fruhstuck = new DefaultListModel();
    private JList mittagessen_list;
    DefaultListModel mittagessen = new DefaultListModel();
    private JList abendessen_list;
    DefaultListModel abendessen = new DefaultListModel();
    private JList snacks_list;
    DefaultListModel snacks = new DefaultListModel();
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
        fruhstuck_list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                if (e.getClickCount() == 2){
                    System.out.println(fruhstuck_list.getSelectedValue());
                }
            }
        });
    }
    public void content(){
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
            ResultSet new_resultSet = statement.executeQuery("SELECT * FROM mmm WHERE ben = '" + get_ben_id + "' AND datum = '" + ft.format(date) + "'");
            ArrayList<Integer> kalories = new ArrayList<>();
            while (new_resultSet.next()){
                int kalorien = new_resultSet.getInt("kalorien");
                kalories.add(kalorien);
            }
            int anz_kalorien = 0;
            for (int i = 0; kalories.size() > i; i++){
                anz_kalorien = anz_kalorien + kalories.get(i);
            }
            kalorien_count.setText("Kalorien: " + anz_kalorien);


            ResultSet fruh_resultSet = statement.executeQuery("SELECT * FROM mahlzeit,mmm WHERE mmm.ben = " + get_ben_id + " AND mahlzeit.ben = " + get_ben_id + " AND mmm.mahl = mahlzeit.id AND mmm.datum = '" + ft.format(date) + "' AND mmm.mahlzeit = 1");
            ArrayList<String> fruh_names = new ArrayList<>();
            while (fruh_resultSet.next()){
                String mahl_name = fruh_resultSet.getString("Name");
                fruh_names.add(mahl_name);
            }
            for (int i = 0; fruh_names.size() > i; i++){
                fruhstuck.addElement(fruh_names.get(i));
            }
            fruhstuck_list.setModel(fruhstuck);


            ResultSet mit_resultSet = statement.executeQuery("SELECT * FROM mahlzeit,mmm WHERE mmm.ben = " + get_ben_id + " AND mahlzeit.ben = " + get_ben_id + " AND mmm.mahl = mahlzeit.id AND mmm.datum = '" + ft.format(date) + "' AND mmm.mahlzeit = 2");
            ArrayList<String> mit_names = new ArrayList<>();
            while (mit_resultSet.next()){
                String mahl_name = mit_resultSet.getString("Name");
                mit_names.add(mahl_name);
            }
            for (int i = 0; mit_names.size() > i; i++){
                mittagessen.addElement(mit_names.get(i));
            }
            mittagessen_list.setModel(mittagessen);


            ResultSet abend_resultSet = statement.executeQuery("SELECT * FROM mahlzeit,mmm WHERE mmm.ben = " + get_ben_id + " AND mahlzeit.ben = " + get_ben_id + " AND mmm.mahl = mahlzeit.id AND mmm.datum = '" + ft.format(date) + "' AND mmm.mahlzeit = 3");
            ArrayList<String> abend_names = new ArrayList<>();
            while (abend_resultSet.next()){
                String mahl_name = abend_resultSet.getString("Name");
                abend_names.add(mahl_name);
            }
            for (int i = 0; abend_names.size() > i; i++){
                abendessen.addElement(abend_names.get(i));
            }
            abendessen_list.setModel(abendessen);


            ResultSet snack_resultSet = statement.executeQuery("SELECT * FROM mahlzeit,mmm WHERE mmm.ben = " + get_ben_id + " AND mahlzeit.ben = " + get_ben_id + " AND mmm.mahl = mahlzeit.id AND mmm.datum = '" + ft.format(date) + "' AND mmm.mahlzeit = 4");
            ArrayList<String> snack_names = new ArrayList<>();
            while (snack_resultSet.next()){
                String mahl_name = snack_resultSet.getString("Name");
                snack_names.add(mahl_name);
            }
            for (int i = 0; snack_names.size() > i; i++){
                snacks.addElement(snack_names.get(i));
            }
            snacks_list.setModel(snacks);
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