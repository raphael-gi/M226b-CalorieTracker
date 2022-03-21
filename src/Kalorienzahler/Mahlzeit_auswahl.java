package Kalorienzahler;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Mahlzeit_auswahl implements ActionListener {
    private JPanel panel1;
    private JButton erstellen;
    private JLabel mahl;
    private JButton zuruck;
    private JComboBox dropname;
    private JLabel anz_kalorien;
    private JLabel anz_carbs;
    private JLabel anz_protein;
    private JLabel anz_fat;
    private JLabel error_message;
    private JButton confirm;
    private JButton hinzufugen;
    private JSpinner portion;
    private JButton hidden;
    private JButton bearbeiten;
    private JLabel kalorien_label;
    private JLabel carb_label;
    private JLabel protein_label;
    private JLabel fat_label;
    private JLabel mahlzeit_label;
    private JLabel portionen_label;
    private JFrame frame;

    private String mahlzeit;
    private String benutzername;
    private int userid;
    private double anz_portionen;

    private Dimension size;
    private Point loc;

    private JButton[] all_buttons = {erstellen, zuruck, confirm, hinzufugen, hidden, bearbeiten, hidden};
    private JLabel[] all_labels = {mahl, anz_kalorien, anz_carbs, anz_protein, anz_fat, kalorien_label, carb_label, protein_label, fat_label, mahlzeit_label, portionen_label};

    public Mahlzeit_auswahl(Dimension size, Point loc, String mahlzeit, String benutzername){
        this.size = size;
        this.loc = loc;

        this.mahlzeit = mahlzeit;
        this.benutzername = benutzername;

        frame = new JFrame("Mahlzeit Auswählen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500,400));

        frame.add(panel1);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.setSize(this.size);
        frame.setLocation(this.loc);

        hidden.setVisible(false);

        for (int i = 0; all_buttons.length > i; i++){
            JButton but = all_buttons[i];
            but.addActionListener(this);
        }

        SpinnerNumberModel model = new SpinnerNumberModel(1, 0.0, 100000.0, 1);
        portion.setModel(model);
        portion.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                anz_portionen = (double) portion.getValue();
                hidden.doClick();
            }
        });
    }
    public void set_data(String what, JLabel name) throws SQLException {
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

        mahl.setText(this.mahlzeit);
        this.anz_portionen = (double)portion.getValue();

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
                //Namen werden abgerufen
                String benutzernamen = new_resultSet.getString("Name");
                //Namen werden in die Dropdown Liste eingetragen
                dropname.addItem(benutzernamen);
            }
        }
        catch (Exception E){
            System.out.println("verbindung zu Name ist Fehlgeschlagen");
        }
        try {
            //Anzahl Kohlenhydrate werden abgerufen
            set_data("carb", this.anz_carbs);
            //Anzahl Protein wird abgerufen
            set_data("protein", this.anz_protein);
            //Anzahl Fett wird abgerufen
            set_data("fat", this.anz_fat);

            double carb_double = Double.parseDouble(this.anz_carbs.getText());
            double protein_double = Double.parseDouble(this.anz_protein.getText());
            double fat_double = Double.parseDouble(this.anz_fat.getText());
            double kalorien_double = (carb_double * 4) + (protein_double * 4) + (fat_double * 9);
            long kalorien_long = Math.round(kalorien_double);
            String kalorien_final = String.valueOf(kalorien_long);
            this.anz_kalorien.setText(kalorien_final);
        }
        catch (SQLException ex){
            System.out.println("content fail");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==erstellen){
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            new Mahlzeit(frame_size, frame_loc, this.mahlzeit, this.benutzername);
        }
        if (e.getSource()== zuruck){
            frame.dispose();
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            Tagebuch n = new Tagebuch(frame_size, frame_loc, this.benutzername);
            n.content();
        }
        if (e.getSource()==dropname || e.getSource()==confirm){
            String mahl_name = String.valueOf(dropname.getSelectedItem());

            error_message.setText("");
            this.anz_portionen = (double)portion.getValue();

            try {
                Connection new_connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
                Statement new_statement = new_connection.createStatement();
                ResultSet new_resultSet = new_statement.executeQuery("SELECT * FROM mahlzeit WHERE name = '" + mahl_name + "'");

                if (error_message.getText().isEmpty()){
                    while (new_resultSet.next()){
                        //Anzahl Kohlenhydrate werden abgerufen
                        set_data("carb", this.anz_carbs);
                        //Anzahl Protein wird abgerufen
                        set_data("protein", this.anz_protein);
                        //Anzahl Fett wird abgerufen
                        set_data("fat", this.anz_fat);
                        //Anzahl Kalorien werden abgerufen
                        double carb_double = Double.parseDouble(this.anz_carbs.getText());
                        double protein_double = Double.parseDouble(this.anz_protein.getText());
                        double fat_double = Double.parseDouble(this.anz_fat.getText());
                        double kalorien_double = (carb_double * 4) + (protein_double * 4) + (fat_double * 9);
                        long kalorien_long = Math.round(kalorien_double);
                        String kalorien_final = String.valueOf(kalorien_long);
                        this.anz_kalorien.setText(kalorien_final);
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == hidden){
            double carbs = Double.parseDouble(this.anz_carbs.getText());
            double protein = Double.parseDouble(this.anz_protein.getText());
            double fat = Double.parseDouble(this.anz_fat.getText());
            double cals = Double.parseDouble(this.anz_kalorien.getText());

            double port = (double) portion.getValue();
            double new_carbs = carbs * port;
            double new_protein = protein * port;
            double new_fat = fat * port;
            double new_cals = cals * port;

            this.anz_carbs.setText(String.valueOf(new_carbs));
            this.anz_protein.setText(String.valueOf(new_protein));
            this.anz_fat.setText(String.valueOf(new_fat));
            this.anz_kalorien.setText(String.valueOf(new_cals));
        }
        if (e.getSource() == hinzufugen){
            //Aktuelles Datum wird abgerufen
            Date date = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyy-MM-dd");

            //Ausgewählte Mahlzeit wird angeschaut
            int mahl_check = 0;
            if (this.mahlzeit.equals("Frühstück")){
                mahl_check = 1;
            }
            if (this.mahlzeit.equals("Mittagessen")){
                mahl_check = 2;
            }
            if (this.mahlzeit.equals("Abendessen")){
                mahl_check = 3;
            }
            if (this.mahlzeit.equals("Snacks")){
                mahl_check = 4;
            }
            try {
                //Verbindung um id der Mahlzeit zu erhalten
                int get_mahl_id = 0;
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT id FROM Mahlzeit WHERE Name = '" + dropname.getSelectedItem() + "'");
                while (resultSet.next()){
                    get_mahl_id = resultSet.getInt("id");
                }

                //Verbindung um id des Benutzer zu erhalten
                int get_ben_id = 0;
                Connection new_connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
                Statement new_statement = new_connection.createStatement();
                ResultSet new_resultSet = new_statement.executeQuery("SELECT id FROM Benutzer WHERE Benutzername = '" + this.benutzername + "'");
                while (new_resultSet.next()){
                    get_ben_id = new_resultSet.getInt("id");
                }

                //Data wird eingefügt
                Connection new_new_connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
                Statement new_new_statement = new_new_connection.createStatement();
                new_new_statement.execute("INSERT INTO mmm (mahl, port, kalorien, carb, protein, fat, datum, ben, mahlzeit) VALUES (" + get_mahl_id + ", " + this.anz_portionen + ", " + this.anz_kalorien.getText() + ", " + this.anz_carbs.getText() + ", " + this.anz_protein.getText() + ", " + this.anz_fat.getText() + ", '" + ft.format(date) + "', " + get_ben_id + ", " + mahl_check + ")");

                frame.dispose();
                Dimension frame_size = frame.getSize();
                Point frame_loc = frame.getLocation();
                Tagebuch n = new Tagebuch(frame_size, frame_loc, this.benutzername);
                n.content();
            }
            catch (Exception E){
                E.printStackTrace();
            }
        }
        if (e.getSource() == bearbeiten){
            frame.dispose();
            String mahl_name = (String) dropname.getSelectedItem();
            DBConnect get_carb = new DBConnect("SELECT carb FROM mahlzeit WHERE Name = '" + mahl_name + "'", "carb", 0);
            get_carb.con();
            int carb_int = Integer.parseInt(get_carb.getResult());
            DBConnect get_protein = new DBConnect("SELECT protein FROM mahlzeit WHERE Name = '" + mahl_name + "'", "protein", 0);
            get_protein.con();
            int protein_int = Integer.parseInt(get_protein.getResult());
            DBConnect get_fat = new DBConnect("SELECT fat FROM mahlzeit WHERE Name = '" + mahl_name + "'", "fat", 0);
            get_fat.con();
            int fat_int = Integer.parseInt(get_fat.getResult());
            Dimension frame_size = frame.getSize();
            Point frame_loc = frame.getLocation();
            new Mahlzeit_Bearbeiten(frame_size, frame_loc, this.benutzername, mahl_name, carb_int, protein_int, fat_int);
        }
    }
}