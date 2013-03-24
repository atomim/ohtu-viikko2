/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.List;
import ohtuesimerkki.Player;
import ohtuesimerkki.Reader;
import ohtuesimerkki.Statistics;
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
public class StatisticsTest {
 
    Statistics stats;
    Reader readerStub = new Reader() {
 
        @Override
        public List<Player> getPlayers() {
            ArrayList<Player> players = new ArrayList<Player>();
 
            players.add(new Player("Semenko", "EDM", 4, 12));
            players.add(new Player("Lemieux", "PIT", 45, 54));
            players.add(new Player("Kurri",   "EDM", 37, 53));
            players.add(new Player("Yzerman", "DET", 42, 56));
            players.add(new Player("Gretzky", "EDM", 35, 89));
 
            return players;
        }
    };
    
    @Before
    public void setUp() {
        stats=new Statistics(readerStub);
    }
    
    @Test
    public void searchPelaajaLöytyy(){
        
        assertFalse(stats.search("Yzerman")==null);
        assertTrue(stats.search("Semenko") instanceof Player);
        assertTrue(stats.search("Yzerman").getName().contains("Yzerman"));
        assertTrue(stats.search("Kurri") != null);
        
    }
    
    @Test
    public void searchPelaajaEiLöydy(){
         assertTrue(stats.search("MacGyver")==null);
         assertTrue(stats.search("Koivu") == null);
    }
    
    @Test
    public void teamPalauttaaOikeanMäärän(){
        assertEquals(stats.team("DET").size(),1); 
        assertEquals(stats.team("EDM").size(),3);  
    }
    @Test
    public void teamPalauttaaPelaajia(){
        assertFalse(stats.team("EDM").get(0).getName()==null);  
    }
    
    @Test
    public void topScoresHasRightNumberOfPlayers(){
        assertEquals(1,stats.topScorers(1).size());
    }
    @Test
    public void topScoresIsNotEmpty(){
        assertTrue(stats.topScorers(2).size()>0);

    }
    
    
}