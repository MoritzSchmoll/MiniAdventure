
/**
 * Beschreiben Sie hier die Klasse Mysterious.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Key extends Item
{
    /**
     * Konstruktor f�r Objekte der Klasse Mysterious
     */
    public Key(String name, String beschreibung, int ability)
    {
        super(name, beschreibung, ability);
        type = 2;
    }
    
    public void useItem()
    {
        stat--;
    }
}
