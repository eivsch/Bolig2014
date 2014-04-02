/*
 * INNHOLD:
 * Klassen Boligliste.
 *
 * Sist oppdatert: 02.04.2014, 12:45.
 * Programmert av: Gretar
 
 * Beskrivelse:
 * Hver Person-objekt Klassen Boligliste er en LinkedList som inneholder alle boliger for hver per
 */
package boligformidleren;

import java.util.*;
import java.io.*;

public class Boligliste implements Serializable{
    List<Bolig> liste = new LinkedList<>();
    
    // Setter inn nytt Bolig-objekt bakerst i lista
    public void settInn( Bolig b ){
        liste.add( b );
    }
    
    //Sorterer Person-objektene alfabetisk på etternavn og fornavn
    //public void sorter(){...}
    
    
    //Returnerer liste over alle Boliger,
    public String toString()
    {
      String boliger = "";
      Iterator<Bolig> iterator = liste.iterator();
      while (iterator.hasNext())
      {
        boliger += iterator.next().toString() + "\n";
      }
      return boliger;
    }
  
      
}
