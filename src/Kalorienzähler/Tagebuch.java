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
    private JLabel fruh_label;
    private JLabel mit_label;
    private JLabel abend_label;
    private JLabel snack_label;
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

        Font label_font = new Font("Arial", Font.BOLD, 15);
        fruh_label.setFont(label_font);
        mit_label.setFont(label_font);
        abend_label.setFont(label_font);
        snack_label.setFont(label_font);

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
    java.util.Date date = new Date();
    SimpleDateFormat ft = new SimpleDateFormat("yyy-MM-dd");
    int get_ben_id = 0;
    public void content(){
        //Verbindung um id des Benutzer zu erhalten
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


            get_delete(fruhstuck_list, fruhstuck,1);

            get_delete(mittagessen_list, mittagessen,2);

            get_delete(abendessen_list, abendessen,3);

            get_delete(snacks_list, snacks,4);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void get_delete(JList list_name , DefaultListModel name, int mahlzeit_id) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM mahlzeit,mmm WHERE mmm.ben = " + get_ben_id + " AND mahlzeit.ben = " + get_ben_id + " AND mmm.mahl = mahlzeit.id AND mmm.datum = '" + ft.format(date) + "' AND mmm.mahlzeit = " + mahlzeit_id + "");
        ArrayList<String> names = new ArrayList<>();
        while (resultSet.next()){
            String mahl_name = resultSet.getString("Name");
            names.add(mahl_name);
        }
        for (int i = 0; names.size() > i; i++){
            name.addElement(names.get(i));
        }
        list_name.setModel(name);
    }
    public void on_delete(JList name, int mahlzeit_id){
        String right_name = (String)name.getSelectedValue();

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

    public void on_new_meal(String mahlzeit){
        frame.dispose();
        Dimension frame_size = frame.getSize();
        Point frame_loc = frame.getLocation();
        Mahlzeit_auswahl mahl = new Mahlzeit_auswahl(frame_size, frame_loc, mahlzeit, this.benutzername);
        mahl.content();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Frühstück){
            on_new_meal("Frühstück");
        }
        if (e.getSource() == Mittagessen){
            on_new_meal("Mittagessen");
        }
        if (e.getSource() == Abendessen){
            on_new_meal("Abendessen");
        }
        if (e.getSource() == Snacks){
            on_new_meal("Snacks");
        }

        if (e.getSource() == fruh_bearbeiten){
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            new Bearbeiten(frame_size,frame_loc);
        }
        if (e.getSource() == fruh_delete){
            on_delete(fruhstuck_list,1);
        }
        if (e.getSource() == mit_delete){
            on_delete(mittagessen_list,2);
        }
        if (e.getSource() == abend_delete){
            on_delete(abendessen_list,3);
        }
        if (e.getSource() == snack_delete){
            on_delete(snacks_list,4);
        }
    }
}