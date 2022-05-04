import java.util.ArrayList;
import java.util.Iterator;

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
    /**
     * Konstruktor f�r Objekte der Klasse Enemy
     * Gibt dem Gegner seine Startwerte.
     */
    public Enemy(String _name, String _description, Weapon _weapon, int _agility, int _health, String _specialFood, Item _drop)
    {
        agility = _agility;
        name = _name;
        description = _description;
        weapon = _weapon;
        health = _health;
        specialFood = _specialFood;
        drop = _drop;
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
     * Gibt zur�ck was der Gegner fallen l�sst.
     */
    public Item getDrop()
    {
        return drop;
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
