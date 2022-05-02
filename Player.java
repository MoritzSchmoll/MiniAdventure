import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
/**
 * Beschreiben Sie hier die Klasse player.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Player
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    final private int maxInventoryWeight = 50;
    
    private int health = 20;
    private int saturation = 60;
    private int agility = 50;
    private int inventoryWeight = 0;
    private Random rand = new Random();
    private boolean hasWeapon = false;
    private ArrayList<Item> inventory;
    /**
     * Konstruktor für Objekte der Klasse player
     */
    public Player()
    {
        inventory = new ArrayList<>();
        for( int i = 0 ; i < 5 ; i++ )
            inventory.add(new Food("Keks", "leckerer Keks, welcher 10 Sättigungspunkte wieder herstellt", 10));
    }

    public boolean hasKey()
    {
        for(int i = 0; i < inventory.size(); i++)
        {
            if(inventory.get(i) instanceof Key)
            {
                return true;
            }
        }
        return false;
    }
    
    public void printInventory(){
        if(inventory.size() == 0){
            System.out.println("Dein Rucksack ist leer!");
        }
        else{
            System.out.println("Du hast die folgenden Gegenstände in deinem Rucksack:");
            for(Item item : inventory){
                System.out.println("    " + item.getName());
            }
        }
    }

    public void printStats(){
        System.out.println("Lebenspunkte: " + health);
        System.out.println("Sättigung: " + saturation);
        System.out.println("Beweglichkeit: " + agility);
    }

    public void loseSaturation(){
        if(rand.nextInt(10) < 4){
            changeSaturation(-10);
            System.out.println("Du hast durch die Anstrengung beim Laufen 10 Sättigungspunkte verloren");
        }
    }
    
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
                if(name.equals(itemName) && x.getType() == 0){
                    System.out.println("Du hast " + name + " gegessen");
                    System.out.println("Durch das essen wurden " + x.getStat() + " Sättigungspunkte wieder hergestellt.");
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

    public void changeSaturation(int change){
        saturation += change;
        // if(saturation <= 0)
        // {
            // changeHealth(-1000);
        // }
    }

    public int getSaturation(){
        return saturation;
    }
    
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
    
    public int getHealth(){
        return health;
    }

    public int getAgility(){
        return agility;
    }
    
    public boolean hasWeapon()
    {
        return hasWeapon;
    }
    
    public boolean pickUp(Item newItem)
    {
        return pickUp(newItem, null, true);
    }
    
    public boolean pickUp(Item newItem, Room currentRoom)
    {
        return pickUp(newItem, currentRoom, false);
    }
    
    public boolean pickUp(Item newItem, Room currentRoom, boolean isItemInContainer){
        if(newItem != null && newItem.getType() != 1){
            inventory.add(newItem);
            System.out.println("Du hast " + newItem.getName() + " aufgehoben");
            return true;
        }
        else if(newItem != null && newItem.getType() == 1){
            if(hasWeapon == false){
                inventory.add(newItem);
                if(!isItemInContainer)
                {
                    currentRoom.removeItem(newItem);
                }
                hasWeapon = true;
                System.out.println("Du hast " + newItem.getName() + " aufgehoben");
            }
            else{
                System.out.println("Du hast schon eine Waffe im Inventar, um eine neue aufzuheben musst du deine jetzige ablegen.");
            }
            return true;
        }
        return false;
    }
    
    /** @author Moritz */
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
                if(item.getType() == 1)
                {
                    hasWeapon = false;
                }
                return true;
            }
        }
        return false;
    }
    
    public Weapon returnWeapon(){
        Iterator<Item> it = inventory.iterator();
        Item item = null;
        while(it.hasNext())
        {
            item = it.next();
            if(item.getType() == 1)
            {
                return (Weapon) item;
            }
        } 
        return null;
    }
    
    public void heal(){
        if(saturation > 10){
            health += 10;
            saturation -= 10;
            System.out.println("Du hast 10 Lebenspunkt regeneriert und dafür 10 Sättigungspunkte verloren.");
        }
        else{
            System.out.println("Deine Sättigung ist zu gering um dich zu regenerieren.");
        }
    }
}
