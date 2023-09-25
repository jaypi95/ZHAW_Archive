# Connect four WBE HS22

You can find the game itself [here](Connect_Four/public/connect4.html) 

If you want to take a look at the code, click [here](https://github.zhaw.ch/peterju1/WBE2022/tree/main/Connect_Four/public)

[Here](Connect_Four/Documentation.pdf) You can also find the documentation as a PDF. Although the text is the same as below.
## Team
Connect Four wurde in einer Dreiergruppe implementiert, diese besteht aus Julian Peter (Peterju1), Silvan Baach (Baachsil) und Badr Outiti (Outitbad)

## Funktionen
Connect Four beinhaltet folgende Funktionen:
### Spielstand Speichern
Mittels dem Save und Load Knopf können die Spieler einen Spielstand im local storage des Browsers Speichern und wieder abrufen.
Spielstand Rückgängig machen

Mittels dem Undo Knopf, kann der Spielstand um einen Zug zurückgesetzt werden. Dies kann beliebig oft wiederholt werden, bis zu dem Zeitpunkt, wo kein Spielstand mehr vorhanden ist ausser ein Leeres Feld, oder der Spielstand welcher gespeichert wurde und über eine Runde mitgenommen wurde. (Save drücken und nach dem gewinnen Load.)

### Spieleranzeige
Der Spieler welcher am Zug ist wird mittels einer Textausgabe angezeigt. Diese ?ndert sich, wenn ein gültiger Zug gemacht wurde.

### Gewinneranzeige
Wenn ein Spieler das Spiel gewonnen hat, erscheint ein pop-up Fenster um dem Spieler dies mitzuteilen. Anschliessend wird das Spielfeld geleert und ein neues Spiel kann beginnen.


### Design
Das Design wurde schlicht gehalten und durch den Dark-Modus inspiriert.
Das Ziel war es, dem Informatiker-Image gerecht zu werden, und die Seite so Dunkel und Schick wie möglich zu halten. 
