/*
 * INNHOLD: Klassen Boligsoeker er subklasse til Person og skal representere en boligsøker.
 * Sist oppdatert: 29.04.2014 kl.14:45
 * Programmert av: Eivind, Gretar
 */

package boligformidleren;

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

    // konstruktør
    public Boligsoeker(String fornavn, String etternavn, String gateadresse, int postnr, String poststed, String epost, int tlfnr, String pInfo, String type,
            int pris, int inneAreal, int rom, int byggeaar, int antEtasjer, boolean heis,
            boolean balkong, boolean kjeller) {

        super(fornavn, etternavn, gateadresse, postnr, poststed, epost, tlfnr);
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
    }

    /**
     * Ufullstendig compareTo-metode. Kun for å kunne lagre Utleiere i en
     * TreeSet som ikke tar imot en komparator i kontruktøren
     */
    public int compareTo(Boligsoeker bs) {
        return bs.getEtternavn().compareTo(this.getEtternavn());
    }

    // toString-metode
    public String toString() {
        String s = super.toString() + "\nKrav: ";
        return s;
    }
}
