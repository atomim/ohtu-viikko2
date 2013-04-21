/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import ohtu.verkkokauppa.Kauppa;
import ohtu.verkkokauppa.Pankki;
import ohtu.verkkokauppa.Tuote;
import ohtu.verkkokauppa.Varasto;
import ohtu.verkkokauppa.Viitegeneraattori;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author olavilin
 */
public class KauppaTest {

    @Before
    public void setUp() {
    }

    @Test
    public void ostoksenPaaytyttyapankinMetodiaTilisiirtoKutsutaan() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(1);

        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(), anyInt());
    }
    //aloitataan asiointi, koriin lisätään koriin tuote jota varastossa on ja suoritetaan ostos (eli kutsutaan metodia kaupan tilimaksu()). 
    //varmistettava että kutsutaan pankin metodia tilisiirto oikealla asiakkaalla, tilinumerolla ja summalla

    @Test
    public void ostettaessaSuoritetaanMaksuOikein() {
        Pankki pankki = mock(Pankki.class);
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(178678);
        Kauppa k = new Kauppa(varasto, pankki, viite);
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");

        verify(pankki).tilisiirto(eq("pekka"), eq(178678), eq("12345"), eq("33333-44455"), eq(5));

    }
    //aloitataan asiointi, koriin lisätään koriin kaksi eri tuotetta joita varastossa on ja suoritetaan ostos. 
    //varmistettava että kutsutaan pankin metodia tilisiirto oikealla asiakkaalla, tilinumerolla ja summalla

    @Test
    public void ostettaessaKaksiTuotettaSuoritetaanMaksuOikein() {
        Pankki pankki = mock(Pankki.class);
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(2)).thenReturn(10);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(1, "siirappi", 6));
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(178678);
        Kauppa k = new Kauppa(varasto, pankki, viite);
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");

        verify(pankki).tilisiirto(eq("pekka"), eq(178678), eq("12345"), eq("33333-44455"), eq(11));

    }
    //aloitataan asiointi, koriin lisätään koriin kaksi samaa tuotetta jota on varastossa tarpeeksi ja suoritetaan ostos. 
    //varmistettava että kutsutaan pankin metodia tilisiirto oikealla asiakkaalla, tilinumerolla ja summalla

    @Test
    public void ostettaessaKaksiSamaaTuotettaSuoritetaanMaksuOikein() {
        Pankki pankki = mock(Pankki.class);
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(2)).thenReturn(0);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(1, "siirappi", 6));
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(178678);
        Kauppa k = new Kauppa(varasto, pankki, viite);
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");

        verify(pankki).tilisiirto(eq("pekka"), eq(178678), eq("12345"), eq("33333-44455"), eq(10));

    }
    //aloitataan asiointi, koriin lisätään koriin tuote jota on varastossa tarpeeksi ja tuote joka on loppu ja suoritetaan ostos. 
    //varmistettava että kutsutaan pankin metodia tilisiirto oikealla asiakkaalla, tilinumerolla ja summalla
    @Test
    public void ostettaessaKaksiTuotettaJaToinenOnLoppuSuoritetaanMaksuOikein() {
        Pankki pankki = mock(Pankki.class);
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(2)).thenReturn(0);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(1, "siirappi", 6));
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(178678);
        Kauppa k = new Kauppa(varasto, pankki, viite);
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");

        verify(pankki).tilisiirto(eq("pekka"), eq(178678), eq("12345"), eq("33333-44455"), eq(5));

    }
    //varmistettava että metodin aloita asiointi kutsuminen nollaa edellisen ostoksen tiedot

    public void uudenAsioinninAloittaminenNollaa() {
        Pankki pankki = mock(Pankki.class);
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(2)).thenReturn(0);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(1, "siirappi", 6));
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(178678);
        Kauppa k = new Kauppa(varasto, pankki, viite);
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.aloitaAsiointi();
        k.tilimaksu("pekka", "12345");
        verify(pankki).tilisiirto(eq("pekka"), eq(178678), eq("12345"), eq("33333-44455"), eq(0));

    }
    //varmistettava että kauppa pyytää uuden viitenumeron jokaiselle maksutapahtumalle    

    public void viitenumero() {
        Pankki pankki = mock(Pankki.class);
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(2)).thenReturn(0);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(1, "siirappi", 6));
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(178678);
        Kauppa k = new Kauppa(varasto, pankki, viite);
        k.aloitaAsiointi();
        k.tilimaksu("pekka", "12345");
        verify(viite).uusi();
        k.tilimaksu("pekka", "12345");
        verify(viite).uusi();
        k.tilimaksu("pekka", "12345");
        verify(viite).uusi();
    }
}
