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

    //Sorterer Person-objektene alfabetisk pÃ¥ etternavn og fornavn
    //public void sorter(){...}

    public boolean boligFinnesFraFør(Boligliste liste, Bolig b){
		Iterator<Bolig> iter = liste.iterator();
		while(liste.hasNext()){
			if(liste.next().equals(b))  //Bruker equals-metoden i Bolig-klassen, returnerer true hvis de er like.
			   return true;
		}
		return false;    //Boligen "b" finnes ikke i lista
	}

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
