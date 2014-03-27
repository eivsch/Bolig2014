/*
 * INNHOLD:
 * Klassen Utleier.
 *
 * Sist oppdatert: 27.03.2014, 11:41.
 * Programmert av: Eivind
 */
package boligformidleren;

/**
 * Klassen er subklasse til Person og skal inneholde data og eventuelle metoder
 * spesifikt for en utleier.
 */
public class Utleier extends Person {

    private String firma;
    //private Boligliste liste;

    public Utleier(String navn, String adresse, String epost, int tlfnr,
            String firma/*Boligliste liste*/) {

        super(navn, adresse, epost, tlfnr);
        this.firma = firma;
        //this.liste = liste;
    }
    public String toString(){
        String s = super.toString() + "\nFirma: " + firma;
        return s;
    }

}
