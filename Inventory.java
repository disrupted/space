
/**
 * Write a description of class Inventory here.
 * 
 * @author Salomon Popp
 * @version 2016.01.08
 */
import java.util.HashMap;
import java.util.Map;
public class Inventory
{
    private String name, description, event;
    private HashMap<String,Item> inventory;

    /**
     * Constructor for objects of class Inventory
     */
    public Inventory()
    {
        inventory = new HashMap<>();
        this.description = description;
        this.name = name;
        this.event = event;
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

    public int getSize()
    {
        return this.size();
    }
    
    public void addItem(String itemName, Item item)
    {
        this.put(itemName,item);
    }
    
    public Item getItem(String itemName)
    {
        for(Map.Entry<String, Item> entry : this.entrySet()) {
            String name = entry.getKey();
            Item item = entry.getValue();
            inventoryList += "\n " + item.getFullDescription();
        }
    }
    
    public String getEvent()
    {
        return event;
    }

    public String getFullDescription()
    {
        String output = "";
        output += name;
        output += " - " + description;
        return output;
    }
}
