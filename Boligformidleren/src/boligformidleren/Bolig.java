/*
 * INNHOLD:
 * Den abstrakte klassen Bolig.
 *
 * Sist oppdatert: 27.03.2014, 11:42.
 * Programmert av: Eivind
 */
package boligformidleren;

import java.io.*;

/**
 * Klassen skal representere en bolig og lagre opplysninger om denne, samt
 * inneholde nødvendige metoder som set/get og toString.
 */
public abstract class Bolig implements Serializable {

    private String adresse, type, beskrivelse, annonsedato;
    private int inneAreal, antRom, byggeaar, leie;
    //bilde

    public Bolig(String adresse, String type, String beskrivelse, 
            String annonsedato, int inneAreal, int antRom, int byggeaar, int leie) {

        this.adresse = adresse;
        this.type = type;
        this.beskrivelse = beskrivelse;
        this.annonsedato = annonsedato;
        this.inneAreal = inneAreal;
        this.antRom = antRom;
        this.byggeaar = byggeaar;
        this.leie = leie;
    }

    public String toString() {
        String s = "Adresse: " + adresse + "\nType: " + type + "\nBeskrivelse: "
                + beskrivelse + "\nAnnonsedato: " + annonsedato + "\nAreal: "
                + inneAreal + "\nAntall rom: " + antRom + "\nByggeår: " + byggeaar
                + "\nLeie: " + leie;

        return s;
    }
}
