package Kalorienzahler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Darkmode {
    private boolean dark;

    private final JButton[] all_buttons;
    private final JLabel[] all_labels;

    private final String benutzername;

    Darkmode(String benutzername, JButton[] all_buttons, JLabel[] all_labels){
        this.benutzername = benutzername;
        this.all_buttons = all_buttons;
        this.all_labels = all_labels;

        check();
        if (dark){
            darken();
        }
        else {
            brighten();
        }
    }

    public JButton[] getAll_buttons() {
        return all_buttons;
    }

    public JLabel[] getAll_labels() {
        return all_labels;
    }

    public boolean isDark() {
        return dark;
    }
    public void check(){
        //Wird geschaut ob Darkmode an oder aus ist
        DBConnect check = new DBConnect("SELECT dark FROM benutzer WHERE Benutzername = '" + benutzername + "'", "dark", 0);
        int dark_check = Integer.parseInt(check.getResult());
        dark = dark_check != 0;
    }

    public void darken(){
        //Buttons werden Dunkel gemacht
        for (JButton but : all_buttons){
            but.setBackground(Color.GRAY);
            but.addMouseListener(new MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    but.setForeground(Color.black);
                    but.setBackground(Color.LIGHT_GRAY);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    but.setForeground(Color.darkGray);
                    but.setBackground(Color.GRAY);
                }
            });
        }
        for (JLabel lab : all_labels){
            lab.setForeground(Color.white);
        }
    }
    public void brighten(){
        //Buttons werden Hell gemacht
        for (JButton but : all_buttons){
            but.setBackground(new JButton().getBackground());
            but.setForeground(new JButton().getForeground());
            but.addMouseListener(new MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    but.setForeground(new JButton().getForeground());
                    but.setBackground(new JButton().getBackground());
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    but.setForeground(new JButton().getForeground());
                    but.setBackground(new JButton().getBackground());
                }
            });
        }
        for (JLabel lab : all_labels){
            lab.setForeground(Color.BLACK);
        }
    }
}