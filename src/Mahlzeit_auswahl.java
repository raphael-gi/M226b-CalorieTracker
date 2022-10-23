import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Mahlzeit_auswahl extends Global implements ActionListener {
    private JPanel panel;
    private JButton erstellen;
    private JLabel mahl;
    private JButton zuruck;
    private JComboBox<String> dropname;
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

    private final String mahlzeit;
    private double anz_portionen;

    private double carb1;
    private double protein1;
    private double fat1;

    SimpleDateFormat ft = new SimpleDateFormat("yyy-MM-dd");

    public Mahlzeit_auswahl(String mahlzeit) {

        newPanel(panel);

        this.mahlzeit = mahlzeit;


        hidden.setVisible(false);

        JButton[] all_buttons = {erstellen, zuruck, confirm, hinzufugen, hidden, bearbeiten, hidden};
        for (JButton but : all_buttons){
            but.addActionListener(this);
        }

        SpinnerNumberModel model = new SpinnerNumberModel(1, 0.0, 100000.0, 1);
        portion.setModel(model);
        portion.addChangeListener(e -> {
            anz_portionen = (double) portion.getValue();
            hidden.doClick();
        });
    }
    public void setData(String what, JLabel name) throws SQLException {
        if (dropname.getSelectedItem() == null){
            name.setText("");
            error_message.setText("Erstellen sie eine Mahlzeit!");
        }
        else {
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
    }
    public void content(){

        mahl.setText(this.mahlzeit);
        this.anz_portionen = (double)portion.getValue();

        try{
            resultSet = statement.executeQuery("SELECT Name FROM mahlzeit WHERE ben = " + id + " ORDER BY Name");
            while (resultSet.next()) {
                String benutzernamen = resultSet.getString("Name");
                dropname.addItem(benutzernamen);
            }
            dropname.addActionListener(this);
        }
        catch (Exception E){
            System.out.println("verbindung zu Name ist Fehlgeschlagen");
        }
        try {
            setData("carb", this.anz_carbs);
            setData("protein", this.anz_protein);
            setData("fat", this.anz_fat);

            if (this.anz_carbs.getText().equals("") || this.anz_protein.getText().equals("") || this.anz_fat.getText().equals("")){
                error_message.setText("Erstellen sie eine Mahlzeit!");
            }
            else {
                double carb_double = Double.parseDouble(this.anz_carbs.getText());
                double protein_double = Double.parseDouble(this.anz_protein.getText());
                double fat_double = Double.parseDouble(this.anz_fat.getText());
                double kalorien_double = (carb_double * 4) + (protein_double * 4) + (fat_double * 9);
                long kalorien_long = Math.round(kalorien_double);

                String kalorien_final = String.valueOf(kalorien_long);
                this.anz_kalorien.setText(kalorien_final);

                carb1 = carb_double;
                protein1 = protein_double;
                fat1 = fat_double;
            }
        }
        catch (SQLException ex){
            System.out.println("content fail");
        }
    }
    public void sendTagebuch() {
        frame.remove(panel);
        Tagebuch tagebuch = new Tagebuch();
        tagebuch.content();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==erstellen){
            frame.remove(panel);
            new Mahlzeit(this.mahlzeit);
        }
        if (e.getSource()== zuruck){
            sendTagebuch();
        }
        if (e.getSource()==dropname){
            String mahl_name = String.valueOf(dropname.getSelectedItem());
            error_message.setText("");
            this.anz_portionen = (double)portion.getValue();
            try {
                resultSet = statement.executeQuery("SELECT * FROM mahlzeit WHERE name = '" + mahl_name + "'");

                if (error_message.getText().isEmpty()) {
                    while (resultSet.next()){
                        setData("carb", this.anz_carbs);
                        setData("protein", this.anz_protein);
                        setData("fat", this.anz_fat);
                        double carb_double = Double.parseDouble(this.anz_carbs.getText());
                        double protein_double = Double.parseDouble(this.anz_protein.getText());
                        double fat_double = Double.parseDouble(this.anz_fat.getText());
                        double kalorien_double = (carb_double * 4) + (protein_double * 4) + (fat_double * 9);
                        long kalorien_long = Math.round(kalorien_double);
                        String kalorien_final = String.valueOf(kalorien_long);
                        this.anz_kalorien.setText(kalorien_final);
                        carb1 = carb_double;
                        protein1 = protein_double;
                        fat1 = fat_double;
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == hidden || e.getSource()==confirm){
            double port = (double) portion.getValue();
            double new_carbs = carb1 * port;
            double new_protein = protein1 * port;
            double new_fat = fat1 * port;
            double doub_cal = (new_carbs * 4) + (new_protein * 4) + (new_fat * 9);
            int new_cal = (int) Math.round(doub_cal);

            this.anz_carbs.setText(String.valueOf(new_carbs));
            this.anz_protein.setText(String.valueOf(new_protein));
            this.anz_fat.setText(String.valueOf(new_fat));
            this.anz_kalorien.setText(String.valueOf(new_cal));
        }
        if (e.getSource() == hinzufugen){
            //Ausgewählte Mahlzeit wird angeschaut
            int mahl_check = 0;
            if (this.mahlzeit.equals("Frühstück") || this.mahlzeit.equals("Breakfast")){
                mahl_check = 1;
            }
            if (this.mahlzeit.equals("Mittagessen") || this.mahlzeit.equals("Lunch")){
                mahl_check = 2;
            }
            if (this.mahlzeit.equals("Abendessen") || this.mahlzeit.equals("Dinner")){
                mahl_check = 3;
            }
            if (this.mahlzeit.equals("Snacks")){
                mahl_check = 4;
            }
            try {
                //Verbindung um id der Mahlzeit zu erhalten
                int get_mahl_id = 0;
                resultSet = statement.executeQuery("SELECT id FROM Mahlzeit WHERE Name = '" + dropname.getSelectedItem() + "'");
                while (resultSet.next()){
                    get_mahl_id = resultSet.getInt("id");
                }

                //Data wird eingefügt
                statement.execute("INSERT INTO mmm (mahl, port, kalorien, carb, protein, fat, datum, ben, mahlzeit) VALUES (" + get_mahl_id + ", " + this.anz_portionen + ", " + this.anz_kalorien.getText() + ", " + this.anz_carbs.getText() + ", " + this.anz_protein.getText() + ", " + this.anz_fat.getText() + ", '" + ft.format(date) + "', " + id + ", " + mahl_check + ")");

                sendTagebuch();
            }
            catch (Exception E){
                E.printStackTrace();
            }
        }

        if (e.getSource() == bearbeiten){
            String mahl_name = (String) dropname.getSelectedItem();
            int carb_int = (int) carb1;
            int protein_int = (int) protein1;
            int fat_int = (int) fat1;
            frame.remove(panel);
            new Mahlzeit_Bearbeiten(mahl.getText(), mahl_name, carb_int, protein_int, fat_int);
        }
    }
}