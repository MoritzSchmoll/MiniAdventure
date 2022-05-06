import java.util.ArrayList;
/**
 * Write a description of class Gegenst�nde here.
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
     * Konstruktor f�r Objekte der Klasse Item
     * Hier werden den Gegenst�nden ihre Daten �bergeben.
     */
    public Item(String _name, String _description, int _stat, int _weight)
    {
        name = _name;
        description = _description;
        stat = _stat;
        weight = _weight;
    }
    
    /**
     * Gibt den Namen zur�ck.
     */
    public String getName(){
        return name;
    }

    /**
     * Gibt die Beschreibung zur�ck.
     */
    public String getDescription(){
        return description;
    }
    
    /**
     * Gibt einen bestimmten Wert zur�ck, der nach Art des Gegenstandes unterschiedlich benutzt werden kann.
     */
    public int getStat(){
        return stat;
    }
       
    /**
     * Gibt das Gewicht des Gegenstandes zur�ck.
     */
    public int getWeight()
    {
        return weight;
    }
}
