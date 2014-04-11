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

    // Returnere et personobjektet som hører til navnet
    public Person finnPerson(String fornavn, String etternavn) {
        Iterator<Person> personIter = mengde.iterator();
        Person p;
        while (personIter.hasNext()) {
            p = personIter.next();
            if (p.getFornavn() == fornavn && p.getEtternavn() == etternavn) {
                return p;
            }
        }
        return null;
    }
    
    //Kan vurdere evt. finnUtleier og finnBoligsøker metoder.

    // registrerer ny bolig til en utleier
    public boolean regBolig(Utleier u, Bolig b) {

        Iterator<Person> personIter = mengde.iterator();

        if (b == null || u == null) {
            return false;
        }

        if (mengde.isEmpty()) {
            return false;
        }
        // kaller på Boligliste sin boligFinnesFraFør for å sjekke om boligen allerede finnes i registeret til personen
        Boligliste boligliste = new Boligliste();
        if (boligliste.boligFinnesFraFoer(u.getBoligliste(),b)) {
            return false;
        }
        //Boligen kan registreres
        u.regBolig(b);
        return true;
    }

    public String toString() {
        Iterator<Person> iter = mengde.iterator();

        String personer = "";
        Person p;
        while (iter.hasNext()) {
            p = iter.next();
            personer += p.toString() + "\n\tBoliger Registrert: ";
        }
        return personer;
    }
}
