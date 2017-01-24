import java.util.ArrayDeque;
import java.util.Deque;

/**
 * GameState - used to store Game status information
 * 
 * @author Salomon Popp & Tony Dorfmeister
 * @version 2017-01-18
 */

public class GameState
{
    Room currentRoom;
    Deque<Room> lastRooms = new ArrayDeque<Room>();
    String output;
}
