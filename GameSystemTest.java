
import static org.hamcrest.Matcher.*;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This is a Game System Tests - it tests the
 * whole game and the endpoints between user input
 * and output to the console.
 *
 * @author  Barne Kleinen
 */
public class GameSystemTest
{
    private Game game;

    public GameSystemTest()
    {

    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        game = new Game();
    }

    @Test
    public void testQuit()
    {
        //given: new game
        //when
        String output = game.processCommand("quit");
        //then
        assertEquals(
            "null is the output that signals that the main loop should stop",
            null,output);
    }

    @Test
    public void testHelp()
    {
        //given: new game

        //when
        String output = game.processCommand("help");
        //then
        assertTrue("should print help message containing command words", output.contains("command words"));
        assertTrue("message contains command word go", output.contains("go"));
        assertTrue("message contains command word quit", output.contains("quit"));
        assertTrue("message contains command word help", output.contains("help"));
        assertTrue("message contains command word help", output.contains("take"));
        assertTrue("message contains command word help", output.contains("look"));
        assertTrue("message contains command word help", output.contains("use"));
    }

    @Test
    public void testUnknownCommand(){
        // given arbitrary game
        // when entering unknown command
        String output = game.processCommand("asdf");
        // then an error message should be returned
        assertTrue("should output error message", output.contains("I don't know what you mean"));
    }

    @Test
    public void testGoSouth()
    {
        //given: new game
        //when
        String output = game.processCommand("go south");
        //then
        assertTrue("door should be locked", output.contains("locked"));
    }

    @Test
    public void testGoNorth()
    {
        //given: new game
        //when
        String output = game.processCommand("go north");
        //then
        assertEquals(true, output.contains("lecture theatre"));
    }

    @Test
    public void testGoWest()
    {
        //given: new game
        //when
        String output = game.processCommand("go west");
        //then
        assertEquals(true, output.contains("no door"));
    }

    /**
     * version 1: manual test case
     */
    @Test
    public void testGoWithoutDirection()
    {
        //given: new game
        //when
        String output = game.processCommand("go");
        //then
        assertEquals(true, output.contains("Go where"));
    }

    /**
     * version 2: recorded test case
     */
    @Test
    public void goWODirectionShouldShowError()
    {
        assertEquals("Go where?", game.processCommand("go"));
    }

    @Test
    public void testInventory()
    {   
        //when
        String output = game.processCommand("inventory");
        //then
        assertEquals(true, output.contains("you haven't collected any items in your inventory"));
    }

//     @Test
//     public void completeWalkthrough()
//     {
//         goAndSee("east",  "lecture theatre");
//         goAndSee("west",  "main entrance");
//         goAndSee("west",  "campus pub");
//         goAndSee("east",  "main entrance");
//         goAndSee("south", "computing lab");
//         goAndSee("east",  "admin office");
//         goAndSee("west",  "computing lab");
//         goAndSee("north", "main entrance");
//     }

    private void goAndSee(String direction, String whatShouldBeContained){
        //when
        String result = game.processCommand("go "+direction);
        //then
        if (!result.contains(whatShouldBeContained))
            fail(result +" does not contain "+whatShouldBeContained);
    }

    @Test
    public void showExits(){
        game.processCommand("go north");
        String result = game.processCommand("go south");
        assertTrue(result.contains("north"));
        assertTrue(result.contains("south"));
        assertTrue(result.contains("east"));
    }
}
