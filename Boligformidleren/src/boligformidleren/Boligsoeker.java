/*
 * INNHOLD:
 * Klassen Boligsoeker.
 * 
 * Sist oppdatert: 27.03.2014, 11:32.
 * Programmert av: Eivind
 */
package boligformidleren;

/**
 * Klassen er subklasse til Person og skal representere en boligsøker.
 */
public class Boligsoeker extends Person implements Comparable<Boligsoeker> {

    /*
     * Hvis boligsøker ikke har noen krav om type,
     * så bruker kun felles feltene.
     * Hvis boligsøker har krav om enebolig, så viser
     * vi enebolig-feltene i tillegg til felles feltene
     */
    // Krav til bolig
    private int pris, inneAreal, rom, byggeaar, antEtasjer;
    private String pInfo, type;
    private boolean heis, balkong, kjeller;

    public Boligsoeker(String fornavn, String etternavn, String adresse, String epost, int tlfnr, String pInfo, String type,
            int pris, int inneAreal, int rom, int byggeaar, int antEtasjer, boolean heis,
            boolean balkong, boolean kjeller) {

        super(fornavn, etternavn, adresse, epost, tlfnr);
        this.pInfo = pInfo;

        //Krav
        this.type = type;
        this.pris = pris;
        this.inneAreal = inneAreal;
        this.rom = rom;
        this.byggeaar = byggeaar;
        this.antEtasjer = antEtasjer;
        this.heis = heis;
        this.balkong = balkong;
        this.kjeller = kjeller;
    }//end of Boligsoeker

    /**
     * Ufullstendig compareTo-metode. Kun for å kunne lagre Utleiere i en
     * TreeSet som ikke tar imot en komparator i kontruktøren
     */
    public int compareTo(Boligsoeker bs) {
        return bs.getEtternavn().compareTo(this.getEtternavn());
    }

    public String toString() {
        String s = super.toString() + "\nKrav: ";
        return s;
    }
}
