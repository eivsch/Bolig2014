/*
 * INNHOLD:
 * Den abstrakte klassen Person.
 *
 * Sist oppdatert: 27.03.2014, 11:40.
 * Programmert av: Eivind
 */

package boligformidleren;

import java.io.*;

/**
 * Klassen skal lagre opplysninger om en person, samt inneholde eventuelle
 * set/get-metoder og toString-metode.
 */
public abstract class Person implements Serializable {

    private String navn, adresse, epost;
    private int tlfnr;

    public Person(String navn, String adresse, String epost, int tlfnr) {
        this.navn = navn;
        this.adresse = adresse;
        this.epost = epost;
        this.tlfnr = tlfnr;
    }
  
    public String getNavn() {
        return navn;
    }

    public String toString() {
        String s = "Navn: " + navn + "\nAdresse: " + adresse
                + "\nE-post: " + epost + "\nTlf: " + tlfnr;
        return s;
    }
}
