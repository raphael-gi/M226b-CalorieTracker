package Kalorienzahler;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Frame {
    public static void main(String[] args){
        //JFrame frame = new JFrame();
        //frame.setTitle("Kalorienzahler");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(500, 500);
        //frame.setVisible(true);
//
        //ImageIcon logo = new ImageIcon("calories-logo.png");
        //frame.setIconImage(logo.getImage());
        //frame.getContentPane().setBackground(Color.DARK_GRAY);

        String[] arr1 = {"test1", "test2"};
        String[] arr2 = {"doop1", "doop2"};

        String[][] bip = {arr1, arr2};

        System.out.println(bip[0][1]);
    }
}