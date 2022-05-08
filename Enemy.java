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
     * Konstruktor f�r Objekte der Klasse Enemy
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
     * Gibt den Namen zur�ck.
     */
    public String getName(){
        return name;
    }
    
    /**
     * Gibt die aktuellen Lebenspunkte zur�ck.
     */
    public int getHealth(){
        return health;
    }
    
    /**
     * Ver�ndert die Lebenspunkte um den �bergebenen Wert.
     */
    public void changeHealth(int change){
        health += change;
    }
    
    /**
     * Gibt die aktuelle Beweglichkeit zur�ck.
     */
    public int getAgility(){
        return agility;
    }
    
    /**
     * Gibt die Beschreibung des Gegners zur�ck.
     */
    public String getDescription(){
        return description;
    }
    
    /**
     * Gibt die Waffe des Gegners zur�ck.
     */
    public Weapon getWeapon(){
        return weapon;
    }
    
    /**
     * Gibt das Lieblingsessen des Gegners zur�ck.
     */
    public String getSpecialFood()
    {
        return specialFood;        
    }
    
    /**
     * Gibt zur�ck, was der Gegner fallen l�sst.
     */
    public Item getDrop()
    {
        return drop;
    }
    
    /**
     * Beim Versuch den Gegner zu f�ttern, wird �berpr�ft, ob dem Gegner im Raum das gegebene Essen schmeckt, und dieser wird in dem Fall besiegt und entfernt und l�sst einen Gegenstand fallen.
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
     * Angriff des Gegner an Spieler. Wenn die zuf�llige Zahl gr��er ist als die Wendigkeit des Spielers wird im Schaden hinzugef�gt, ansonsten nicht.
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
     * F�gt dem Gegner die gegeben Anzahl an schaden hinzu und erkennt im Falle von einer Lebensanzahl von unter 0 den Sieg des Spielers.
     */
    public boolean damageEnemy(int damage){
        if(damage != -1){
            changeHealth(-damage);
            System.out.println("Du hast dem Gegner " + damage + " Schaden zugef�gt");
            System.out.println("Damit hat der Gegner noch " + health + " Lebenspunkte");
        }
        if(health <= 0){
            System.out.println("Du hast den Gegner im Raum besiegt");
            return true;
        }
        return false;
    }
    
    /**
     * Gibt die Informationen �ber den Gegner in der Konsole aus.
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
