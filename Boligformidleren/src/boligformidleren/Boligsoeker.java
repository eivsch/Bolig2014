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
    //Krav til pris
    private String persInfo;

    public Boligsoeker(String navn, String adresse, String epost, int tlfnr,
            String persInfo /*Krav kommer her*/) {
        super(navn, adresse, epost, tlfnr);
        this.persInfo = persInfo;
        //Krav
    }

    public String toString() {
        String s = super.toString() + "\nKrav: ";
        return s;
    }
}
