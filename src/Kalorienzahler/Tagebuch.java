package Kalorienzahler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Tagebuch implements ActionListener {
    private JPanel panel1;
    private JButton Fruhstuck;
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
    private JLabel fruh_kalorien;
    private JLabel mit_kalorien;
    private JLabel abend_kalorien;
    private JLabel snack_kalorien;
    private JButton einstellungen;
    private JLabel kalorien_anz;
    private JLabel kalorien_ziel_label;
    private JLabel kons_kalorien_label;
    private JLabel verb_kalorien_label;
    private JLabel verb_kalorien;
    private JLabel minus;
    private JLabel gleich;
    private JLabel datum;
    private JButton fruher;
    private JButton spater;
    private JFrame frame;
    private int gender;
    private double groesse;
    private double gewicht;
    private int alter;

    private Dimension size;
    private Point loc;

    private String benutzername;
    private int anz_fruh_kalorien;
    private int anz_mit_kalorien;
    private int anz_abend_kalorien;
    private int anz_snack_kalorien;
    private double formel;
    private double rest;

    private Date date_select;
    private java.util.Date date_now = new Date();
    private Calendar c = Calendar.getInstance();


    private int sprache = 1;

    private JButton[] all_buttons = {Fruhstuck, Mittagessen, Abendessen, Snacks, fruh_bearbeiten, fruh_delete, mit_bearbeiten, mit_delete, abend_bearbeiten, abend_delete, snack_bearbeiten, snack_delete, einstellungen, fruher, spater};
    private JLabel[] all_labels = {kalorien_count, fruh_label, mit_label, abend_label, abend_label, snack_label, fruh_kalorien, mit_kalorien, abend_kalorien, snack_kalorien, kalorien_ziel_label, kons_kalorien_label, verb_kalorien_label, kalorien_anz, verb_kalorien, minus, gleich, datum};
    private JList[] all_lists = {fruhstuck_list, mittagessen_list, abendessen_list, snacks_list};

    String[] fruh_list = {"Frühstück", "Breakfast"};

    private boolean darkmode;

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public Tagebuch(Dimension size, Point loc, String benutzername, Date datum){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        this.benutzername = benutzername;
        this.date_select = datum;

        frame = new JFrame("Tagebuch");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(panel1);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        this.size = size;
        this.loc = loc;

        frame.setSize(this.size);
        frame.setLocation(loc);

        fruh_label.setText(fruh_list[this.sprache]);
        Dimension ein = new Dimension(40,40);
        einstellungen.setPreferredSize(ein);

        Dimension time = new Dimension(130,30);
        fruher.setPreferredSize(time);
        spater.setPreferredSize(time);

        Darkmode check = new Darkmode(benutzername, all_buttons, all_labels);
        darkmode = check.isDark();
        if (this.darkmode){
            panel1.setBackground(Color.DARK_GRAY);
            for (int li = 0; this.all_lists.length > li; li++){
                JList list = this.all_lists[li];
                list.setForeground(Color.white);
                list.setBackground(Color.gray);
            }
            Darkmode n = new Darkmode(benutzername, all_buttons, all_labels);
            all_buttons = n.getAll_buttons();
            all_labels = n.getAll_labels();
        }

        Font label_font = new Font("Arial", Font.BOLD, 15);
        fruh_label.setFont(label_font);
        mit_label.setFont(label_font);
        abend_label.setFont(label_font);
        snack_label.setFont(label_font);
        kalorien_count.setFont(label_font);
        kalorien_anz.setFont(label_font);
        verb_kalorien.setFont(label_font);
        minus.setFont(label_font);
        gleich.setFont(label_font);

        for (int i = 0; all_buttons.length > i; i++){
            JButton but = all_buttons[i];
            but.addActionListener(this);
        }

        save(fruh_bearbeiten, fruh_delete, fruhstuck_list);

        save(mit_bearbeiten, mit_delete, mittagessen_list);

        save(abend_bearbeiten, abend_delete, abendessen_list);

        save(snack_bearbeiten, snack_delete, snacks_list);
        dat();
    }

    public void save(JButton name_edit, JButton name_delete, JList list_name){
        name_edit.setVisible(false);
        name_delete.setVisible(false);
        list_name.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                if (e.getClickCount() == 2 && !list_name.isSelectionEmpty()){
                    name_edit.setVisible(true);
                    name_delete.setVisible(true);
                }
                if (e.getClickCount() == 1){
                    if (list_name.equals(fruhstuck_list)){
                        mittagessen_list.clearSelection();
                        abendessen_list.clearSelection();
                        snacks_list.clearSelection();
                    }
                    if (list_name.equals(mittagessen_list)){
                        fruhstuck_list.clearSelection();
                        abendessen_list.clearSelection();
                        snacks_list.clearSelection();
                    }
                    if (list_name.equals(abendessen_list)){
                        fruhstuck_list.clearSelection();
                        mittagessen_list.clearSelection();
                        snacks_list.clearSelection();
                    }
                    if (list_name.equals(snacks_list)){
                        fruhstuck_list.clearSelection();
                        mittagessen_list.clearSelection();
                        abendessen_list.clearSelection();
                    }
                    fruh_bearbeiten.setVisible(false);
                    fruh_delete.setVisible(false);
                    mit_bearbeiten.setVisible(false);
                    mit_delete.setVisible(false);
                    abend_bearbeiten.setVisible(false);
                    abend_delete.setVisible(false);
                    snack_bearbeiten.setVisible(false);
                    snack_delete.setVisible(false);
                }
            }
        });
    }
    SimpleDateFormat ft = new SimpleDateFormat("yyy-MM-dd");
    SimpleDateFormat format = new SimpleDateFormat("EEEE, MMM d");
    public void dat(){
        c.setTime(date_now);
        c.add(Calendar.DATE, -1);
        Date fruh = c.getTime();
        c.add(Calendar.DATE, 2);
        Date spat = c.getTime();
        if (ft.format(date_now).equals(ft.format(date_select))){
            datum.setText("Heute");
        }
        else {
            datum.setText(format.format(date_select));
        }
        if (ft.format(fruh.getTime()).equals(ft.format(date_select))){
            datum.setText("Gestern");
        }
        if (ft.format(spat.getTime()).equals(ft.format(date_select))){
            datum.setText("Morgen");
        }
        c.setTime(date_select);
    }

    public void list_content(ArrayList kalories){
        try {
            resultSet = statement.executeQuery("SELECT * FROM mmm WHERE ben = '" + get_ben_id + "' AND datum = '" + ft.format(date_select) + "'");
            while (resultSet.next()){
                int kalorien = resultSet.getInt("kalorien");
                kalories.add(kalorien);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    int get_ben_id = 0;
    public void content(){
        DBConnect gender = new DBConnect("SELECT gender FROM Benutzer WHERE Benutzername = '" + this.benutzername + "'", "gender",0);
        this.gender = Integer.parseInt(gender.getResult());
        DBConnect alter = new DBConnect("SELECT age FROM Benutzer WHERE Benutzername = '" + this.benutzername + "'", "age", 0);
        this.alter = Integer.parseInt(alter.getResult());
        DBConnect gewicht = new DBConnect("SELECT gewicht FROM Benutzer WHERE Benutzername = '" + this.benutzername + "'", "gewicht", 0);
        this.gewicht = Double.parseDouble(gewicht.getResult());
        DBConnect groesse = new DBConnect("SELECT groesse FROM Benutzer WHERE Benutzername = '" + this.benutzername + "'", "groesse", 0);
        this.groesse = Double.parseDouble(groesse.getResult());

        if(this.gender == 1){
            formel =  (66.47 + (13.7 * this.gewicht) + (5 * this.groesse) - (6.8 * this.alter));
        }else{
            formel =  (655.1 + (9.6 * this.gewicht) + (1.8 * this.groesse) - (4.7 * this.alter));
        }
        //Verbindung um id des Benutzer zu erhalten
        try {
            resultSet = statement.executeQuery("SELECT id FROM Benutzer WHERE Benutzername = '" + this.benutzername + "'");
            while (resultSet.next()){
                get_ben_id = resultSet.getInt("id");
            }
            ArrayList<Integer> kalories = new ArrayList<>();
            list_content(kalories);

            int anz_kalorien = 0;
            for (int i = 0; kalories.size() > i; i++){
                anz_kalorien = anz_kalorien + kalories.get(i);
            }
            int formel_gerund = (int) Math.round(formel);
            rest = formel - anz_kalorien;

            kalorien_count.setText(String.valueOf(formel_gerund));
            kalorien_anz.setText(String.valueOf(anz_kalorien));
            int rest_round = (int) (Math.round(rest * 10d) / 10d);
            verb_kalorien.setText(String.valueOf(rest_round));

            //Frühstück Kalorien werden angezeigt
            get_meal_cal(1);
            fruh_kalorien.setText(String.valueOf(this.anz_fruh_kalorien));
            //Mittagessen Kalorien werden angezeigt
            get_meal_cal(2);
            mit_kalorien.setText(String.valueOf(this.anz_mit_kalorien));
            //Abendessen Kalorien werden angezeigt
            get_meal_cal(3);
            abend_kalorien.setText(String.valueOf(this.anz_abend_kalorien));
            //Snacks Kalorien werden angezeigt
            get_meal_cal(4);
            snack_kalorien.setText(String.valueOf(this.anz_snack_kalorien));


            get_select(fruhstuck_list, fruhstuck,1);

            get_select(mittagessen_list, mittagessen,2);

            get_select(abendessen_list, abendessen,3);

            get_select(snacks_list, snacks,4);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void get_meal_cal(int mahlzeit_id){
        try {
            resultSet = statement.executeQuery("SELECT * FROM mmm WHERE ben = '" + get_ben_id + "' AND datum = '" + ft.format(date_select) + "' AND mahlzeit = " + mahlzeit_id + "");
            ArrayList<Integer> kalories = new ArrayList<>();
            while (resultSet.next()){
                int kalorien = resultSet.getInt("kalorien");
                kalories.add(kalorien);
            }
            int anz_kalorien = 0;
            for (int i = 0; kalories.size() > i; i++){
                anz_kalorien = anz_kalorien + kalories.get(i);
            }

            if (mahlzeit_id == 1){
                this.anz_fruh_kalorien = anz_kalorien;
            }
            if (mahlzeit_id == 2){
                this.anz_mit_kalorien = anz_kalorien;
            }
            if (mahlzeit_id == 3){
                this.anz_abend_kalorien = anz_kalorien;
            }
            if (mahlzeit_id == 4){
                this.anz_snack_kalorien = anz_kalorien;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void get_select(JList list_name , DefaultListModel name, int mahlzeit_id) throws SQLException {
        name.clear();
        resultSet = statement.executeQuery("SELECT * FROM mahlzeit,mmm WHERE mmm.ben = " + get_ben_id + " AND mahlzeit.ben = " + get_ben_id + " AND mmm.mahl = mahlzeit.id AND mmm.datum = '" + ft.format(date_select) + "' AND mmm.mahlzeit = " + mahlzeit_id + "");
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
    int correct_id = 0;
    public void on_button(JList name, int mahlzeit_id){
        String right_name = (String)name.getSelectedValue();

        ArrayList same_name = new ArrayList();
        for (int i = 0; name.getModel().getSize() > i; i++){
            Object cont = name.getModel().getElementAt(i);
            if (right_name.equals(cont)){
                same_name.add(cont);
            }
        }
        try {
            DBConnect fruh_select = new DBConnect("SELECT id FROM mahlzeit WHERE Name = '" + right_name + "'", "id", 0);

            resultSet = statement.executeQuery("SELECT id FROM mmm WHERE mahl = '" + fruh_select.getResult() + "' AND mahlzeit = " + mahlzeit_id + "");
            ArrayList also_same_id = new ArrayList();
            while (resultSet.next()){
                int mahl_id = resultSet.getInt("id");
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
            correct_id = (int)also_same_id.get(right_id);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void on_delete(JList name, int mahlzeit_id){
        on_button(name,mahlzeit_id);
        new DBConnect("DELETE FROM mmm WHERE id = " + correct_id + " AND mahlzeit = " + mahlzeit_id + "", " ", 1);

        frame.dispose();
        Dimension frame_size = frame.getSize();
        Point frame_loc = frame.getLocation();
        Tagebuch n = new Tagebuch(frame_size, frame_loc, this.benutzername, date_select);
        n.content();
    }

    public void on_edit(JList name, int mahlzeit_id){
        on_button(name, mahlzeit_id);
        DBConnect get_mahl = new DBConnect("SELECT mahl FROM mmm WHERE id = " + correct_id + " AND mahlzeit = " + mahlzeit_id + "", "mahl", 0);
        String mahl = get_mahl.getResult();

        DBConnect get_name = new DBConnect("SELECT Name FROM mahlzeit WHERE id = " + mahl + "", "Name", 0);
        String mahl_name = get_name.getResult();

        DBConnect get_port = new DBConnect("SELECT port FROM mmm WHERE id = " + correct_id + " AND mahlzeit = " + mahlzeit_id + "", "port", 0);
        String port = get_port.getResult();

        frame.dispose();
        Dimension frame_size = frame.getSize();
        Point frame_loc = frame.getLocation();
        Bearbeiten n = new Bearbeiten(frame_size, frame_loc, this.benutzername, mahl_name ,port, correct_id, date_select);
        n.content();
    }

    public void on_new_meal(String mahlzeit){
        frame.dispose();
        Dimension frame_size = frame.getSize();
        Point frame_loc = frame.getLocation();
        Mahlzeit_auswahl mahl = new Mahlzeit_auswahl(frame_size, frame_loc, mahlzeit, this.benutzername, date_select);
        mahl.content();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Fruhstuck){
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
            on_edit(fruhstuck_list, 1);
        }
        if (e.getSource() == fruh_delete){
            on_delete(fruhstuck_list,1);
        }
        if (e.getSource() == mit_bearbeiten){
            on_edit(mittagessen_list, 2);
        }
        if (e.getSource() == mit_delete){
            on_delete(mittagessen_list,2);
        }
        if (e.getSource() == abend_bearbeiten){
            on_edit(abendessen_list, 3);
        }
        if (e.getSource() == abend_delete){
            on_delete(abendessen_list,3);
        }
        if (e.getSource() == snack_bearbeiten){
            on_edit(snacks_list, 4);
        }
        if (e.getSource() == snack_delete){
            on_delete(snacks_list,4);
        }
        if (e.getSource() == einstellungen){
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Einstellungen n = new Einstellungen(frame_size, frame_loc, this.benutzername, date_select);
            n.content();
        }
        if (e.getSource() == fruher){
            c.add(Calendar.DATE, -1);
            date_select = c.getTime();
            dat();
            content();
        }
        if (e.getSource() == spater){
            c.add(Calendar.DATE, 1);
            date_select = c.getTime();
            dat();
            content();
        }
    }
}