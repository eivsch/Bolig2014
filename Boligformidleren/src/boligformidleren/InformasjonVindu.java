/*
 * Innhold: Vindu som brukes for å hente informasjon om personer, boliger og kontrakter
 * Sist oppdatert: 30.04.2014 kl.13:45
 * Programmert av: Gretar
 */
package boligformidleren;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InformasjonVindu extends JFrame implements ActionListener{
    
   /* Informasjons-vinduet er i to deler, øvre og nedre.
    * Øvre delen inneholder top-panelen (GridLayout).
    * Nedre delen inneholder under-panelen for utskrift-området.
    * Top-panelen er delt opp i tre kolonner:
    *      til venstre er en panel for person-informasjon, 
    *      i midten er en panel for bolig-informasjon
    *      til høyre er en panel for kontrakt-informasjon
    */
    
    private JPanel masterPanel, top, under, personPanel, boligPanel, kontraktPanel;
    
    // konstruktør
    public InformasjonVindu(){
        super("Informasjon");
        
        // antall rader, antall kolonner og gap størrelse for top-panelen
        int antRadPersonPanel = 9;
        int antRadBoligPanel = 9;
        int antRadKontraktPanel = 9;
        int antKolonner = 2;
        int gap = 0;
    }
    
    // Lyttemetode
    public void actionPerformed(ActionEvent e) {
        
       
    
        
    }
}
