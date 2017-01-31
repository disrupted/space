/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * This class holds information about a command that was issued by the user.
 * A command currently consists of two strings: a command word and a second
 * word (for example, if the command was "take map", then the two strings
 * obviously are "take" and "map").
 * 
 * The way this is used is: Commands are already checked for being valid
 * command words. If the user entered an invalid command (a word that is not
 * known) then the command word is <null>.
 *
 * If the command had only one word, then the second word is <null>.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */

public class Command
{
    private CommandWord commandWord;
    private String secondWord;
    private static Inventory inventory;

    /**
     * Create a command object. First and second word must be supplied, but
     * either one (or both) can be null.
     * @param firstWord The first word of the command. Null if the command
     *                  was not recognised.
     * @param secondWord The second word of the command.
     */
    public Command(String firstWord, String secondWord)
    {
        commandWord = CommandWord.getForWord(firstWord);
        this.secondWord = secondWord;
        inventory = Game.getInventory();
    }

    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     * @return The command word.
     */
    public CommandWord getCommandWord()
    {
        return commandWord;
    }

    /**
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    public String getSecondWord()
    {
        return secondWord;
    }

    /**
     * @return true if this command was not understood.
     */
    public boolean isUnknown()
    {
        return (commandWord == null);
    }

    /**
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    public static String processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();
        switch (commandWord) {
            case HELP: return "use these command words:\n   " + printHelp();
            case GO: return goRoom(command);
            case QUIT: return quit(command);
            case TAKE: return take(command);
            case LOOK: return look(command);
            case INVENTORY: return showInventory(command);
            case USE: return use(command);
            case DROP: return drop(command);
            case BACK: return back(command);
            case UNKNOWN: return "I don't know what you mean.\nuse these command words to give me advice..\n   " + printHelp();
        }

        return null;
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    public static String printHelp() 
    {   
        return CommandWord.getCommandWords();
    }

    /**
     * Print out the opening message for the player.
     */
    public static void printWelcome()
    {
        System.out.println("\nWelcome to <Game title>!");
        System.out.println("Hey you, I am so glad that I found this communicator here to talk to someone.\nI really hope you can help me out because I just woke up in this strange room\nand I have no idea what's going on here but this environment looks kinda spacey.");
        System.out.println("\nCan you give me some tips what to do by using these commands? \n " + printHelp());
        System.out.println("\n" + Game.state.currentRoom.getFullDescription());
    }

    /** 
     * Try to go to one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    public static String goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            return "Go where?";
        }

        String direction = command.getSecondWord();

        String result = "";
        Room nextRoom = Game.state.currentRoom.getNextRoom(direction);
        if (nextRoom == null){
            result += "There is no door";
            return result;
        }
        if (nextRoom.getSecurityLvl() > 0) {
            result += "This exit seems to be locked by a security level " + Game.state.currentRoom.getNextRoom(direction).getSecurityLvl() + " hatch";
            return result;
        }
        else
        {
            Game.state.currentRoom.getTransDescription(direction);
            Game.state.lastRooms.push(Game.state.currentRoom);
            Game.state.currentRoom = nextRoom;
            if (Game.state.currentRoom.getName() == "airlock") { 
                Game.gameOver();
                return null;
            }
            else {
                Game.state.currentRoom.addVisit();
                return Game.state.currentRoom.getFullDescription();
            }
        }
    }

    public static String take(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            return "Take what?";
        }
        String result = "";
        String itemName = command.getSecondWord();
        Item item = Game.state.currentRoom.getItem(itemName);
        if (item != null) 
        {
            if (inventory.getCurrentWeight() + item.getWeight() <= inventory.getWeightLimit() || itemName.equals("backpack")) {
                result = itemName + " was added to your inventory - " + item.getDescription();
                if (item.getEvent() != null) { result += "\n--> " + item.getEvent(); }
                Game.state.currentRoom.removeItem(itemName);
                inventory.addItem(itemName,item);
                if (itemName.equals("backpack")) { inventory.setWeightLimit(5000); };
                if (Game.debugMode()) { result += "\n\n### DEBUG MESSAGE ###\ninventory size: " + inventory.getSize() + " / " + inventory.getWeightLimit() + "\nitems remaining in room: " + Game.state.currentRoom.showItems() + "\n---------------------"; }
            } else {
                result += "This item is too heavy. I can't carry it right now.";
            }
        }
        else
        {
            result = "there's no such item " + itemName;
        }  
        return result;
    }

    public static String look(Command command)
    {
        return Game.state.currentRoom.getFullDescription();
    }

    public static String showInventory(Command command)
    {
        return inventory.getFullDescription();
    }

    public static String drop(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know which item to drop...
            return "Drop what?";
        }
        String result = "";
        String itemName = command.getSecondWord();
        if (itemName.equals("all"))
        {
            //todo: drop all
        }
        else
        {
            //todo: drop backpack (decreases inventory limit again and) drops all items
            Item item = inventory.getItem(itemName);
            inventory.removeItem(itemName);
            Game.state.currentRoom.placeItem(itemName, item);
            result = itemName + " was removed from inventory";
            if (itemName.equals("backpack")) { inventory.setWeightLimit(1); };
            if (Game.debugMode()) { result += "\n\n### DEBUG MESSAGE ###\ninventory size: " + inventory.getSize() + " / " + inventory.getWeightLimit() + "\nitems remaining in room: " + Game.state.currentRoom.showItems() + "\n---------------------"; }
        }       
        if (result == "") {
            result = "inventory doesn't contain " + itemName; 
        }
        return result;
    }

    public static String use(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know which item to use...
            return "Use what?";
        }
        String result = "";
        String itemName = command.getSecondWord();

        Item item = inventory.getItem(itemName);
        if (item != null) {
            String name = item.getName();
            if (name.equals(itemName))
            {
                if (name.contains("keycardLvl"))
                {
                    int securityLvl = Integer.parseInt(name.replace("keycardLvl", ""));
                    String direction = Game.state.currentRoom.getExitMatchingSecurityLvl(securityLvl);
                    if (direction != null) {
                        Game.state.currentRoom.getNextRoom(direction).setSecurityLvl(0);
                        return "yes, that's it! this keycard unlocks the security hatch here.";
                    }
                    else {
                        result += "there's no security level " + securityLvl + " hatch here.";
                    }
                }
                else
                {
                    result += "I have no idea what to do with this, maybe I can use it somewhere else...";
                }
                return result;
            }
        }
        return itemName + " wasn't found in your inventory"; 
    }

    public static String back(Command command)
    {
        String result = "";
        if (!Game.state.lastRooms.isEmpty()) {
            result += "Going back to ";
            if (Game.debugMode()) { result += Game.state.lastRooms.peek().getName() + ": "; }
            result += Game.state.lastRooms.peek().getDescription() + ".";
            Game.state.currentRoom = Game.state.lastRooms.pop();
        } else {
            result += "I don't remember where I was before...";
        }
        return result;
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return null, if this command quits the game, something else to output otherwise.
     */
    public static String quit(Command command) 
    {
        if(command.hasSecondWord()) {
            return "Quit what?";
        }
        else {
            return null;  // signal that we want to quit
        }
    }
}

