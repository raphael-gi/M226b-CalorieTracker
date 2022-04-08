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
    private JList<String> fruhstuck_list;
    DefaultListModel<String> fruhstuck = new DefaultListModel<>();
    private JList<String> mittagessen_list;
    DefaultListModel<String> mittagessen = new DefaultListModel<>();
    private JList<String> abendessen_list;
    DefaultListModel<String> abendessen = new DefaultListModel<>();
    private JList<String> snacks_list;
    DefaultListModel<String> snacks = new DefaultListModel<>();
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
    private JLabel datum_label;
    private JButton fruher;
    private JButton spater;
    private JLabel protein_ziel_label;
    private JLabel konsumierte_protein_label;
    private JLabel verbleibende_protein_label;
    private JLabel protein_anz;
    private JLabel protein_ziel;
    private JLabel verb_protein;
    private JLabel minus2;
    private JLabel gleich2;
    private final JFrame frame;

    private final String benutzername;
    private int anz_fruh_kalorien;
    private int anz_mit_kalorien;
    private int anz_abend_kalorien;
    private int anz_snack_kalorien;
    private double prot_formel;

    private Date date_select;
    private final java.util.Date date_now = new Date();
    private final Calendar c = Calendar.getInstance();


    private int sprache;

    //Arrays mit Sprachen
    String[] fruh_list = {"Frühstück", "Breakfast"};
    String[] Eat_SP = {"Mahlzeit hinzufügen", "Add meal"};
    String[] Abend_SP = {"Abendessen", "Dinner"};
    String[] Mitt_SP = {"Mittagessen", "Lunch"};
    String[] Snack_SP = {"Imbiss", "Snack"};
    String[] kalorien_ziel_list = {"Kalorien Ziel:", "Calories target:"};
    String[] kons_kalorien_list = {"Konsumierte Kalorien:", "Calories consumed:"};
    String[] verb_kalorien_list = {"Verbleibbende Kalorien:", "Remaining calories:"};
    String[] loeschen_list = {"Löschen", "Delete"};
    String[] bearbeiten_list = {"Bearbeiten", "Edit"};

    //Array mit allen Sprachen Arrays
    String[][] spracharr = {fruh_list, Mitt_SP, Abend_SP, Snack_SP, kalorien_ziel_list, kons_kalorien_list, verb_kalorien_list, Eat_SP, Eat_SP, Eat_SP, Eat_SP, bearbeiten_list, loeschen_list, bearbeiten_list, loeschen_list, bearbeiten_list, loeschen_list, bearbeiten_list, loeschen_list};
    JLabel[] lab_lang = {fruh_label, mit_label, abend_label, snack_label, kalorien_ziel_label, kons_kalorien_label, verb_kalorien_label};
    JButton[] but_lang = {Fruhstuck, Mittagessen, Abendessen, Snacks, fruh_bearbeiten, fruh_delete, mit_bearbeiten, mit_delete, abend_bearbeiten, abend_delete, snack_bearbeiten, snack_delete};

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public Tagebuch(Dimension size, Point loc, String benutzername, Date datum){
        try {
            //DB Verbindung wird aufgebaut
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        this.benutzername = benutzername;
        this.date_select = datum;

        frame = new JFrame();
        new StarterPack(frame, panel1, "Tagebuch", size, loc);

        fruh_label.setText(fruh_list[this.sprache]);
        Dimension ein = new Dimension(40,40);
        einstellungen.setPreferredSize(ein);

        Dimension time = new Dimension(130,30);
        fruher.setPreferredSize(time);
        spater.setPreferredSize(time);

        JButton[] all_buttons = {Fruhstuck, Mittagessen, Abendessen, Snacks, fruh_bearbeiten, fruh_delete, mit_bearbeiten, mit_delete, abend_bearbeiten, abend_delete, snack_bearbeiten, snack_delete, einstellungen, fruher, spater};
        JLabel[] all_labels = {kalorien_count, fruh_label, mit_label, abend_label, abend_label, snack_label, fruh_kalorien, mit_kalorien, abend_kalorien, snack_kalorien, kalorien_ziel_label, kons_kalorien_label, verb_kalorien_label, kalorien_anz, verb_kalorien, minus, gleich, datum_label, protein_ziel_label, konsumierte_protein_label, verbleibende_protein_label, protein_ziel, protein_anz, verb_protein, minus2, gleich2};
        Darkmode d = new Darkmode(benutzername, all_buttons, all_labels);
        if (d.isDark()){
            panel1.setBackground(Color.DARK_GRAY);
            var all_lists = new JList[]{fruhstuck_list, mittagessen_list, abendessen_list, snacks_list};
            for (var list : all_lists){
                list.setForeground(Color.white);
                list.setBackground(Color.gray);
            }
        }

        Font label_font = new Font("Arial", Font.BOLD, 15);
        JLabel[] big_labs = {fruh_label, mit_label, abend_label, snack_label, kalorien_count, kalorien_anz, verb_kalorien, protein_ziel, protein_anz, verb_protein, minus, minus2, gleich, gleich2};
        for (JLabel lab : big_labs){
            lab.setFont(label_font);
        }

        for (JButton but : all_buttons){
            but.addActionListener(this);
        }

        //Die Buttons und Listen aller Mahlzeiten werden richtig eingestellt
        save(fruh_bearbeiten, fruh_delete, fruhstuck_list);
        save(mit_bearbeiten, mit_delete, mittagessen_list);
        save(abend_bearbeiten, abend_delete, abendessen_list);
        save(snack_bearbeiten, snack_delete, snacks_list);

        DBConnect get_sprache = new DBConnect("SELECT sprache FROM benutzer WHERE Benutzername = '" + this.benutzername + "'", "sprache", 0);
        sprache = Integer.parseInt(get_sprache.getResult());

        int len = lab_lang.length + but_lang.length;
        int ii;
        for (int i = 0; len > i; i++) {
            if (lab_lang.length > i) {
                lab_lang[i].setText(spracharr[i][this.sprache]);
            } else {
                ii = i - lab_lang.length;
                but_lang[ii].setText(spracharr[i][this.sprache]);
            }
        }
        dat();
    }


    public void save(JButton name_edit, JButton name_delete, JList<String> list_name){
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
                    //Loesch und Bearbeit Buttons werden Unsichtbar gemacht
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
            String [] heute_list = {"Heute","Today"};
            datum_label.setText(heute_list[this.sprache]);
        }
        else {
            datum_label.setText(format.format(date_select));
        }
        if (ft.format(fruh.getTime()).equals(ft.format(date_select))){
            String [] gestern_list = {"Gestern","Yesterday"};
            datum_label.setText(gestern_list[this.sprache]);
        }
        if (ft.format(spat.getTime()).equals(ft.format(date_select))){
            String [] morgen_list = {"Morgen","Tomorrow"};
            datum_label.setText(morgen_list[this.sprache]);
        }
        c.setTime(date_select);
    }

    public void list_content(ArrayList<Integer> kalories, ArrayList<Integer> proteins){
        try {
            resultSet = statement.executeQuery("SELECT * FROM mmm WHERE ben = '" + get_ben_id + "' AND datum = '" + ft.format(date_select) + "'");
            while (resultSet.next()){
                int kalorien = resultSet.getInt("kalorien");
                kalories.add(kalorien);
                double protein = resultSet.getDouble("protein");
                int prot = (int) Math.round(protein);
                proteins.add(prot);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    int get_ben_id = 0;
    public void content(){
        DBConnect get_gender = new DBConnect("SELECT gender FROM Benutzer WHERE Benutzername = '" + this.benutzername + "'", "gender",0);
        int gender = Integer.parseInt(get_gender.getResult());
        DBConnect get_alter = new DBConnect("SELECT age FROM Benutzer WHERE Benutzername = '" + this.benutzername + "'", "age", 0);
        int alter = Integer.parseInt(get_alter.getResult());
        DBConnect get_gewicht = new DBConnect("SELECT gewicht FROM Benutzer WHERE Benutzername = '" + this.benutzername + "'", "gewicht", 0);
        double gewicht = Double.parseDouble(get_gewicht.getResult());
        DBConnect get_groesse = new DBConnect("SELECT groesse FROM Benutzer WHERE Benutzername = '" + this.benutzername + "'", "groesse", 0);
        double groesse = Double.parseDouble(get_groesse.getResult());
        DBConnect get_muskel = new DBConnect("SELECT muskel FROM Benutzer WHERE Benutzername = '" + this.benutzername + "'", "muskel", 0);
        int muskel = Integer.parseInt(get_muskel.getResult());

        if (muskel == 0){
            protein_ziel_label.setVisible(false);
            konsumierte_protein_label.setVisible(false);
            verbleibende_protein_label.setVisible(false);
            protein_ziel.setVisible(false);
            protein_anz.setVisible(false);
            verb_protein.setVisible(false);
            minus2.setVisible(false);
            gleich2.setVisible(false);
        }
        else {
            prot_formel = gewicht * 2.3;
        }
        double cal_formel;
        if(gender == 1){
            cal_formel =  (66.47 + (13.7 * gewicht) + (5 * groesse) - (6.8 * alter));
        }else{
            cal_formel =  (655.1 + (9.6 * gewicht) + (1.8 * groesse) - (4.7 * alter));
        }
        //Verbindung um id des Benutzer zu erhalten
        try {
            resultSet = statement.executeQuery("SELECT id FROM Benutzer WHERE Benutzername = '" + this.benutzername + "'");
            while (resultSet.next()){
                get_ben_id = resultSet.getInt("id");
            }
            DBConnect bulk = new DBConnect("SELECT bulk FROM Benutzer WHERE Benutzername = '" + this.benutzername + "'", "bulk", 0);
            ArrayList<Integer> kalories = new ArrayList<>();
            ArrayList<Integer> proteins = new ArrayList<>();
            list_content(kalories, proteins);

            int anz_kalorien = 0;
            for (Integer kalory : kalories) {
                anz_kalorien = anz_kalorien + kalory;
            }

            if (bulk.getResult().equals("1")){
                cal_formel = cal_formel + 350;
            }
            if (bulk.getResult().equals("2")){
                cal_formel = cal_formel - 350;
            }
            int formel_gerund = (int) Math.round(cal_formel);
            double cal_rest = cal_formel - anz_kalorien;

            kalorien_count.setText(String.valueOf(formel_gerund));
            kalorien_anz.setText(String.valueOf(anz_kalorien));
            int rest_round = (int) (Math.round(cal_rest));
            verb_kalorien.setText(String.valueOf(rest_round));


            int anz_protein = 0;
            for (Integer protein : proteins) {
                anz_protein = anz_protein + protein;
            }
            formel_gerund = (int) Math.round(prot_formel);
            double prot_rest = prot_formel - anz_protein;

            protein_ziel.setText(String.valueOf(formel_gerund));
            protein_anz.setText(String.valueOf(anz_protein));
            rest_round = (int) (Math.round(prot_rest * 10d) / 10d);
            verb_protein.setText(String.valueOf(rest_round));


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
            for (Integer kalory : kalories) {
                anz_kalorien = anz_kalorien + kalory;
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

    public void get_select(JList<String> list_name , DefaultListModel<String> name, int mahlzeit_id) throws SQLException {
        name.clear();
        resultSet = statement.executeQuery("SELECT * FROM mahlzeit,mmm WHERE mmm.ben = " + get_ben_id + " AND mahlzeit.ben = " + get_ben_id + " AND mmm.mahl = mahlzeit.id AND mmm.datum = '" + ft.format(date_select) + "' AND mmm.mahlzeit = " + mahlzeit_id + "");
        ArrayList<String> names = new ArrayList<>();
        while (resultSet.next()){
            String mahl_name = resultSet.getString("Name");
            names.add(mahl_name);
        }
        for (String s : names) {
            name.addElement(s);
        }
        list_name.setModel(name);
    }
    int correct_id = 0;
    public void on_button(JList<String> name, int mahlzeit_id){
        String right_name = name.getSelectedValue();

        try {
            DBConnect fruh_select = new DBConnect("SELECT id FROM mahlzeit WHERE Name = '" + right_name + "'", "id", 0);

            resultSet = statement.executeQuery("SELECT id FROM mmm WHERE mahl = '" + fruh_select.getResult() + "' AND mahlzeit = " + mahlzeit_id + "");
            ArrayList<Integer> also_same_id = new ArrayList<>();
            while (resultSet.next()){
                int mahl_id = resultSet.getInt("id");
                also_same_id.add(mahl_id);
            }
            name.getSelectedIndex();
            ArrayList<Integer> indexes = new ArrayList<>();
            for (int i = 0; name.getModel().getSize() > i; i++){
                Object cont = name.getModel().getElementAt(i);
                if (right_name.equals(cont)){
                    indexes.add(i);
                }
            }
            int right_id = 0;
            for (int i = 0; indexes.size() > i; i++){
                int list_index = indexes.get(i);
                if (name.getSelectedIndex() == list_index){
                    right_id = i;
                }
            }
            correct_id = also_same_id.get(right_id);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void on_delete(JList<String> name, int mahlzeit_id){
        on_button(name,mahlzeit_id);
        new DBConnect("DELETE FROM mmm WHERE id = " + correct_id + " AND mahlzeit = " + mahlzeit_id + "", " ", 1);
        content();
    }

    public void on_edit(JList<String> name, int mahlzeit_id){
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
            String mahl_name = "";
            if (sprache == 0){
                mahl_name = "Frühstück";
            }
            else if (sprache == 1){
                mahl_name = "Breakfast";
            }
            on_new_meal(mahl_name);
        }
        if (e.getSource() == Mittagessen){
            String mahl_name = "";
            if (sprache == 0){
                mahl_name = "Mittagessen";
            }
            else if (sprache == 1){
                mahl_name = "Lunch";
            }
            on_new_meal(mahl_name);
        }
        if (e.getSource() == Abendessen){
            String mahl_name = "";
            if (sprache == 0){
                mahl_name = "Abendessen";
            }
            else if (sprache == 1){
                mahl_name = "Dinner";
            }
            on_new_meal(mahl_name);
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
            new Einstellungen(frame_size, frame_loc, this.benutzername, date_select);
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