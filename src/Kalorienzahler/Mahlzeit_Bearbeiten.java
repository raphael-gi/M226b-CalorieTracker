package Kalorienzahler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class Mahlzeit_Bearbeiten extends Global implements ActionListener {
    private JPanel panel;
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

    private final String mahl;
    private final String mahl_name;
    private final int carb;
    private final int protein;
    private final int fat;

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

    //2 Dimensionaler Array mit allen Sprachen Arrays
    String [][] spracharr = {name_list, carb_list, fat_list, protein_list, bearbeiten_list, zuruck_list, loeschen_list};

    public Mahlzeit_Bearbeiten(String mahl, String mahl_name, int carb, int protein, int fat){
        this.mahl = mahl;
        this.mahl_name = mahl_name;
        this.carb = carb;
        this.protein = protein;
        this.fat = fat;

        frame.add(panel);
        frame.revalidate();
        frame.repaint();

        for (JButton but : all_buttons){
            but.addActionListener(this);
        }
        loeschen.addActionListener(this);
        content();
        sprach();
    }
    public void sprach(){
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
        //Labels werden angeschrieben
        name_input.setText(mahl_name);
        carb_model.setValue(carb);
        protein_model.setValue(protein);
        fat_model.setValue(fat);

        carb_input.setModel(carb_model);
        protein_input.setModel(protein_model);
        fat_input.setModel(fat_model);

        new Darkmode(all_buttons, all_labels);
        if (darkmode){
            panel.setBackground(Color.DARK_GRAY);
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

    public void newFrame() {
        frame.remove(panel);
        Mahlzeit_auswahl mahlzeit_auswahl = new Mahlzeit_auswahl(this.mahl);
        mahlzeit_auswahl.content();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bearbeiten){
            //Variabeln werden deklariert
            String name = name_input.getText();
            int carb = (int) carb_input.getValue();
            int protein = (int) protein_input.getValue();
            int fat = (int) fat_input.getValue();
            int kalorien = (carb * 4) + (protein * 4) + (fat * 9);

            DBConnect dbConnect = new DBConnect("UPDATE mahlzeit SET Name = '" + name + "', kalorien = '" + kalorien + "', carb = '" + carb_input.getValue() + "', protein = '" + protein_input.getValue() + "', fat = '" + fat_input.getValue() + "' WHERE Name = '" + mahl_name + "' AND ben = " + id + "");
            dbConnect.con();
            newFrame();
        }
        if (e.getSource() == loeschen){
            DBConnect dbConnect = new DBConnect("DELETE FROM mahlzeit WHERE ben = " + id + " AND Name = '" + mahl_name + "'");
            dbConnect.con();

            newFrame();
        }
        if (e.getSource() == zuruck){
            newFrame();
        }
    }
}