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
    protected int type;
    /**
     * Constructor for objects of class Gegenstände
     */
    public Item(String _name, String _description, int _stat)
    {
        name = _name;
        description = _description;
        stat = _stat;
    }
    
    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }
    
    public int getStat(){
        return stat;
    }
    
    public int getType()
    {
        return type;
    }


    
    
    
}
