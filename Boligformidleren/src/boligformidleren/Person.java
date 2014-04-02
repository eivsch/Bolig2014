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

    private String fornavn, etternavn, adresse, epost;
    private int tlfnr;

    public Person(String fornavn, String etternavn, String adresse, String epost, int tlfnr) {
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.adresse = adresse;
        this.epost = epost;
        this.tlfnr = tlfnr;
    }
  
    public String getFornavn() {
        return fornavn;
    }
    
    public String getEtternavn(){
        return etternavn;
    }
    
    public boolean equals(Object p){
		return ((Person) p).getEtternavn().equals( etternavn ) &&
           ((Person) p).getFornavn().equals( fornavn ) ;
	}

    public String toString() {
        String s = "Navn: " + fornavn + " " + etternavn + "\nAdresse: " + adresse
                + "\nE-post: " + epost + "\nTlf: " + tlfnr;
        return s;
    }
}
