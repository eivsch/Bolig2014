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
    private BoligListe liste;

    // konstruktør
    public Utleier(String fornavn, String etternavn, String adresse, String epost, int tlfnr,
            String firma) {

        super(fornavn, etternavn, adresse, epost, tlfnr);
        this.firma = firma;
        liste = new BoligListe();
    }

    // registrerer ny bolig til utleieren
    public void regBolig(Bolig b) {
        if (b != null) {
            liste.settInn(b);
        } else {
            return;
        }
    }

    // returnerer boliglisten knyttet til denne utleieren
    public BoligListe getBoligliste() {
        return liste;
    }

    public String toString() {
        String s = super.toString() + "\nFirma: " + firma + "\nBoliger: " + liste.toString();
        return s;
    }

}
