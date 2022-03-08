package Kalorienzähler;

import javax.swing.*;
import javax.swing.text.html.HTML;
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
    private JButton fruh_bearbeiten;
    private JButton fruh_delete;
    private JButton mit_bearbeiten;
    private JButton mit_delete;
    private JButton abend_bearbeiten;
    private JButton abend_delete;
    private JButton snack_bearbeiten;
    private JButton snack_delete;
    DefaultListModel snacks = new DefaultListModel();
    private JFrame frame;

    private Dimension size;
    private Point loc;

    private String benutzername;

    public Tagebuch(Dimension size, Point loc, String benutzername){
        this.benutzername = benutzername;

        frame = new JFrame("Tagebuch");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500,400));

        frame.add(panel1);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        this.size = size;
        this.loc = loc;

        frame.setSize(this.size);
        frame.setLocation(loc);

        Frühstück.addActionListener(this);
        Mittagessen.addActionListener(this);
        Abendessen.addActionListener(this);
        Snacks.addActionListener(this);

        fruh_bearbeiten.addActionListener(this);
        fruh_delete.addActionListener(this);
        fruh_bearbeiten.setVisible(false);
        fruh_delete.setVisible(false);
        fruhstuck_list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                if (e.getClickCount() == 2){
                    fruh_bearbeiten.setVisible(true);
                    fruh_delete.setVisible(true);
                }
                if (e.getClickCount() == 1){
                    mittagessen_list.clearSelection();
                    abendessen_list.clearSelection();
                    snacks_list.clearSelection();

                    vis();
                }
            }
        });

        mit_bearbeiten.addActionListener(this);
        mit_delete.addActionListener(this);
        mit_bearbeiten.setVisible(false);
        mit_delete.setVisible(false);
        mittagessen_list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                if (e.getClickCount() == 2){
                    mit_bearbeiten.setVisible(true);
                    mit_delete.setVisible(true);
                }
                if (e.getClickCount() == 1){
                    fruhstuck_list.clearSelection();
                    abendessen_list.clearSelection();
                    snacks_list.clearSelection();

                    vis();
                }
            }
        });

        abend_bearbeiten.addActionListener(this);
        abend_delete.addActionListener(this);
        abend_bearbeiten.setVisible(false);
        abend_delete.setVisible(false);
        abendessen_list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                if (e.getClickCount() == 2){
                    abend_bearbeiten.setVisible(true);
                    abend_delete.setVisible(true);
                }
                if (e.getClickCount() == 1){
                    fruhstuck_list.clearSelection();
                    mittagessen_list.clearSelection();
                    snacks_list.clearSelection();

                    vis();
                }
            }
        });

        snack_bearbeiten.addActionListener(this);
        snack_delete.addActionListener(this);
        snack_bearbeiten.setVisible(false);
        snack_delete.setVisible(false);
        snacks_list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                if (e.getClickCount() == 2){
                    snack_bearbeiten.setVisible(true);
                    snack_delete.setVisible(true);
                }
                if (e.getClickCount() == 1){
                    fruhstuck_list.clearSelection();
                    mittagessen_list.clearSelection();
                    abendessen_list.clearSelection();

                    vis();
                }
            }
        });
    }
    public void vis(){
        fruh_bearbeiten.setVisible(false);
        fruh_delete.setVisible(false);
        mit_bearbeiten.setVisible(false);
        mit_delete.setVisible(false);
        abend_bearbeiten.setVisible(false);
        abend_delete.setVisible(false);
        snack_bearbeiten.setVisible(false);
        snack_delete.setVisible(false);
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
                String id = fruh_resultSet.getString("mmm.id");
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
    public void on_delete(JList name, int mahlzeit_id){
        String right_name = (String)name.getSelectedValue();

        System.out.println(right_name);

        ArrayList same_name = new ArrayList();
        for (int i = 0; name.getModel().getSize() > i; i++){
            Object cont = name.getModel().getElementAt(i);
            if (right_name.equals(cont)){
                same_name.add(cont);
            }
        }

        if (same_name.size() == 1){
            DBConnect fruh_select = new DBConnect("SELECT id FROM mahlzeit WHERE Name = '" + right_name + "'", "id", 0);
            fruh_select.con();
            String mahl_id = fruh_select.getResult();
            DBConnect fruh_delete = new DBConnect("DELETE FROM mmm WHERE mahl = " + mahl_id + " AND mahlzeit = " + mahlzeit_id + "", "id", 1);
            fruh_delete.con();

            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Tagebuch n = new Tagebuch(frame_size, frame_loc, this.benutzername);
            n.content();
        }
        else {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
                Statement statement = connection.createStatement();
                DBConnect fruh_select = new DBConnect("SELECT id FROM mahlzeit WHERE Name = '" + right_name + "'", "id", 0);
                fruh_select.con();

                ResultSet fruh_select_mult = statement.executeQuery("SELECT id FROM mmm WHERE mahl = '" + fruh_select.getResult() + "' AND mahlzeit = " + mahlzeit_id + "");
                ArrayList also_same_id = new ArrayList();
                while (fruh_select_mult.next()){
                    int mahl_id = fruh_select_mult.getInt("id");
                    also_same_id.add(mahl_id);
                }
                name.getSelectedIndex();
                ArrayList indexes = new ArrayList();
                for (int i = 0; name.getModel().getSize() > i; i++){
                    Object cont = name.getModel().getElementAt(i);
                    if (right_name.equals(cont)){
                        indexes.add(i);
                    }
                }
                int right_id = 0;
                for (int i = 0; indexes.size() > i; i++){
                    int list_index = (int)indexes.get(i);
                    if (name.getSelectedIndex() == list_index){
                        right_id = i;
                    }
                }
                int correct_id = (int)also_same_id.get(right_id);
                DBConnect fruh_delete = new DBConnect("DELETE FROM mmm WHERE id = " + correct_id + " AND mahlzeit = " + mahlzeit_id + "", " ", 1);
                fruh_delete.con();

                frame.dispose();
                Dimension frame_size = frame.getSize();
                Point frame_loc = frame.getLocation();
                Tagebuch n = new Tagebuch(frame_size, frame_loc, this.benutzername);
                n.content();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Frühstück){
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Mahlzeit_auswahl mahl = new Mahlzeit_auswahl(frame_size, frame_loc, "Frühstück", this.benutzername);
            mahl.content();
        }
        if (e.getSource() == Mittagessen){
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Mahlzeit_auswahl mahl = new Mahlzeit_auswahl(frame_size, frame_loc,"Mittagessen", this.benutzername);
            mahl.content();
        }
        if (e.getSource() == Abendessen){
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Mahlzeit_auswahl mahl = new Mahlzeit_auswahl(frame_size, frame_loc,"Abendessen", this.benutzername);
            mahl.content();
        }
        if (e.getSource() == Snacks){
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Mahlzeit_auswahl mahl = new Mahlzeit_auswahl(frame_size, frame_loc,"Snacks", this.benutzername);
            mahl.content();
        }

        if (e.getSource() == fruh_bearbeiten){
            System.out.println("hi");
        }
        if (e.getSource() == fruh_delete){
            on_delete(fruhstuck_list,1);
        }
        if (e.getSource() == mit_delete){
            on_delete(mittagessen_list,2);
        }
        if (e.getSource() == abend_delete){
            on_delete(mittagessen_list,3);
        }
        if (e.getSource() == snack_delete){
            on_delete(mittagessen_list,4);
        }
    }
}