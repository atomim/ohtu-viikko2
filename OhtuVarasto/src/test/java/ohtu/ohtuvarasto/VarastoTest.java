package ohtu.ohtuvarasto;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class VarastoTest {

    Varasto varasto;
    double vertailuTarkkuus = 0.0001;

    @Before
    public void setUp() {
        varasto = new Varasto(10);
    }

    @Test
    public void konstruktoriLuoTyhjanVaraston() {
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void uudellaVarastollaOikeaTilavuus() {
        assertEquals(10, varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaSaldoa() {
        varasto.lisaaVarastoon(8);

        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaPienentaaVapaataTilaa() {
        varasto.lisaaVarastoon(8);

        // vapaata tilaa pitäisi vielä olla tilavuus-lisättävä määrä eli 2
        assertEquals(2, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void ottaminenPalauttaaOikeanMaaran() {
        varasto.lisaaVarastoon(8);

        double saatuMaara = varasto.otaVarastosta(2);

        assertEquals(2, saatuMaara, vertailuTarkkuus);
    }

    @Test
    public void liianOttaminenAntaaLoput(){
        varasto.lisaaVarastoon(5);
	double saatuMaara = varasto.otaVarastosta(10);
	assertEquals(5,saatuMaara,vertailuTarkkuus);
    }

    @Test
    public void ottaminenLisääTilaa() {
        varasto.lisaaVarastoon(8);

        varasto.otaVarastosta(2);

        // varastossa pitäisi olla tilaa 10 - 8 + 2 eli 4
        assertEquals(4, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }


    @Test
	    public void konstr() {
		    varasto = new Varasto(-1);
		    assertEquals(0,varasto.getSaldo(),vertailuTarkkuus);
		    varasto = new Varasto(0);
		    assertEquals(0,varasto.getSaldo(),vertailuTarkkuus);
	            varasto = new Varasto(0,0);
        	    assertEquals(0,varasto.getSaldo(),vertailuTarkkuus);
	            varasto = new Varasto(1,1);
		    assertEquals(1,varasto.getSaldo(),vertailuTarkkuus);
		    varasto = new Varasto(2,1);
                    assertEquals(1,varasto.getSaldo(),vertailuTarkkuus);
                    varasto = new Varasto(1,2);
		    assertEquals(1,varasto.getSaldo(),vertailuTarkkuus);
		    varasto = new Varasto(-1,2);
		    assertEquals(0,varasto.getSaldo(),vertailuTarkkuus);
		    varasto = new Varasto(-1,-1);
		    assertEquals(0,varasto.getSaldo(),vertailuTarkkuus);
		    varasto = new Varasto(2,1);
		    assertEquals(1,varasto.getSaldo(),vertailuTarkkuus);
		    assertTrue(varasto.toString().contains("1"));
	    }

    @Test
	    public void negatiivinenOttaminen() {
		    double maara=varasto.otaVarastosta(-20);
		    assertEquals(0.0,maara,vertailuTarkkuus);
	    }
    @Test
	    public void liikaaOttaminen() {
		    double saldoAlussa=varasto.getSaldo();
		    double maara=varasto.otaVarastosta(20);
		    assertEquals(0.0,varasto.getSaldo(), vertailuTarkkuus);
		    assertEquals(saldoAlussa,maara,vertailuTarkkuus);
	    }
    @Test
	    public void negatiivinenLisaaminen() {
		    double saldoAlussa=varasto.getSaldo();
		    varasto.lisaaVarastoon(-20);
		    assertEquals(saldoAlussa, varasto.getSaldo(),vertailuTarkkuus);
	    }
    @Test
	    public void liikaaLisaaminen() {
		    varasto.otaVarastosta(varasto.getTilavuus());
		    varasto.lisaaVarastoon(varasto.getTilavuus()*2);
		    assertEquals(varasto.getTilavuus(),varasto.getSaldo(),vertailuTarkkuus);
	    }
}
