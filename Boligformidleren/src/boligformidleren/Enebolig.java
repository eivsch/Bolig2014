/*
 * INNHOLD:
 * Klassen Enebolig.
 *
 * Sist oppdatert: 27.03.2014, 11:43.
 * Programmert av: Eivind
 */
package boligformidleren;

/**
 * Klassen er subklasse til Bolig og skal mer spesifikt representere en enebolig
 * eller rekkehus.
 */
public class Enebolig extends Bolig {

    private int antEtasjer, tomtAreal;
    private boolean kjeller;

    public Enebolig(String adresse, String type, String beskrivelse,
            String annonsedato, int inneAreal, int antRom, int byggeaar, int leie,
            int antEtasjer, int tomtAreal, boolean kjeller) {
        
        super(adresse, type, beskrivelse, annonsedato, inneAreal, antRom,
                byggeaar, leie);
        this.antEtasjer = antEtasjer;
        this.tomtAreal = tomtAreal;
        this.kjeller = kjeller;
    }
    
    public String toString(){
        String s = super.toString() + "Etasjer: " + antEtasjer + "\nTomteareal: "
                + tomtAreal + "\nKjeller: " + (kjeller ? "Ja" : "Nei");
        return s;
    }
}
