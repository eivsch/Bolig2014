/*
 * INNHOLD:
 * Klassen Leilighet.
 *
 * Sist oppdatert: 27.03.2014, 12:05.
 * Programmert av: Eivind
 */

package boligformidleren;

/**
 * Klassen er subklasse til Bolig og skal representere en leilighet.
 */
public class Leilighet extends Bolig {
    private int etasje;
    private boolean heis, balkong;
    private String diverse;
    
    public Leilighet(String adresse, String type, String beskrivelse, 
            String annonsedato, int inneAreal, int antRom, int byggeaar, int leie, 
            int etasje, boolean heis, boolean balkong, String diverse){
        
        super(adresse, type, beskrivelse, annonsedato, inneAreal, antRom,
                byggeaar, leie);
        this.etasje = etasje;
        this.heis = heis;
        this.balkong = balkong;
        this.diverse = diverse;
    }
    
    public String toString(){
        String s = super.toString() + "\nEtasje: " + etasje + "\nHeis: " 
                + (heis ? "Ja" : "Nei") + "\nBalkong: " + (balkong ? "Ja" : "Nei") +
                "\nDiverse: " + diverse;
        return s;
    }
}