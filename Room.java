import java.util.Set;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
/**
 * Diese Klasse modelliert Räume des MiniAdventures.
 * 
 * Ein "Raum" repräsentiert einen Ort in der virtuellen Landschaft des
 * Spiels. Ein Raum ist mit anderen Räumen über Ausgänge verbunden.
 * Für jeden existierenden RoomExit hält ein Raum eine Referenz auf 
 * den benachbarten Raum.
 * 
 * @author Moritz
 * 
 */

class Room 
{
    private String description;
    private ArrayList<Item> inventory;
    private ArrayList<RoomExit> exits;
    private Enemy enemy;
    private boolean isLocked; // Wenn wahr, dann benötigt das Betreten des Raumes einen Schlüssel
    protected Container container;

    /**
     * Erzeugt einen Raum mit der gegebenen Beschreibung, falls nicht gegeben ist, ob dieser verschlossen ist, wird der Wert isLocked auf false gesetzt.
     */
    public Room(String _description)
    {
        this(_description, false);
    }

    
    /**
     *  Erzeugt einen Raum mit der gegebenen Beschreibung und dem Wahrheitswert, ob der Raum verschlossen ist.
     */
    public Room(String _description, boolean _isLocked) 
    {
        description = _description;
        exits = new ArrayList<>();
        inventory = new ArrayList<>();
        isLocked = _isLocked;
    }

    /**
     * Definiere einen RoomExit für diesen Raum.
     * @param direction  die Direction, in der der RoomExit liegen soll
     * @param nachbar   der Raum, der über diesen RoomExit erreicht wird
     */
    public void setExit(String direction, Room nachbarRaum) 
    {
        exits.add(new RoomExit(direction, nachbarRaum));
    }

    /**
     * Gibt zurück, ob der Raum verschlossen ist.
     */
    public boolean checkIfLocked()
    {
        return isLocked;
    }

    /**
     * Fügt dem Raum den gegebenen Gegenstand hinzu.
     */
    public void addItem(Item item)
    {
        inventory.add(item);
    }
    
    /**
     * Fügt dem Raum einen Container mit Namen und Maximalgröße hinzu.
     * Setzt den Verschlossenheitswert auf false
     * @author Moritz
     */
    public void addContainer(String name, int sizeLimit)
    {
        addContainer(name, sizeLimit, false);
    }

       /**
     * Fügt dem Raum einen Container mit Namen und Maximalgröße hinzu.
     * Ermöglicht das Verschließen des Containers
     * @author Moritz
     */
    
    public void addContainer(String name, int sizeLimit, boolean isLocked)
    {
        container = new Container(name, sizeLimit, isLocked);
    }
    
    /**
     * Gibt zurück, ob der Raum einen Container besitzt.
     */
    public boolean hasContainer()
    {
        return container != null;
    }

    /**
     * Gibt die Referenz zum sich im Raum befindenden Container zurück.
     */
    public Container getContainer()    
    {    
        return container;
    }

    /**
     * Fügt dem Raum einen Gegner hinzu.
     */
    public void addEnemy(String name, String description, Weapon weapon, int agility, int health, String specialFood, Item drop){
        enemy = new Enemy(name, description, weapon, agility, health, specialFood, drop, this);
    }

    /**
     * Entfernt einen Gegenstand aus dem Inventar des Raumes.
     * @author Moritz
     */
    public void removeItem(Item item)
    {
        for(int i = 0; i < inventory.size(); i++)
        {
            if(inventory.get(i) == item)
            {
                inventory.remove(i);
            }
        }
    }

    /**
     * @return  die kurze Beschreibung dieses Raums (die dem Konstruktor
     *          übergeben wurde)
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Liefere eine lange Beschreibung dieses Raums, in der Form:
     *     Sie sind in der Küche.
     *     Ausgänge: north west
     * @return  eine lange Beschreibung dieses Raumes
     */
    public String getLongDescription()
    {
        return "Sie sind " + description + ".\n" + gibAusgaengeAlsString();
    }

    /**
     * Liefere eine Beschreibung der Ausgänge dieses Raumes,
     * beispielsweise "Ausgänge: north west".
     * @return  eine Beschreibung der Ausgänge dieses Raumes
     */
    private String gibAusgaengeAlsString()
    {
        String ergebnis = "Ausgänge:";

        for(RoomExit exit : exits)
            ergebnis += " " + exit.getDirection();
        return ergebnis;
    }

    /**
     * Druckt alle Gegenstände, die sich im Raum befinden.
     */
    public void printAllItemsInRoom()
    {
        if(!inventory.isEmpty())
        {
            System.out.println("Du hast die folgenden Gegenstände im Raum gefunden");
            for (Item item : inventory)
            {
                System.out.println("  " + item.getName());
            }
        }
        else if(container == null)
        {
            System.out.println("In diesem Raum liegen keine Gegenstände");
        }
        if(container != null){
            System.out.println("Im Raum befindet sich der Container " + container.getName());
        }
    }

    /**
     * Gibt die Beschreibung eines Gegenstandes mit dem gegebenen Namen zurück, wenn es den Gegenstand gibt.
     */
    public void printItemDescription(String name)
    {
        for (Item item : inventory){
            if(name.equals(item.getName())){
                item.printAllStats();
                return;
            }
        }
        System.out.println("Solch einen Item gibt es nicht!");
    }

    /**
     * Liefere den Raum, den wir erreichen, wenn wir aus diesem Raum
     * in die angegebene Direction gehen. Liefere 'null', wenn in
     * dieser Direction kein RoomExit ist.
     * @param   direction die Direction, in die gegangen werden soll
     * @return  den Raum in der angegebenen Direction
     */
    public Room getExit(String direction) 
    {
        for (RoomExit exit : exits){
            if (direction.equals(exit.getDirection())){
                return exit.getNeighbourRoom();
            }
        }
        return null;
    }

    /**
     *  Überprüft, ob sich ein Gegenstand mit dem gegebenen Namen im Raum befindet und gibt diesen in dem Fall zurück.
     *  @author Moritz
     */
    public Item findItem(String name){
        Item item;
        Iterator<Item> it = inventory.iterator();
        while (it.hasNext()){
            item = it.next();
            if(name.equals(item.getName())){
                return item;
            }
        }
        System.out.println("Es gibt keinen Gegenstand in diesem Raum, der so heißt!");
        return null;
    }

    /**
     * Gibt zurück, ob sich ein Gegner im Raum befindet.
     */
    public boolean hasEnemy(){
        if(enemy != null) return true;
        return false;
    }

    /**
     * Zuständig für den Versuch dem Kampf zu flüchten. Hierzu werden die Spielereigenschaften zur Wahrscheinlichkeitsüberprüfung hinzugezogen.
     * Ebenso wird überprüft, ob der gewählte Ausgang verfügbar ist.
     * @author Leonhard
     */
    public int tryingToEscape(String direction){
        Random rand = new Random();
        boolean exitAvailable = false;
        for(RoomExit exit : exits){
            if(exit.getDirection().equals(direction)){
                exitAvailable = true;
                break;
            }
        }
        if(exitAvailable){
            if(rand.nextInt(101) > enemy.getAgility()){
                return 0;
            }
            return 1;
        }
        return 2;
    }
    
    /**
     * Druckt die Beschreibung des Gegners im Raum
     */
    public void printEnemyDescription(){
        System.out.println("Der Gegner mit dir im Raum ist ein " + enemy.getName() + " dabei handelt es sich um " + enemy.getDescription());
        System.out.println("Der Gegner hat die folgenden Stats:");
        enemy.printStats();
    }

    /**
     * Gibt die Referenz zum Gegner im Raum zurück
     */
    public Enemy getEnemy(){
        if(enemy == null)
            return null;
        return enemy;
    }
    
    public void removeEnemy()
    {
        enemy = null;
    }
}