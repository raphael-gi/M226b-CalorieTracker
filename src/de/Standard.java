package de;

import java.util.ArrayList;

public class Standard {
    private final ArrayList<Object> data_default = new ArrayList<>(){{
        add("Terraria");
        add("Cool");
        add("Sandbox Based game");
        add(true);
    }};
    private final ArrayList<Object> data_default2 = new ArrayList<>(){{
        add("2Terraria");
        add("2Cool");
        add("2Sandbox Based game");
        add(false);
    }};

    public ArrayList<Object> getData_default() {
        return data_default;
    }

    public ArrayList<Object> getData_default2() {
        return data_default2;
    }
}