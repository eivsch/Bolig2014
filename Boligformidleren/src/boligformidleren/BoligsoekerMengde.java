/*
 * Innhold:
 * Sist oppdatert:
 * Programmert av:
 */
package boligformidleren;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Eivind
 */
public class BoligsoekerMengde {

    private Comparator komp = new Personsammenlikner();

    private Set<Boligsoeker> mengde = new TreeSet<>(komp);

    // setter inn ny person i mengden
    public void settInn(Boligsoeker b) {
        mengde.add(b);
    }

    // Returnere et boligsoeker som hører til navnet
    public Boligsoeker finnBoligsoeker(String fornavn, String etternavn) {
        Iterator<Boligsoeker> bsIter = mengde.iterator();
        Boligsoeker bs;
        while (bsIter.hasNext()) {
            bs = bsIter.next();
            if (bs.getFornavn().equals(fornavn) && bs.getEtternavn().equals(etternavn)) {
                return bs;
            }
        }
        return null;
    }

    //Kan vurdere evt. finnBoligsøker metode.

    public String toString() {
        Iterator<Boligsoeker> iter = mengde.iterator();

        String boligsoekere = "";
        Boligsoeker bs;
        while (iter.hasNext()) {
            bs = iter.next();
            boligsoekere += bs.toString() + "\n";
        }
        return boligsoekere;
    }
}
