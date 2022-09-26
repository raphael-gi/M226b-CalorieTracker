# CalorieTracker
Gaining or losing weight can be hard... but not if you've got this calorie tracker!

## Tech Stack
- Java
- MySQL

## Beschreibung
### Benutzer anlegen
Mit diesem Programm können Benutzer registriert werden, diese werden in einer Datenbank 
abgespeichert und ihr Passwort gehasht. Ausserdem muss man Grösse, Geschlecht, Gewicht und 
Alter angeben, welche wichtig für spätere Berechnungen sein werden.
### Einstellungen 
In den Einstellungen können die während der Registrierung angegebenen Daten bearbeitet werden. 
Ausserdem kann zum Darkmode gewechselt und ausgewählt werden ob abgenommen, zunehmen 
oder das Gewicht beibehalten soll. Dementsprechend wird berechnet wie viele Kalorien man 
braucht. Ebenfalls kann die Sprache auf Englisch gewechselt werden (Standartmässig Deutsch). Es 
kann auch ausgewählt werden ob Muskeln aufgebaut werden, dadurch wird die Anzahl benötigter 
Proteine berechnet. In den Einstellungen kann man sich auch abmelden, oder seinen Account 
löschen.
### Startseite (Tagebuch)
Die Startseite ist so konzipiert, dass vier Spalten vorhanden sind. Frühstück, Mittagessen, Snack und 
Abendessen. Man kann dort jeweils Mahlzeiten hinzufügen, löschen und bearbeiten. Oben auf der 
Seite stehen die benötigten Kalorien, je nachdem welche Option man in den Einstellungen 
ausgewählt hat, kann diese Zahl grösser oder kleiner sein. Falls ausgewählt stehen auch die Anzahl 
Proteine. Mit den Pfeiltasten kann man auf vergangene oder zukünftigen Tage zugreifen, um 
Korrekturen vorzunehmen, Pläne zu erstellen oder sich einfach nur seine Einträge anzuschauen. Die 
Angaben werden alle in der Datenbank gespeichert.
### Mahlzeit
Die Mahlzeiten werden von einem selbst erstellt, dabei wird der Mahlzeit einen Namen gegeben, die
Anzahl Portionen, Kalorien, Kohlenhydrate und Fette angegeben. Das Ganze wird in der Datenbank 
gespeichert und kann dann immer in der Dropdownliste ausgewählt werden. Darunter kann die 
Anzahl Portionen angegeben werden und daneben wird die Anzahl Kalorien berechnet.

## Benutzerhandbuch
Um das Programm an erster Stelle zu benutzen, muss man einen Account erstellen. Wenn 
man bereits einen besitzt, kann man sich einloggen. Sonst kann man sich registrieren.  

Auf der Startseite angelangt hat man die Möglichkeit sich Mahlzeiten aufzuschreiben. Dafür 
muss man nur auf einer der Vier Buttons drücken.  

Auf der nächsten Seite angelangt kann man sich eine Mahlzeit auswählen. Falls man das 
noch nicht gemacht hat, kann man ein neues Gericht erstellen. Dafür muss man die Anzahl 
Kohlenhydrate, Proteine und Fette eingeben. Dies wird abgespeichert und die Anzahl 
Kalorien werden berechnet. Danach muss man nur noch die Anzahl Portionen eingeben und 
schon ist die Mahlzeit erstellt!  

Wieder auf der Startfläche angelangt, kann man sehen wie viele Kalorien man zu sich 
genommen hat und wie viele man an diesem Tag noch zu sich nehmen muss. Falls man 
wieder eine Mahlzeit einspeichern möchte, kann man den Vorgang wiederholen.  

Wenn man später merkt, dass man einen Fehler gemacht hat, kann man die Mahlzeit 
jederzeit Bearbeiten, oder auch Löschen.  

Es kann vorkommen, dass man bei der Registrierung einen Fehler gemacht hat. Deswegen 
gibt es die Einstellungen, in denen man sehr vieles umstellen kann. Wie zum Beispiel das 
Alter oder das Geschlecht.
