package Kalorienzahler;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class StarterPack {
    private final JFrame frame = new JFrame();
    StarterPack(JPanel panel, String title){
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("calories-logo.png")));
        frame.setIconImage(image.getImage());

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public void setDimensions(Dimension size, Point loc) {
        frame.setSize(size);
        frame.setLocation(loc);
    }
    public JFrame getFrame() {
        return frame;
    }
}