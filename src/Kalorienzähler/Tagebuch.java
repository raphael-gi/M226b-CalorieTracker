package Kalorienzähler;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import javax.swing.border.LineBorder;
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
    private JFrame frame;


    private Dimension size;
    private Point loc;

    private String benutzername;
    private int anz_fruh_kalorien;
    private int anz_mit_kalorien;
    private int anz_abend_kalorien;
    private int anz_snack_kalorien;

    private JButton[] all_buttons = {Frühstück, Mittagessen, Abendessen, Snacks, fruh_bearbeiten, fruh_delete, mit_bearbeiten, mit_delete, abend_bearbeiten, abend_delete, snack_bearbeiten, snack_delete, einstellungen};
    private JLabel[] all_labels = {kalorien_count, fruh_label, mit_label, abend_label, abend_label, snack_label, fruh_kalorien, mit_kalorien, abend_kalorien, snack_kalorien};
    private JList[] all_lists = {fruhstuck_list, mittagessen_list, abendessen_list, snacks_list};

    private boolean darkmode;

    public Tagebuch(Dimension size, Point loc, String benutzername){
        this.benutzername = benutzername;

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

        Darkmode check = new Darkmode(benutzername, all_buttons, all_labels);
        darkmode = check.isDark();
        if (darkmode){

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

        Frühstück.addActionListener(this);
        Mittagessen.addActionListener(this);
        Abendessen.addActionListener(this);
        Snacks.addActionListener(this);
        einstellungen.addActionListener(this);

        save(fruh_bearbeiten, fruh_delete, fruhstuck_list);

        save(mit_bearbeiten, mit_delete, mittagessen_list);

        save(abend_bearbeiten, abend_delete, abendessen_list);

        save(snack_bearbeiten, snack_delete, snacks_list);
    }
    public void save(JButton name_edit, JButton name_delete, JList list_name){
        name_edit.addActionListener(this);
        name_delete.addActionListener(this);
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
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
            Statement statement = connection.createStatement();
            ResultSet new_resultSet = statement.executeQuery("SELECT * FROM mmm WHERE ben = '" + get_ben_id + "' AND datum = '" + ft.format(date) + "' AND mahlzeit = " + mahlzeit_id + "");
            ArrayList<Integer> kalories = new ArrayList<>();
            while (new_resultSet.next()){
                int kalorien = new_resultSet.getInt("kalorien");
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
            correct_id = (int)also_same_id.get(right_id);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void on_delete(JList name, int mahlzeit_id){
        on_button(name,mahlzeit_id);
        DBConnect delete = new DBConnect("DELETE FROM mmm WHERE id = " + correct_id + " AND mahlzeit = " + mahlzeit_id + "", " ", 1);
        delete.con();

        frame.dispose();
        Dimension frame_size = frame.getSize();
        Point frame_loc = frame.getLocation();
        Tagebuch n = new Tagebuch(frame_size, frame_loc, this.benutzername);
        n.content();
    }
    public void on_edit(JList name, int mahlzeit_id){
        on_button(name, mahlzeit_id);
        DBConnect get_mahl = new DBConnect("SELECT mahl FROM mmm WHERE id = " + correct_id + " AND mahlzeit = " + mahlzeit_id + "", "mahl", 0);
        get_mahl.con();
        String mahl = get_mahl.getResult();
        DBConnect get_name = new DBConnect("SELECT Name FROM mahlzeit WHERE id = " + mahl + "", "Name", 0);
        get_name.con();
        String mahl_name = get_name.getResult();
        DBConnect get_port = new DBConnect("SELECT port FROM mmm WHERE id = " + correct_id + " AND mahlzeit = " + mahlzeit_id + "", "port", 0);
        get_port.con();
        String port = get_port.getResult();

        frame.dispose();
        Dimension frame_size = frame.getSize();
        Point frame_loc = frame.getLocation();
        Bearbeiten n = new Bearbeiten(frame_size, frame_loc, this.benutzername, mahl_name ,port, correct_id);
        n.content();
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
            Einstellungen n = new Einstellungen(frame_size, frame_loc, this.benutzername);
            n.content();
        }
    }
}