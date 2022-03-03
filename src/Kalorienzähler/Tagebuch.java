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
    private JButton Snacks;
    private JFrame frame;

    private String benutzername;

    public Tagebuch(String benutzername){
        this.benutzername = benutzername;

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
        Snacks.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Frühstück){
            frame.dispose();
            Mahlzeit_auswahl mahl = new Mahlzeit_auswahl("Frühstück", this.benutzername);
            mahl.content();
        }
        if (e.getSource() == Mittagessen){
            frame.dispose();
            Mahlzeit_auswahl mahl = new Mahlzeit_auswahl("Mittagessen", this.benutzername);
            mahl.content();
        }
        if (e.getSource() == Abendessen){
            frame.dispose();
            Mahlzeit_auswahl mahl = new Mahlzeit_auswahl("Abendessen", this.benutzername);
            mahl.content();
        }
        if (e.getSource() == Snacks){
            frame.dispose();
            Mahlzeit_auswahl mahl = new Mahlzeit_auswahl("Snacks", this.benutzername);
            mahl.content();
        }
    }
}