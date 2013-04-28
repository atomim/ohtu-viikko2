
package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
                            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] ljono;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        ljono = new int[KAPASITEETTI];
        alkioidenLkm = 0;
        this.kasvatuskoko = OLETUSKASVATUS;
    }

    public IntJoukko(int kapasiteetti) {
        this();
        if (kapasiteetti < 0) {
            throw new IllegalArgumentException("Kapasitteetin pitää olla positiivinen");
        }
        ljono = new int[kapasiteetti+1];
    }
    
    
    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        this(kapasiteetti);
        if (kasvatuskoko <= 0) {
            throw new IllegalArgumentException("kasvatuskoon pitää olla yli nolla");
        }
        this.kasvatuskoko = kasvatuskoko;
    }

    public IntJoukko(IntJoukko joukko) {
        this(joukko.mahtavuus());
        this.alkioidenLkm = joukko.mahtavuus();
        kopioiTaulukko(joukko.ljono,this.ljono);
    }

    public boolean lisaa(int luku) {
        if (!kuuluu(luku)) {
            ljono[alkioidenLkm] = luku;
            alkioidenLkm++;
            kasvataJosTarvii();
            return true;
        }
        return false;
    }

    private void kasvataJosTarvii(){
        if (alkioidenLkm % ljono.length == 0) {
            int[] taulukkoOld = new int[ljono.length];
            kopioiTaulukko(ljono, taulukkoOld);
            ljono = new int[alkioidenLkm + kasvatuskoko];
            kopioiTaulukko(taulukkoOld, ljono);
        }
    }

    private int haeIndeksi(int luku){
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == ljono[i]) {
                return i;
            }
        }
        return -1;
    }

    public boolean kuuluu(int luku) {
        return haeIndeksi(luku)>=0;
    }

    public boolean poista(int luku) {
        int kohta = haeIndeksi(luku);
        if (kohta != -1) {
            for (int j = kohta; j < alkioidenLkm - 1; j++) {
                ljono[j] = ljono[j + 1];
            }
            alkioidenLkm--;
            return true;
        }
        return false;
    }


    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        for (int i = 0; i < Math.min(uusi.length,vanha.length); i++) {
            uusi[i] = vanha[i];
        }

    }

    public int mahtavuus() {
        return alkioidenLkm;
    }


    @Override
    public String toString() {
        if (alkioidenLkm == 0) {
            return "{}";
        } else {
            String tuotos = "{";
            for (int i = 0; i < alkioidenLkm - 1; i++) {
                tuotos += ljono[i]+ ", ";
            }
            return tuotos + ljono[alkioidenLkm - 1] + "}";
        }
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        kopioiTaulukko(ljono,taulu);
        return taulu;
    }
   

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko x = new IntJoukko(a);
        for (int i = 0; i < b.mahtavuus(); i++) {
            x.lisaa(b.ljono[i]);
        }
        return x;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko y = new IntJoukko();
        for (int i = 0; i < a.mahtavuus(); i++) {
            if (b.haeIndeksi(a.ljono[i]) >=0 ) {
                y.lisaa(b.ljono[i]);
            }
        }
        return y;
    }
    
    public static IntJoukko erotus ( IntJoukko a, IntJoukko b) {
        IntJoukko uusi = new IntJoukko(a);
        for (int i = 0; i < b.mahtavuus(); i++) {
            uusi.poista(b.ljono[i]);
        }
        return uusi;
    }
        
}