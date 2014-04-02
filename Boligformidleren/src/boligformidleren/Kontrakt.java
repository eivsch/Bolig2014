/*
 * INNHOLD:
 * Den abstrakte klassen Bolig.
 *
 * Sist oppdatert: 02.04.2014, 11:00.
 * Programmert av: Gretar
 */
package boligformidleren;

import java.io.*;

/**
 * Klassen skal representere en kontrakt. Kontrakt skal ikke kunne endres
 * og derfor settes fungerer toString()-metoden egentlig som en get-metode,
 * dvs. den returneren alle informasjon om kontrakten.
 */
public class Kontrakt implements Serializable {
    
    private String utskrift, varighet;
    
    // konstruktør
    public Kontrakt( Bolig b, Utleier u, Boligsoeker bs, String v){
        varighet = v;
        utskrift = "Bolig-info:\n" + b.toString() +
                   "\n\nUtleier-info:\n" + u.toString() +
                   "\n\nLeietaker-info:\n" + bs.toString() +
                   "\n\nVarighet: " + v;
    }
    
    public String toString(){
        return utskrift;
    }
}
