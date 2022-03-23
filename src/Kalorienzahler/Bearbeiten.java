package Kalorienzahler;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Date;

public class Bearbeiten implements ActionListener {
    private JPanel panel1;
    private JButton bearbeiten;
    private JComboBox dropname;
    private JButton confirm;
    private JLabel anz_kalorien;
    private JLabel anz_carbs;
    private JLabel anz_protein;
    private JLabel anz_fat;
    private JLabel error_message;
    private JButton zuruck;
    private JButton hidden;
    private JSpinner portionen;
    private JLabel anz_port_label;
    private JLabel kal_label;
    private JLabel cal_label;
    private JLabel protein_label;
    private JLabel fat_label;
    private JLabel head_label;
    private JFrame frame;

    private Dimension size;
    private Point loc;

    private String benutzername;
    private String mahl_name;
    private String Portion;
    private int mmm_id;

    private Date date_select;

    private int userid;
    private double anz_portionen;

    JButton[] all_buttons = {bearbeiten, confirm, zuruck, hidden};
    JLabel[] all_labels = {anz_kalorien, anz_carbs, anz_protein, anz_fat, anz_port_label, kal_label, cal_label, protein_label, fat_label, head_label};

    public Bearbeiten(Dimension size, Point loc, String benutzername, String mahl_name, String portion, int mmm_id, Date datum){
        this.benutzername = benutzername;
        this.Portion = portion;
        this.mahl_name = mahl_name;
        this.mmm_id = mmm_id;
        this.date_select = datum;

        frame = new JFrame("Bearbeiten");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(panel1);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        this.size = size;
        this.loc = loc;

        frame.setSize(this.size);
        frame.setLocation(loc);

        hidden.setVisible(false);

        dropname.addActionListener(this);
        confirm.addActionListener(this);
        bearbeiten.addActionListener(this);
        zuruck.addActionListener(this);
        hidden.addActionListener(this);
        SpinnerNumberModel model = new SpinnerNumberModel(1, 0.0, 100000.0, 1);
        portionen.setModel(model);
        portionen.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                anz_portionen = (double) portionen.getValue();
                hidden.doClick();
            }
        });
    }
    public void set_data(String what, JLabel name) {
        String mahl_name = String.valueOf(dropname.getSelectedItem());
        try {
            DBConnect get_mahl = new DBConnect("SELECT * FROM mahlzeit WHERE Name = '" + mahl_name + "'", what, 0);
            get_mahl.con();
            String mahl = get_mahl.getResult();
            int mahl_int = Integer.parseInt(mahl);
            double mahl_double = mahl_int * this.anz_portionen;
            double mahl_round = Math.round(mahl_double * 10d) / 10d;
            String mahl_final = String.valueOf(mahl_round);
            name.setText(mahl_final);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void content(){
        Darkmode n = new Darkmode(this.benutzername, all_buttons, all_labels);
        if (n.isDark()){
            panel1.setBackground(Color.DARK_GRAY);
            all_buttons = n.getAll_buttons();
            all_labels = n.getAll_labels();
        }
        double Portion = Double.parseDouble(this.Portion);
        portionen.setValue(Portion);

        try{
            //Verbindung um id zu erhalten
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM benutzer WHERE Benutzername = '" + this.benutzername + "'");
            while (resultSet.next()){
                int userid = resultSet.getInt("id");
                this.userid = userid;
            }
        }
        catch (Exception E){
            System.out.println("verbindung zu ID ist Fehlgeschlagen");
        }
        try{
            //Verbindung um Namen zu erhalten
            Connection new_connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
            Statement new_statement = new_connection.createStatement();
            ResultSet new_resultSet = new_statement.executeQuery("SELECT Name FROM mahlzeit WHERE ben = " + this.userid + " ORDER BY Name");
            while (new_resultSet.next()){
                String benutzernamen = new_resultSet.getString("Name");
                dropname.addItem(benutzernamen);
            }
            dropname.setSelectedItem(this.mahl_name);
        }
        catch (Exception E){
            System.out.println("verbindung zu Name ist Fehlgeschlagen");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == zuruck){
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Tagebuch n = new Tagebuch(frame_size, frame_loc, this.benutzername, date_select);
            n.content();
        }
        if (e.getSource() == dropname || e.getSource() == confirm || e.getSource() == hidden){
            String mahl_name = String.valueOf(dropname.getSelectedItem());

            error_message.setText("");

            try {
                Connection new_connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
                Statement new_statement = new_connection.createStatement();
                ResultSet new_resultSet = new_statement.executeQuery("SELECT * FROM mahlzeit WHERE name = '" + mahl_name + "'");

                try {
                    this.anz_portionen = (double)portionen.getValue();
                }
                catch (Exception E){
                    error_message.setText("Geben sie eine Zahl als Portion an!");
                }

                if (error_message.getText().isEmpty()){
                    while (new_resultSet.next()){
                        //Anzahl Kohlenhydrate werden abgerufen
                        set_data("carb", anz_carbs);
                        //Anzahl Protein wird abgerufen
                        set_data("protein", anz_protein);
                        //Anzahl Fett wird abgerufen
                        set_data("fat", anz_fat);
                        //Anzahl Kalorien werden abgerufen
                        double anz_carbs = Double.parseDouble(this.anz_carbs.getText()) * 4;
                        double anz_protein = Double.parseDouble(this.anz_protein.getText()) * 4;
                        double anz_fat = Double.parseDouble(this.anz_fat.getText()) * 9;

                        double kalorien_double = anz_carbs + anz_protein + anz_fat;
                        long kalorien_long = Math.round(kalorien_double);
                        String kalorien_final = String.valueOf(kalorien_long);
                        this.anz_kalorien.setText(kalorien_final);
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == bearbeiten){
            String drop_selected = (String)dropname.getSelectedItem();
            int kalorien = Integer.parseInt(anz_kalorien.getText());
            float carb = Float.parseFloat(anz_carbs.getText());
            float protein = Float.parseFloat(anz_protein.getText());
            float fat = Float.parseFloat(anz_fat.getText());


            DBConnect get_ben = new DBConnect("SELECT id FROM benutzer WHERE Benutzername = '" + this.benutzername + "'", "id", 0);
            get_ben.con();
            String ben_id = get_ben.getResult();

            DBConnect get_mahl = new DBConnect("SELECT id FROM mahlzeit WHERE Name = '" + drop_selected + "' AND ben = " + ben_id + "", "id", 0);
            get_mahl.con();
            String mahl = get_mahl.getResult();

            DBConnect update = new DBConnect("UPDATE mmm SET mahl = " + mahl + ", port = " + portionen.getValue() + ", kalorien = " + kalorien + ", carb = " + carb + ", protein = " + protein + ", fat = " + fat + " WHERE id = " + this.mmm_id + "", " ", 1);
            update.con();

            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Tagebuch n = new Tagebuch(frame_size, frame_loc, this.benutzername, date_select);
            n.content();
        }
    }
}