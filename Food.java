
/**
 * Beschreiben Sie hier die Klasse Food.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Food extends Item
{
    /**
     * Konstruktor für Objekte der Klasse Food
     */
    public Food (String name, String beschreibung, int saturation)
    {
        super(name, beschreibung, saturation);
        type = 0;
    }
}
