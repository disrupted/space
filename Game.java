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

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private boolean finished;
    private Room start;
    private Room ventilationshaft_0to1;
    private Room corridor1_1;
    private Room corridor1_2;
    public boolean DEBUG = true;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        // create the rooms
        start = new Room("start","in the Welcome Room");
        Room corridor01 = new Room("corridor01","in a lecture theatre");
        Room corridor02 = new Room("corridor02","in the campus pub");
        Room corridor03 = new Room("corridor03","in a computing lab");
        //Room ventilationshaft_0to1 = new Room("ventilationshaft_0to1","\nthere is a golden magic coffee machine.");
        //Room corridor1_1 = new Room("corridor1_1","in a generic hallway");
        corridor1_2 = new Room("corridor1_2","in a generic hallway");
        Room corridor1_3 = new Room("corridor1_3","in a generic hallway");
        Room corridor1_4 = new Room("corridor1_4","in a generic hallway");
        Room corridor2_1 = new Room("corridor2_1","in a generic hallway");
        Room corridor2_2 = new Room("corridor2_1","in a generic hallway");
        Room corridor2_3 = new Room("corridor2_1","in a generic hallway");
        Room corridor2_4 = new Room("corridor2_1","in a generic hallway");
        Room airlock = new Room("airlock","DANGER !");
        Room elevator_lvl0 = new Room("elevator_lvl0","in the elevator at level 0.");
        Room elevator_lvl1 = new Room("elevator_lvl1","in the elevator at level 1.");
        Room elevator_lvl2 = new Room("elevator_lvl2","in the elevator at level 2.");
        Room elevator_airlock = new Room("elevator_airlock","in the elevator at level -1.");

        // initialise room exits
        start.setExits("north", corridor01);
        start.setExits("east", corridor03);
        corridor01.setExits("east", corridor02);
        corridor01.setExits("south", start);
        corridor02.setExits("west", corridor01);
        corridor02.setExits("south", corridor03);
        corridor03.setExits("north", corridor02);
        corridor03.setExits("west", start);
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
        boolean corridor01 = true;
        boolean corridor02 = true;
        boolean corridor03 = true;
        boolean ventOpen = false;
        while (! finished) {
            if (!ventOpen) {
                if (currentRoom.getName() == "corridor01") { corridor01=true; }
                if (currentRoom.getName() == "corridor02") { corridor02=true; }
                if (currentRoom.getName() == "corridor03") { corridor03=true; }
                if (corridor01 && corridor02 && corridor03) 
                {
                    Room ventilationshaft_0to1 = new Room("ventilationshaft_0to1","\nthere is a golden magic coffee machine.");
                    Room corridor1_1 = new Room("corridor1_1","in a generic hallway");
                    corridor1_1.setExits("east", corridor1_2);
                    corridor1_2.setExits("west", corridor1_1);
                    corridor1_1.setExits("down", ventilationshaft_0to1);
                    ventilationshaft_0to1.setExits("down", start);
                    ventilationshaft_0to1.setExits("up", corridor1_1);
                    start.setExits("up", ventilationshaft_0to1);
                    corridor01 = false;
                    ventOpen = true;
                    System.out.println("\nHave you noticed the broken ventilation shaft in the other room?");
                }
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
        System.out.println("\nWelcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println("\n" + currentRoom.getFullDescription());
        //System.out.println("________________1¶¶¶¶¶¶¶¶¶¶¶1________________\n _____________¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶1____________\n __________¶¶¶¶118¶¶8¶¶¶¶¶¶¶¶¶¶¶¶¶¶___________\n _______8¶¶¶¶888¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶8________\n ______¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶______\n ____8¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶____\n ___¶¶¶¶¶¶¶¶¶¶¶8¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶8¶¶¶___\n __8¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶8¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶8¶¶¶¶__\n __¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶88881__¶¶¶¶¶¶¶__\n _¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶81___________¶¶¶¶¶¶__\n 1¶¶¶¶¶111____________________________8¶¶¶¶¶¶_\n ¶¶¶¶¶1___________________________1___1¶¶¶¶¶¶_\n ¶¶¶¶¶8111___________________________11¶¶¶¶¶¶_\n 1¶¶¶¶88111__________________________111¶¶¶¶¶_\n _¶¶¶¶1881________________________11111_¶¶¶¶¶_\n _¶¶¶¶18811_____________________¶¶¶¶¶¶8_1¶¶¶¶1\n _¶¶¶¶118¶¶¶¶81______________8¶¶¶¶¶¶¶¶¶_1¶¶¶8¶\n _8¶¶881¶¶¶¶¶¶¶¶¶1_________1¶¶¶¶811__1¶811¶¶1¶\n ¶¶¶¶118¶1__18¶¶¶¶¶8118818¶¶¶88¶111¶888¶11¶¶18\n ¶8¶¶11¶¶11¶¶111¶¶¶¶¶1___1¶¶¶¶1_1¶__¶¶8888¶¶8_\n _1¶¶11¶¶¶¶¶_8¶8_8¶8¶¶____8188__¶¶__¶1__18881_\n __8¶88111¶¶_8¶8__1__11___1___111_181___18811_\n __11881___181111____11_________________1881__\n __118¶81_____________8_________________1881__\n ___18¶¶1__________1111_____1_11______1_188___\n ___88¶¶8________88____________8¶81____188____\n ______1¶1_____8¶888_11____18¶¶118¶8888888____\n _______¶¶8881¶¶818¶¶¶¶¶8_1¶¶¶8118¶¶¶¶8888____\n _______¶¶¶¶¶¶¶¶881188¶¶¶¶¶¶818¶¶¶¶¶__1888____\n _______¶888¶18¶¶¶¶¶8888¶¶¶8¶¶¶¶8_11__88¶_____\n ______1¶¶8181_118¶¶¶¶¶¶¶¶¶¶¶¶8111___8¶¶______\n ¶¶¶¶¶¶¶¶¶¶88¶8_1_18¶¶¶¶¶¶¶8881_____1¶8_______\n 88888¶¶¶¶¶¶¶¶¶8_11118881111_______8¶¶________\n 88118¶__¶¶8¶8888_1_____________18¶¶_¶¶_______\n 88888¶___¶¶8818¶¶11_1_______11¶¶¶8__¶¶¶______\n ¶81¶¶¶____1¶¶¶88¶¶¶88888188¶¶¶¶81__¶¶¶¶¶_____\n 88¶¶¶8______¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶811__1¶¶¶¶¶¶1____\n 8¶¶8_________11_1¶¶¶¶¶¶¶888811__¶¶¶¶8¶¶¶¶1___\n ¶¶8_______¶¶¶¶¶_____11118881__1¶¶¶¶¶¶¶¶¶¶¶___\n ¶1_______¶¶¶¶¶¶______________¶¶¶¶¶¶¶¶¶8¶¶¶8__\n ¶_______¶¶¶¶¶¶¶____________1¶¶¶¶¶¶¶8¶¶¶88¶¶8_\n _______1¶¶¶¶8_____________¶¶¶¶¶8¶¶¶888888¶¶¶8\n _______¶¶¶¶_____________1¶¶¶¶¶88¶¶¶¶¶88¶¶¶88¶\n ______¶¶¶¶¶____________¶¶¶¶8888¶8¶88881¶8¶888\n _____¶¶¶¶¶8__________8¶¶88¶18818¶88118¶88888¶\n");
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

        if(command.isUnknown()) {
            return "I don't know what you mean...";
        }

        String commandWord = command.getCommandWord();
        String result = null;
        if (commandWord.equals("help"))
            result = printHelp();
        else if (commandWord.equals("go"))
            result = goRoom(command);
        else if (commandWord.equals("quit"))
            result = quit(command);

        return result;

    }

    // implementations of user commands:
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private String printHelp() 
    {   String result = "";
        result += "You are lost. You are alone. You wander\n";
        result += "around at the university.\n";
        result += "\n";
        result += "Your command words are:\n";
        result += "   go quit help\n";
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

        if (direction.contains("0")) {
            System.out.println("the elevator is taking you to level 0..");
            try {
                Thread.sleep(2000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            System.out.println("\n\n");
        }
        if (direction .contains("-1")) {
            System.out.println("the elevator is taking you to level -1..\nyou should be careful with the airlock");
            try {
                Thread.sleep(2000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            System.out.println("\n\n");
        } else {
            if (direction.contains("1")) {
                System.out.println("the elevator is taking you to level 1..");
                try {
                    Thread.sleep(2000);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("\n\n");
            }
        }
        if (direction.contains("2")) {
            System.out.println("the elevator is taking you to level 2..");
            try {
                Thread.sleep(2000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            System.out.println("\n\n");
        }

        String result = "";
        if (currentRoom == currentRoom.getNextRoom(direction)){
            result = "There is no door\n";
            return result;
        }
        else {
            currentRoom = currentRoom.getNextRoom(direction);
            if (currentRoom.getName() == "airlock") { 
                gameOver();
                return null;
            }
            else {
                return currentRoom.getFullDescription();
            }
        }
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

    public void wait(int ms)
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
        wait(5000);
        for (int i = 0; i<= 50; i++) {
            wait(50);
            System.out.println();
        }
    }
}
