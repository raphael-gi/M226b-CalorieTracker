package de;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NewGame implements ActionListener {
    private final JFrame frame;
    private JPanel panel;
    private JTextField name_feld;
    private JTextField att_feld;
    private JTextField descr_feld;
    private JButton addGameButton;
    private JButton homeButton;
    private JLabel titel;
    private JCheckBox favorite;

    private final ArrayList<ArrayList<Object>> all;
    private final ArrayList<Integer> favs;

    NewGame(ArrayList<ArrayList<Object>> arrayList, ArrayList<Integer> favs) {
        frame = new JFrame();
        frame.setVisible(true);
        frame.add(panel);

        this.all = arrayList;
        this.favs = favs;

        Font titel = new Font("Arial", Font.BOLD, 20);
        this.titel.setFont(titel);

        addGameButton.addActionListener(this);
        homeButton.addActionListener(this);
    }

    public void setDimensions(Point ort, Dimension gross) {
        frame.setLocation(ort);
        frame.setSize(gross);
    }

    public HomePanel send() {
        frame.dispose();
        HomePanel homePanel = new HomePanel(all, favs);
        homePanel.setDimensions(frame.getLocation(), frame.getSize());
        return homePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addGameButton){
            String name = name_feld.getText();
            String att = att_feld.getText();
            String descr = descr_feld.getText();
            Boolean fav = favorite.isSelected();

            if (!name.isEmpty() && !att.isEmpty() && !descr.isEmpty()){
                HomePanel homePanel = send();
                homePanel.addGame(name, att, descr, fav);
            }
        }
        if (e.getSource() == homeButton){
            send();
        }
    }
}