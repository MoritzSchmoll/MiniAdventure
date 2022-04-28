
/**
 * Die Klasse Ausgang beschreibt die Richtung des Ausgangs und den Raum, der an diesen angrenzt.
 *
 * 
 */
public class RoomExit
{
    // instance variables - replace the example below with your own
    private String direction;
    private Room nachbarRaum;

    /**
     * Constructor for objects of class Ausgang
     */
    public RoomExit(String _direction, Room _neighbourRoom)
    {
        direction = _direction;
        nachbarRaum = _neighbourRoom;
    }

    /**
     * Gibt die Richtung des Ausgangs zurück.
     *
     */
    public String getDirection()
    {
        return direction;
    }
    
    /**
     * Gibt den Raum hinter dem Ausgang an.
     *
     */
    public Room getNeighbourRoom()
    {
        return nachbarRaum;
    }
}
