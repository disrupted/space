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
import java.util.Map;

public class Room 
{
    private String name, description, transDescription;
    private int securityLvl;
    private HashMap<String,Room> exits;
    private HashMap<String,Item> itemMap;
    private int visits;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String name, String description, Integer securityLvl) 
    {
        this.description = description;
        this.name = name;
        this.securityLvl = securityLvl;
        exits = new HashMap<>();
        visits = 0;
        itemMap = new HashMap<>();
    }

    /**
     * Define the exits of this room.
     */
    public void setExits(String direction, Room room)
    {
        exits.put(direction, room);
    }

    public void setSecurityLvl(int newSecurityLvl)
    {
        securityLvl = newSecurityLvl;
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
        String exitDescription = "Exits: ";
        for (String direction : exits.keySet())
        {
            int nextRoomSecurityLvl = getNextRoom(direction).getSecurityLvl();
            exitDescription += direction + " ";
            if (Game.debugMode())
                exitDescription += "(" + nextRoomSecurityLvl + ")\n       ";      
        }
        if (Game.debugMode() && exitDescription.length() > 8) { exitDescription = exitDescription.substring(0, exitDescription.length() - 8); }
        return exitDescription;
    }

    public void getTransDescription(String direction)
    {
        String currentRoomName = getName();
        String nextRoomName = getNextRoom(direction).getName();
        if (currentRoomName.contains("elevator") && nextRoomName.contains("elevator"))
        {
            //transition message
            if (nextRoomName == "elevator_lvl0") {
                System.out.println("the elevator is taking you to level 0..");
                if (!Game.debugMode()) { Game.wait(2000); }
                System.out.println("\n");
            }
            if (nextRoomName == "elevator_airlock") {
                System.out.println("the elevator is taking you to level -1..\nyou should be careful with the airlock");
                if (!Game.debugMode()) { Game.wait(2000); }
                System.out.println("\n");
            }
            if (nextRoomName == "elevator_lvl1") {
                System.out.println("the elevator is taking you to level 1..");
                if (!Game.debugMode()) { Game.wait(2000); }
                System.out.println("\n");
            }
            if (nextRoomName == "elevator_lvl2") {
                System.out.println("the elevator is taking you to level 2..");
                if (!Game.debugMode()) { Game.wait(2000); }
                System.out.println("\n");
            }
        }
    }

    public String getItemDescription()
    {
        String items = showItems();
        if (items != null)
            return "Oh look, I found an item in this room: " + items;
        else
            return null;
    }

    public String getFullDescription()
    {
        String result = "";
        if (Game.debugMode()) {
            result = "### DEBUG MESSAGE ###\nRoom name: " + getName() + "\nvisits: " + getVisits() + "\nitems: " + showItems() + "\n---------------------\n" + getDescription() + "\n" + getExitDescription();
        }
        else {
            result = getDescription() + "\n" + getExitDescription();
            if (getItemDescription() != null)
                result += "\n\n" + getItemDescription();
        }
        return result;
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
            return nextRoom;
        }
    }

    public String getName() 
    {
        return name;
    }

    public void placeItem(String itemName, Item item)
    {
        itemMap.put(itemName, item);
    }

    public Item getItem(String itemName)
    {
        String result = "";
        Item item = null;
        if (showItems() != null && showItems().contains(itemName))
        {
            item = itemMap.get(itemName);
        }
        return item;
    }

    public void removeItem(String itemName)
    {
        itemMap.remove(itemName);
    }

    public String showItems()
    {
        String itemDescription = "";
        for(Map.Entry<String, Item> entry : itemMap.entrySet()) {
            String name = entry.getKey();
            Item item = entry.getValue();
            itemDescription += "\n " + item.getFullDescription();
        }
        if (itemDescription != "") {
            return itemDescription;
        } else {
            return null;
        }
    }

    public Integer getSecurityLvl()
    {
        return securityLvl;
    }

    public Integer getMaxSecurityLvlOfExits()
    {
        int maxSecurityLvl = 0;
        for (String direction : exits.keySet())
        {
            int nextRoomSecurityLvl = getNextRoom(direction).getSecurityLvl();
            if (nextRoomSecurityLvl > maxSecurityLvl)
            {
                maxSecurityLvl = nextRoomSecurityLvl;
            }
        }
        return maxSecurityLvl;
    }

    public String getExitMatchingSecurityLvl(int securityLvl)
    {
        // finds the nextRoom with matching securityLvl and returns its direction
        for (String direction : exits.keySet())
        {
            if (getNextRoom(direction).getSecurityLvl() == securityLvl)
            {
                return direction;
            }
        }
        return null;
    }
}
