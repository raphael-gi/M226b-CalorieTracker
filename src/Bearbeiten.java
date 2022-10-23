import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Bearbeiten extends Global implements ActionListener {
    private JPanel panel;
    private JButton bearbeiten;
    private JComboBox <String> dropname;
    private JButton confirm;
    private JLabel anz_kalorien;
    private JLabel anz_carbs;
    private JLabel anz_protein;
    private JLabel anz_fat;
    private JLabel error_message;
    private JButton zuruck;
    private JButton hidden;
    private JSpinner portionen;

    private final int mmm_id;

    private String name;
    private float carb;
    private float protein;
    private float fat;
    private float port;

    private double anz_portionen;

    JButton[] all_buttons = {bearbeiten, confirm, zuruck, hidden};

    public Bearbeiten(int mmm_id){
        newPanel(panel);

        this.mmm_id = mmm_id;

        hidden.setVisible(false);

        //Action Listeners wird hinzugefügt
        for (JButton but : all_buttons){
            but.addActionListener(this);
        }
        SpinnerNumberModel model = new SpinnerNumberModel(1, 0.0, 100000.0, 1);
        portionen.setModel(model);
        portionen.addChangeListener(e -> {
            anz_portionen = (double) portionen.getValue();
            hidden.doClick();
        });

        try {
            resultSet = statement.executeQuery("SELECT mmm.port, mmm.carb, mmm.protein, mmm.fat FROM mmm, mahlzeit WHERE mmm.id = '" + mmm_id + "' AND mahlzeit.id = mmm.id");
            while (resultSet.next()){
                this.name = resultSet.getString("Name");
                this.port = resultSet.getFloat("port");
                this.carb = resultSet.getFloat("carb");
                this.protein = resultSet.getFloat("protein");
                this.fat = resultSet.getFloat("fat");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void set_data(String what, JLabel name) {
        String mahl_name = String.valueOf(dropname.getSelectedItem());
        try {
            DBConnect get_mahl = new DBConnect("SELECT carb, protein, fat FROM mahlzeit WHERE Name = '" + mahl_name + "'");
            get_mahl.setSql_get(what);
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
        portionen.setValue(port);
        try{
            resultSet = statement.executeQuery("SELECT Name FROM mahlzeit WHERE ben = " + id + " ORDER BY Name");
            while (resultSet.next()){
                String benutzernamen = resultSet.getString("Name");
                dropname.addItem(benutzernamen);
            }
            dropname.addActionListener(this);
        }
        catch (Exception E){
            System.out.println("verbindung zu Name ist Fehlgeschlagen");
        }
    }
    public void sendTagebuch() {
        frame.remove(panel);
        Tagebuch tagebuch = new Tagebuch();
        tagebuch.content();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == zuruck) {
            sendTagebuch();
        }
        if (e.getSource() == dropname) {
            String mahlName = String.valueOf(dropname.getSelectedItem());
        }
        if (e.getSource() == dropname || e.getSource() == confirm || e.getSource() == hidden){
            error_message.setText("");
            try {
                this.anz_portionen = (double)portionen.getValue();
            }
            catch (Exception E){
                error_message.setText("Geben sie eine Zahl als Portion an!");
                return;
            }

            set_data("carb", anz_carbs);
            set_data("protein", anz_protein);
            set_data("fat", anz_fat);

            double anz_carbs = Double.parseDouble(this.anz_carbs.getText()) * 4;
            double anz_protein = Double.parseDouble(this.anz_protein.getText()) * 4;
            double anz_fat = Double.parseDouble(this.anz_fat.getText()) * 9;

            double kalorien_double = anz_carbs + anz_protein + anz_fat;
            long kalorien_long = Math.round(kalorien_double);
            String kalorien_final = String.valueOf(kalorien_long);
            this.anz_kalorien.setText(kalorien_final);
        }
        if (e.getSource() == bearbeiten) {
            String drop_selected = (String)dropname.getSelectedItem();
            int kalorien = Integer.parseInt(anz_kalorien.getText());
            float carb = Float.parseFloat(anz_carbs.getText());
            float protein = Float.parseFloat(anz_protein.getText());
            float fat = Float.parseFloat(anz_fat.getText());

            DBConnect get_mahl = new DBConnect("SELECT id FROM mahlzeit WHERE Name = '" + drop_selected + "' AND ben = " + id + "");
            get_mahl.setSql_get("id");
            get_mahl.con();
            String mahl = get_mahl.getResult();
            DBConnect dbConnect = new DBConnect("UPDATE mmm SET mahl = " + mahl + ", port = " + portionen.getValue() + ", kalorien = " + kalorien + ", carb = " + carb + ", protein = " + protein + ", fat = " + fat + " WHERE id = " + this.mmm_id + "");
            dbConnect.con();
            sendTagebuch();
        }
    }
}