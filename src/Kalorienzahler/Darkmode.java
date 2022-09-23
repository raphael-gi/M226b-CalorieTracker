package Kalorienzahler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Darkmode extends Global {
    private final JButton[] all_buttons;
    private final JLabel[] all_labels;

    Darkmode(JButton[] all_buttons, JLabel[] all_labels){
        this.all_buttons = all_buttons;
        this.all_labels = all_labels;

        if (darkmode){
            darken();
        }
        else {
            brighten();
        }
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