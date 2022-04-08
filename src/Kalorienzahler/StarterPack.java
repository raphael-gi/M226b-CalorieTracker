package Kalorienzahler;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class StarterPack {
    StarterPack(JFrame frame, JPanel panel, String title, Dimension size, Point loc){
        //Title wird gesetzt
        frame.setTitle(title);
        //Frame wird richtig geschlossen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Frame Logo
        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("calories-logo.png")));
        frame.setIconImage(image.getImage());

        frame.add(panel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //Gösse und Location werden gesetzt
        frame.setSize(size);
        frame.setLocation(loc);
    }
}