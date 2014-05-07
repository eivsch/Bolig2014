/*
 * INNHOLD:
 * Den abstrakte klassen Person.
 *
 * Sist oppdatert: 29.04.2014, 14:30.
 * Programmert av: Eivind, Gretar
 */
package boligformidleren;

import java.io.*;

/**
 * Klassen skal lagre opplysninger om en person, samt inneholde eventuelle
 * set/get-metoder og toString-metode.
 */
public abstract class Person implements Serializable {

    private String fornavn, etternavn, gateadresse, poststed, epost;
    private int postnr, tlfnr;

    public Person(String fornavn, String etternavn, String gateadresse, int postnr, String poststed, String epost, int tlfnr) {
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.gateadresse = gateadresse;
        this.postnr = postnr;
        this.poststed = poststed;
        this.epost = epost;
        this.tlfnr = tlfnr;
    }

    // get-metoder
    public String getFornavn() {
        return fornavn;
    }

    public String getEtternavn() {
        return etternavn;
    }
 
    public String getGateadresse() {
        return gateadresse;
    }
 
    public int getPostnr() {
        return postnr;
    }
 
    public String getPoststed() {
        return poststed;
    }
 
    public String getEpost() {
        return epost;
    }
 
    public int getTelefonnr() {
        return tlfnr;
    }
 
    // set-metoder
    public void setFornavn(String f) {
        fornavn = f;
    }

    public void setEtternavn(String e) {
        etternavn = e;
    }
 
    public void setGateadresse(String g) {
        gateadresse = g;
    }
 
    public void setPostnr(int p) {
        postnr = p;
    }
 
    public void setPoststed(String p) {
        poststed = p;
    }
 
    public void setEpost(String e) {
        epost = e;
    }
 
    public void setTelefonnr(int t) {
        tlfnr = t;
    }
    
 
    public boolean equals(Object p) {
        return ((Person) p).getEtternavn().equals(etternavn)
                && ((Person) p).getFornavn().equals(fornavn);
    }
    
    public String toString() {
        String s = "Navn: " + fornavn + " " + etternavn + "\nGateadresse: " + gateadresse
                + "\nPostnummer: " + postnr + "\nPoststed: " + poststed
                + "\nE-post: " + epost + "\nTlf: " + tlfnr;
        return s;
    }
}
