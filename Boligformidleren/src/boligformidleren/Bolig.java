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
    private int inneAreal, antRom, byggeaar, pris;
    //bilde

    // konstruktør
    public Bolig(String adresse, String type, String beskrivelse, 
            String annonsedato, int inneAreal, int antRom, int byggeaar, int pris) {

        this.adresse = adresse;
        this.type = type;
        this.beskrivelse = beskrivelse;
        this.annonsedato = annonsedato;
        this.inneAreal = inneAreal;
        this.antRom = antRom;
        this.byggeaar = byggeaar;
        this.pris = pris;
    }
    
    // get-metoder
    public String getAdresse(){
        return adresse;
    }
    
    public String getType(){
        return type;
    }
    
    public String getBeskrivelse(){
        return beskrivelse;
    }
    
    public String getAnnonsedato(){
        return annonsedato;
    }
    
    public int getInneAreal(){
        return inneAreal;
    }
    
    public int getAntRom(){
        return antRom;
    }
    
    public int getByggeaar(){
        return byggeaar;
    }
    
    public int getPris(){
        return pris;
    }
    
    // set-metoder
    public void setAdresse( String a ){
        adresse = a;
    }
    
    public void setType( String t ){
        type = t;
    }
    
    public void setBeskrivelse( String b ){
        beskrivelse = b;
    }
    
    public void setAnnonsedato( String a ){
        annonsedato = a;
    }
    
    public void setInneAreal( int i ){
        inneAreal = i;
    }
    
    public void setAntRom( int a ){
        antRom = a;
    }
    
    public void setByggeaar( int b){
        byggeaar = b;
    }
    
    public void setPris( int p ){
        pris = p;
    }
    

    public String toString() {
        String s = "Adresse: " + adresse + "\nType: " + type + "\nBeskrivelse: "
                + beskrivelse + "\nAnnonsedato: " + annonsedato + "\nAreal: "
                + inneAreal + "\nAntall rom: " + antRom + "\nByggeår: " + byggeaar
                + "\nPris: " + pris;

        return s;
    }
}
