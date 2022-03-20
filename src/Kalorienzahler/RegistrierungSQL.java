package Kalorienzahler;

public class RegistrierungSQL {
    private final String name;
    private final String passwort;
    private final int gender;
    private final int alter;
    private final double gewicht;
    private final double groesse;
    private String result;

    RegistrierungSQL(String name, String passwort, int gender, int alter, double gewicht, double groesse){
        this.name = name;
        this.passwort = passwort;
        this.gender = gender;
        this.alter = alter;
        this.gewicht = gewicht;
        this.groesse = groesse;
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
            DBConnect registrierung = new DBConnect("INSERT INTO benutzer (Benutzername, Passwort, gender, age, gewicht, groesse) VALUES ('" + this.name + "', '" + this.passwort + "', " + gender + ", " + alter + ", " + gewicht + ", " + groesse + ")","Benutzername",1);
            System.out.println(this.name);
            System.out.println(this.passwort);
            registrierung.con();
            this.result = "";
        }
    }
}