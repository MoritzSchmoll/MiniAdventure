import java.util.Scanner;

/**
 * Diese Klasse ist Teil der Anwendung "MiniAdventure".
 * "MiniAdventure" ist ein sehr einfaches textbasiertes 
 * Adventure-Game.
 *
 * Dieser Parser liest Benutzereingaben und wandelt sie in
 * Commande f�r das Adventure-Game um. Bei jedem Aufruf
 * liest er eine Zeile von der Konsole und versucht, diese als
 * einen Command aus bis zu zwei W�rtern zu interpretieren. Er
 * liefert den Command als ein Objekt der Klasse Command zur�ck.
 * 
 * Der Parser verf�gt �ber einen Satz an bekannten Commanden. Er
 * vergleicht die Eingabe mit diesen Commanden. Wenn die Eingabe
 * keinen bekannten Command enth�lt, dann liefert der Parser ein als 
 * unbekannter Command gekennzeichnetes Objekt zur�ck.
 * 
 * @author  Michael K�lling und David J. Barnes
 * @version 2016.02.29
 */
class Parser 
{
    private CommandWords commandWords;  // h�lt die g�ltigen Commandsw�rter
    private Scanner leser;         // Lieferant f�r eingegebene Commande

    /**
     * Erzeuge einen Parser, der Commande von der Konsole einliest.
     */
    public Parser() 
    {
        commandWords = new CommandWords();
        leser = new Scanner(System.in);
    }

    /**
     * @return  den n�chsten Command des Benutzers
     */
    public Command liefereCommand() 
    {
        String eingabezeile;   // f�r die gesamte Eingabezeile
        String wort1 = null;
        String wort2 = null;

        System.out.print("> ");     // Eingabeaufforderung

        eingabezeile = leser.nextLine();
        
        // Finde bis zu zwei W�rter in der Zeile
        Scanner zerleger = new Scanner(eingabezeile);
        if(zerleger.hasNext()) {
            wort1 = zerleger.next();     // erstes Wort lesen: Buchstaben bis zum n�chsten Leerzeichen/Return
            if (zerleger.hasNext()) {
                wort2 = zerleger.next();    // zweites Wort lesen: Buchstaben bis zum n�chsten Leerzeichen/Return
                // Hinweis: Wir ignorieren den Rest der Eingabezeile.
            }
        }
        
        // Jetzt pr�fen, ob der Command bekannt ist. Wenn ja, erzeugen
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
     * Gibt alle g�ltigen Commande wieder.
     */
    public CommandWords getCommandWords()    
    {    
        return commandWords;    
    }

    /**
     * Gib eine Liste der bekannten Commandsw�rter aus.
     */
    public void zeigeCommande()
    {
        commandWords.printAll();
    }
}
