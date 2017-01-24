import java.util.Stack;

/**
 * GameState - used to store Game status information
 * 
 * @author Salomon Popp & Tony Dorfmeister
 * @version 2017-01-18
 */

public class GameState
{
    Room currentRoom;
    Stack<Room> lastRooms = new Stack<Room>();
    String output;
}
