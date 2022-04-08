package Kalorienzahler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Date;

public class Bearbeiten implements ActionListener {
    private JPanel panel1;
    private JButton bearbeiten;
    private JComboBox <String> dropname;
    private JButton confirm;
    private JLabel anz_kalorien;
    private JLabel anz_carbs;
    private JLabel anz_protein;
    private JLabel anz_fat;
    private JLabel error_message;
    private JButton zuruck;
    private JButton hidden;
    private JSpinner portionen;
    private JLabel anz_port_label;
    private JLabel kal_label;
    private JLabel cal_label;
    private JLabel protein_label;
    private JLabel fat_label;
    private JLabel head_label;
    private final JFrame frame;

    private final String benutzername;
    private final String mahl_name;
    private final String Portion;
    private final int mmm_id;

    private final Date date_select;

    private int userid;
    private double anz_portionen;

    //Arrays mit Sprachen
    String [] anz_port_list = {"Anzahl Portionen", "Number of portions"};
    String [] kal_list={"Kalorien:","Calories:"};
    String [] cal_list={"Kohlenhydrate:","Carbohydrates:"};
    String [] protein_list={"Protein","Protein"};
    String [] fat_list={"Fett:","Fat:"};
    String [] head_list={"Mahlzeit Bearbeiten","Edit meal"};
    String [] zuruck_list = {"Zurück","Back"};
    String [] bearbeiten_list = {"Bearbeiten","Edit"};

    //Array mit allen Sprachen Arrays
    String [][] spracharr = {anz_port_list, kal_list, cal_list, protein_list, fat_list, head_list, zuruck_list, bearbeiten_list};
    JLabel [] lab_lang = {anz_port_label, kal_label, cal_label, protein_label, fat_label, head_label};
    JButton [] but_lang = {zuruck, bearbeiten};

    JButton[] all_buttons = {bearbeiten, confirm, zuruck, hidden};
    JLabel[] all_labels = {anz_kalorien, anz_carbs, anz_protein, anz_fat, anz_port_label, kal_label, cal_label, protein_label, fat_label, head_label};

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public Bearbeiten(Dimension size, Point loc, String benutzername, String mahl_name, String portion, int mmm_id, Date datum){
        try {
            //Verbindung zur DB wird aufgebaut
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.benutzername = benutzername;
        this.Portion = portion;
        this.mahl_name = mahl_name;
        this.mmm_id = mmm_id;
        this.date_select = datum;

        frame = new JFrame();
        new StarterPack(frame, panel1, "Bearbeiten", size, loc);

        hidden.setVisible(false);

        //Action Listeners wird hinzugefügt
        dropname.addActionListener(this);
        for (JButton but : all_buttons){
            but.addActionListener(this);
        }
        SpinnerNumberModel model = new SpinnerNumberModel(1, 0.0, 100000.0, 1);
        portionen.setModel(model);
        portionen.addChangeListener(e -> {
            anz_portionen = (double) portionen.getValue();
            hidden.doClick();
        });
        sprach();
    }
    public void sprach(){
        //Ausgewählte Sprache des Benutzers wird angepasst
        DBConnect get_sprache = new DBConnect("SELECT sprache FROM benutzer WHERE Benutzername = '" + this.benutzername + "'", "sprache", 0);
        int sprache = Integer.parseInt(get_sprache.getResult());
        int len = lab_lang.length + but_lang.length;
        int ii;
        //Alle Labels und Buttons werden auf die gewünschte Sprache übersetzt
        for (int i = 0; len > i; i++){
            if (lab_lang.length > i){
                lab_lang[i].setText(spracharr[i][sprache]);
            }
            else {
                ii = i - lab_lang.length;
                but_lang[ii].setText(spracharr[i][sprache]);
            }
        }
    }
    public void set_data(String what, JLabel name) {
        String mahl_name = String.valueOf(dropname.getSelectedItem());
        try {
            DBConnect get_mahl = new DBConnect("SELECT * FROM mahlzeit WHERE Name = '" + mahl_name + "'", what, 0);
            String mahl = get_mahl.getResult();
            int mahl_int = Integer.parseInt(mahl);
            double mahl_double = mahl_int * this.anz_portionen;
            double mahl_round = Math.round(mahl_double * 10d) / 10d;
            String mahl_final = String.valueOf(mahl_round);
            name.setText(mahl_final);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void content(){
        //Darkmode wird gemacht
        Darkmode d = new Darkmode(this.benutzername, all_buttons, all_labels);
        if (d.isDark()){
            panel1.setBackground(Color.DARK_GRAY);
        }

        double Portion = Double.parseDouble(this.Portion);
        portionen.setValue(Portion);
        try{
            //Verbindung um id zu erhalten
            resultSet = statement.executeQuery("SELECT * FROM benutzer WHERE Benutzername = '" + this.benutzername + "'");
            while (resultSet.next()){
                this.userid = resultSet.getInt("id");
            }
        }
        catch (Exception E){
            System.out.println("verbindung zu ID ist Fehlgeschlagen");
        }
        try{
            //Verbindung um Namen zu erhalten
            resultSet = statement.executeQuery("SELECT Name FROM mahlzeit WHERE ben = " + this.userid + " ORDER BY Name");
            while (resultSet.next()){
                String benutzernamen = resultSet.getString("Name");
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
        if (e.getSource() == zuruck){
            //Frame wird geschlossen und Tagebuch wird geöffnet
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Tagebuch n = new Tagebuch(frame_size, frame_loc, this.benutzername, date_select);
            n.content();
        }
        if (e.getSource() == dropname || e.getSource() == confirm || e.getSource() == hidden){
            String mahl_name = String.valueOf(dropname.getSelectedItem());

            //Error Message wird reseted
            error_message.setText("");
            //Error Handling beginn
            try {
                resultSet = statement.executeQuery("SELECT * FROM mahlzeit WHERE name = '" + mahl_name + "'");

                try {
                    this.anz_portionen = (double)portionen.getValue();
                }
                catch (Exception E){
                    error_message.setText("Geben sie eine Zahl als Portion an!");
                }

                if (error_message.getText().isEmpty()){
                    while (resultSet.next()){
                        //Anzahl Kohlenhydrate werden abgerufen
                        set_data("carb", anz_carbs);
                        //Anzahl Protein wird abgerufen
                        set_data("protein", anz_protein);
                        //Anzahl Fett wird abgerufen
                        set_data("fat", anz_fat);
                        //Anzahl Kalorien werden abgerufen
                        double anz_carbs = Double.parseDouble(this.anz_carbs.getText()) * 4;
                        double anz_protein = Double.parseDouble(this.anz_protein.getText()) * 4;
                        double anz_fat = Double.parseDouble(this.anz_fat.getText()) * 9;

                        double kalorien_double = anz_carbs + anz_protein + anz_fat;
                        long kalorien_long = Math.round(kalorien_double);
                        String kalorien_final = String.valueOf(kalorien_long);
                        this.anz_kalorien.setText(kalorien_final);
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == bearbeiten){
            String drop_selected = (String)dropname.getSelectedItem();
            int kalorien = Integer.parseInt(anz_kalorien.getText());
            float carb = Float.parseFloat(anz_carbs.getText());
            float protein = Float.parseFloat(anz_protein.getText());
            float fat = Float.parseFloat(anz_fat.getText());


            DBConnect get_ben = new DBConnect("SELECT id FROM benutzer WHERE Benutzername = '" + this.benutzername + "'", "id", 0);
            String ben_id = get_ben.getResult();

            DBConnect get_mahl = new DBConnect("SELECT id FROM mahlzeit WHERE Name = '" + drop_selected + "' AND ben = " + ben_id + "", "id", 0);
            String mahl = get_mahl.getResult();
            //verändert das ausgewählte mahl in der DB
            new DBConnect("UPDATE mmm SET mahl = " + mahl + ", port = " + portionen.getValue() + ", kalorien = " + kalorien + ", carb = " + carb + ", protein = " + protein + ", fat = " + fat + " WHERE id = " + this.mmm_id + "", " ", 1);

            //Frame wird geschlossen und Tagebuch wird geöffnet
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Tagebuch n = new Tagebuch(frame_size, frame_loc, this.benutzername, date_select);
            n.content();
        }
    }
}