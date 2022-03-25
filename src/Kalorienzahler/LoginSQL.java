package Kalorienzahler;

public class LoginSQL {
    private String name;
    private String passwort;
    private String result;
    LoginSQL(String name, String passwort) {
        this.name = name;
        this.passwort = passwort;
    }
    //a
    public String getResult() {
        return result;
    }

    public void connect(){
        DBConnect login = new DBConnect("SELECT * FROM benutzer WHERE Benutzername = '"+ this.name +"' AND Passwort = '"+ this.passwort +"'","Benutzername",0);
        login.con();
        this.result = login.getResult();
    }
}