package de;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HomePanel implements ActionListener {
    public JFrame frame;
    private JTable table;
    DefaultTableModel tableModel = new DefaultTableModel();
    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    private JPanel panel;
    private JButton addGameButton;
    private JButton deleteGameButton;
    private JButton favoriteButton;
    private JButton favoritesListButton;
    private JLabel titel;

    private final ArrayList<ArrayList<Object>> all;
    private final ArrayList<Integer> favs;

    HomePanel(ArrayList<ArrayList<Object>> arrayList, ArrayList<Integer> favs){
        frame = new JFrame();
        frame.setVisible(true);

        frame.add(panel);

        this.all = arrayList;
        this.favs = favs;

        table.getTableHeader().setReorderingAllowed(false);

        Font titel = new Font("Arial", Font.BOLD, 20);
        this.titel.setFont(titel);

        //Buttons erhalten einen Action Listener
        addGameButton.addActionListener(this);
        deleteGameButton.addActionListener(this);
        favoriteButton.addActionListener(this);
        favoritesListButton.addActionListener(this);

        //Die Columns werden gesetzt
        tableModel.addColumn("Name");
        tableModel.addColumn("Attributs");
        tableModel.addColumn("Description");
        tableModel.addColumn("FAV");

        table.setModel(tableModel);
        TableColumn col3 = table.getColumnModel().getColumn(3);
        col3.setMaxWidth(40);
        renderer.setHorizontalAlignment(JLabel.CENTER);
        col3.setCellRenderer(renderer);
    }

    public void addGame(String name, String att, String descr, Boolean fav) {
        ArrayList<Object> data = new ArrayList<>();
        data.add(name);
        data.add(att);
        data.add(descr);
        data.add(fav);
        all.add(data);
        showGames();
    }

    public void showGames() {
        for (ArrayList<Object> strings : this.all) {
            ArrayList<String> content = new ArrayList<>(){{
                add(String.valueOf(strings.get(0)));
                add(String.valueOf(strings.get(1)));
                add(String.valueOf(strings.get(2)));
            }};
            if ((boolean)strings.get(3)) {
                content.add("⭐");
            }
            else {
                content.add(" ");
            }
            tableModel.addRow(content.toArray());
        }
    }

    public void setDimensions(Point ort, Dimension gross) {
        frame.setSize(gross);
        frame.setLocation(ort);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addGameButton){
            frame.dispose();
            NewGame newGame = new NewGame(all, favs);
            newGame.setDimensions(frame.getLocation(), frame.getSize());
        }
        if (e.getSource() == favoritesListButton){
            frame.dispose();
            Favorites favorites = new Favorites(favs, all);
            favorites.setDimensions(frame.getLocation(), frame.getSize());
        }
        if (e.getSource() == deleteGameButton){
            if (table.getSelectedRow() != -1){
                int index = table.getSelectedRow();
                tableModel.removeRow(index);
                all.remove(index);
            }
        }
        if (e.getSource() == favoriteButton){
            int index = table.getSelectedRow();
            int deu = 0;
            for (int i : favs){
                if (i == index) {
                    deu = 1;
                    break;
                }
            }
            if (deu == 0) {
                favs.add(index);
            }
        }
    }
}