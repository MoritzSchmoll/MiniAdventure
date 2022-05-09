import java.util.Scanner;

class Parser 
{
    private CommandWords commandWords;  // hält die gültigen Commandswörter
    private Scanner leser;         // Lieferant für eingegebene Commande

    /**
     * Erzeuge einen Parser, der Commande von der Konsole einliest.
     */
    public Parser() 
    {
        commandWords = new CommandWords();
        leser = new Scanner(System.in);
    }

    /**
     * @return  den nächsten Command des Benutzers
     */
    public Command liefereCommand() 
    {
        String eingabezeile;   // für die gesamte Eingabezeile
        String wort1 = null;
        String wort2 = null;

        System.out.print("> ");     // Eingabeaufforderung

        eingabezeile = leser.nextLine();
        
        // Finde bis zu zwei Wörter in der Zeile
        Scanner zerleger = new Scanner(eingabezeile);
        if(zerleger.hasNext()) {
            wort1 = zerleger.next().toLowerCase(); // erstes Wort lesen: Buchstaben bis zum nächsten Leerzeichen/Return und in Kleinschrift umwandeln
            if (zerleger.hasNext()) {
                wort2 = zerleger.next().toLowerCase(); // zweites Wort lesen: Buchstaben bis zum nächsten Leerzeichen/Return und in Kleinschrift umwandeln
                if(!commandWords.isDirection(wort2)) //überprüfen, ob es sich um eine Richtung handelt, da diese kleingeschrieben werden
                {
                    char[] charArray = wort2.toCharArray(); //String wird in einen Character-Array umgewandelt, um einzelne Buchstaben betrachten zu können
                    wort2 = ""; //String wird geleert, um ihn einzeln wieder zusammensetzen zu können
                    for(int i = 0; i < charArray.length; i++) //geht durch den Array von Buchstaben des Strings und passt an, ob sie groß- oder kleingeschrieben werden
                    {
                        if(i == 0)
                        {
                            charArray[i] = Character.toUpperCase(charArray[i]); //der erste Buchstabe wird großgeschrieben
                        }
                        else
                        {
                            charArray[i] = Character.toLowerCase(charArray[i]); //macht alle weiteren Buchstaben klein
                        }
                        wort2 += charArray[i]; //fügt den einzelnen Buchstaben zurück zum String hinzu
                    }
                }
            }
        }
        
        // Jetzt prüfen, ob der Command bekannt ist. Wenn ja, erzeugen
        // wir das passende Command-Objekt. Wenn nicht, erzeugen wir
        // einen unbekannten Command mit 'null'.
        if(commandWords.isCommand(wort1)) {
            return new Command(wort1, wort2);
        }
        else {
            return new Command(null, wort2);
        }
    }
    
    /**
     * Gibt alle gültigen Commande wieder.
     */
    public CommandWords getCommandWords()    
    {    
        return commandWords;    
    }

    /**
     * Gib eine Liste der bekannten Commandswörter aus.
     */
    public void printCommands()
    {
        commandWords.printAll();
    }
}
