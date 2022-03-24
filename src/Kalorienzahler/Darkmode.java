package Kalorienzahler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Darkmode {
    private boolean dark;

    private JButton[] all_buttons;
    private JLabel[] all_labels;

    private String benutzername;

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
        DBConnect check = new DBConnect("SELECT dark FROM benutzer WHERE Benutzername = '" + benutzername + "'", "dark", 0);
        check.con();
        int dark_check = Integer.parseInt(check.getResult());
        if (dark_check == 0){
            dark = false;
        }
        else {
            dark = true;
        }
    }

    public void darken(){
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
        for (JButton but : all_buttons){
            but.setBackground(new JButton().getBackground());
            but.setForeground(new JButton().getForeground());
            //but.addMouseListener(new MouseAdapter() {
            //    public void mouseEntered(java.awt.event.MouseEvent evt) {
            //        but.getColorModel();
            //    }
            //    public void mouseExited(java.awt.event.MouseEvent evt) {
            //        but.setForeground(Color.darkGray);
            //        but.setBackground(Color.GRAY);
            //    }
            //});
        }
        for (JLabel lab : all_labels){
            lab.setForeground(new JLabel().getForeground());
        }
    }
}
