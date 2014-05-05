/*
 * INNHOLD: Klassen Boligsoeker er subklasse til Person og skal representere en boligsøker.
 * Sist oppdatert: 05.05.2014 kl.11:50
 * Programmert av: Eivind, Gretar
 */

package boligformidleren;

import java.util.Date;

public class Boligsoeker extends Person implements Comparable<Boligsoeker> {

    /*
     * Hvis boligsøker ikke har noen krav om type,
     * så bruker kun felles feltene.
     * Hvis boligsøker har krav om enebolig, så viser
     * vi enebolig-feltene i tillegg til felles feltene
     */
    // Krav til bolig
    private int pris, areal, soverom, byggeaar, maxAntEtasjer, maxEtasje, tomtestorrelse;
    private String pInfo, type;
    private Date dato;
    private boolean heis, balkong, kjeller, leterEtterBolig = true;

    // konstruktør
    public Boligsoeker(String fornavn, String etternavn, String gateadresse, int postnr, String poststed, String epost, int tlfnr, String pInfo,
            String type, int areal, int soverom, int byggeaar, int pris, Date dato,
            int maxAntEtasjer, int tomtestorrelse, boolean kjeller,
            int maxEtasje, boolean heis, boolean balkong) {

        super(fornavn, etternavn, gateadresse, postnr, poststed, epost, tlfnr);
        this.pInfo = pInfo;

        // felles krav
        this.areal = areal;
        this.byggeaar = byggeaar;
        this.dato = dato;
        this.pris = pris;
        this.soverom = soverom;
        this.type = type;
        
        // enebolig krav
        this.maxAntEtasjer = maxAntEtasjer;
        this.tomtestorrelse = tomtestorrelse;
        this.kjeller = kjeller;
        
        // leilighet krav
        this.maxEtasje = maxEtasje;
        this.balkong = balkong;
        this.heis = heis;
    }
    
    // get metoder
    public int getAreal(){
        return areal;
    }
    
    public int getByggeaar(){
        return byggeaar;
    }
    
    public Date getDato(){
        return dato;
    }
    
    public int getPris(){
        return pris;
    }
    
    public int getSoverom(){
        return soverom;
    }
    
    public String getType(){
        return type;
    }
    
    public int getMaxAntEtasjer(){
        return maxAntEtasjer;
    }
    
    public int getTomtestorrelse(){
        return tomtestorrelse;
    }
    
    public boolean getKjeller(){
        return kjeller;
    }
    
    public int getMaxEtasje(){
        return maxEtasje;
    }
    
    public boolean getBalkong(){
        return balkong;
    }
    
    public boolean getHeis(){
        return heis;
    }
    
    // set metoder
    public void setAreal(int a){
        areal = a;
    }
    
    public void setByggeaar(int b){
        byggeaar = b;
    }
    
    public void setDato(Date d){
        dato = d;
    }
    
    public void setPris(int p){
        pris = p;
    }
    
    public void setSoverom(int s){
        soverom = s;
    }
    
    public void setType(String t){
        type = t;
    }
    
    public void setMaxAntEtasjer(int m){
        maxAntEtasjer = m;
    }
    
    public void setTomtestorrelse(int t){
        tomtestorrelse = t;
    }
    
    public void setKjeller(boolean k){
        kjeller = k;
    }
    
    public void setMaxEtasje(int m){
        maxEtasje = m;
    }
    
    public void setBalkong(boolean b){
        balkong = b;
    }
    
    public void setHeis(boolean h){
        heis = h;
    }
    
    public boolean getLeterEtterBolig(){
        return leterEtterBolig;
    }
    
    // sjekker om bolig passer til boligsøkerens krav
    public boolean passerTilBolig(Bolig b){
        if( b == null)
            return false;
        
        if(!b.getLedig())
            return false;
        
        boolean passer = true;

        if(areal > b.getAreal()){
            System.out.println("areal: " + passer);
            passer = false;
        }if(byggeaar > b.getByggeaar()){
            System.out.println("byggeaar: " + passer);
            passer = false;
        }if(dato.after(b.getDato())){
            System.out.println("dato: " + passer);
            passer = false;
        }if(pris < b.getPris()){
            System.out.println("pris: " + passer);
            passer = false;
        }if(soverom > b.getSoverom()){
            System.out.println("rom: " + passer);
            passer = false;
        }if(!type.equals(b.getType())){
            System.out.println("type: " + passer);
            passer = false;
        }
        System.out.println("Felles: " + passer);
        
        if(b instanceof Enebolig){
            Enebolig e = (Enebolig) b;
            
            if(maxAntEtasjer < e.getAntEtasjer())
                passer = false;
            if(tomtestorrelse > e.getTomtAreal())
                passer = false;
            if(kjeller != e.getKjeller())
                passer = false;
            
            System.out.println("Enebolig: " + passer);
        }
        
        if(b instanceof Leilighet){
            Leilighet l = (Leilighet) b;
            
            if(maxEtasje < l.getEtasje())
                passer = false;
            if(balkong != l.getBalkong())
                passer = false;
            if(heis != l.getHeis())
                passer = false;
            
            System.out.println("Leilighet: " + passer);
        }
        
        return passer;
    }
            
    
    public void leterIkkeEtterBolig(){
        leterEtterBolig = false;
    }

    /**
     * Ufullstendig compareTo-metode. Kun for å kunne lagre Utleiere i en
     * TreeSet som ikke tar imot en komparator i kontruktøren
     */
    public int compareTo(Boligsoeker bs) {
        return bs.getEtternavn().compareTo(this.getEtternavn());
    }

    // toString-metode
    public String toString() {
        String s = super.toString() + "Personlige opplysninger: " + pInfo + 
                "\nKrav: " + 
                "\nBoligype: " + type + 
                "\nMin. areal: " + areal + 
                "\nMin. sovesrom: " + soverom + 
                "\nMin. byggeår: " + byggeaar + 
                "\nMax. leiepris: " + pris + 
                "\nMax. antall etasjer: " + maxAntEtasjer + 
                "\nMin. tomtestørrelse: " + tomtestorrelse + 
                "\nKjeller: " + (kjeller ? "ja": "nei") +
                "\nMax. etasje: " + maxEtasje + 
                "\nHeis: " + (heis ? "ja": "nei") + 
                "\nBalkong: " + (balkong ? "ja": "nei") + 
                "\nDato: " + StartVindu.datoFormat.format(dato);
        return s;
    }
}
