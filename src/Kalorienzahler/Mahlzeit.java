package Kalorienzahler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Mahlzeit implements ActionListener {
    private JFrame frame;
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

    private String mahlzeit;
    private String benutzername;

    private Dimension size;
    private Point loc;

    JButton[] all_buttons = {Erstellen, zuruck};
    JLabel[] all_labels = {name_label, carb_label, protein_label, fat_label};
    JSpinner[] all_spinners = {carb_input, protein_input, fat_input};

    public Mahlzeit(Dimension size, Point loc, String mahlzeit, String benutzername){
        this.size = size;
        this.loc = loc;

        this.mahlzeit = mahlzeit;
        this.benutzername = benutzername;

        frame = new JFrame("Mahlzeit Erstellen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(panel1);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.setSize(this.size);
        frame.setLocation(this.loc);

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
                    check_id.con();
                    String id = check_id.getResult();

                    DBConnect check_name = new DBConnect("SELECT Name FROM mahlzeit WHERE Name = '" + name + "' AND ben = " + id + "", "Name", 0);
                    check_name.con();
                    if (check_name.getResult() != null){
                        error_message.setText("Mahlzeit exestiert bereits. Wählen sie einen anderen Namen!");
                    }
                    else {
                        try{
                            int kalorien = carb * 4 + protein * 4 + fat * 9;

                            DBConnect get_id = new DBConnect("SELECT id FROM benutzer WHERE Benutzername = '" + this.benutzername + "'","id",0);
                            get_id.con();
                            int userid = Integer.parseInt(get_id.getResult());

                            new DBConnect("INSERT INTO mahlzeit (Name, kalorien, carb, protein, fat, ben) VALUES ('" + name +"', '" + kalorien +"', '" + carb + "', '" + protein + "', '" + fat + "', '" + userid + "')", "Benutzername", 1);

                            frame.dispose();
                            Dimension frame_size = frame.getSize();
                            Point frame_loc = frame.getLocation();
                            Mahlzeit_auswahl mahl = new Mahlzeit_auswahl(frame_size, frame_loc, this.mahlzeit, this.benutzername);
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
            Mahlzeit_auswahl n = new Mahlzeit_auswahl(frame_size, frame_loc, this.mahlzeit, this.benutzername);
            n.content();
        }
    }
}