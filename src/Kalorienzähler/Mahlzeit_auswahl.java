package Kalorienzähler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Mahlzeit_auswahl implements ActionListener {
    private JButton erstellen;
    private JPanel panel1;
    private JLabel Mahlzeit;
    private JFrame frame;

    private String mahlzeit;

    public Mahlzeit_auswahl(String mahlzeit){
        this.mahlzeit = mahlzeit;

        frame = new JFrame("Mahlzeit Auswählen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500,400));

        frame.add(panel1);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        erstellen.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==erstellen){
            frame.dispose();
            new Mahlzeit(this.mahlzeit);
        }
    }
}
