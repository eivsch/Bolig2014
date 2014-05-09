/*
 * INNHOLD:
 * Klassen Leilighet.
 *
 * Sist oppdatert: 27.03.2014, 12:05.
 * Programmert av: Eivind, Gretar
 */

package boligformidleren;

import java.util.Date;

/**
 * Klassen er subklasse til Bolig og skal representere en leilighet.
 */
public class Leilighet extends Bolig {
    private int etasje;
    private boolean heis, balkong;
    private String diverse;

    public Leilighet(String gateadresse, int postnr, String poststed, String type, String beskrivelse,
            Date annonsedato, int inneAreal, int antRom, int byggeaar, int pris,
            int etasje, boolean heis, boolean balkong){

        super(gateadresse, postnr, poststed, type, beskrivelse, annonsedato, inneAreal, antRom,
                byggeaar, pris);
        this.etasje = etasje;
        this.heis = heis;
        this.balkong = balkong;
    }

    // get-metoder
    public int getEtasje(){
        return etasje;
    }

    public boolean getHeis(){
        return heis;
    }

    public boolean getBalkong(){
        return balkong;
    }

    // set-metoder
    public void setEtasje( int e ){
        etasje = e;
    }

    public void setHeis( boolean h ){
        heis = h;
    }

    public void setBalkong( boolean b ){
        balkong = b;
    }
    
    public String toString(){
        String s = super.toString() + "\nEtasje: " + etasje + "\nHeis: "
                + (heis ? "Ja" : "Nei") + "\nBalkong: " + (balkong ? "Ja" : "Nei");
        return s;
    }
}
