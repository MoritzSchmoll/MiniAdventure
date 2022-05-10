import java.util.ArrayList;
/**
 * @author Moritz und Leonhard
 */
public class Item
{
    private String name;
    private String description;
    private int stat;
    private int weight;
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
    
    /**
     * Druckt die Eigenschaften eines Gegenstandes
     * @author Moritz
     */
    public void printAllStats()
    {
        System.out.println(name + ":");
        System.out.println(description);
        System.out.println("Gewicht: " + weight);
        if(this instanceof Weapon)
            System.out.println("Schaden: " + stat);
        else if(this instanceof Food)
            System.out.println("Nahrungswert: " + stat);
    }
}
