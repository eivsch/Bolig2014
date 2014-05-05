/*
 * INNHOLD:
 * Klassen Kontrakt.
 *
 * Sist oppdatert: 02.04.2014, 11:00.
 * Programmert av: Gretar
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
    private Date sluttDato;
    private int pris;
    
    // konstruktør
    public Kontrakt( Bolig b, Utleier u, Boligsoeker bs, int pris, Date dato){
        sluttDato = dato;
        this.pris = pris;
        
        utskrift = "Bolig-info:\n" + b.toString() +
                   "\n\nUtleier-info:\n" + u.toString() +
                   "\n\nLeietaker-info:\n" + bs.toString() +
                   "\n\nLeiepris:\n" + pris +
                   "\n\nSluttdato: " + StartVindu.datoFormat.format(dato);
    }
    
    public String toString(){
        return utskrift;
    }
}
