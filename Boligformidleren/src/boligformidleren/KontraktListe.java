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

public class KontraktListe implements Serializable{
    
    private List<Kontrakt> kontraktListe = new ArrayList<Kontrakt>();
    
    public void settInn(Kontrakt k){
        kontraktListe.add(k);
    }
    public String toString(){
        Iterator<Kontrakt> iter = kontraktListe.iterator();
        
        String kontrakter = "";
        
        while(iter.hasNext()){
            kontrakter += iter.next().toString() + "\n";
        }
        return kontrakter;
    }
}
