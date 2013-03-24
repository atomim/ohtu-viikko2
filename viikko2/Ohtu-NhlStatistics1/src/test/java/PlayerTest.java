/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import ohtuesimerkki.Player;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author olavi
 */
public class PlayerTest {

    Player player;

    @Before
    public void setUp() {
        player = new Player("Jeesus", "Jumala", 100, 101);
    }

    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //

    @Test
    public void konstruktoriJaPerusgetterit() {
        assertEquals(player.getName(), "Jeesus");
        assertEquals(player.getTeam(), "Jumala");
        assertEquals(player.getGoals(), 100);
        assertEquals(player.getAssists(), 101);
    }

    @Test
    public void getPoints() {
        assertEquals(player.getPoints(), player.getGoals() + player.getAssists());
    }

    @Test
    public void setterit() {
        player.setAssists(10);
        player.setGoals(20);
        player.setName("Isä");
        player.setTeam("Jee");
        assertEquals(player.getName(), "Isä");
        assertEquals(player.getTeam(), "Jee");
        assertEquals(player.getGoals(), 20);
        assertEquals(player.getAssists(), 10);
    }
    @Test
    public void toStringi() {
        String s= player.toString();
        assertTrue(s.contains(player.getName()));
        assertTrue(s.contains(player.getTeam()));
        assertTrue(s.contains(Integer.toString(player.getPoints())));
        assertTrue(s.contains(Integer.toString(player.getGoals())));
        assertTrue(s.contains(Integer.toString(player.getAssists())));
    }
}
