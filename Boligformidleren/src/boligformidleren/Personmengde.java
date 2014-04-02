/*
 * Innhold:
 * Sist oppdatert:
 * Programmert av: Eivind
 */
package boligformidleren;

import java.util.*;
import java.io.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Eivind
 */
public class Personmengde {

    private Comparator komp = new Personsammenlikner();

    private Set<Person> mengde = new TreeSet<Person>(komp);

    public void settInn(Person p) {
        mengde.add(p);
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
