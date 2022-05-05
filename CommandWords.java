/**
 * Diese Klasse ist Teil der Anwendung "MiniAdventure".
 * "MiniAdventure" ist ein sehr einfaches textbasiertes 
 * Adventure-Game.
 * 
 * Diese Klasse hält eine Aufzählung aller Befehlswörter, die dem
 * Spiel bekannt sind. Mit ihrer Hilfe werden eingetippte Befehle
 * erkannt.
 *
 * @author  Michael Kölling und David J. Barnes
 * @version 2016.02.29
 */

class CommandWords
{
    // ein konstantes Array mit den gültigen Befehlswörtern
    
    private static final String[] VALID_COMMANDS = {
        "go", "quit", "exit", "stop", "help", "look", "inventory", "stats", "eat", "pickup", "container", "open", "attack", "escape", "enemy", "feed", "heal"
    };
    private static final String[] CONTAINER_COMMANDS = {
        "put", "take", "close"
    };

    /**
     * Konstruktor - initialisiere die Befehlswörter.
     */
    public CommandWords()
    {
        // nichts zu tun momentan...
    }

    /**
     * Prüfe, ob eine gegebene Zeichenkette ein gültiger
     * Befehl ist.
     * @return true  wenn die gegebene Zeichenkette ein gültiger
     *               Befehl ist, false sonst
     */
    public boolean isCommand(String eingabe)
    {
         for(int i = 0; i < VALID_COMMANDS.length; i++) {    
            if(VALID_COMMANDS[i].equals(eingabe)) 
                return true;
        }
        // Wenn wir hierher gelangen, wurde die Eingabe nicht
        // in den Befehlswörter gefunden.
        return isContainerCommand(eingabe);
    }
    
    /**
     * Überprüft ob ein Befehl zum Bedienen von Containern genutzt wird.
     */
    public boolean isContainerCommand(String eingabe)    
    {    
        for(int i = 0; i < CONTAINER_COMMANDS.length; i++)    
        {    
            if(CONTAINER_COMMANDS[i].equals(eingabe))     
                return true;    
        }    
        return false;    
    }
    
    /**
     * Gib alle gültigen Befehlswörter auf die Konsole aus.
     */
    public void printAll() 
    {
        for(String befehl : VALID_COMMANDS) {
            System.out.print(befehl + "  ");
        }
        System.out.println();
    }
}
