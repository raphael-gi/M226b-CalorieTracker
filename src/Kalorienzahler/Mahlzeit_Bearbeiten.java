package Kalorienzahler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.*;
import java.util.Date;

public class Mahlzeit_Bearbeiten implements ActionListener {
    private JPanel panel1;
    private JButton bearbeiten;
    private JTextField name_input;
    private JLabel name_label;
    private JLabel carb_label;
    SpinnerNumberModel carb_model = new SpinnerNumberModel(1, 0, 100000, 1);
    private JSpinner carb_input;
    SpinnerNumberModel protein_model = new SpinnerNumberModel(1, 0, 100000, 1);
    private JSpinner protein_input;
    SpinnerNumberModel fat_model = new SpinnerNumberModel(1, 0, 100000, 1);
    private JSpinner fat_input;
    private JLabel fat_label;
    private JLabel protein_label;
    private JButton zuruck;
    private JButton loeschen;
    private final JFrame frame;

    private final String benutzername;
    private final String mahl;
    private final String mahl_name;
    private final int carb;
    private final int protein;
    private final int fat;

    private final Date date_selected;

    //Arrays mit Sprachen
    String[] carb_list = {"Kohlenhydrate:","Carbohydrates:"};
    String [] name_list={"Name:","Name:"};
    String [] protein_list={"Protein:","Protein:"};
    String [] fat_list={"Fett:","Fat:"};
    String [] zuruck_list = {"Zurück","Back"};
    String [] loeschen_list = {"Löschen","Delete"};
    String [] bearbeiten_list = {"Bearbeiten","Edit"};
    JButton[] all_buttons = {bearbeiten, zuruck, loeschen};
    JLabel[] all_labels = {name_label, carb_label, fat_label, protein_label,};
    String [][] spracharr = {name_list, carb_list, fat_list, protein_list, bearbeiten_list, zuruck_list, loeschen_list};

    //Array mit allen Sprachen Arrays
    String [][] spracharr = {name_list, carb_list, fat_list, protein_list, bearbeiten_list, zuruck_list, loeschen_list};

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public Mahlzeit_Bearbeiten(Dimension size, Point loc, String benutzername, String mahl, String mahl_name, int carb, int protein, int fat, Date datum){
        //Verbindung wird aufgebaut
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.date_selected = datum;
        this.benutzername = benutzername;
        this.mahl = mahl;
        this.mahl_name = mahl_name;
        this.carb = carb;
        this.protein = protein;
        this.fat = fat;

        frame = new JFrame();
        new StarterPack(frame, panel1, "Mahlzeit Bearbeiten", size, loc);

        for (JButton but : all_buttons){
            but.addActionListener(this);
        }
        loeschen.addActionListener(this);
        content();
        sprach();
    }
    public void sprach(){
        DBConnect get_sprache = new DBConnect("SELECT sprache FROM benutzer WHERE Benutzername = '" + this.benutzername + "'", "sprache", 0);
        int sprache = Integer.parseInt(get_sprache.getResult());
        int len = all_labels.length + all_buttons.length;
        int ii;
        for (int i = 0; len > i; i++){
            if (all_labels.length > i){
                all_labels[i].setText(spracharr[i][sprache]);
            }
            else {
                ii = i - all_labels.length;
                all_buttons[ii].setText(spracharr[i][sprache]);
            }
        }
    }
    public void content(){
        name_input.setText(mahl_name);
        carb_model.setValue(carb);
        protein_model.setValue(protein);
        fat_model.setValue(fat);

        carb_input.setModel(carb_model);
        protein_input.setModel(protein_model);
        fat_input.setModel(fat_model);

        Darkmode n = new Darkmode(this.benutzername, all_buttons, all_labels);
        if (n.isDark()){
            panel1.setBackground(Color.DARK_GRAY);
            all_buttons = n.getAll_buttons();
            all_labels = n.getAll_labels();
            loeschen.addMouseListener(new MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    loeschen.setForeground(Color.RED);
                    loeschen.setBackground(Color.LIGHT_GRAY);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    loeschen.setForeground(Color.RED);
                    loeschen.setBackground(Color.GRAY);
                }
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bearbeiten){
            String name = name_input.getText();
            int carb = (int) carb_input.getValue();
            int protein = (int) protein_input.getValue();
            int fat = (int) fat_input.getValue();
            int kalorien = (carb * 4) + (protein * 4) + (fat * 9);

            int id = 0;
            try {
                resultSet = statement.executeQuery("SELECT id FROM benutzer WHERE Benutzername = '" + benutzername + "'");
                while (resultSet.next()){
                    id = resultSet.getInt("id");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            new DBConnect("UPDATE mahlzeit SET Name = '" + name + "', kalorien = '" + kalorien + "', carb = '" + carb_input.getValue() + "', protein = '" + protein_input.getValue() + "', fat = '" + fat_input.getValue() + "' WHERE Name = '" + mahl_name + "' AND ben = " + id + "", " ", 1);
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Mahlzeit_auswahl n = new Mahlzeit_auswahl(frame_size, frame_loc, this.mahl, this.benutzername, date_selected);
            n.content();
        }
        if (e.getSource() == loeschen){
            DBConnect id = new DBConnect("SELECT id FROM benutzer WHERE Benutzername = '" + benutzername + "'", "id", 0);

            new DBConnect("DELETE FROM mahlzeit WHERE ben = " + id.getResult() + " AND Name = '" + mahl_name + "'", "", 1);

            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Mahlzeit_auswahl n = new Mahlzeit_auswahl(frame_size, frame_loc, this.mahl, this.benutzername, date_selected);
            n.content();
        }
        if (e.getSource() == zuruck){
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Mahlzeit_auswahl n = new Mahlzeit_auswahl(frame_size, frame_loc, this.mahl, this.benutzername, date_selected);
            n.content();
        }
    }
}