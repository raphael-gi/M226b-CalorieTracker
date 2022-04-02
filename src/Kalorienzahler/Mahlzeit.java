package Kalorienzahler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class Mahlzeit implements ActionListener {
    private final JFrame frame;
    private JPanel panel1;
    private JButton Erstellen;
    private JTextField Name;
    private JLabel error_message;
    private JButton zuruck;
    private JLabel name_label;
    private JLabel carb_label;
    private JLabel protein_label;
    private JLabel fat_label;
    private JSpinner carb_input;
    private JSpinner protein_input;
    private JSpinner fat_input;

    private final String mahlzeit;
    private final String benutzername;

    private final Date date_selected;
    String [] name_list = {"Mahlzeitame","Mealname"};
    String [] cal_list={"Kohlenhydrate:","Carbohydrates:"};
    String [] protein_list={"Protein","Protein"};
    String [] fat_list={"Fett:","Fat:"};
    String [] zuruck_list = {"Zurück","Back"};
    String [] Erstellen_list = {"Erstellen","Create"};
    String [][] spracharr = {name_list, cal_list, protein_list, fat_list, Erstellen_list, zuruck_list};

    JButton[] all_buttons = {Erstellen, zuruck};
    JLabel[] all_labels = {name_label, carb_label, protein_label, fat_label};

    public Mahlzeit(Dimension size, Point loc, String mahlzeit, String benutzername, Date datum){
        this.mahlzeit = mahlzeit;
        this.benutzername = benutzername;
        this.date_selected = datum;

        frame = new JFrame();
        new StarterPack(frame, panel1, "Mahlzeit Erstellen", size, loc);

        Erstellen.addActionListener(this);
        zuruck.addActionListener(this);

        content();
    }

    public void content(){
        Darkmode n = new Darkmode(this.benutzername, all_buttons, all_labels);
        if (n.isDark()){
            panel1.setBackground(Color.DARK_GRAY);
            all_buttons = n.getAll_buttons();
            all_labels = n.getAll_labels();
            sprach();
        }
    }
    public void sprach(){
        DBConnect get_sprache = new DBConnect("SELECT sprache FROM benutzer WHERE Benutzername = '" + this.benutzername + "'", "sprache", 0);
        int sprache = Integer.parseInt(get_sprache.getResult());
        int len = all_labels.length + all_buttons.length;
        int ii;
        for (int i = 0; len > i; i++){
            if (all_labels.length > i){
                all_labels[i].setText(spracharr[i][sprache]);
            }
            else {
                ii = i - all_labels.length;
                all_buttons[ii].setText(spracharr[i][sprache]);
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==Erstellen){
            String name = Name.getText();
            int carb = (int) carb_input.getValue();
            int protein = (int) protein_input.getValue();
            int fat = (int) fat_input.getValue();

            error_message.setText("");

            if (name.isEmpty() || carb_input.getValue() == null || protein_input.getValue() == null || fat_input.getValue() == null){
                error_message.setText("Füllen sie alle Felder aus!");
            }
            else {
                if (name.length() > 90){
                    error_message.setText("Der Name ist zu lange!");
                }
                else {
                    DBConnect check_id = new DBConnect("SELECT id FROM benutzer WHERE Benutzername = '" + this.benutzername + "'", "id", 0);
                    String id = check_id.getResult();

                    DBConnect check_name = new DBConnect("SELECT Name FROM mahlzeit WHERE Name = '" + name + "' AND ben = " + id + "", "Name", 0);
                    if (check_name.getResult() != null){
                        error_message.setText("Mahlzeit exestiert bereits. Wählen sie einen anderen Namen!");
                    }
                    else {
                        try{
                            int kalorien = carb * 4 + protein * 4 + fat * 9;

                            DBConnect get_id = new DBConnect("SELECT id FROM benutzer WHERE Benutzername = '" + this.benutzername + "'","id",0);
                            int userid = Integer.parseInt(get_id.getResult());

                            new DBConnect("INSERT INTO mahlzeit (Name, kalorien, carb, protein, fat, ben) VALUES ('" + name +"', '" + kalorien +"', '" + carb + "', '" + protein + "', '" + fat + "', '" + userid + "')", "Benutzername", 1);

                            frame.dispose();
                            Dimension frame_size = frame.getSize();
                            Point frame_loc = frame.getLocation();
                            Mahlzeit_auswahl mahl = new Mahlzeit_auswahl(frame_size, frame_loc, this.mahlzeit, this.benutzername, date_selected);
                            mahl.content();
                        }
                        catch (Exception E){
                            error_message.setText("Geben sie als Wert Zahlen an!");
                        }
                    }
                }
            }
        }

        if (e.getSource() == zuruck){
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Mahlzeit_auswahl n = new Mahlzeit_auswahl(frame_size, frame_loc, this.mahlzeit, this.benutzername, date_selected);
            n.content();
        }
    }
}