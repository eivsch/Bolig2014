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

public class UtleierMengde implements Serializable {

    private Comparator komp = new Personsammenlikner();

    private Set<Utleier> mengde = new TreeSet<>(komp);

    // setter inn ny person i mengden
    public void settInn(Utleier ul) {
        mengde.add(ul);
    }

    public boolean fjern(Utleier ul) {
        return mengde.remove(ul);
    }

    // Returnere et personobjektet som hører til navnet
    public Utleier finnUtleier(String fornavn, String etternavn) {
        Iterator<Utleier> utleierIter = mengde.iterator();
        Utleier ul;
        while (utleierIter.hasNext()) {
            ul = utleierIter.next();
            if (ul.getFornavn().equals(fornavn) && ul.getEtternavn().equals(etternavn)) {
                return ul;
            }
        }
        return null;
    }
    
    // søker i hele utleiermengden etter bolig ut fra en gitt adresse
    public Bolig finnBolig(String gateadresse, int postnr, String poststed){
        Iterator<Utleier> utleierIter = mengde.iterator();
        Utleier ul;
        BoligListe bl;
        Bolig b;
        
        while(utleierIter.hasNext()){
            ul = utleierIter.next();
            bl = ul.getBoligliste();
            b = bl.finnBolig(gateadresse, postnr, poststed);
            if( b != null )
                return b;
        }
        return null;
    }

    // registrerer ny bolig til en utleier
    public boolean regBolig(Utleier u, Bolig b) {

        if (b == null || u == null) {
            return false;
        }

        /**
         * finner utleieren sin boligliste og kaller på containsmetoden for å
         * sjekke om boligen finnes fra før i lista, må evt. gjøres for hver
         * utleier for maks sikkerhet. Vil komplisere hele funksjonen.
         */
        Iterator<Utleier> iter = mengde.iterator();
        BoligListe bl;
        while (iter.hasNext()) {
            bl = iter.next().getBoligliste();
            List l = bl.getListe();
            if (l.contains(b)) {
                return false;
            }
        }

        //Boligen kan registreres
        u.regBolig(b);
        return true;
    }
    
    // For filbehandling. ruleBasedKollator er ikke serialiserbar.
    public Set<Utleier> kopierMengdeUsortert() {
        Set<Utleier> usortertKopiMengde = new TreeSet<>();
        Iterator<Utleier> iter = mengde.iterator();
        while (iter.hasNext()) {
            Utleier u = iter.next();
            usortertKopiMengde.add(u);
        }
        return usortertKopiMengde;
    }

    public Set<Utleier> getSortertMengde() {
        return mengde;
    }

    public String toString() {
        Iterator<Utleier> iter = mengde.iterator();

        String utleiere = "";
        Utleier ul;
        while (iter.hasNext()) {
            ul = iter.next();
            utleiere += ul.toString() + "\n";
        }
        return utleiere;
    }
}
