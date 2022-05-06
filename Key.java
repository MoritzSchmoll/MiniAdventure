
/**
 * Beschreiben Sie hier die Klasse Mysterious.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Key extends Item
{
    private String type;
    
    /**
     * Konstruktor für Objekte der Klasse Mysterious
     */
    public Key(String _name, String _beschreibung, int _weight, String _type)
    {
        super(_name, _beschreibung, 0, _weight);
        type = _type;
    }
    
    public String getType()
    {
        return type;
    }
}
