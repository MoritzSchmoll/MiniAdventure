import java.util.ArrayList;
import java.util.Iterator;
/**
 * Write a description of class Container here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Container
{
    private ArrayList<Item> contents;
    private String name;
    private int sizeLimit;
    /**
     * Constructor for objects of class Container
     */
    public Container(String _name, int _sizeLimit)
    {
        name = _name;
        sizeLimit = _sizeLimit;
        contents = new ArrayList<>();
    }
    
    public String getName(){
        return name;
    }
    
    public void printContents()
    {
        if(contents.isEmpty())
        {
            System.out.println(name + " ist leer");
        }
        else
        {
            System.out.println(name + " enthält:");
            for(Item item : contents)
            {
                System.out.println(item.getName());
            }
        }
    }
    
    public void addItem(Item item)
    {
        if(contents.size() <= sizeLimit)
        {
            contents.add(item);
        }
        System.out.println(item.getName() + " wurde " + name + " hinzugefügt");
    }
    
    public Item takeItem(String itemName, boolean hasWeapon)
    {
        Item item;
        Iterator<Item> it = contents.iterator();
        while(it.hasNext())
        {
            item = it.next();
            if(item.getName().equals(itemName) && ((item.getType() == 1 && !hasWeapon) || item.getType() != 1))
            {
                it.remove();
                return item;
            }
            if(item.getName().equals(itemName) && hasWeapon && item.getType() == 1)
            {
                System.out.println("Du hast kannst nur eine Waffe tragen und keine weitere aufheben");
                return null;
            }
        }
        return null;
    }
}
