package Kalorienzahler;

public class RegistrierungSQL {
    private String name;
    private String passwort;
    private int gender;
    private int alter;
    private String result;
    RegistrierungSQL(String name, String passwort, int gender, int alter){
        this.name = name;
        this.passwort = passwort;
        this.gender = gender;
        this.alter = alter;
    }

    public String getResult() {
        return result;
    }

    public void connect(){
        DBConnect registrierung_check = new DBConnect("SELECT Benutzername FROM benutzer WHERE Benutzername = '" + this.name + "'","Benutzername",0);
        registrierung_check.con();
        if (registrierung_check.getResult() != null){
            this.result = "being_used";
        }
        else{
            DBConnect registrierung = new DBConnect("INSERT INTO benutzer (Benutzername, Passwort, gender, age) VALUES ('" + this.name + "', '" + this.passwort + "', " + gender + ", " + alter + ")","Benutzername",1);
            System.out.println(this.name);
            System.out.println(this.passwort);
            registrierung.con();
            this.result = "";
        }
    }
}