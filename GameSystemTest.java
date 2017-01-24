
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
        assertEquals(true, output.contains("Lavatory"));
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

    @Test
    public void completeWalkthrough()
    {
        game.setDebugMode(true);
        game.triggerEvent("vent");
        goAndSee("north",  "Lavatory");
        goAndSee("east",  "Medical Facilities");
        goAndSee("south",  "Lunchroom");
        goAndSee("west", "Cryosleep");
        goAndSee("up", "vent");
        goAndSee("up", "Hallway");
        goAndSee("east", "Computer Core");
        goAndSee("south", "locked by a security level 1 hatch");
        goAndSee("north", "Console I");
        take("keycardLvl1");
        go("south");
        use("keycardLvl1", "keycard unlocks the security hatch");
        goAndSee("south", "Engine Room");
        take("backpack");
        go("north");
        goAndSee("east", "Elevator: Deck 1");
        goAndSee("2", "Elevator: Deck 2");
        goAndSee("west",  "Great Hall");
        goAndSee("north", "Console II");
        goAndSee("west", "locked by a security level 1 hatch");
        use("keycardLvl1", "keycard unlocks the security hatch");
        goAndSee("west",  "Situation Room");
        goAndSee("west",  "Cockpit");
        take("keycardLvl2");
        go("east");
        go("east");
        go("south");
        go("east");
        go("1");
        go("west");
        go("west");
        go("down");
        goAndSee("down", "Cryosleep");
        use("keycardLvl2", "keycard unlocks the security hatch");
        goAndSee("south", "Command Center");
        go("north");
        go("up");
        go("up");
        go("east");
        go("east");
        goAndSee("0", "Elevator: Deck 0");
        goAndSee("-1", "Elevator: Deck -1"); 
    }

    private void goAndSee(String direction, String whatShouldBeContained){
        //when
        String result = game.processCommand("go " + direction);
        //then
        if (!result.contains(whatShouldBeContained))
            fail(result + " does not contain " + whatShouldBeContained);
    }

    private void go(String direction)
    {
        game.processCommand("go " + direction);
    }

    private void take(String itemName){
        //when
        String result = game.processCommand("take " + itemName);
        //then
        String whatShouldBeContained = itemName + " was added to your inventory";
        if (!result.contains(whatShouldBeContained))
            fail(result + " does not contain " + whatShouldBeContained);
    }

    private void use(String itemName, String whatShouldBeContained){
        //when
        String result = game.processCommand("use " + itemName);
        //then
        if (!result.contains(whatShouldBeContained))
            fail(result + " does not contain " + whatShouldBeContained);
    }

    @Test
    public void showExits(){
        game.processCommand("go north");
        String result = game.processCommand("go south");
        assertTrue(result.contains("north"));
        assertTrue(result.contains("south"));
        assertTrue(result.contains("east"));
    }
    
    @Test
    public void testBack(){
        game.processCommand("go east");
        String result = game.processCommand("back");
        assertTrue(result.contains("Cryosleep"));
    }

    @Test
    public void testDoubleBack(){
        game.processCommand("go east");
        game.processCommand("go north");
        game.processCommand("back");
        String result = game.processCommand("back");
        assertTrue(result.contains("Cryosleep"));
    }
    
//     @Test
//     public void testTripleBack(){
//         game.processCommand("go east");
//         game.processCommand("go north");
//         game.processCommand("back");
//         game.processCommand("back");
//         String result = game.processCommand("back");
//         System.out.println(result);
//         assertTrue(result.contains("remember"));
//     }
}
