package Kalorienzahler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Mahlzeit_Bearbeiten implements ActionListener {
    private JPanel panel1;
    private JButton bearbeiten;
    private JTextField name_input;
    private JLabel name_label;
    private JSpinner carb_input;
    private JLabel carb_label;
    private JSpinner protein_input;
    private JSpinner fat_input;
    private JLabel fat_label;
    private JLabel protein_label;
    private JButton zuruck;
    private JFrame frame;

    private Dimension size;
    private Point loc;

    private String benutzername;
    private String mahl;
    private String mahl_name;
    private int carb;
    private int protein;
    private int fat;

    JButton[] all_buttons = {bearbeiten, zuruck};
    JLabel[] all_labels = {name_label, carb_label, fat_label, protein_label,};

    public Mahlzeit_Bearbeiten(Dimension size, Point loc, String benutzername, String mahl, String mahl_name, int carb, int protein, int fat){
        this.benutzername = benutzername;
        this.mahl_name = mahl_name;
        this.carb = carb;
        this.protein = protein;
        this.fat = fat;
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(panel1);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        this.size = size;
        this.loc = loc;

        frame.setSize(this.size);
        frame.setLocation(loc);

        for (int i = 0; all_buttons.length > i; i++){
            JButton but = all_buttons[i];
            but.addActionListener(this);
        }
        contenttt();
    }

    public void contenttt(){
        name_input.setText(mahl_name);
        carb_input.setValue(carb);
        protein_input.setValue(protein);
        fat_input.setValue(fat);

        Darkmode n = new Darkmode(this.benutzername, all_buttons, all_labels);
        if (n.isDark()){
            panel1.setBackground(Color.DARK_GRAY);
            all_buttons = n.getAll_buttons();
            all_labels = n.getAll_labels();
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

            new DBConnect("UPDATE mahlzeit SET Name = '" + name + "', kalorien = '" + kalorien + "', carb = '" + carb_input.getValue() + "', protein = '" + protein_input.getValue() + "', fat = '" + fat_input.getValue() + "'", " ", 1);
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Mahlzeit_auswahl n = new Mahlzeit_auswahl(frame_size, frame_loc, this.mahl_name, this.benutzername);
            n.content();
        }
        if (e.getSource() == zuruck){
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Mahlzeit_auswahl n = new Mahlzeit_auswahl(frame_size, frame_loc, this.mahl_name, this.benutzername);
            n.content();
        }
    }
}