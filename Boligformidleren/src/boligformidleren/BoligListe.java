/*
 * INNHOLD:
 * Klassen Boligliste er en LinkedList som inneholder alle boliger for hver person
 *
 * Sist oppdatert: 02.04.2014, 12:45.
 * Programmert av: Gretar, Eivind
 */
package boligformidleren;

import java.util.*;
import java.io.*;

public class BoligListe implements Serializable {

    List<Bolig> liste = new LinkedList<>();

    // Setter inn nytt Bolig-objekt bakerst i lista
    public void settInn(Bolig b) {
        liste.add(b);
    }
    
    public boolean fjern(Bolig b){
        return liste.remove(b);
    }
    
    public List getListe(){
        return liste;
    }
    
    // Finner bolig på gitt adresse
    public Bolig finnBolig(String gateadresse, int postnr, String poststed){
        Iterator<Bolig> iter = liste.iterator();
        while(iter.hasNext()){
            Bolig b = iter.next();
            if(gateadresse.equals(b.getGateadresse()) 
                    && postnr == b.getPostnr() 
                    && poststed.equals(b.getPoststed()))
                return b;
        }
        return null;
    }
    
    //Returnerer en tekst med liste over alle Boliger,
    public String toString() {
        String boliger = "";
        Iterator<Bolig> iterator = liste.iterator();
        while (iterator.hasNext()) {
            boliger += iterator.next().toString() + "\n";
        }
        return boliger;
    }

}
