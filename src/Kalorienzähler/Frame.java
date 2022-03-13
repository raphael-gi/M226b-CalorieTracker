package Kalorienzähler;

import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Frame {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setTitle("Kalorienzähler");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);

        ImageIcon logo = new ImageIcon("calories-logo.png");
        frame.setIconImage(logo.getImage());
        frame.getContentPane().setBackground(Color.DARK_GRAY);
    }
}