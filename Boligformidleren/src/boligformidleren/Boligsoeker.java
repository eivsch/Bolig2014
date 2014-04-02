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
public class Boligsoeker extends Person {

    //Krav til bolig
    private int leie, inneAreal, rom, byggeaar, antEtasjer;
    private String type;
    private boolean heis, balkong, kjeller;

    public Boligsoeker(String navn, String adresse, String epost, String type, int tlfnr,
            int leie, int inneAreal, int rom, int byggeaar, int antEtasjer, boolean heis, 
            boolean balkong, boolean kjeller) {
        super(navn, adresse, epost, tlfnr);
        //Krav
        this.type = type;
        this.leie = leie;
        this. inneAreal = inneAreal;
        this.rom = rom;
        this.byggeaar = byggeaar;
        this. antEtasjer = antEtasjer;
        this.heis = heis;
        this.balkong = balkong;
        this.kjeller = kjeller;
    }//end of Boligsoeker

    public String toString() {
        String s = super.toString() + "\nKrav: ";
        return s;
    }
}
