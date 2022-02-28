package Kalorienzähler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnect {
    private String sql_statement;
    private String sql_get;
    private String result;
    private int check;

    DBConnect(String sql_statement, String sql_get, int check){
        this.sql_statement = sql_statement;
        this.sql_get = sql_get;
        this.check = check;
    }

    public String getResult() {
        return result;
    }

    public void con() {
        try{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");

        Statement statement = connection.createStatement();

        if (this.check == 0){
            ResultSet resultSet = statement.executeQuery(this.sql_statement);
            while (resultSet.next()){
                this.result = resultSet.getString(this.sql_get);
            }
        }
        if (this.check == 1){
            statement.execute(this.sql_statement);
        }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}