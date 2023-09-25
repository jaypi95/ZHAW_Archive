# Gruppe 13 (it's a feature) Projekt2: VIM v2.0

_English version below_

# Deutsch
## Anweisungen zum Setup des Programms
### Voraussetzungen
 - Git
 - IDE

### Wie vorzugehen ist

1. Klonen Sie das Repository mittels `git clone` auf Ihren Rechner. Sie können es auch zuerst forken, wenn Sie selbst daran arbeiten möchten.
1. Öffnen Sie das Projekt in einem IDE Ihrer Wahl (It's a feature empfiehlt Jetbrains IntelliJ IDEA, aber auch andere IDEs sind denkbar).
1. Rechtsklicken Sie auf die Klasse TextEditor.java und klicken Sie auf run.
1. Um die Tests durchzuführen gehen Sie gleich vor wie beim TextEditor, führen aber die Dateien Test_Index.java und Test_StorageBucket.java aus.

## Kurzanleitung

Der Texteditor wird mit folgenden Befehlen benutzt:

Befehl | Erklärung 
---------|---------- 
[n]      |         Optionaler Parameter, kein Befehl: Absatznummer.
ADD [n]  |         Fügt einen neuen Absatz nach der angegebenen Absatznummer hinzu. Falls kein [n] angegeben wird, wird ein neuer Absatz unten eingefügt.  
DEL [n]  |         Löscht einen bestehenden Absatz bei der Absatznummer. Falls keine Nummer angegeben wird, wird der letzte Absatz gelöscht.  
DUMMY [n]|         Fügt einen Blindtext / Dummytext ein. Nummer wird gleich wie bei ADD behandelt.  
EXIT     |         Verlässt das Programm.  
FORMAT RAW|        Setzt die Ausgabebreite auf Standard.  
FORMAT FIX \<b>|   Setzt die Ausgabebreite auf den Wert \<b>.  
INDEX    |         Gibt ein Wortverzeichnis über alle Absätze für alle Wörter, die mehr als dreimal vorkommen, aus.  
PRINT    |         Gibt alle Absätze aus.  
REPLACE [n]|       Ersetzt Wörter aus dem Absatz bei der angegebenen Absatznummer (Suchen und Ersetzen).  
HELP     |         Gibt eine kurze Hilfe aus  

## Testresultate
Die Dateien im Ordner Testresultate können heruntergeladen und anschliessend im Browser betrachtet werden.

# English
## Instructions on the usage of this program
### Prerequisites
 - Git
 - IDE
 
### How to
1. Clone this repository with `git clone` to your computer. You can also fork this project if you want to contribute to it.
1. Open the project in an IDE of your choice (It's a feature recommends Jetbrains IntelliJ IDEA but other IDEs are also possible).
1. Right-click on the class TextEditor.java and click run.
1. To run the tests you do the same as with the main program but run the files Test_Index.java and Test_Storagebucket.java.

## Quick start guide

You can use this text editor with the following commands:

Command | Explanation  
---------|---------- 
[n]      |   Optional parameter, not a command: number of paragraph.
ADD [n]  |       Adds a new paragraph after number [n]. Without a number it adds the paragraph to the end.  
DEL [n]  |       Deletes the paragraph at number [n]. Without a number it deletes the last one.  
DUMMY [n]|       Adds a blind text / dummy text. [n] is being treated the same as in ADD.  
EXIT     |       Exits the program.  
FORMAT RAW|      Sets the output width to default.  
FORMAT FIX \<b>|  Sets the output width to value \<b>.  
INDEX    |       Prints a word index of all paragraphs containing all words that appear more than 3 times.  
PRINT    |       Prints all paragraphs.  
REPLACE [n]|     Replaces words in paragraph at specified paragraph number (search and replace).  
HELP     |       Prints a quick help.

## Test results
The files in the directory Testresultate can be downloaded and viewed in the browser.
