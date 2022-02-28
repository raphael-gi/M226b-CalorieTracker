package Kalorienzähler;

public class RegistrierungSQL {
    private String name;
    private String passwort;
    private String result;
    RegistrierungSQL(String name, String passwort){
        this.name = name;
        this.passwort = passwort;
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
            DBConnect registrierung = new DBConnect("INSERT INTO benutzer (Benutzername, Passwort) VALUES ('"+ this.name +"', '"+ this.passwort +"')","Benutzername",1);
            System.out.println(this.name);
            System.out.println(this.passwort);
            registrierung.con();
            this.result = " ";
        }
    }
}