/*
 * INNHOLD:
 * Klassen Utleier.
 *
 * Sist oppdatert: 15.05.2014, 1700.
 * Programmert av: Eivind
 */
package boligformidleren;

/**
 * Klassen er subklasse til Person og skal inneholde data og eventuelle metoder
 * spesifikt for en utleier.
 */
public class Utleier extends Person implements Comparable<Utleier> {

    private String firma;
    private BoligListe liste;

    // konstruktør
    public Utleier(String fornavn, String etternavn, String gateadresse, int postnr,
            String poststed, String epost, int tlfnr, String firma) {

        super(fornavn, etternavn, gateadresse, postnr, poststed, epost, tlfnr);
        this.firma = firma;
        liste = new BoligListe();
    }

    // get metoder
    public String getFirma() {
        return firma;
    }

    // returnerer boliglisten knyttet til denne utleieren
    public BoligListe getBoligliste() {
        return liste;
    }

    // set metode
    public void setFirma(String f) {
        firma = f;
    }

    // Lager en array som skal tilsvare en rad i en utleiertabell.
    public Object[] tilRad() {
        Object[] rad = {super.getEtternavn() + ", " + super.getFornavn(), firma,
            super.getEpost(), new Integer(super.getTelefonnr()),
            super.getFornavn(), super.getEtternavn()};
        return rad;
    }

    // registrerer ny bolig til utleieren
    public void regBolig(Bolig b) {
        if (b != null) {
            liste.settInn(b);
        }
    }

    // sletter bolig fra utleieren
    public void slettBolig(Bolig b) {
        if (b != null) {
            liste.fjern(b);
        }
    }

    /**
     * Enkel compareTo-metode. Kun for å kunne lagre Utleiere i en TreeSet som
     * ikke tar imot en comparator i kontruktøren, slik at skriving til fil
     * muliggjøres.
     */
    public int compareTo(Utleier ul) {
        return ul.getEtternavn().compareTo(this.getEtternavn());
    }

    public String toString() {
        String s = super.toString() + "\nFirma: " + firma + "\nBoliger: " + liste.toString();
        return s;
    }

}
