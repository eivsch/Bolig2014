/*
 * Innhold: Liste over kontrakter i en ArrayList
 * Sist oppdatert:
 * Programmert av: Eivind
 */
package boligformidleren;

import java.util.*;
import java.io.*;

/**
 *
 * Beskrivelse *
 */
public class KontraktListe implements Serializable {

    private List<Kontrakt> kontraktListeGjeldende = new ArrayList<Kontrakt>();
    private List<Kontrakt> kontraktListeArkiv = new ArrayList<Kontrakt>();

    public void settInn(Kontrakt k) {
        kontraktListeGjeldende.add(k);
    }
    
    public void fjernGjeldendeKontraktOgArkiver(Kontrakt k){
        kontraktListeGjeldende.remove(k);
        // Setter boligen i kontrakten til "ledig".
        Bolig b1 = k.getBolig();
        Bolig b2 = StartVindu.getUtleierVindu().getUtleierMengde().finnBolig(b1.getGateadresse(), 
                b1.getPostnr(), b1.getPoststed());
        b2.boligErLedig();
        kontraktListeArkiv.add(k);
    }
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
    
    /* Tatt ut pga ny organisering av utløpte/gjeldende kontrakter
    
    public int antallGjeldendeKontrakterRegistrertPaaBoligsoeker(Boligsoeker bs) {
        Iterator<Kontrakt> kIter = kontraktListeGjeldende.iterator();
        Kontrakt k;
        int antKontrakter = 0;
        while (kIter.hasNext()) {
            k = kIter.next();
            if (k.getBoligsoeker().equals(bs)) {
                antKontrakter++;
            }
        }
        return antKontrakter;
    }

    
     * Returnerer en kontraktarray for leietakeren. Returnerer null dersom
     * leietakeren ikke har opprettet noen kontrakter.
     *
    public Kontrakt[] finnGjeldendeKontrakter(Boligsoeker bs) {
        Iterator<Kontrakt> kIter = kontraktListeGjeldende.iterator();
        int storrelse = antallGjeldendeKontrakterRegistrertPaaBoligsoeker(bs);
        if (storrelse == 0) {
            return null;
        }
        Kontrakt[] kA = new Kontrakt[storrelse];
        Kontrakt k;
        while (kIter.hasNext()) {
            k = kIter.next();
            int indeks = 0;
            if (k.getBoligsoeker().equals(bs)) {
                kA[indeks++] = k;
            }
        }
        return kA;
    }*/
    
    public String sjekkUtloepteOgArkiver(Date idag){
        Iterator<Kontrakt> kIter = kontraktListeGjeldende.iterator();
        String s;
        Kontrakt k;
        while(kIter.hasNext()){
            k = kIter.next();
            if(k.getSluttDato().before(idag)){
                fjernGjeldendeKontraktOgArkiver(k);
                s = k.toString();
                return s;
            }
        }
        return null;
    }

    public String toString() {
        Iterator<Kontrakt> gIter = kontraktListeGjeldende.iterator();
        Iterator<Kontrakt> aIter = kontraktListeArkiv.iterator();

        String kontrakter = "Gjeldende Kontrakter: \n";

        while (gIter.hasNext()) {
            kontrakter += gIter.next().toString() + "\n";
        }
        kontrakter += "Arkiverte kontrakter: \n";
        while (aIter.hasNext()){
            kontrakter += aIter.next().toString() + "\n";
        }
        return kontrakter;
    }
}
