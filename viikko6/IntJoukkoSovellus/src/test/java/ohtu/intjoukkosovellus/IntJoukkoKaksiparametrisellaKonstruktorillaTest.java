
package ohtu.intjoukkosovellus;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IntJoukkoKaksiparametrisellaKonstruktorillaTest extends IntJoukkoTest {
    
    @Before
    public void setUp() {
        joukko = new IntJoukko(4, 2);
        joukko.lisaa(10);
        joukko.lisaa(3);
    }

    @Test(expected=IllegalArgumentException.class)
    public void liianPieniKapasiteettiEitoimi(){

    	joukko = new IntJoukko(-1);
    }
    @Test(expected=IllegalArgumentException.class)
    public void liianPieniKasvatusEiToimi(){

    	joukko = new IntJoukko(5,0);
    }

    @Test
    public void koonRajatapaus(){
        joukko = new IntJoukko(0,1);
        joukko.lisaa(1);
    }
}
