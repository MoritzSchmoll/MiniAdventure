/**
 * Diese Klasse ist Teil der Anwendung "MiniAdventure".
 * "MiniAdventure" ist ein sehr einfaches textbasiertes 
 * Adventure-Game.
 * 
 * Diese Klasse h�lt eine Aufz�hlung aller Befehlsw�rter, die dem
 * Spiel bekannt sind. Mit ihrer Hilfe werden eingetippte Befehle
 * erkannt.
 *
 * @author  Michael K�lling und David J. Barnes
 * @version 2016.02.29
 */

class Befehlswoerter
{
    // ein konstantes Array mit den g�ltigen Befehlsw�rtern
    
    private static final String[] VALID_COMMANDS = {
        "go", "quit", "exit", "stop", "help", "look", "inventory", "stats", "eat", "pickup", "container", "open", "attack", "escape", "enemy", "feed"
    };
    private static final String[] CONTAINER_COMMANDS = {
        "put", "take", "close"
    };

    /**
     * Konstruktor - initialisiere die Befehlsw�rter.
     */
    public Befehlswoerter()
    {
        // nichts zu tun momentan...
    }

    /**
     * Pr�fe, ob eine gegebene Zeichenkette ein g�ltiger
     * Befehl ist.
     * @return true  wenn die gegebene Zeichenkette ein g�ltiger
     *               Befehl ist, false sonst
     */
    public boolean istBefehl(String eingabe)
    {
         for(int i = 0; i < VALID_COMMANDS.length; i++) {    
            if(VALID_COMMANDS[i].equals(eingabe)) 
                return true;
        }
        // Wenn wir hierher gelangen, wurde die Eingabe nicht
        // in den Befehlsw�rter gefunden.
        return isContainerCommand(eingabe);
    }

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
     * Gib alle g�ltigen Befehlsw�rter auf die Konsole aus.
     */
    public void alleAusgeben() 
    {
        for(String befehl : VALID_COMMANDS) {
            System.out.print(befehl + "  ");
        }
        System.out.println();
    }
}
