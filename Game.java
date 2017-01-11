/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */
import java.util.HashMap;
import java.util.Map;

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private boolean finished;
    private Room start, commandcenter;
    private Room corridor0_1, corridor0_2, corridor0_3, corridor1_1, corridor1_2, corridor1_3, corridor1_4, corridor2_1, corridor2_2, corridor2_3, corridor2_4, airlock, elevator_airlock, elevator_lvl0, elevator_lvl1, elevator_lvl2, ventilationshaft_0to1;
    private static boolean DEBUG = false;
    private Item keycardLvl1, keycardLvl2, coin, picture, backpack;
    private HashMap<String,Item> inventory;
    private int inventoryLimit = 1;
    private int securityLvl = 0;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        createItems();
        inventory = new HashMap<>();
        placeItems();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        // create the rooms
        start = new Room("start","Cryosleep Room", 0);
        commandcenter = new Room ("commandcenter","in the Command Center", 2);
        corridor0_1 = new Room("corridor0_1","lavatory", 0);
        corridor0_2 = new Room("corridor0_2","medical facilities", 0);
        corridor0_3 = new Room("corridor0_3", "lunchroom", 0);
        ventilationshaft_0to1 = new Room("ventilationshaft_0to1","pretty dark in here..", 0);
        corridor1_1 = new Room("corridor1_1","Hallway", 0);
        corridor1_2 = new Room("corridor1_2","Computer Core", 0);
        corridor1_3 = new Room("corridor1_3","Terminal I", 0);
        corridor1_4 = new Room("corridor1_4","Engine Room", 1);
        corridor2_1 = new Room("corridor2_1","Great Hall", 0);
        corridor2_2 = new Room("corridor2_2","Terminal II", 0);
        corridor2_3 = new Room("corridor2_3","Situation Room", 1);
        corridor2_4 = new Room("corridor2_4","Cockpit", 0);
        airlock = new Room("airlock","DANGER !", 0);
        elevator_lvl0 = new Room("elevator_lvl0","Elevator: Deck 0 – Central Area\nLevel 1 Security hatches are locked.", 0);
        elevator_lvl1 = new Room("elevator_lvl1","Elevator: Deck 1 – Engineer's Quarters", 0);
        elevator_lvl2 = new Room("elevator_lvl2","Elevator: Deck 2 – Administration", 0);
        elevator_airlock = new Room("elevator_airlock","Elevator: Deck -1 – Cargo Bay", 0);

        // initialise room exits
        start.setExits("north", corridor0_1);
        start.setExits("east", corridor0_3);
        start.setExits("south", commandcenter);
        commandcenter.setExits("north", start);
        corridor0_1.setExits("east", corridor0_2);
        corridor0_1.setExits("south", start);
        corridor0_2.setExits("west", corridor0_1);
        corridor0_2.setExits("south", corridor0_3);
        corridor0_3.setExits("north", corridor0_2);
        corridor0_3.setExits("west", start);
        corridor1_2.setExits("east", elevator_lvl1);
        corridor1_2.setExits("north", corridor1_3);
        corridor1_2.setExits("south", corridor1_4);
        corridor1_3.setExits("south", corridor1_2);
        corridor1_4.setExits("north", corridor1_2);
        corridor2_1.setExits("east", elevator_lvl2);
        corridor2_1.setExits("north", corridor2_2);
        corridor2_2.setExits("south", corridor2_1);
        corridor2_2.setExits("west", corridor2_3);
        corridor2_3.setExits("east", corridor2_2);
        corridor2_3.setExits("west", corridor2_4);
        corridor2_4.setExits("east", corridor2_3);
        elevator_lvl1.setExits("west", corridor1_2);
        elevator_lvl1.setExits("-1", elevator_airlock);
        elevator_lvl1.setExits("0", elevator_lvl0);
        elevator_lvl1.setExits("2", elevator_lvl2);
        elevator_lvl2.setExits("west", corridor2_1);
        elevator_lvl2.setExits("-1", elevator_airlock);
        elevator_lvl2.setExits("0", elevator_lvl0);        
        elevator_lvl2.setExits("1", elevator_lvl1);
        elevator_lvl0.setExits("-1", elevator_airlock);
        elevator_lvl0.setExits("1", elevator_lvl1);
        elevator_lvl0.setExits("2", elevator_lvl2);
        elevator_airlock.setExits("0", elevator_lvl0);
        elevator_airlock.setExits("1", elevator_lvl1);
        elevator_airlock.setExits("2", elevator_lvl2);
        elevator_airlock.setExits("east", airlock);

        currentRoom = start;  // starting point
        currentRoom.addVisit();
    }

    private void createItems()
    {
        picture = new Item("picture", "looks like it was taken with an instant camera", "\n________________1¶¶¶¶¶¶¶¶¶¶¶1________________\n _____________¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶1____________\n __________¶¶¶¶118¶¶8¶¶¶¶¶¶¶¶¶¶¶¶¶¶___________\n _______8¶¶¶¶888¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶8________\n ______¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶______\n ____8¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶____\n ___¶¶¶¶¶¶¶¶¶¶¶8¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶8¶¶¶___\n __8¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶8¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶8¶¶¶¶__\n __¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶88881__¶¶¶¶¶¶¶__\n _¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶81___________¶¶¶¶¶¶__\n 1¶¶¶¶¶111____________________________8¶¶¶¶¶¶_\n ¶¶¶¶¶1___________________________1___1¶¶¶¶¶¶_\n ¶¶¶¶¶8111___________________________11¶¶¶¶¶¶_\n 1¶¶¶¶88111__________________________111¶¶¶¶¶_\n _¶¶¶¶1881________________________11111_¶¶¶¶¶_\n _¶¶¶¶18811_____________________¶¶¶¶¶¶8_1¶¶¶¶1\n _¶¶¶¶118¶¶¶¶81______________8¶¶¶¶¶¶¶¶¶_1¶¶¶8¶\n _8¶¶881¶¶¶¶¶¶¶¶¶1_________1¶¶¶¶811__1¶811¶¶1¶\n ¶¶¶¶118¶1__18¶¶¶¶¶8118818¶¶¶88¶111¶888¶11¶¶18\n ¶8¶¶11¶¶11¶¶111¶¶¶¶¶1___1¶¶¶¶1_1¶__¶¶8888¶¶8_\n _1¶¶11¶¶¶¶¶_8¶8_8¶8¶¶____8188__¶¶__¶1__18881_\n __8¶88111¶¶_8¶8__1__11___1___111_181___18811_\n __11881___181111____11_________________1881__\n __118¶81_____________8_________________1881__\n ___18¶¶1__________1111_____1_11______1_188___\n ___88¶¶8________88____________8¶81____188____\n ______1¶1_____8¶888_11____18¶¶118¶8888888____\n _______¶¶8881¶¶818¶¶¶¶¶8_1¶¶¶8118¶¶¶¶8888____\n _______¶¶¶¶¶¶¶¶881188¶¶¶¶¶¶818¶¶¶¶¶__1888____\n _______¶888¶18¶¶¶¶¶8888¶¶¶8¶¶¶¶8_11__88¶_____\n ______1¶¶8181_118¶¶¶¶¶¶¶¶¶¶¶¶8111___8¶¶______\n ¶¶¶¶¶¶¶¶¶¶88¶8_1_18¶¶¶¶¶¶¶8881_____1¶8_______\n 88888¶¶¶¶¶¶¶¶¶8_11118881111_______8¶¶________\n 88118¶__¶¶8¶8888_1_____________18¶¶_¶¶_______\n 88888¶___¶¶8818¶¶11_1_______11¶¶¶8__¶¶¶______\n ¶81¶¶¶____1¶¶¶88¶¶¶88888188¶¶¶¶81__¶¶¶¶¶_____\n 88¶¶¶8______¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶811__1¶¶¶¶¶¶1____\n 8¶¶8_________11_1¶¶¶¶¶¶¶888811__¶¶¶¶8¶¶¶¶1___\n ¶¶8_______¶¶¶¶¶_____11118881__1¶¶¶¶¶¶¶¶¶¶¶___\n ¶1_______¶¶¶¶¶¶______________¶¶¶¶¶¶¶¶¶8¶¶¶8__\n ¶_______¶¶¶¶¶¶¶____________1¶¶¶¶¶¶¶8¶¶¶88¶¶8_\n _______1¶¶¶¶8_____________¶¶¶¶¶8¶¶¶888888¶¶¶8\n _______¶¶¶¶_____________1¶¶¶¶¶88¶¶¶¶¶88¶¶¶88¶\n ______¶¶¶¶¶____________¶¶¶¶8888¶8¶88881¶8¶888\n _____¶¶¶¶¶8__________8¶¶88¶18818¶88118¶88888¶\n");
        keycardLvl1 = new Item("keycardLvl1", "you can unlock security level 1 hatches with it", null);
        keycardLvl2 = new Item("keycardLvl2", "you can unlock security level 2 hatches with it", null);
        coin = new Item("coin", "no idea which currency that is", null);
        backpack = new Item("backpack", "this bag can hold up to 10 items", "increased inventory limit");
    }

    private void placeItems()
    {
        corridor1_3.placeItem("keycardLvl1", keycardLvl1);
        start.placeItem("coin", coin);
        corridor0_1.placeItem("picture", picture);
        corridor2_4.placeItem("keycardLvl2", keycardLvl2);
        corridor1_4.placeItem("backpack", backpack);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        finished = false;
        boolean ventOpen = false;
        while (! finished) {
            if (!ventOpen) {
                if ((corridor0_1.getVisits() > 0) && (corridor0_2.getVisits() > 0) && (corridor0_3.getVisits() > 0)) 
                {                    
                    corridor1_1.setExits("east", corridor1_2);
                    corridor1_2.setExits("west", corridor1_1);
                    corridor1_1.setExits("down", ventilationshaft_0to1);
                    ventilationshaft_0to1.setExits("down", start);
                    ventilationshaft_0to1.setExits("up", corridor1_1);
                    start.setExits("up", ventilationshaft_0to1);
                    ventOpen = true;
                    System.out.println("\nI haven't noticed this ventilation shaft at the top before.\nJust wondering where it's leading...  I could perhaps try to find an entrance\nand climb inside because it looks out of function anyways.");
                    Game.wait(1500);
                }
            }
            if (currentRoom.getName().contains("corridor0") && (corridor0_1.getVisits() > 1) == (corridor0_2.getVisits() > 1) == (corridor0_3.getVisits() > 1))
            {
                System.out.println("\nthink I might be going in circles...");
            }
            Command command = parser.getCommand();
            String output = processCommand(command);
            finished = (null == output);
            if (!finished)
            { 
                System.out.println(output);
            }
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println("\nWelcome to <Game title>!");
        System.out.println("Hey you, I am so glad that I found this communicator here to talk to someone.\nI really hope you can help me out because I just woke up in this strange room\nand I have no idea what's going on here but this environment looks kinda spacey.");
        System.out.println("\nCan you give me some tips what to do by using these commands? \n " + printHelp());
        System.out.println("\n" + currentRoom.getFullDescription());
    }

    /**
     * This is a further method added by BK to 
     * provide a clearer interface that can be tested:
     * Game processes a commandLine and returns output.
     * @param commandLine - the line entered as String
     * @return output of the command
     */
    public String processCommand(String commandLine){
        Command command = parser.getCommand(commandLine);
        return processCommand(command);
    } 

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    public String processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();
        switch(commandWord) {
            case HELP: return printHelp();
            case GO: return goRoom(command);
            case QUIT: return quit(command);
            case TAKE: return take(command);
            case LOOK: return look(command);
            case INVENTORY: return showInventory(command);
            case USE: return use(command);
            case DROPITEMS: return dropItems(command);
            case UNKNOWN: return "I don't know what you mean. Please use these words...\n" + printHelp();
        }

        return null;
    }

    // implementations of user commands:
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private String printHelp() 
    {   
        String result = "You are lost. You are alone. You wander\naround at the university.\n\nYour command words are:\n   " + CommandWord.getCommandWords();
        return result;
    }

    /** 
     * Try to go to one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private String goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            return "Go where?";
        }

        String direction = command.getSecondWord();

        String result = "";
        if (currentRoom.getNextRoom(direction).getSecurityLvl() > 0) {
            result += "This exit seems to be locked by a security level " + currentRoom.getNextRoom(direction).getSecurityLvl() + " hatch";
            return result;
        }
        else
        {
            if (currentRoom == currentRoom.getNextRoom(direction)){
                result += "There is no door";
                return result;
            }
            else {
                currentRoom.getTransDescription(direction);
                currentRoom = currentRoom.getNextRoom(direction);
                if (currentRoom.getName() == "airlock") { 
                    gameOver();
                    return null;
                }
                else {
                    currentRoom.addVisit();
                    return currentRoom.getFullDescription();
                }
            }
        }
    }

    private String take(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            return "Take what?";
        }
        String result = "";
        String itemName = command.getSecondWord();
        Item item = currentRoom.getItem(itemName);
        if (item != null) 
        {
            if (inventory.size() < inventoryLimit || itemName.equals("backpack")) {
                result = itemName + " was added to your inventory - " + item.getDescription();
                if (item.getEvent() != null) { result += "\n--> " + item.getEvent(); }
                currentRoom.removeItem(itemName);
                inventory.put(itemName,item);
                if (itemName.equals("backpack")) { inventoryLimit = 10; };
                if (debugMode()) { result += "\n\n### DEBUG MESSAGE ###\ninventory size: " + inventory.size() + " / " + inventoryLimit + "\nitems remaining in room: " + currentRoom.showItems() + "\n---------------------"; }
            } else {
                result += "I'll need some sort of bag to carry more than 1 item";
            }
        }
        else
        {
            result = "there's no such item " + itemName;
        }  
        return result;
    }

    private String look(Command command)
    {
        return currentRoom.getFullDescription();
    }

    private String showInventory(Command command)
    {
        String inventoryList = "";
        for(Map.Entry<String, Item> entry : inventory.entrySet()) {
            String name = entry.getKey();
            Item item = entry.getValue();
            inventoryList += "\n " + item.getFullDescription();
        }
        if (inventoryList == "") { return "you haven't collected any items in your inventory"; }
        return "your inventory contains " + inventory.size() + "/" + inventoryLimit + " items :" + inventoryList;
    }

    private String dropItems(Command command)
    {
        String result = "";
        for(Map.Entry<String, Item> entry : inventory.entrySet()) {
            String name = entry.getKey();
            Item item = entry.getValue();
            currentRoom.placeItem(name, item);
            result += name + ", ";
        }
        inventory.clear();
        inventoryLimit = 1;
        if (result == "") { return "you haven't collected any items in your inventory"; }
        return "inventory dropped: " + result.substring(0, result.length() - 2);
    }

    private String use(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know which item to use...
            return "Use what?";
        }
        String result = "";
        String itemName = command.getSecondWord();
        boolean itemInInventory = false;
        for(Map.Entry<String, Item> entry : inventory.entrySet()) {
            String name = entry.getKey();
            Item item = entry.getValue();
            if (name.equals(itemName))
            {
                if (name.contains("keycardLvl"))
                {
                    int securityLvl = Integer.parseInt(name.replace("keycardLvl", ""));
                    String direction = currentRoom.getExitMatchingSecurityLvl(securityLvl);
                    if (direction != null) {
                        currentRoom.getNextRoom(direction).setSecurityLvl(0);
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

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return null, if this command quits the game, something else to output otherwise.
     */
    private String quit(Command command) 
    {
        if(command.hasSecondWord()) {
            return "Quit what?";
        }
        else {
            return null;  // signal that we want to quit
        }
    }

    public static void main(String[] args){
        Game game = new Game();
        game.play();
    }

    public static void wait(int ms)
    {
        try {
            Thread.sleep(ms);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void gameOver()
    {
        for (int i = 0; i<= 40; i++) {
            wait(50);
            System.out.println();
        }
        System.out.println(" dP\"\"b8    db    8b    d8 888888      dP\"Yb  Yb    dP 888888 88\"\"Yb ");
        wait(100);
        System.out.println("dP   `\"   dPYb   88b  d88 88__       dP   Yb  Yb  dP  88__   88__dP ");
        wait(100);
        System.out.println("Yb  \"88  dP__Yb  88YbdP88 88\"\"       Yb   dP   YbdP   88\"\"   88\"Yb  ");
        wait(100);
        System.out.println(" YboodP dP\"\"\"\"Yb 88 YY 88 888888      YbodP     YP    888888 88  Yb ");
        wait(100);
        for (int i = 0; i<= 20; i++) {
            wait(100);
            System.out.println();
        }
        wait(3000);
        for (int i = 0; i<= 40; i++) {
            wait(20);
            System.out.println();
        }
    }

    public static boolean debugMode()
    {
        return DEBUG;
    }
    
    public static void setDebugMode(boolean debugMode)
    {
        DEBUG = debugMode;
    }
}
