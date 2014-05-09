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
 * Klassen skal representere en kontrakt. Kontrakt skal ikke kunne endres
 * og derfor settes fungerer toString()-metoden egentlig som en get-metode,
 * dvs. den returneren alle informasjon om kontrakten.
 */
public class Kontrakt implements Serializable {
    
    private String utskrift;
    private Date startDato, sluttDato;
    private int pris;
    private Bolig b;
    private Utleier u;
    private Boligsoeker bs;
    
    // konstruktør
    public Kontrakt( Bolig b, Utleier u, Boligsoeker bs, int pris, Date startDato, Date sluttDato){
        this.startDato = startDato;
        this.sluttDato = sluttDato;
        this.pris = pris;
        this.b = b;
        this.u = u;
        this.bs = bs;
        
        utskrift = "Bolig-info:\n" + b.toString() +
                   "\n\nUtleier-info:\n" + u.toString() +
                   "\n\nLeietaker-info:\n" + bs.toString() +
                   "\n\nLeiepris:\n" + pris +
                   "\n\nStartDato:\n" + StartVindu.ENKELDATOFORMAT.format(startDato) +
                   "\n\nSluttdato: " + StartVindu.ENKELDATOFORMAT.format(sluttDato);
    }
    
    public Boligsoeker getBoligsoeker(){
        return bs;
    }

    public Bolig getBolig() {
        return b;
    }

    public Date getSluttDato() {
        return sluttDato;
    }

    public Date getStartDato() {
        return startDato;
    }
    
    public String toString(){
        return utskrift;
    }
}
