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

    private List<Kontrakt> kontraktListe = new ArrayList<Kontrakt>();

    public void settInn(Kontrakt k) {
        kontraktListe.add(k);
    }

    public int antallKontrakterRegistrertPaaBoligsoeker(Boligsoeker bs) {
        Iterator<Kontrakt> kIter = kontraktListe.iterator();
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

    /** 
     * Returnerer en kontraktarray for leietakeren. Returnerer null dersom
     * leietakeren ikke har opprettet noen kontrakter.
     */
    public Kontrakt[] finnKontrakter(Boligsoeker bs) {
        Iterator<Kontrakt> kIter = kontraktListe.iterator();
        int storrelse = antallKontrakterRegistrertPaaBoligsoeker(bs);
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
    }

    public String toString() {
        Iterator<Kontrakt> iter = kontraktListe.iterator();

        String kontrakter = "";

        while (iter.hasNext()) {
            kontrakter += iter.next().toString() + "\n";
        }
        return kontrakter;
    }
}
