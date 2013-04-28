
package ohtu.intjoukkosovellus;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class JoukkoOperaatiotTest {
    
    
    @Test
    public void testYhdiste() {
        IntJoukko eka = teeJoukko(1,2);
        IntJoukko toka = teeJoukko(3,4);
        
        IntJoukko tulos = IntJoukko.yhdiste(eka, toka);
        int[] vastauksenLuvut = tulos.toIntArray();
        Arrays.sort(vastauksenLuvut);
        
        int[] odotettu = {1,2,3,4};
        assertArrayEquals(odotettu, vastauksenLuvut);

        eka = teeJoukko(5,4);   

        tulos = IntJoukko.yhdiste(eka, toka);
        vastauksenLuvut = tulos.toIntArray();
        Arrays.sort(vastauksenLuvut);
        
        int[] odotettu2 = {3,4,5};
        assertArrayEquals(odotettu2, vastauksenLuvut);
    } 

    @Test
    public void testErotus() {
        IntJoukko eka = teeJoukko(1,2);
        IntJoukko toka = teeJoukko(3,4);
        
        IntJoukko tulos = IntJoukko.erotus(eka, toka);
        int[] vastauksenLuvut = tulos.toIntArray();
        Arrays.sort(vastauksenLuvut);
        
        int[] odotettu = {1,2};
        assertArrayEquals(odotettu, vastauksenLuvut);

        eka = teeJoukko(2,4);   

        tulos = IntJoukko.erotus(eka, toka);
        vastauksenLuvut = tulos.toIntArray();
        Arrays.sort(vastauksenLuvut);
        
        int[] odotettu2 = {2};
        assertArrayEquals(odotettu2, vastauksenLuvut);
    } 
    @Test
    public void testLeikkaus() {
        IntJoukko eka = teeJoukko(1,2);
        IntJoukko toka = teeJoukko(3,4);
        
        IntJoukko tulos = IntJoukko.leikkaus(eka, toka);
        int[] vastauksenLuvut = tulos.toIntArray();
        Arrays.sort(vastauksenLuvut);
        
        int[] odotettu = {};
        assertArrayEquals(odotettu, vastauksenLuvut);

        eka = teeJoukko(2,4);   

        tulos = IntJoukko.leikkaus(eka, toka);
        vastauksenLuvut = tulos.toIntArray();
        Arrays.sort(vastauksenLuvut);
        
        int[] odotettu2 = {4};
        assertArrayEquals(odotettu2, vastauksenLuvut);
    } 


    private IntJoukko teeJoukko(int... luvut) {
        IntJoukko joukko = new IntJoukko();
        
        for (int luku : luvut) {
            joukko.lisaa(luku);
        }
        
        return joukko;
    }
}
