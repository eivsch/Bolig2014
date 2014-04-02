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

    // konstruktør
    public Enebolig(String adresse, String type, String beskrivelse,
            String annonsedato, int inneAreal, int antRom, int byggeaar, int pris,
            int antEtasjer, int tomtAreal, boolean kjeller) {
        
        super(adresse, type, beskrivelse, annonsedato, inneAreal, antRom,
                byggeaar, pris);
        this.antEtasjer = antEtasjer;
        this.tomtAreal = tomtAreal;
        this.kjeller = kjeller;
    }
    
    // get-metoder
    public int getAntEtasjer(){
        return antEtasjer;
    }
    
    public int getTomtAreal(){
        return tomtAreal;
    }
    
    public boolean getKjeller(){
        return kjeller;
    }
    
    // set-metoder
    public void setAntEtasjer( int a ){
        antEtasjer = a;
    }
    
    public void setTomtAreal( int t ){
        tomtAreal = t;
    }
    
    public void setKjeller( boolean k ){
        kjeller = k;
    }
    
    public String toString(){
        String s = super.toString() + "Etasjer: " + antEtasjer + "\nTomteareal: "
                + tomtAreal + "\nKjeller: " + (kjeller ? "Ja" : "Nei");
        return s;
    }
}
