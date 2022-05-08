import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Write a description of class Enemy here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Enemy
{
    private String name;
    private String description;
    private Weapon weapon;
    private int agility;
    private int health;
    private String specialFood;
    private Item drop;
    private Room room;
    /**
     * Konstruktor für Objekte der Klasse Enemy
     * Gibt dem Gegner seine Startwerte.
     */
    public Enemy(String _name, String _description, Weapon _weapon, int _agility, int _health, String _specialFood, Item _drop, Room _room)
    {
        agility = _agility;
        name = _name;
        description = _description;
        weapon = _weapon;
        health = _health;
        specialFood = _specialFood;
        drop = _drop;
        room = _room;
    }
    
    /**
     * Gibt den Namen zurück.
     */
    public String getName(){
        return name;
    }
    
    /**
     * Gibt die aktuellen Lebenspunkte zurück.
     */
    public int getHealth(){
        return health;
    }
    
    /**
     * Verändert die Lebenspunkte um den übergebenen Wert.
     */
    public void changeHealth(int change){
        health += change;
    }
    
    /**
     * Gibt die aktuelle Beweglichkeit zurück.
     */
    public int getAgility(){
        return agility;
    }
    
    /**
     * Gibt die Beschreibung des Gegners zurück.
     */
    public String getDescription(){
        return description;
    }
    
    /**
     * Gibt die Waffe des Gegners zurück.
     */
    public Weapon getWeapon(){
        return weapon;
    }
    
    /**
     * Gibt das Lieblingsessen des Gegners zurück.
     */
    public String getSpecialFood()
    {
        return specialFood;        
    }
    
    /**
     * Gibt zurück, was der Gegner fallen lässt.
     */
    public Item getDrop()
    {
        return drop;
    }
    
    /**
     * Beim Versuch den Gegner zu füttern, wird überprüft, ob dem Gegner im Raum das gegebene Essen schmeckt, und dieser wird in dem Fall besiegt und entfernt und lässt einen Gegenstand fallen.
     * @author Moritz
     */
    public boolean feed(String food)
    {
        if(getSpecialFood().equals(food))
        {
            String dropName = drop.getName();
            String enemyName = getName();
            System.out.println("Du hast " + enemyName + " im Raum zufriedengestellt. Er hat als Dank einen " + dropName + " auf den Boden gelegt.");
            room.addItem(drop);
            return true;
        }
        return false;
    }
    
    /**
     * Angriff des Gegner an Spieler. Wenn die zufällige Zahl größer ist als die Wendigkeit des Spielers wird im Schaden hinzugefügt, ansonsten nicht.
     */
        public int attack(int playerAgility){
        Random rand = new Random();
        if(rand.nextInt(101) > playerAgility){
            return -(weapon.getStat());
        }
        System.out.println("Der Gegner hat dich mit seiner Attacke verfehlt");
        return 0;
    }
    
    /**
     * Fügt dem Gegner die gegeben Anzahl an schaden hinzu und erkennt im Falle von einer Lebensanzahl von unter 0 den Sieg des Spielers.
     */
    public boolean damageEnemy(int damage){
        if(damage != -1){
            changeHealth(-damage);
            System.out.println("Du hast dem Gegner " + damage + " Schaden zugefügt");
            System.out.println("Damit hat der Gegner noch " + health + " Lebenspunkte");
        }
        if(health <= 0){
            System.out.println("Du hast den Gegner im Raum besiegt");
            return true;
        }
        return false;
    }
    
    /**
     * Gibt die Informationen über den Gegner in der Konsole aus.
     */
    public void printStats(){
        System.out.println("  Lebenspunkte: " + health);
        System.out.println("  Beweglichkeit: " + agility);
        System.out.println("  Der Gegner hat die folgende Waffe:");
        System.out.println("    " + weapon.getName());
        System.out.println("    " + weapon.getDescription());
        System.out.println("    Angriffspunkte: " + weapon.getStat());
    }
}
