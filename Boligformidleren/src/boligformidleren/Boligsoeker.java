/*
 * INNHOLD: Klassen Boligsoeker er subklasse til Person og skal representere en boligsøker.
 * Sist oppdatert: 29.04.2014 kl.14:45
 * Programmert av: Eivind, Gretar
 */

package boligformidleren;

import java.util.Date;

public class Boligsoeker extends Person implements Comparable<Boligsoeker> {

    /*
     * Hvis boligsøker ikke har noen krav om type,
     * så bruker kun felles feltene.
     * Hvis boligsøker har krav om enebolig, så viser
     * vi enebolig-feltene i tillegg til felles feltene
     */
    // Krav til bolig
    private int pris, inneAreal, rom, byggeaar, maxAntEtasjer, maxEtasje, tomtestorrelse;
    private String pInfo, type;
    private Date dato;
    private boolean heis, balkong, kjeller, leterEtterBolig = true;

    // konstruktør
    public Boligsoeker(String fornavn, String etternavn, String gateadresse, int postnr, String poststed, String epost, int tlfnr, String pInfo,
            String type, int inneAreal, int rom, int byggeaar, int pris, Date dato,
            int maxAntEtasjer, int tomtestorrelse, boolean kjeller,
            int maxEtasje, boolean heis, boolean balkong) {

        super(fornavn, etternavn, gateadresse, postnr, poststed, epost, tlfnr);
        this.pInfo = pInfo;

        //Krav
        this.type = type;
        this.pris = pris;
        this.inneAreal = inneAreal;
        this.rom = rom;
        this.byggeaar = byggeaar;
        this.dato = dato;
        this.maxAntEtasjer = maxAntEtasjer;
        this.maxEtasje = maxEtasje;
        this.tomtestorrelse = tomtestorrelse;
        this.heis = heis;
        this.balkong = balkong;
        this.kjeller = kjeller;
    }
    
    public boolean getLeterEtterBolig(){
        return leterEtterBolig;
    }
    
    public void leterIkkeEtterBolig(){
        leterEtterBolig = false;
    }

    /**
     * Ufullstendig compareTo-metode. Kun for å kunne lagre Utleiere i en
     * TreeSet som ikke tar imot en komparator i kontruktøren
     */
    public int compareTo(Boligsoeker bs) {
        return bs.getEtternavn().compareTo(this.getEtternavn());
    }

    // toString-metode
    public String toString() {
        String s = super.toString() + "Personlige opplysninger: " + pInfo + 
                "\nKrav: " + 
                "\nBoligype: " + type + 
                "\nAreal: " + inneAreal + 
                "\nMin. soverom: " + rom + 
                "\nMin .byggeår: " + byggeaar + 
                "\nMax. antall etasjer: " + maxAntEtasjer + 
                "\nMin. tomtestørrelse: " + tomtestorrelse + 
                "\nKjeller: " + kjeller +
                "\nMax. etasje: " + maxEtasje + 
                "\nHeis: " + heis + 
                "\nBalkong: " + balkong + 
                "\nDato: " + StartVindu.datoFormat.format(dato);
        return s;
    }
}
