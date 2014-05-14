/*
 * Innhold: Tabellmodell for visning av kontrakter
 * Sist oppdatert: 13.05.2014, 1600
 * Programmert av: Eivind
 */
package boligformidleren;

import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class KontraktTabellmodell {

    private final String[] KOLONNENAVN = {"Adresse", "Poststed", "Postnummer",
        "Pris", "Areal", "Byggeår", "Avertert fra"};
    private final int[] NOKKELKOLONNER = {0, 1, 2};
    private Object[][] celler;

    // Tegner tabell i konstruktør
    public KontraktTabellmodell(List<Kontrakt> l) {
        int maxAntRader = l.size();
        celler = new Object[maxAntRader][KOLONNENAVN.length];
        Iterator<Kontrakt> iter = l.iterator();
        int radTeller = 0;
        Kontrakt k;
        for (int rad = 0; rad < maxAntRader; rad++) {
            if (iter.hasNext()) {
                k = iter.next();
                celler[rad] = k.tilRad();
            }
        }
    }

    public void sammenslaaKontrakter() {

    }
}
