import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
/**
 * @author Moritz und Leonhard
 */
public class Player
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    final private int maxInventoryWeight = 15;

    private int health = 20;
    private int saturation = 60;
    private int agility = 50;
    private int weight = 0;
    private Random rand = new Random();
    private boolean hasWeapon = false;
    private ArrayList<Item> inventory;

    /**
     * Konstruktor f�r Objekte der Klasse Player
     * Hier wird das Anfangsinventar des Spielers zusammen gestellt.
     */
    public Player()
    {
        inventory = new ArrayList<>();
        for( int i = 0 ; i < 5 ; i++ )
            inventory.add(new Food("Keks", "leckerer Keks, welcher 10 S�ttigungspunkte wieder herstellt", 10, 1));
    }

    /**
     * �berpr�ft ob der Spieler ein Gegenstand mit dem �bergebenen Namen im Inventar hat.
     * @author Moritz
     */
    
    public Item getItem(String itemName)
    {
        for(int i = 0; i < inventory.size(); i++)
        {
            if(inventory.get(i).getName().equals(itemName))
            {
                return inventory.get(i);
            }
        }
        return null;
    }
    
    /**
     * �berpr�ft ob der Spieler ein Gegenstand mit dem �bergebenen Namen im Inventar hat.
     * @author Moritz
     */
    public boolean hasItem(String itemName)
    {
        if(getItem(itemName) != null)
        {
            return true;
        }
        return false;
    }

    /**
     * Entfernt ein Gegenstand mit dem �bergebenen Namen.
     * @author Moritz
     */
    public void removeItem(String name)
    {
        Iterator<Item> it = inventory.iterator();
        while (it.hasNext()){
            Item x = it.next();
            String itemName = x.getName();
            if(name.equals(itemName)){
                it.remove();
                break;
            }
        }
    }

    /**
     * Z�hlt �ber die Konsole alle Gegenst�nde die im Spieler Inventar vorhanden sind auf.
     * @author Leonhard
     */
    public void printInventory(){
        if(inventory.size() == 0){
            System.out.println("Dein Rucksack ist leer!");
        }
        else{
            System.out.println("Du hast die folgenden Gegenst�nde in deinem Rucksack:");
            for(Item item : inventory){
                System.out.println("    " + item.getName());
            }
        }
    }

    /**
     * Gibt �ber die Konsole alle Spielerinformationen aus.
     */
    public void printStats(){
        System.out.println("Lebenspunkte: " + health);
        System.out.println("S�ttigung: " + saturation);
        System.out.println("Beweglichkeit: " + (agility - updateWeight()));
        System.out.println("Gewicht: " + updateWeight() + " von " + maxInventoryWeight);
    }

    /**
     * Sorgt daf�r, dass der Spieler mit einer Wahrscheinlichkeit von 50% 10 S�ttigungspunkte verliert.
     * @author Leonhard
     */
    public void loseSaturation(){
        if(rand.nextInt(10) < 4){
            changeSaturation(-10);
            System.out.println("Du hast durch die Anstrengung beim Laufen 10 S�ttigungspunkte verloren");
        }
    }

    /**
     * Mit dieser Methode kann der Spieler essen.
     * Wobei immer so viele S�ttigungspunkte hergestellt werden wie das Essen als Wert �ber gibt.
     * Au�erdem wird kontrolliert ob die S�ttigung nicht schon bei 90 oder h�her liegt. Wenn das der Fall ist kann nichts mehr gegessen werden.
     * @author Leonhard und Moritz
     */
    public void eat(String name){
        if(saturation >= 90){
            System.out.println("Du hast keinen Hunger");
        }
        else{
            Iterator<Item> it = inventory.iterator();
            boolean gefunden = false;
            while (it.hasNext()){
                Item x = it.next();
                String itemName = x.getName();
                if(name.equals(itemName) && x instanceof Food){
                    System.out.println("Du hast " + name + " gegessen");
                    System.out.println("Durch das essen wurden " + x.getStat() + " S�ttigungspunkte wieder hergestellt.");
                    changeSaturation(x.getStat());
                    it.remove();
                    gefunden = true;
                    break;
                }
            }
            if(!gefunden)  
                System.out.println("Du hast kein solches Essen in deinem Rucksack");
        }
    }

    /**
     * Ver�ndert die S�ttigung um den �bergebenen Wert.
     */
    public void changeSaturation(int change){
        saturation += change;
    }

    /**
     * Gibt die S�ttigung zur�ck.
     */
    public int getSaturation(){
        return saturation;
    }

    /**
     * Wenn der Spieler mehr als 10 S�ttigungspunkte besitzt werden 10 Lebenspunkte unter Verbrauch von 10 S�ttigungspunkten hergestellt.
     * @author Leonhard
     */
    public void heal(){
        if(saturation > 10){
            health += 10;
            saturation -= 10;
            System.out.println("Du hast 10 Lebenspunkt regeneriert und daf�r 10 S�ttigungspunkte verloren.");
        }
        else{
            System.out.println("Deine S�ttigung ist zu gering um dich zu regenerieren.");
        }
    }

    /**
     * Ver�ndert die Lebenspunkte um den �bergebenen Wert und informiert �ber diese Ver�nderung mit Text �ber die Konsole.
     * @author Leonhard
     */
    public void changeHealth(int change){
        health += change;
        if(change > 0){
            System.out.println("Du hast " + change + " Lebenspunkte regeneriert");
            System.out.println("Jetzt besitzt du " + health + " Lebenspunkte");
        }
        else if(change < 0){
            if(health < 0)
                health = 0;
            System.out.println("Du hast " + Math.abs(change) + " Schadenspunkte erhalten");
            System.out.println("Jetzt besitzt du " + health + " Lebenspunkte");
        }
        if(health <= 0){
            System.out.println("Du bist gestorben");
            System.exit(0);
        }
    }

    /**
     * Gibt die aktuelle Anzahl an Lebenspunkten wieder.
     */
    public int getHealth(){
        return health;
    }

    /**
     * Gibt die aktuelle Anzahl an Beweglichkeitspunkten unter Ber�cksichtigung des Gewichts wieder.
     */
    public int getAgility(){
        return agility - updateWeight();
    }

    /**
     * �berpr�ft ob der Spieler eine Waffe besitzt.
     */
    public boolean hasWeapon()
    {
        return hasWeapon;
    }

    /**
     * @author Moritz
     */    
    public boolean hasKeyOfType(String type)
    {
        Item item;
        for(int i = 0; i < inventory.size(); i++)
        {
            item = inventory.get(i);
            if(item instanceof Key && ((Key) item).getType().equals(type))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * F�gt denn �bergebenen Gegenstand dem Spieler Inventar hinzu.
     */
    public boolean pickUp(Item newItem)
    {
        return pickUp(newItem, null, true);
    }

    /**
     * F�gt denn �bergebenen Gegenstand dem Spieler Inventar hinzu unter Ber�cksichtigung des Raumes. (N�tig f�r Container)
     */
    public boolean pickUp(Item newItem, Room currentRoom)
    {
        return pickUp(newItem, currentRoom, false);
    }

    /**
     * Dies ist die eigentliche Methode zum hinzuf�gen von Gegenst�nden in der noch �berpr�ft wird ob der Spieler schon eine Waffe hat und verbietet es eine aufzuheben, wenn er schon eine besizt.
     * Des Weiteren wird das L�schen des Gegenstandes an seinem jetzigen Standort in Auftrag gegeben.
     * Das Maximalgewicht darf ebenso nicht �berschritten werden.
     * @author Moritz und Leonhard
     */
    public boolean pickUp(Item newItem, Room currentRoom, boolean isItemInContainer){
        if(newItem != null && newItem.getName().equals("Excalibur")){
            System.out.print("");
            System.out.println("Du hast das legend�re Schwert Excalibur aufgehoben und somit das Ziel des Spiels erreicht.");
            System.out.println("");
            System.out.println("Herzlichen Gl�ckwunsch");
            System.exit(0);
        }
        if(newItem != null && updateWeight() + newItem.getWeight() < maxInventoryWeight)
        {
            if(!(newItem instanceof Weapon))
            {
                inventory.add(newItem);
                if(!isItemInContainer)
                    currentRoom.removeItem(newItem);
                System.out.println("Du hast " + newItem.getName() + " aufgehoben");
                return true;
            }
            else if(!hasWeapon)
            {
                inventory.add(newItem);
                if(!isItemInContainer)
                    currentRoom.removeItem(newItem);
                hasWeapon = true;
                System.out.println("Du hast " + newItem.getName() + " aufgehoben");
                return true;
            }
            else
            {
                System.out.println("Du hast schon eine Waffe im Inventar, um eine neue aufzuheben musst du deine jetzige ablegen.");
                return false;
            }
        }
        else if(newItem != null)
        {
            System.out.println("Du kannst nichts weiteres tragen. Leg einen anderen Gegenstand in einem Beh�lter ab, um einen weiteren aufzuheben.");
        }
        return false;
    }

    /**
     * Erm�glicht es dem Spieler Gegenst�nde in den Container zu legen.
     * @author Moritz
     */
    public boolean putItemInContainer(Container container, String itemName)
    {
        Iterator<Item> it = inventory.iterator();
        Item item = null;
        while(it.hasNext())
        {
            item = it.next();
            if(item.getName().equals(itemName))
            {
                container.addItem(item);
                it.remove();
                if(item instanceof Weapon)
                {
                    hasWeapon = false;
                }
                System.out.println(itemName + " wurde " + container.getName() + " hinzugef�gt");
                return true;
            }
        }
        return false;
    }

    /**
     * Aktualisiert das Gewicht des Inventars des Spielers.
     * @author Moritz
     */
    private int updateWeight()
    {
        weight = 0;
        for(int i = 0; i < inventory.size(); i++)
        {
            weight += inventory.get(i).getWeight();
        }
        return weight;
    }
    
    /**
     * Gibt die aktuelle Waffe des Spielers zur�ck.
     * @author Leonhard
     */
    public Weapon returnWeapon(){
        Iterator<Item> it = inventory.iterator();
        Item item = null;
        while(it.hasNext())
        {
            item = it.next();
            if(item instanceof Weapon)
            {
                return (Weapon) item;
            }
        } 
        return null;
    }

}
