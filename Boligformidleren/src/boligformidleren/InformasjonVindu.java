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
    
    // person informasjon
    private JTextField fornavn, etternavn;
    private JButton hentInfoPerson, visAlleBoligsoekere;
    
    // bolig informasjon
    private JTextField gateadresse, postnr, poststed;
    private JButton visBoligInfo, hentBoligInteresse;
    
    private JTextArea output;
    
    // konstruktør
    public InformasjonVindu(){
        super("Informasjon");
        
        // antall rader, antall kolonner og gap størrelse for top-panelene
        int antRadTopPanel = 1;
        int antKolTopPanel = 3;
        int antRadPersonPanel = 9;
        int antRadBoligPanel = 9;
        int antRadKontraktPanel = 9;
        int antKolonner = 2;
        int gapTop = 20;
        int gap = 0;
        
        // paneler
        masterPanel = new JPanel(new BorderLayout());
        top = new JPanel(new GridLayout(antRadTopPanel, antKolTopPanel, gapTop, gap));
        personPanel = new JPanel(new GridLayout(antRadPersonPanel, antKolonner, gap, gap));
        boligPanel = new JPanel(new GridLayout(antRadBoligPanel, antKolonner, gap, gap));
        kontraktPanel = new JPanel(new GridLayout(antRadKontraktPanel, antKolonner, gap, gap));
        under = new JPanel(new BorderLayout());
        top.setVisible(true);
        personPanel.setVisible(true);
        boligPanel.setVisible(true);
        kontraktPanel.setVisible(true);
        masterPanel.add(top, BorderLayout.PAGE_START);
        masterPanel.add(under, BorderLayout.CENTER);
        top.add(personPanel);
        top.add(boligPanel);
        top.add(kontraktPanel);
        this.getContentPane().add(masterPanel);
        setSize(1000, 750);
        
        output = new JTextArea();
        output.setEditable(false);
        JScrollPane scroll = new JScrollPane(output);
        under.add(scroll, BorderLayout.CENTER);
        
        // person panel
        personPanel.add(new JLabel("Fornavn: "));
        fornavn = new JTextField(10);
        personPanel.add(fornavn);
         
        personPanel.add(new JLabel("Etternavn: "));
        etternavn = new JTextField(10);
        personPanel.add(etternavn);
        
        hentInfoPerson = new JButton("Vis person info");
        hentInfoPerson.addActionListener(this);
        personPanel.add(hentInfoPerson);
        
        visAlleBoligsoekere = new JButton("Vis alle boligsøkere");
        visAlleBoligsoekere.addActionListener(this);
        personPanel.add(visAlleBoligsoekere);
        
        // bolig panel
        boligPanel.add(new JLabel("Gateadresse: "));
        gateadresse = new JTextField(10);
        boligPanel.add(gateadresse);
        
        boligPanel.add(new JLabel("Postnummer: "));
        postnr = new JTextField(10);
        boligPanel.add(postnr);
        
        boligPanel.add(new JLabel("Poststed: "));
        poststed = new JTextField(10);
        boligPanel.add(poststed);
        
        visBoligInfo = new JButton("Vis bolig info");
        visBoligInfo.addActionListener(this);
        boligPanel.add(visBoligInfo);
        
        
        hentBoligInteresse = new JButton("Vis interesserte personer");
        hentBoligInteresse.addActionListener(this);
        boligPanel.add(hentBoligInteresse);
    }
    
    public void hentInfoPerson(){
        //...
    }
    
    public void visAlleBoligsoekere(){
        //...
    }
    
    public void visBoligInfo(){
        //...
    }
    
    public void hentBoligInteresse(){
        //...
    }
    
    
    
    // Lyttemetode
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == hentInfoPerson){
            hentInfoPerson();
        } else if(e.getSource() == visAlleBoligsoekere){
            visAlleBoligsoekere();
        } else if(e.getSource() == visBoligInfo){
            visBoligInfo();
        } else if(e.getSource() == hentBoligInteresse){
            hentBoligInteresse();
        } 
        
    
        
    }
}
