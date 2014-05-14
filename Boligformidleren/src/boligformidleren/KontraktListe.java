/*
 * Innhold: Liste over kontrakter i to ArrayLister.
 * Sist oppdatert: 13.05.2014, 1400.
 * Programmert av: Eivind
 */
package boligformidleren;

import java.util.*;
import java.io.*;

/**
 * Klassen KontraktListe håndterer lagring og behandling av programmets
 * opprettede kontrakter. Kontraktene er skilt i to lister, en for gjeldende og
 * en for utløpte Viktige funksjoner er blant annet å for hver programoppstart
 * sjekke hvilke gjeldende kontrakter som har passert utløpstdatoen og flytte
 * disse over fra kontraktListeGjeldende til kontraktListeArkiv.
 */
public class KontraktListe implements Serializable {

    private List<Kontrakt> kontraktListeGjeldende = new ArrayList<Kontrakt>();
    private List<Kontrakt> kontraktArkiv = new ArrayList<Kontrakt>();

    // get metoder
    public void settInn(Kontrakt k) {
        kontraktListeGjeldende.add(k);
    }

    // Returnerer totalt antall lagrede kontrakter
    public int antKontrakter() {
        return kontraktListeGjeldende.size() + kontraktArkiv.size();
    }

    public List<Kontrakt> getKontraktListeGjeldende() {
        return kontraktListeGjeldende;
    }

    public List<Kontrakt> getKontraktListeArkiv() {
        return kontraktArkiv;
    }

    /**
     * Metode for å fjerne parameteren kontrakt fra kontraktListeGjeldende,
     * legge den over i kontraktArkiv, sette boligen til "ledig" og tidligere
     * leietaker til "leter etter bolig".
     */
    public void fjernGjeldendeKontraktOgArkiver(Kontrakt k) {
        kontraktListeGjeldende.remove(k);
        // Setter boligen i kontrakten til "ledig".
        Bolig b1 = k.getBolig();
        Bolig b2 = StartVindu.getUtleierVindu().getUtleierMengde().finnBolig(b1.getGateadresse(),
                b1.getPostnr(), b1.getPoststed());
        b2.boligErLedig();
        // Setter leietakeren i kontrakten til "leter etter bolig".
        Boligsoeker bs1 = k.getBoligsoeker();
        Boligsoeker bs2 = StartVindu.getBoligsoekerVindu().getBoligsoekerMengde().
                finnBoligsoeker(bs1.getFornavn(), bs1.getEtternavn());
        bs2.leterEtterBolig();
        // Legger kontrakten ove ri arkiv.
        kontraktArkiv.add(k);
    }

    // Tar imot en boligsøker som parameter. Returnerer kontrakten denne har 
    // registrert i kontraktListeGjeldende.
    public Kontrakt finnGjeldendeKontrakt(Boligsoeker bs) {
        Iterator<Kontrakt> kIter = kontraktListeGjeldende.iterator();
        Kontrakt k;
        while (kIter.hasNext()) {
            k = kIter.next();
            if (k.getBoligsoeker().equals(bs)) {
                return k;
            }
        }
        return null;
    }

    /**
     * Tar imot en parameter dato, sjekker kontraktene i kontraktListeGjeldende
     * opp mot denne datoen. Er kontraktenes sluttdato før denne datoen skal
     * kontraktene arkiveres og deres toString returneres.
     */
    public String sjekkUtloepteOgArkiver(Date idag) {
        Iterator<Kontrakt> kIter = kontraktListeGjeldende.iterator();
        String s;
        Kontrakt k;
        while (kIter.hasNext()) {
            k = kIter.next();
            if (k.getSluttDato().before(idag)) {
                fjernGjeldendeKontraktOgArkiver(k);
                s = k.toString();
                return s;
            }
        }
        return null;
    }

    // Skriver ut alt som ligger lagret i objektet.
    public String toString() {
        Iterator<Kontrakt> gIter = kontraktListeGjeldende.iterator();
        Iterator<Kontrakt> aIter = kontraktArkiv.iterator();
        
        String kontrakter = "Gjeldende Kontrakter: \n";

        while (gIter.hasNext()) {
            kontrakter += gIter.next().toString() + "\n";
        }
        kontrakter += "Arkiverte kontrakter: \n";
        while (aIter.hasNext()) {
            kontrakter += aIter.next().toString() + "\n";
        }
        return kontrakter;
    }
}
