/**
 * Write a description of class Item here.
 * 
 * @author Salomon Popp
 * @version 2016.01.08
 */
import java.util.HashMap;

public class Item
{
    private String name, description, event;
    
    /**
     * Constructor for objects of class Item
     */
    public Item(String name, String description, String event)
    {
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