package Kalorienzähler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bearbeiten implements ActionListener {
    private JPanel panel1;
    private JButton bearbeiten;
    private JComboBox dropname;
    private JButton plus;
    private JButton minus;
    private JButton confirm;
    private JLabel anz_kalorien;
    private JLabel anz_carbs;
    private JLabel anz_protein;
    private JLabel anz_fat;
    private JLabel error_message;
    private JFrame frame;

    private Dimension size;
    private Point loc;

    public Bearbeiten(Dimension size, Point loc){
        frame = new JFrame("Bearbeiten");
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
            System.out.println("hi");
        }
    }
}