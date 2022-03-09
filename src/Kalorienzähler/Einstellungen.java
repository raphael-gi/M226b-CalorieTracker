package Kalorienzähler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Einstellungen implements ActionListener {
    private JLabel benutzername;
    private JPanel panel1;
    private JButton dark;

    private JFrame frame;

    private Dimension size;
    private Point loc;
    private String name;
    private boolean darkmode;

    public Einstellungen(Dimension size, Point loc, String name){
        this.size = size;
        this.loc = loc;
        this.name = name;

        frame = new JFrame("Einstellungen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(panel1);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.setSize(this.size);
        frame.setLocation(loc);

        DBConnect darkcon = new DBConnect("SELECT dark FROM benutzer WHERE Benutzername = '" + this.name + "'", "dark", 0);
        darkcon.con();
        int numb = Integer.parseInt(darkcon.getResult());
        if (numb == 0){
            this.darkmode = false;
        }
        else {
            this.darkmode = true;

            panel1.setBackground(Color.DARK_GRAY);
        }

        dark.addActionListener(this);
    }
    public void content(){
        benutzername.setText(this.name);

        if (!this.darkmode){
            dark.setText("Aus");
        }
        else {
            dark.setText("An");

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dark){
            if (!this.darkmode){
                DBConnect turn_light = new DBConnect("UPDATE benutzer SET dark = " + 1 + "", " ", 1);
                turn_light.con();
            }
            else {
                DBConnect turn_dark = new DBConnect("UPDATE benutzer SET dark = " + 0 + "", " ", 1);
                turn_dark.con();
            }
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Einstellungen n = new Einstellungen(frame_size, frame_loc, this.name);
            n.content();
        }
    }
}