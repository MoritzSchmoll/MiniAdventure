import java.util.Set;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
/**
 * Diese Klasse modelliert R�ume des MiniAdventures.
 * 
 * Ein "Raum" repr�sentiert einen Ort in der virtuellen Landschaft des
 * Spiels. Ein Raum ist mit anderen R�umen �ber Ausg�nge verbunden.
 * F�r jeden existierenden RoomExit h�lt ein Raum eine Referenz auf 
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
    private boolean isLocked; // Wenn wahr, dann ben�tigt das Betreten des Raumes einen Schl�ssel
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
     * Definiere einen RoomExit f�r diesen Raum.
     * @param direction  die Direction, in der der RoomExit liegen soll
     * @param nachbar   der Raum, der �ber diesen RoomExit erreicht wird
     */
    public void setExit(String direction, Room nachbarRaum) 
    {
        exits.add(new RoomExit(direction, nachbarRaum));
    }

    /**
     * Gibt zur�ck, ob der Raum verschlossen ist.
     */
    public boolean checkIfLocked()
    {
        return isLocked;
    }

    /**
     * F�gt dem Raum den gegebenen Gegenstand hinzu.
     */
    public void addItem(Item item)
    {
        inventory.add(item);
    }
    
    /**
     * F�gt dem Raum einen Container mit Namen und Maximalgr��e hinzu.
     * Setzt den Verschlossenheitswert auf false
     * @author Moritz
     */
    public void addContainer(String name, int sizeLimit)
    {
        addContainer(name, sizeLimit, false);
    }

       /**
     * F�gt dem Raum einen Container mit Namen und Maximalgr��e hinzu.
     * Erm�glicht das Verschlie�en des Containers
     * @author Moritz
     */
    
    public void addContainer(String name, int sizeLimit, boolean isLocked)
    {
        container = new Container(name, sizeLimit, isLocked);
    }
    
    /**
     * Gibt zur�ck, ob der Raum einen Container besitzt.
     */
    public boolean hasContainer()
    {
        return container != null;
    }

    /**
     * Gibt die Referenz zum sich im Raum befindenden Container zur�ck.
     */
    public Container getContainer()    
    {    
        return container;
    }

    /**
     * F�gt dem Raum einen Gegner hinzu.
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
     *          �bergeben wurde)
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Liefere eine lange Beschreibung dieses Raums, in der Form:
     *     Sie sind in der K�che.
     *     Ausg�nge: north west
     * @return  eine lange Beschreibung dieses Raumes
     */
    public String getLongDescription()
    {
        return "Sie sind " + description + ".\n" + gibAusgaengeAlsString();
    }

    /**
     * Liefere eine Beschreibung der Ausg�nge dieses Raumes,
     * beispielsweise "Ausg�nge: north west".
     * @return  eine Beschreibung der Ausg�nge dieses Raumes
     */
    private String gibAusgaengeAlsString()
    {
        String ergebnis = "Ausg�nge:";

        for(RoomExit exit : exits)
            ergebnis += " " + exit.getDirection();
        return ergebnis;
    }

    /**
     * Druckt alle Gegenst�nde, die sich im Raum befinden.
     */
    public void printAllItemsInRoom()
    {
        if(!inventory.isEmpty())
        {
            System.out.println("Du hast die folgenden Gegenst�nde im Raum gefunden");
            for (Item item : inventory)
            {
                System.out.println("  " + item.getName());
            }
        }
        else if(container == null)
        {
            System.out.println("In diesem Raum liegen keine Gegenst�nde");
        }
        if(container != null){
            System.out.println("Im Raum befindet sich der Container " + container.getName());
        }
    }

    /**
     * Gibt die Beschreibung eines Gegenstandes mit dem gegebenen Namen zur�ck, wenn es den Gegenstand gibt.
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
     *  �berpr�ft, ob sich ein Gegenstand mit dem gegebenen Namen im Raum befindet und gibt diesen in dem Fall zur�ck.
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
        System.out.println("Es gibt keinen Gegenstand in diesem Raum, der so hei�t!");
        return null;
    }

    /**
     * Gibt zur�ck, ob sich ein Gegner im Raum befindet.
     */
    public boolean hasEnemy(){
        if(enemy != null) return true;
        return false;
    }

    /**
     * Zust�ndig f�r den Versuch dem Kampf zu fl�chten. Hierzu werden die Spielereigenschaften zur Wahrscheinlichkeits�berpr�fung hinzugezogen.
     * Ebenso wird �berpr�ft, ob der gew�hlte Ausgang verf�gbar ist.
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
     * Gibt die Referenz zum Gegner im Raum zur�ck
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