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
     * Konstruktor für Objekte der Klasse Enemy
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
     * Gibt zurück was der Gegner fallen lässt.
     */
    public Item getDrop()
    {
        return drop;
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
