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
    /**
     * Constructor for objects of class Enemy
     */
    public Enemy(String name, String description, Weapon weapon, int agility, int health)
    {
        this.agility = agility;
        this.name = name;
        this.description = description;
        this.weapon = weapon;
        this.health = health;
    }
    public String getName(){
        return name;
    }
    public int getHealth(){
        return health;
    }
    public void changeHealth(int change){
        health += change;
    }
    public int getAgility(){
        return agility;
    }
    public String getDescription(){
        return description;
    }
    public Weapon getWeapon(){
        return weapon;
    }
    public void printStats(){
        System.out.println("  Lebenspunkte: " + health);
        System.out.println("  Beweglichkeit: " + agility);
        System.out.println("  Der Gegner hat die folgende Waffe:");
        System.out.println("    " + weapon.getName());
        System.out.println("    " + weapon.getDescription());
        System.out.println("    Angriffspunkte: " + weapon.getStat());
    }
}
