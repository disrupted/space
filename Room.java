/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */
import java.util.HashMap;

public class Room 
{
    public String name;
    public String description;
    public String transDescription;
    private HashMap<String,Room> exits;
    private int visits;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String name, String description) 
    {
        this.description = description;
        this.name = name;
        exits = new HashMap<>();
        visits = 0;
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExits(String direction, Room room)
    {
        exits.put(direction, room);
    }

    public void addVisit()
    {
        visits++;
    }

    public int getVisits()
    {
        return visits;
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @return All exits of the room.
     */
    public String getExitDescription()
    {
        String exitDescription = "Exits:";
        for (String direction : exits.keySet())
        {
            exitDescription += " " + direction;
        }
        return exitDescription;
    }

    public void getTransDescription()
    {
        String name = getName();
        if (name.contains("elevator"))
        {
            //transition message
            if (name == "elevator_lvl0") {
                System.out.println("the elevator is taking you to level 0..");
                Game.wait(2000);
                System.out.println("\n\n");
            }
            if (name == "elevator_airlock") {
                System.out.println("the elevator is taking you to level -1..\nyou should be careful with the airlock");
                Game.wait(2000);
                System.out.println("\n\n");
            }
            if (name == "elevator_lvl1") {
                System.out.println("the elevator is taking you to level 1..");
                Game.wait(2000);
                System.out.println("\n\n");
            }
            if (name == "elevator_lvl2") {
                System.out.println("the elevator is taking you to level 2..");
                Game.wait(2000);
                System.out.println("\n\n");
            }
        }
    }
    
    public String getFullDescription()
    {
        getTransDescription();
        if (debugMode() == true) {
            return "### DEBUG MESSAGE ###\nRoom name: " + getName() + "\nvisits: " + getVisits() + "\n---------------------\n" + getDescription() + "\n" + getExitDescription();
        }
        else {
            return getDescription() + "\n" + getExitDescription();
        }
    }

    public Room getNextRoom(String direction)
    {
        Room nextRoom = null;
        Room currentRoom = this;
        nextRoom = exits.get(direction);
        if (nextRoom == null) {
            return currentRoom;
        }
        else {
            currentRoom = nextRoom;
            return nextRoom;
        }
    }

    public String getName() 
    {
        return name;
    }

    public boolean debugMode()
    {
        return true;
    } 
}
