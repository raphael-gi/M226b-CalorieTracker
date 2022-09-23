package de;

import java.awt.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> favs = new ArrayList<>();
        ArrayList<ArrayList<Object>> all = new ArrayList<>();
        Standard standard = new Standard();
        all.add(standard.getData_default());
        all.add(standard.getData_default2());
        HomePanel homePanel = new HomePanel(all, favs);
        homePanel.setDimensions(new Point(0, 0), new Dimension(500, 500));
        homePanel.frame.setLocationRelativeTo(null);
        homePanel.showGames();
    }
}