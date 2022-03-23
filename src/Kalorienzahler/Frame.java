package Kalorienzahler;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;

public class Frame {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setTitle("Kalorienzahler");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);

        ImageIcon logo = new ImageIcon("calories-logo.png");
        frame.setIconImage(logo.getImage());
        frame.getContentPane().setBackground(Color.DARK_GRAY);
    }
}