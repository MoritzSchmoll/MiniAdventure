import java.util.ArrayList;
import java.util.Iterator;
/**
 * Container stellen Beh�ltnisse, die Gegenst�nde beinhalten k�nnen, die der Spieler nehmen kann.
 *
 * @author Moritz
 * 
 */
public class Container
{
    private ArrayList<Item> contents;
    private String name;
    private int sizeLimit;
    private boolean isLocked;
    /**
     * Constructor for objects of class Container
     */
    public Container(String _name, int _sizeLimit)
    {
        this(_name, _sizeLimit, false);
    }
    
    public Container(String _name, int _sizeLimit, boolean _isLocked)
    {
        name = _name;
        sizeLimit = _sizeLimit;
        contents = new ArrayList<>();
        isLocked = _isLocked;
    }
    
    /**
     * Gibt den Namen des Containers zur�ck
     */
    public String getName(){
        return name;
    }
    
    public boolean isLocked()
    {
        return isLocked;    
    }
    
    /**
     * Druckt die Inhalte des Containers einzeln, falls dieser Gegenst�nde in sich tr�gt. Ansonsten wird gedruckt, dass dieser leer ist.
     */
    public void printContents()
    {
        if(contents.isEmpty())
        {
            System.out.println(name + " ist leer");
        }
        else
        {
            System.out.println(name + " enth�lt:");
            for(Item item : contents)
            {
                System.out.println(item.getName());
            }
        }
    }
    
    /**
     * F�gt einen Gegenstand zum Container hinzu
     */
    public void addItem(Item item)
    {
        if(contents.size() < sizeLimit)
        {
            contents.add(item);
        }
    }
    
    
    /**
     * Nimmt einen Gegenstand aus dem Container, f�gt diesen dem Inventar des Spielers hinzu und �berpr�ft, ob der Spieler bereits eine Waffe hat, wenn er eine neue aufheben will.
     */
    public Item takeItem(String itemName, boolean hasWeapon)
    {
        Item item;
        Iterator<Item> it = contents.iterator();
        while(it.hasNext())
        {
            item = it.next();
            if(item.getName().equals(itemName) && ((item instanceof Weapon && !hasWeapon) || !(item instanceof Weapon)))
            {
                it.remove();
                return item;
            }
            if(item.getName().equals(itemName) && hasWeapon && item instanceof Weapon)
            {
                System.out.println("Du hast kannst nur eine Waffe tragen und keine weitere aufheben");
                return null;
            }
        }
        return null;
    }
}
