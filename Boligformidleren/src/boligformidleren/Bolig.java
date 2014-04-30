/*
 * INNHOLD:
 * Den abstrakte klassen Bolig.
 *
 * Sist oppdatert: 27.03.2014, 11:42.
 * Programmert av: Eivind, Gretar
 */
package boligformidleren;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Klassen skal representere en bolig og lagre opplysninger om denne, samt
 * inneholde nødvendige metoder som set/get og toString.
 */
public abstract class Bolig implements Serializable {

    private String gateadresse, poststed, type, beskrivelse;
    private int postnr, inneAreal, antRom, byggeaar, pris;
    private Date annonsedato;
    private boolean ledig = true;
    //bilde

    // konstruktør
    public Bolig(String gateadresse, int postnr, String poststed, String type, String beskrivelse,
            Date annonsedato, int inneAreal, int antRom, int byggeaar, int pris) {

        this.gateadresse = gateadresse;
        this.postnr = postnr;
        this.poststed = poststed;
        this.type = type;
        this.beskrivelse = beskrivelse;
        this.annonsedato = annonsedato;
        this.inneAreal = inneAreal;
        this.antRom = antRom;
        this.byggeaar = byggeaar;
        this.pris = pris;
    }

    // get-metoder
    public String getGateadresse(){
        return gateadresse;
    }
    
    public int getPostnr(){
        return postnr;
    }
    
    public String getPoststed(){
        return poststed;
    }

    public String getType(){
        return type;
    }

    public String getBeskrivelse(){
        return beskrivelse;
    }

    public Date getAnnonsedato(){
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
    
    public boolean getLedig(){
        return ledig;
    }

    // set-metoder
    public void setGateadresse( String a ){
        gateadresse = a;
    }
 
    public void setPostnr( int p ){
        postnr = p;
    }
 
    public void setPoststed( String p ){
        poststed = p;
    }
 
    public void setType( String t ){
        type = t;
    }

    public void setBeskrivelse( String b ){
        beskrivelse = b;
    }

    public void setAnnonsedato( Date a ){
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
    
    public void boligErOpptatt(){
        ledig = false;
    }

    // her må vi sette in en equals-metode for å komparere to boliger
    public boolean equals(Object o) {
        if(o instanceof Bolig){
            Bolig b = (Bolig) o;
            
            //foreløpig løsning. Boligene er like hvis de har samme adresse.
            if( b.getGateadresse().equals(gateadresse) 
                    && b.getPostnr() == postnr 
                    && b.getPoststed().equals(poststed) )
                return true;
        }
        return false;
    }

    public String toString() {
        String s = "Gateadresse: " + gateadresse + "\nPostnummer: " + postnr + "\nPoststed: " + poststed 
                + "\nType: " + type + "\nBeskrivelse: " + beskrivelse 
                + "\nAnnonsedato: " + StartVindu.datoFormat.format(annonsedato) + "\nAreal: " + inneAreal 
                + "\nAntall rom: " + antRom + "\nByggeår: " + byggeaar
                + "\nPris: " + pris;

        return s;
    }
}
