import java.util.Random;
/**
 * Beschreiben Sie hier die Klasse Weapons.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Weapon extends Item
{
    private Random rand;
    /**
     * Konstruktor f�r Objekte der Klasse Weapon
     */
    public Weapon (String name, String beschreibung, int damage, int weight)
    {
        super(name, beschreibung, damage, weight);
        rand = new Random();
    } 
    public int attack(int agility){
        if(rand.nextInt(100) > agility){
            return stat;
        }
        else{
            return stat;
        }
    }
}
