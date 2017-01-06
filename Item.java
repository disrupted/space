
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
    

    /**
     * Constructor for objects of class Item
     */
    public Item(String name, String description)
    {
        this.description = description;
        this.name = name;

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
        output += " - " + description;
        return output;
    }
}
