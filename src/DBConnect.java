import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnect {
    private final String sql_statement;
    private String sql_get;
    private String result;

    DBConnect(String sql_statement){
        this.sql_statement = sql_statement;
    }

    public void setSql_get(String sql_get) {
        this.sql_get = sql_get;
    }
    public String getResult() {
        return result;
    }

    public void con() {
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kalorien", "root", "");
            Statement statement = connection.createStatement();

            if (this.sql_get != null){
                ResultSet resultSet = statement.executeQuery(this.sql_statement);
                while (resultSet.next()) {
                    this.result = resultSet.getString(this.sql_get);
                }
                resultSet.close();
            }
            else {
                statement.execute(this.sql_statement);
            }
            //connection.close();
            //statement.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}