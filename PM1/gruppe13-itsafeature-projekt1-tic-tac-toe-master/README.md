# gruppe13-itsafeature-projekt1-tic-tac-toe
## Klassendiagramm

Die Dateien für das Klassendiagramm können bei [Umletino](https://www.umletino.com/umletino.html) hochgeladen werden um sie anzuschauen.

## Benutzung in einer IDE

Wenn man das Projekt in einer IDE laufen lässt, spielt es automatisch 3 Spiele durch. 
Sollte man es selbst probieren, muss man es in BlueJ importieren.


## Wie importiert man das Programm in BlueJ
Alle Klassen mit Ausnahme der Klasse `TicTacToe.java` müssen in einen eigenen Ordner kopiert werden.
In BlueJ kann das Projekt dann folgendermassen geöffnet werden: 
Oben Links auf Project --> Open Non BlueJ

## Benutzeranleitung für Verwendung in BlueJ

1. Rechtsklick auf die Klasse GameField --> newGamefield() um ein neues Spiel zu starten.
2. Rechtsklick auf das erzeugte Objekt --> Methode init() aufrufen.
3. Das initialisiert das Spiel und zeigt ein leeres Feld an.
4. Danach kann man die Methode makeAMove(int cellNumber, boolean changeLanguage) aufrufen.
6. Die Nummer des Spielfeldes als ersten Parameter mitgeben. Möchte man die Sprache wechseln, als zweiten Parameter true angeben. Ansonsten false.
7. Nach erfolgtem Zug ist Spieler 2 dran.

Das Spielfeld ist von oben links nach unten rechts durchnummeriert.
Es sieht so aus:

```
-------------
| 1 | 2 | 3 |
-------------
| 4 | 5 | 6 |
-------------
| 7 | 8 | 9 |
-------------
```
Unerlaubte Züge erkennt das Spiel selbstständig. In diesem Fall darf man noch einmal eine Eingabe machen.
