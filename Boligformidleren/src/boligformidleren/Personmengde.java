/*
 * INNHOLD:
 * Klassen Personmengde.
 *
 * Sist oppdatert: 04.04.2014, 12:45.
 * Programmert av: Eivind, Gretar
 
 * Beskrivelse:
 * En TreeSet-mengde som inneholder alle Person-objektene
 */
package boligformidleren;

import java.util.*;
import java.io.*;

public class Personmengde {

    private Comparator komp = new Personsammenlikner();

    private Set<Person> mengde = new TreeSet<>(komp);

    // setter inn ny person i mengden
    public void settInn(Person p) {
        mengde.add(p);
    }
    
    // registrerer ny bolig til en utleier
    public boolean regBolig( Utleier u, Bolig b ){
        
        Iterator<Person> personIter = mengde.iterator();
        
        if( b == null || u == null )
            return false;
        
        if ( mengde.isEmpty() )
                return false;
        // løper igjennom mengden og søker å boligen "b" eksisterer ikke fra før
        while( personIter.hasNext() ){
            // her må vi kalle på "equals" metode in Bolig-klassen
            // og sjekke om boligen "b" er lik noen annen i Boliglista
            // for hver utleier
        }         
        u.regBolig(b);
        return true;
    }

    public String toString() {
        Iterator<Person> iter = mengde.iterator();

        String personer = "";

        while (iter.hasNext()) {
            personer += iter.next().toString() + "\n";
        }
        return personer;
    }
}
