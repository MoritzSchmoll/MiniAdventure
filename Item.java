import java.util.ArrayList;
/**
 * Write a description of class Gegenstände here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    protected String name;
    protected String description;
    protected int stat;
    protected int weight;
    /**
     * Konstruktor für Objekte der Klasse Item
     * Hier werden den Gegenständen ihre Daten übergeben.
     */
    public Item(String _name, String _description, int _stat, int _weight)
    {
        name = _name;
        description = _description;
        stat = _stat;
        weight = _weight;
    }
    
    /**
     * Gibt den Namen zurück.
     */
    public String getName(){
        return name;
    }

    /**
     * Gibt die Beschreibung zurück.
     */
    public String getDescription(){
        return description;
    }
    
    /**
     * Gibt einen bestimmten Wert zurück, der nach Art des Gegenstandes unterschiedlich benutzt werden kann.
     */
    public int getStat(){
        return stat;
    }
       
    /**
     * Gibt das Gewicht des Gegenstandes zurück.
     */
    public int getWeight()
    {
        return weight;
    }
}
