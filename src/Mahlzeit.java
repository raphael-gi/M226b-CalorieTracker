import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Mahlzeit extends Global implements ActionListener {
    private JPanel panel;
    private JButton Erstellen;
    private JTextField Name;
    private JLabel error_message;
    private JButton zuruck;
    private JSpinner carb_input;
    SpinnerNumberModel carb_model = new SpinnerNumberModel(0, 0, 100000, 1);
    private JSpinner protein_input;
    SpinnerNumberModel protein_model = new SpinnerNumberModel(0, 0, 100000, 1);
    private JSpinner fat_input;
    SpinnerNumberModel fat_model = new SpinnerNumberModel(0, 0, 100000, 1);

    private final String mahlzeit;

    public Mahlzeit(String mahlzeit){
        this.mahlzeit = mahlzeit;

        frame.add(panel);
        frame.revalidate();
        frame.repaint();

        //JSpinner erhalten ein Model
        carb_input.setModel(carb_model);
        protein_input.setModel(protein_model);
        fat_input.setModel(fat_model);

        //Buttons erhalten Action Listeners
        Erstellen.addActionListener(this);
        zuruck.addActionListener(this);
    }

    public void sendMahlzeit() {
        frame.remove(panel);
        Mahlzeit_auswahl mahlzeit_auswahl = new Mahlzeit_auswahl(this.mahlzeit);
        mahlzeit_auswahl.content();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==Erstellen){
            //Variabeln werden deklariert
            String name = Name.getText();
            int carb = (int) carb_input.getValue();
            int protein = (int) protein_input.getValue();
            int fat = (int) fat_input.getValue();

            //Error Message wird reseted
            error_message.setText("");

            //Error Handling beginnt
            if (name.isEmpty() || carb_input.getValue() == null || protein_input.getValue() == null || fat_input.getValue() == null){
                error_message.setText("Füllen sie alle Felder aus!");
                return;
            }
            if (name.length() > 90){
                error_message.setText("Der Name ist zu lange!");
                return;
            }
            DBConnect check_name = new DBConnect("SELECT Name FROM mahlzeit WHERE Name = '" + name + "' AND ben = " + id + "");
            check_name.setSql_get("Name");
            check_name.con();

            if (check_name.getResult() != null){
                error_message.setText("Mahlzeit exestiert bereits. Wählen sie einen anderen Namen!");
                return;
            }
            try{
                int kalorien = carb * 4 + protein * 4 + fat * 9;
                DBConnect dbConnect = new DBConnect("INSERT INTO mahlzeit (Name, kalorien, carb, protein, fat, ben) VALUES ('" + name +"', '" + kalorien +"', '" + carb + "', '" + protein + "', '" + fat + "', " + id + ")");
                dbConnect.con();
                sendMahlzeit();
            }
            catch (Exception E){
                error_message.setText("Geben sie als Wert Zahlen an!");
            }
        }

        if (e.getSource() == zuruck){
            sendMahlzeit();
        }
    }
}