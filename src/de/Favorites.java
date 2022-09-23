package de;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Favorites implements ActionListener {

    private final JFrame frame;
    private JPanel panel;
    private JLabel titel;
    private JTable table;
    DefaultTableModel tableModel = new DefaultTableModel();
    private JButton homeButton;
    private JButton removeButton;

    private final ArrayList<ArrayList<Object>> all;
    private final ArrayList<Integer> favs;

    Favorites(ArrayList<Integer> favs, ArrayList<ArrayList<Object>> arrayList){
        frame = new JFrame();
        frame.setVisible(true);
        frame.add(panel);

        this.all = arrayList;
        this.favs = favs;

        table.getTableHeader().setReorderingAllowed(false);

        Font titel = new Font("Arial", Font.BOLD, 20);
        this.titel.setFont(titel);

        homeButton.addActionListener(this);
        removeButton.addActionListener(this);

        //Die Columns werden gesetzt
        tableModel.addColumn("Name");
        tableModel.addColumn("Attributs");
        tableModel.addColumn("Description");
        tableModel.addColumn("FAV");

        for (int i = 0; this.all.size()> i; i++){
            ArrayList<Object> mysarr;
            mysarr=this.all.get(i);
            for (int j : favs){
                if (j == i){
                    tableModel.addRow(mysarr.toArray());
                }
            }
        }
        table.setModel(tableModel);
    }

    public void setDimensions(Point ort, Dimension gross) {
        frame.setLocation(ort);
        frame.setSize(gross);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == homeButton){
            frame.dispose();
            HomePanel homePanel = new HomePanel(all, favs);
            homePanel.setDimensions(frame.getLocation(), frame.getSize());
            homePanel.showGames();
        }
        if (e.getSource() == removeButton){
            if (table.getSelectedRow() != -1){
                int index = table.getSelectedRow();
                tableModel.removeRow(index);
                favs.remove(index);
            }
        }
    }
}