import java.util.Arrays;

class CommandWords
{
    // ein konstantes Array mit den g�ltigen Befehlsw�rtern
    
    private static final String[] VALID_COMMANDS = {
        "go", "quit", "exit", "stop", "help", "look", "inventory", "stats", "eat", "pickup", "open", "attack", "escape", "enemy", "give", "heal"
    };
    private static final String[] CONTAINER_COMMANDS = {
        "put", "take", "close"
    };
    private static final String[] ALL_COMMANDS = {
        "go", "quit", "exit", "stop", "help", "look", "look {item}", "inventory", "stats", "eat {item}", "pickup {item}", "open {container}", 
        "close", "put {item}", "take {item}", "attack", "escape {item}", "enemy", "give {item}", "heal"
    };
    
    private static final String[] VALID_DIRECTIONS = {
        "north", "east", "south", "west"
    };
    
    public CommandWords()
    {
        
    }

    /**
     * Pr�fe, ob eine gegebene Zeichenkette ein g�ltiger
     * Befehl ist.
     * @return true  wenn die gegebene Zeichenkette ein g�ltiger
     *               Befehl ist, false sonst
     */
    public boolean isCommand(String input)
    {
        return Arrays.asList(VALID_COMMANDS).contains(input);
    }
    
    /**
     * �berpr�ft ob ein Befehl zum Bedienen von Containern genutzt wird.
     */
    public boolean isContainerCommand(String input)    
    {    
        return Arrays.asList(CONTAINER_COMMANDS).contains(input);
    }
    
    public boolean isDirection(String input)
    {
        return Arrays.asList(VALID_DIRECTIONS).contains(input);
    }
    
    /**
     * Gib alle g�ltigen Befehlsw�rter auf die Konsole aus.
     */
    public void printAll() 
    {
        for(String befehl : ALL_COMMANDS) 
        {
            System.out.print(befehl + ",  ");
        }
        System.out.println();
    }
}
