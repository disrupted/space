
/**
 * Write a description of class Item here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.HashMap;
public class Item
{
    private String name;
    private String description;
    public static HashMap<Item, Room> itemMap;

    /**
     * Constructor for objects of class Item
     */
    public Item(String name, String description)
    {
        this.description = description;
        this.name = name;
        itemMap = new HashMap<>();
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }
    
    public String getFullDescription()
    {
        String output = "";
        output += name;
        output += " - " + description + "\n";
        return output;
    }
    
    public void placeItem(Room room)
    {
        itemMap.put(this, room);
    }
    
    public void takeItem()
    {
        itemMap.remove(this);
    }
}
