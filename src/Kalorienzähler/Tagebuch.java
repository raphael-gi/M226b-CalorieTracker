package Kalorienzähler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tagebuch implements ActionListener {
    private JPanel panel1;
    private JButton Frühstück;
    private JButton Mittagessen;
    private JButton Abendessen;
    private JFrame frame;

    private String name;

    public Tagebuch(String name){
        this.name = name;

        frame = new JFrame("Tagebuch");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500,400));

        frame.add(panel1);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Frühstück.addActionListener(this);
        Mittagessen.addActionListener(this);
        Abendessen.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Frühstück){
            frame.dispose();
            new Mahlzeit_auswahl("Frühstück");
        }
        if (e.getSource() == Mittagessen){
            frame.dispose();
            new Mahlzeit_auswahl("Mittagessen");
        }
        if (e.getSource() == Abendessen){
            frame.dispose();
            new Mahlzeit_auswahl("Abendessen");
        }
    }
}