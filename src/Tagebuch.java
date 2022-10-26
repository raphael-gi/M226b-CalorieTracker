import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Tagebuch extends Global implements ActionListener {
    private JPanel panel;
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
    private JLabel fruhLabel;
    private JLabel mitLabel;
    private JLabel abendLabel;
    private JLabel snackLabel;
    private JLabel fruh_kalorien;
    private JLabel mit_kalorien;
    private JLabel abend_kalorien;
    private JLabel snack_kalorien;
    private JButton einstellungen;
    private JLabel kalorien_anz;
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

    private double prot_formel;

    private final java.util.Date date_now = new Date();
    private final Calendar c = Calendar.getInstance();

    //Array mit allen Sprachen Arrays
    JLabel[] mealLabels = {fruhLabel, mitLabel, abendLabel, snackLabel};

    private final JButton[] add = {Fruhstuck, Mittagessen, Abendessen, Snacks};
    private final JList[] list = {fruhstuck_list, mittagessen_list, abendessen_list, snacks_list};
    private final JButton[] edit = {fruh_bearbeiten, mit_bearbeiten, abend_bearbeiten, snack_bearbeiten};
    private final JButton[] delete = {fruh_delete, mit_delete, abend_delete, snack_delete};

    public Tagebuch() {
        newPanel(panel);

        Dimension ein = new Dimension(40,40);
        einstellungen.setPreferredSize(ein);

        Dimension time = new Dimension(130,30);
        fruher.setPreferredSize(time);
        spater.setPreferredSize(time);

        JButton[] all_buttons = {Fruhstuck, Mittagessen, Abendessen, Snacks, fruh_bearbeiten, fruh_delete, mit_bearbeiten, mit_delete, abend_bearbeiten, abend_delete, snack_bearbeiten, snack_delete, einstellungen, fruher, spater};

        Font label_font = new Font("Arial", Font.BOLD, 15);
        JLabel[] big_labs = {fruhLabel, mitLabel, abendLabel, snackLabel, kalorien_count, kalorien_anz, verb_kalorien, protein_ziel, protein_anz, verb_protein, minus, minus2, gleich, gleich2};
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
    //Datum wird erstellt für DB und für die anzeige im Gui
    SimpleDateFormat ft = new SimpleDateFormat("yyy-MM-dd");
    SimpleDateFormat format = new SimpleDateFormat("EEEE, MMM d");
    public void dat(){
        c.setTime(date_now);
        c.add(Calendar.DATE, -1);
        Date fruh = c.getTime();
        c.add(Calendar.DATE, 2);
        Date spat = c.getTime();
        String datumText = format.format(date);
        if (ft.format(date_now).equals(ft.format(date))) datumText = "Heute";
        if (ft.format(fruh.getTime()).equals(ft.format(date))) datumText = "Gestern";
        if (ft.format(spat.getTime()).equals(ft.format(date))) datumText = "Morgen";
        datum_label.setText(datumText);
        c.setTime(date);
    }
    //Abrufen der Daten die an dem Ausgewählten Datum gespeichert wurden
    public ArrayList[] list_content(){
        ArrayList<Integer> kalories = new ArrayList<>();
        ArrayList<Integer> proteins = new ArrayList<>();
        try {
            resultSet = statement.executeQuery("SELECT kalorien, protein FROM mmm WHERE ben = '" + id + "' AND datum = '" + ft.format(date) + "'");
            while (resultSet.next()){
                int kalorien = resultSet.getInt("kalorien");
                kalories.add(kalorien);
                double protein = resultSet.getDouble("protein");
                int prot = (int) Math.round(protein);
                proteins.add(prot);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList[] list = new ArrayList[2];
        list[0] = kalories;
        list[1] = proteins;
        return list;
    }

    //Die Daten des Benutzers abrufen
    public void content(){
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
            cal_formel =  (66.47 + (13.7 * gewicht) + (5 * groesse) - (6.8 * age));
        }else{
            cal_formel =  (655.1 + (9.6 * gewicht) + (1.8 * groesse) - (4.7 * age));
        }
        //Verbindung um id des Benutzer zu erhalten
        try {

            ArrayList[] list = list_content();

            int anz_kalorien = 0;
            for (Object kalory : list[0]) {
                anz_kalorien = anz_kalorien + (int) kalory;
            }

            if (bulk == 1) {
                cal_formel = cal_formel + 350;
            }
            if (bulk == 2) {
                cal_formel = cal_formel - 350;
            }
            int formel_gerund = (int) Math.round(cal_formel);
            double cal_rest = cal_formel - anz_kalorien;

            kalorien_count.setText(String.valueOf(formel_gerund));
            kalorien_anz.setText(String.valueOf(anz_kalorien));
            int rest_round = (int) (Math.round(cal_rest));
            verb_kalorien.setText(String.valueOf(rest_round));


            int anz_protein = 0;
            for (Object protein : list[1]) {
                anz_protein = anz_protein + (int) protein;
            }
            formel_gerund = (int) Math.round(prot_formel);
            double prot_rest = prot_formel - anz_protein;

            protein_ziel.setText(String.valueOf(formel_gerund));
            protein_anz.setText(String.valueOf(anz_protein));
            rest_round = (int) (Math.round(prot_rest * 10d) / 10d);
            verb_protein.setText(String.valueOf(rest_round));

            fruh_kalorien.setText(String.valueOf(get_meal_cal(1)));
            mit_kalorien.setText(String.valueOf(get_meal_cal(2)));
            abend_kalorien.setText(String.valueOf(get_meal_cal(3)));
            snack_kalorien.setText(String.valueOf(get_meal_cal(4)));

            get_select(fruhstuck_list, fruhstuck,1);

            get_select(mittagessen_list, mittagessen,2);

            get_select(abendessen_list, abendessen,3);

            get_select(snacks_list, snacks,4);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int get_meal_cal(int mahlzeit_id) {
        int anz_kalorien = 0;
        try {
            resultSet = statement.executeQuery("SELECT kalorien FROM mmm WHERE ben = '" + id + "' AND datum = '" + ft.format(date) + "' AND mahlzeit = " + mahlzeit_id + "");
            ArrayList<Integer> kalories = new ArrayList<>();
            while (resultSet.next()){
                int kalorien = resultSet.getInt("kalorien");
                kalories.add(kalorien);
            }
            resultSet.close();
            for (Integer kalory : kalories) {
                anz_kalorien = anz_kalorien + kalory;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return anz_kalorien;
    }

    public void get_select(JList<String> list_name , DefaultListModel<String> name, int mahlzeit_id) throws SQLException {
        name.clear();
        resultSet = statement.executeQuery("SELECT Name FROM mahlzeit,mmm WHERE mmm.ben = " + id + " AND mahlzeit.ben = " + id + " AND mmm.mahl = mahlzeit.id AND mmm.datum = '" + ft.format(date) + "' AND mmm.mahlzeit = " + mahlzeit_id + "");
        ArrayList<String> names = new ArrayList<>();
        while (resultSet.next()){
            String mahl_name = resultSet.getString("Name");
            names.add(mahl_name);
        }
        resultSet.close();
        for (String s : names) {
            name.addElement(s);
        }
        list_name.setModel(name);
    }
    public int on_button(JList<String> name, int mahlzeit_id){
        String right_name = name.getSelectedValue();
        try {
            DBConnect fruh_select = new DBConnect("SELECT id FROM mahlzeit WHERE Name = '" + right_name + "'");
            fruh_select.setSql_get("id");
            fruh_select.con();
            resultSet = statement.executeQuery("SELECT id FROM mmm WHERE mahl = '" + fruh_select.getResult() + "' AND mahlzeit = " + mahlzeit_id + "");
            ArrayList<Integer> also_same_id = new ArrayList<>();
            while (resultSet.next()){
                int mahl_id = resultSet.getInt("id");
                also_same_id.add(mahl_id);
            }
            resultSet.close();
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
            return also_same_id.get(right_id);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public void on_delete(JList<String> name, int mahlzeit_id) {
        int correct_id = on_button(name,mahlzeit_id);
        DBConnect dbConnect = new DBConnect("DELETE FROM mmm WHERE id = " + correct_id + " AND mahlzeit = " + mahlzeit_id + "");
        dbConnect.con();
        content();
    }

    public void on_edit(JList<String> name, int mahlzeit_id) {
        int correct_id = on_button(name, mahlzeit_id);
        DBConnect get_mahl = new DBConnect("SELECT mahl, port FROM mmm WHERE id = " + correct_id + " AND mahlzeit = " + mahlzeit_id + "");
        get_mahl.setSql_get("mahl");
        get_mahl.con();
        String mahl = get_mahl.getResult();

        DBConnect get_name = new DBConnect("SELECT Name FROM mahlzeit WHERE id = " + mahl + "");
        get_name.setSql_get("Name");
        get_name.con();
        String mahl_name = get_name.getResult();

        DBConnect get_port = new DBConnect("SELECT port FROM mmm WHERE id = " + correct_id + " AND mahlzeit = " + mahlzeit_id + "");
        get_port.setSql_get("port");
        get_port.con();
        String port = get_port.getResult();

        frame.remove(panel);
        Bearbeiten bearbeiten = new Bearbeiten(correct_id);
        bearbeiten.content();
    }

    //Sprache Anpassen für die Überschriften für die Bearbeiten seite
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 4; i++) {
            JList<String> list = this.list[i];
            if (e.getSource() == this.add[i]) {
                frame.remove(panel);
                Mahlzeit_auswahl mahlzeit_auswahl = new Mahlzeit_auswahl(mealLabels[i].getText());
                mahlzeit_auswahl.content();
            }
            if (e.getSource() == this.delete[i]) {
                on_delete(list, i+1);
            }
            if (e.getSource() == this.edit[i]) {
                on_edit(list, i+1);
            }
        }
        if (e.getSource() == einstellungen){
            frame.remove(panel);
            new Einstellungen();
        }
        if (e.getSource() == fruher){
            c.add(Calendar.DATE, -1);
            date = c.getTime();
            dat();
            content();
        }
        if (e.getSource() == spater){
            c.add(Calendar.DATE, 1);
            date = c.getTime();
            dat();
            content();
        }
    }
}