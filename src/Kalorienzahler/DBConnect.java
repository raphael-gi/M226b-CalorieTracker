package Kalorienzahler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnect {
    private final String sql_statement;
    private final String sql_get;
    private String result;
    private final int check;

    DBConnect(String sql_statement, String sql_get, int check){
        this.sql_statement = sql_statement;
        this.sql_get = sql_get;
        this.check = check;
        con();
    }

    public String getResult() {
        return result;
    }

    public void con() {
        try{
            //Verbindung wird aufgebaut
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
            Statement statement = connection.createStatement();

            if (this.check == 0){
                //Statement um etwas abzurufen
                ResultSet resultSet = statement.executeQuery(this.sql_statement);
                while (resultSet.next()){
                    this.result = resultSet.getString(this.sql_get);
                }
                resultSet.close();
            }
            if (this.check == 1){
                //Verbindung einen Befehl auszuführen
                statement.execute(this.sql_statement);
            }
            //Verbindungen werden wieder geschlossen
            connection.close();
            statement.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}