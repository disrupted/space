/**
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 * <p>
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */

public enum CommandWord {
    GO("go"),

    LOOK("look"),
    
    USE("use"),

    TAKE("take"),
    
    INVENTORY("inventory"),

    QUIT("quit"), 

    HELP("help"), 

    UNKNOWN("unknown");
    private String word;

    /**
     * Constructor - initialise the command words.
     */
    private CommandWord(String word) {
        this.word = word;
    }

    // a constant array that holds all valid command words
    private static final String[] validCommands = {
            "go", "look", "use", "take", "inventory", "quit", "help", "unknown"
        };

    public static String getCommandWords()
    {
        String output = "";
        for (String command : validCommands)
        {
            if (command != "unknown")
            {
                output += command + " ";
            }
        }
        return output;
    }

    public String getWord() {
        return word;
    }

    public static CommandWord getForWord(String word){

        for (CommandWord cw : values()) {
            if (cw.getWord().equals(word))
                return cw;
        }
        return UNKNOWN;

    }

    /**
     * Check whether a given String is a valid command word.
     *
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public static boolean isCommand(String aString) {

        for (CommandWord cw : values()) {
            if (cw.getWord().equals(aString))
                return true;
        }

        // if we get here, the string was not found in the commands
        return false;
    }

}
