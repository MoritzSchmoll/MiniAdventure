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
     * Konstruktor für Objekte der Klasse Weapon
     */
    public Weapon (String name, String beschreibung, int damage)
    {
        super(name, beschreibung, damage);
        rand = new Random();
        type = 1;
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
