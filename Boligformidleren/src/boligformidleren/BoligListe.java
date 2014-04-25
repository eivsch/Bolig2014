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
    
    public List getListe(){
        return liste;
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
