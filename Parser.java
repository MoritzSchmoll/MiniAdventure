import java.util.Scanner;

/**
 * Diese Klasse ist Teil der Anwendung "MiniAdventure".
 * "MiniAdventure" ist ein sehr einfaches textbasiertes 
 * Adventure-Game.
 *
 * Dieser Parser liest Benutzereingaben und wandelt sie in
 * Commande für das Adventure-Game um. Bei jedem Aufruf
 * liest er eine Zeile von der Konsole und versucht, diese als
 * einen Command aus bis zu zwei Wörtern zu interpretieren. Er
 * liefert den Command als ein Objekt der Klasse Command zurück.
 * 
 * Der Parser verfügt über einen Satz an bekannten Commanden. Er
 * vergleicht die Eingabe mit diesen Commanden. Wenn die Eingabe
 * keinen bekannten Command enthält, dann liefert der Parser ein als 
 * unbekannter Command gekennzeichnetes Objekt zurück.
 * 
 * @author  Michael Kölling und David J. Barnes
 * @version 2016.02.29
 */
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
            wort1 = zerleger.next();     // erstes Wort lesen: Buchstaben bis zum nächsten Leerzeichen/Return
            if (zerleger.hasNext()) {
                wort2 = zerleger.next();    // zweites Wort lesen: Buchstaben bis zum nächsten Leerzeichen/Return
                // Hinweis: Wir ignorieren den Rest der Eingabezeile.
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
    public void zeigeCommande()
    {
        commandWords.printAll();
    }
}
