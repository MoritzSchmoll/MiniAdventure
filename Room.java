import java.util.Set;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
/**
 * Diese Klasse modelliert Räume des MiniAdventures.
 * 
 * Diese Klasse ist Teil der Anwendung "MiniAdventure".
 * "MiniAdventure" ist ein sehr einfaches textbasiertes 
 * Adventure-Game.
 * 
 * Ein "Raum" repräsentiert einen Ort in der virtuellen Landschaft des
 * Spiels. Ein Raum ist mit anderen Räumen über Ausgänge verbunden.
 * Für jeden existierenden RoomExit hält ein Raum eine Referenz auf 
 * den benachbarten Raum.
 * 
 * @author  Michael Kölling und David J. Barnes
 * 
 */

class Room 
{
    private Random rand;
    private String description;
    private ArrayList<Item> inventory;
    private ArrayList<RoomExit> exits; // die Ausgänge dieses Raums
    private Enemy enemy;
    private boolean isLocked; // Wenn wahr, dann benötigt das Betreten des Raumes einen Schlüssel
    protected Container container;

    /**
     * Erzeuge einen Raum mit einer Beschreibung. Ein Raum
     * hat anfangs keine Ausgänge. Eine Beschreibung hat die Form 
     * "in einer Küche" oder "auf einem Sportplatz".
     * @param description  die Beschreibung des Raums
     */

    public Room(String _description)
    {
        this(_description, false);
    }

    public Room(String _description, boolean _isLocked) 
    {
        rand = new Random();
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

    public boolean checkIfLocked()
    {
        return isLocked;
    }

    public void addFood(String name, String description, int stat)
    {
        inventory.add(new Food(name, description, stat));
    }

    public void addWeapon(String name, String description, int stat)
    {
        inventory.add(new Weapon(name, description, stat));
    }

    public void addKey(String name, String description, int stat)
    {
        inventory.add(new Key(name, description, stat));
    }

    private void addItem(Item item)
    {
        inventory.add(item);
    }
    
    public void addContainer(String name, int sizeLimit)
    {
        container = new Container(name, sizeLimit);
    }

    public boolean hasContainer()
    {
        return container != null;
    }

    public Container getContainer()    
    {    
        return container;
    }

    public void addEnemy(String name, String description, Weapon weapon, int agility, int health, String specialFood, Item drop){
        enemy = new Enemy(name, description, weapon, agility, health, specialFood, drop);
    }

    public void removeItem(Item item)
    {
        //.remove(inventory.indexOf(item));

        for(int i = 0; i < inventory.size()-1; i++)
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

    public void printAlleGegenstaende(){
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

    public String getItemDescription(String name){
        for (Item item : inventory){
            if(name.equals(item.getName())){
                return item.getDescription();
            }
        }
        return ("Solch einen Item gibt es nicht !");
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

    public Item findItem(String name){
        Iterator<Item> it = inventory.iterator();
        boolean gefunden = false;
        while (it.hasNext()){
            Item x = it.next();
            String itemName = x.getName();
            if(name.equals(itemName)){
                return x;
            }
        }
        System.out.println("Es gibt keinen Item in diesem Raum der so heißt !");
        return null;
    }

    public boolean hasEnemy(){
        if(enemy != null) return true;
        return false;
    }

    public int tryingToEscape(String direction){
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

    public void printEnemyDescription(){
        System.out.println("Der Gegner mit dir im Raum ist ein " + enemy.getName() + " dabei handelt es sich um " + enemy.getDescription());
        System.out.println("Der Gegner hat die folgenden Stats:");
        enemy.printStats();
    }

    public boolean damageEnemy(int damage){
        if(damage != -1){
            enemy.changeHealth(-damage);
            System.out.println("Du hast dem Gegner " + damage + " Schaden zugefügt");
            System.out.println("Damit hat der Gegner noch " + enemy.getHealth() + " Lebenspunkte");
        }
        if(enemy.getHealth() <= 0){
            System.out.println("Du hast den Gegner im Raum besiegt");
            enemy = null;
            return true;
        }
        return false;
    }

    public Enemy getEnemy(){
        if(enemy == null)
            return null;
        return enemy;
    }

    public boolean feedEnemy(String food)
    {
        if(enemy != null && enemy.getSpecialFood().equals(food))
        {
            String drop = enemy.getDrop().getName();
            String enemyName = enemy.getName();
            System.out.println("Du hast " + enemyName + " im Raum zufriedengestellt. Er hat als Dank einen " + drop + " auf den Boden gelegt.");
            addItem(enemy.getDrop());
            enemy = null;
            return true;
        }
        return false;
    }
    
    public int enemyAttack(int playerAgility){
        if(rand.nextInt(101) > playerAgility){
            return -enemy.getWeapon().getStat();
        }
        System.out.println("Der Gegner hat dich mit seiner Attacke verfehlt");
        return 0;
    }
}