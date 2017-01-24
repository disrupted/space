/**
 * Write a description of class Item here.
 * 
 * @author Salomon Popp
 * @version 2016.01.08
 */

public class Item
{
    private String name, description, event;
    private int weight;
    
    /**
     * Constructor for objects of class Item
     */
    public Item(String name, String description, String event, int weight)
    {
        this.description = description;
        this.name = name;
        this.event = event;
        this.weight = weight;
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
    
    public int getWeight(){
        return weight;
    }
    
    public String getFullDescription()
    {
        String output = name + " (" + weight + "g) - " + description;
        return output;
    }
}