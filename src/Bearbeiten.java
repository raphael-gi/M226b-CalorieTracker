import javax.swing.*;
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
    private float portion;

    private float anz_portionen;

    JButton[] all_buttons = {bearbeiten, confirm, zuruck, hidden};

    public Bearbeiten(int mmm_id){
        newPanel(panel);

        this.mmm_id = mmm_id;

        hidden.setVisible(false);

        //Action Listeners wird hinzugefügt
        for (JButton but : all_buttons){
            but.addActionListener(this);
        }
        SpinnerNumberModel model = new SpinnerNumberModel(1.0, 0.0, 100000.0, 1.0);
        portionen.setModel(model);
        portionen.addChangeListener(e -> {
            String portionString = String.valueOf(portionen.getValue());
            portion = Float.parseFloat(portionString);
            setData();
        });

        try {
            resultSet = statement.executeQuery("SELECT mmm.port, mahlzeit.carb, mahlzeit.protein, mahlzeit.fat FROM mmm, mahlzeit WHERE mmm.id = " + mmm_id + " AND mmm.mahl = mahlzeit.id");
            while (resultSet.next()){
                this.portion = resultSet.getFloat("port");
                this.carb = resultSet.getFloat("carb");
                this.protein = resultSet.getFloat("protein");
                this.fat = resultSet.getFloat("fat");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setData();
    }
    public String getMeal(float meal) {
        return String.valueOf(Math.round((meal * this.portion) * 10) / 10);
    }
    public void setData() {
        anz_carbs.setText(getMeal(carb));
        anz_protein.setText(getMeal(protein));
        anz_fat.setText(getMeal(fat));
        anz_kalorien.setText(String.valueOf(((carb * portion) * 4) + ((protein * portion) * 4) + ((fat * portion) * 9)));
    }
    public void content(){
        portionen.setValue(portion);
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
        if (e.getSource() == bearbeiten) {
            String drop_selected = (String)dropname.getSelectedItem();
            float kalorien = Float.parseFloat(anz_kalorien.getText());
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