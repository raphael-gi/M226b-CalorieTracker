package Kalorienzahler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Mahlzeit_Bearbeiten implements ActionListener {
    private JButton bearbeiten;
    private JTextField name_input;
    private JLabel name_label;
    private JSpinner carb_input;
    private JLabel carb_label;
    private JSpinner protein_input;
    private JSpinner fat_input;
    private JLabel fat_label;
    private JPanel panel1;
    private JFrame frame;

    private Dimension size;
    private Point loc;

    private String benutzername;
    private String mahl_name;
    private int carb;
    private int protein;
    private int fat;

    public Mahlzeit_Bearbeiten(Dimension size, Point loc, String benutzername, String mahl_name, int carb, int protein, int fat){
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

        bearbeiten.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bearbeiten){

        }
    }
}