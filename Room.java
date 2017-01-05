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
    private Room northExit;
    private Room southExit;
    private Room eastExit;
    private Room westExit;
    private HashMap<String,Room> exits;

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
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    //     public void setExits(Room north, Room east, Room south, Room west) 
    //     {
    //         if(north != null)
    //             northExit = north;
    //         if(east != null)
    //             eastExit = east;
    //         if(south != null)
    //             southExit = south;
    //         if(west != null)
    //             westExit = west;
    //     }
    public void setExits(String direction, Room room)
    {
        exits.put(direction, room);
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

    public String getFullDescription()
    {
        if (debugMode() == true) {
            return "### DEBUG MESSAGE ###\nRoom name: " + getName() + "\n---------------------\n" + getDescription() + "\n" + getExitDescription();
        }
        else {
            return getDescription() + "\n" + getExitDescription();
        }
    }

    public Room getNextRoom(String direction)
    {
        Room nextRoom = null;
        Room currentRoom = this;
        //         if(direction.equals("north")) {
        //             nextRoom = exits.get("north");
        //         }
        //         if(direction.equals("east")) {
        //             nextRoom = exits.get("east");
        //         }
        //         if(direction.equals("south")) {
        //             nextRoom = exits.get("south");
        //         }
        //         if(direction.equals("west")) {
        //             nextRoom = exits.get("west");
        //         }
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
