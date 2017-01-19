
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
    private int limit = 1;

    /**
     * Constructor for objects of class Inventory
     */
    public Inventory()
    {
        inventory = new HashMap<>();
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
        return inventory.size();
    }

    public void addItem(String itemName, Item item)
    {
        inventory.put(itemName,item);
    }

    public void removeItem(String itemName)
    {
        inventory.remove(itemName);
    }

    public Item getItem(String itemName)
    {
        String inventoryList = "";
        for(Map.Entry<String, Item> entry : inventory.entrySet()) {
            String name = entry.getKey();
            if (name.equals(itemName)) { 
                Item item = entry.getValue();
                return item;
            }
        }
        return null;
    }

    public int getLimit()
    {
        return limit;
    }

    public void setLimit(int newLimit)
    {
        limit = newLimit;      
    }

    public String getFullDescription()
    {
        String inventoryList = "";
        for(Map.Entry<String, Item> entry : inventory.entrySet()) {
            String name = entry.getKey();
            Item item = entry.getValue();
            inventoryList += "\n " + item.getFullDescription();
        }
        if (inventoryList == "") { return "you haven't collected any items in your inventory"; }
        return "your inventory contains " + getSize() + "/" + getLimit() + " items :" + inventoryList;
    }
}