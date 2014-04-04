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
    private Boligliste liste;

    // konstruktør
    public Utleier(String fornavn, String etternavn, String adresse, String epost, int tlfnr,
            String firma) {

        super(fornavn, etternavn, adresse, epost, tlfnr);
        this.firma = firma;
        liste = new Boligliste();
    }
    
    // registrerer ny bolig til utleieren
    public void regBolig( Bolig b ){
        if( b != null )
            liste.settInn(b);
        else
            return;
    }
    
    // returnerer boliglisten knyttet til denne utleieren
    public Boligliste getBoligliste(){
        return liste;
    }
    
    public String toString(){
        String s = super.toString() + "\nFirma: " + firma;
        return s;
    }

}
