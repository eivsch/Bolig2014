/*
 * INNHOLD:
 * Klassen Kontrakt.
 *
 * Sist oppdatert: 05.05.2014, 14:00.
 * Programmert av: Gretar, Eivind
 */
package boligformidleren;

import java.io.*;
import java.util.Date;

/**
 * Klassen skal representere en kontrakt. Kontrakt skal ikke kunne endres.
 */
public class Kontrakt implements Serializable {

    private String utskrift;
    private Date startDato, sluttDato;
    private int pris;
    private Bolig b;
    private Utleier u;
    private Boligsoeker bs;

    // konstruktør
    public Kontrakt(Bolig b, Utleier u, Boligsoeker bs, int pris, Date startDato, Date sluttDato) {
        this.startDato = startDato;
        this.sluttDato = sluttDato;
        this.pris = pris;
        this.b = b;
        this.u = u;
        this.bs = bs;
    }
    
    // get metoder
    public int getPris(){
        return pris;
    }
    
    public Date getStartDato(){
        return startDato;
    }
    
    public Date getSluttDato(){
        return sluttDato;
    }

    public Boligsoeker getBoligsoeker() {
        return bs;
    }

    public Bolig getBolig() {
        return b;
    }
    
    public Utleier getUtleier(){
        return u;
    }
    
    public void setSluttDato(Date sluttDato){
        this.sluttDato = sluttDato;
    }
    
    public String toString(){
        utskrift = "Bolig-info:\t" + b.getGateadresse() + ", " + b.getPostnr() + " " + b.getPoststed() +
                   "\nUtleier-info:\t" + u.getFornavn() + " " + u.getEtternavn() +
                   "\nLeietaker-info:\t" + bs.getFornavn() + " " + bs.getEtternavn() +
                   "\nStartDato:\t" + StartVindu.ENKELDATOFORMAT.format(startDato) +
                   "\nSluttdato:\t" + StartVindu.ENKELDATOFORMAT.format(sluttDato) +
                   "\nLeiepris:\t" + pris;
        return utskrift;
    }

    // Skal legge informasjon om en kontrakt i en tabellrad. 
    public Object[] tilRad() {
        Object[] rad = {b.getGateadresse(), new Integer(b.getPostnr()) + ", " + 
                b.getPoststed(), u.getFornavn() + " " + u.getEtternavn(), 
                bs.getFornavn() + " " + bs.getEtternavn(), startDato, sluttDato};
        return rad;
    }
}
