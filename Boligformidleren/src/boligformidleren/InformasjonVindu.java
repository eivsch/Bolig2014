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
    * Top-panelen er delt opp i fire kolonner:
    *      #1 er en panel for person-informasjon, 
    *      #2 er en panel for bolig-informasjon for EN bolig
    *      #3 er en panel for bolig-informasjon ut fra parametre (som inneholder minMaxPanel)
    *      #4 er en panel for kontrakt-informasjon
    */
    private JPanel masterPanel, top, under, personPanel, boligPanel, parameterPanel, minMaxPanel, fellesPanel, eneboligPanel, leilighetPanel, kontraktPanel;
    
    // person panel
    private JTextField fornavn, etternavn;
    private JButton hentInfoPerson, visAlleBoligsoekere;
    
    // bolig panel
    private JTextField gateadresse, postnr, poststed;
    private JButton visBoligInfo, hentBoligInteresse;
    
    // parameter panel
    private JComboBox boligtype;
    private final String[] TYPE = {"Ingen krav", "Enebolig/rekkehus", "Leilighet"};
    
    // min-max panel
    private JTextField minAreal, maxAreal, minRom, maxRom, minByggeaar, maxByggeaar, minPris, maxPris, minDato, maxDato;

    // enebolig panel
    private JTextField etasjer, tomtestorrelse;
    private JCheckBox kjeller;
    
    // leilighet panel
    private JTextField etasje;
    private JCheckBox heis, balkong;
    
    
    private JTextArea output;
    
    // konstruktør
    public InformasjonVindu(){
        super("Informasjon");
        
        // antall rader, antall kolonner og gap størrelse for top-panelene
        int antRadTopPanel = 1;
        int antKolTopPanel = 4;
        int antRadPersonPanel = 9;
        int antRadBoligPanel = 9;
        int antRadFellesPanel = 1;
        int antRadMinMaxPanel = 6;
        int antRadEneboligLeilighetPanel = 3;
        int antRadKontraktPanel = 9;
        int antKolonner = 2;
        int antMinMaxKolonner = 3;
        int gapTop = 20;
        int gap = 0;
        
        // paneler
        masterPanel = new JPanel(new BorderLayout());
        top = new JPanel(new GridLayout(antRadTopPanel, antKolTopPanel, gapTop, gap));
        personPanel = new JPanel(new GridLayout(antRadPersonPanel, antKolonner, gap, gap));
        boligPanel = new JPanel(new GridLayout(antRadBoligPanel, antKolonner, gap, gap));
        parameterPanel = new JPanel(new BorderLayout());
        fellesPanel = new JPanel(new GridLayout(antRadFellesPanel, antKolonner, gap, gap));
        minMaxPanel = new JPanel(new GridLayout(antRadMinMaxPanel, antMinMaxKolonner, gap, gap));
        eneboligPanel = new JPanel(new GridLayout(antRadEneboligLeilighetPanel, antKolonner, gap, gap));
        leilighetPanel = new JPanel(new GridLayout(antRadEneboligLeilighetPanel, antKolonner, gap, gap));
        kontraktPanel = new JPanel(new GridLayout(antRadKontraktPanel, antKolonner, gap, gap));
        under = new JPanel(new BorderLayout());
        masterPanel.add(top, BorderLayout.PAGE_START);
        masterPanel.add(under, BorderLayout.CENTER);
        top.add(personPanel);
        top.add(boligPanel);
        top.add(parameterPanel);
        top.add(kontraktPanel);
        parameterPanel.add(fellesPanel, BorderLayout.PAGE_START);
        parameterPanel.add(minMaxPanel, BorderLayout.CENTER);
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
        
        // parameter panel
        fellesPanel.add(new JLabel("Boligtype: "));
        boligtype = new JComboBox(TYPE);
        boligtype.setSelectedIndex(0);
        boligtype.addActionListener(this);
        fellesPanel.add(boligtype);
        
        // min-max panel
        minMaxPanel.add(new JLabel(" "));
        minMaxPanel.add(new JLabel("MIN.", SwingConstants.CENTER));
        minMaxPanel.add(new JLabel("MAX.", SwingConstants.CENTER));
        
        minMaxPanel.add(new JLabel("Areal: "));
        minAreal = new JTextField(10);
        maxAreal = new JTextField(10);
        minMaxPanel.add(minAreal);
        minMaxPanel.add(maxAreal);
        
        minMaxPanel.add(new JLabel("Soverom: "));
        minRom = new JTextField(10);
        maxRom = new JTextField(10);
        minMaxPanel.add(minRom);
        minMaxPanel.add(maxRom);
        
        minMaxPanel.add(new JLabel("Byggeår: "));
        minByggeaar = new JTextField(10);
        maxByggeaar = new JTextField(10);
        minMaxPanel.add(minByggeaar);
        minMaxPanel.add(maxByggeaar);
        
        minMaxPanel.add(new JLabel("Leiepris: "));
        minPris = new JTextField(10);
        maxPris = new JTextField(10);
        minMaxPanel.add(minPris);
        minMaxPanel.add(maxPris);
        
        minMaxPanel.add(new JLabel("Dato: "));
        minDato = new JTextField(10);
        maxDato = new JTextField(10);
        minMaxPanel.add(minDato);
        minMaxPanel.add(maxDato);
        
        // enebolig panel
        eneboligPanel.add(new JLabel("Antall etasjer: "));
        etasjer = new JTextField(10);
        eneboligPanel.add(etasjer);

        eneboligPanel.add(new JLabel("Tomtestørrelse: "));
        tomtestorrelse = new JTextField(10);
        eneboligPanel.add(tomtestorrelse);

        eneboligPanel.add(new JLabel("Kjeller: "));
        kjeller = new JCheckBox("");
        eneboligPanel.add(kjeller);
        
        // leilighet panel
        leilighetPanel.add(new JLabel("Etasje: "));
        etasje = new JTextField(10);
        leilighetPanel.add(etasje);

        leilighetPanel.add(new JLabel("Heis: "));
        heis = new JCheckBox("");
        leilighetPanel.add(heis);

        leilighetPanel.add(new JLabel("Balkong: "));
        balkong = new JCheckBox();
        leilighetPanel.add(balkong);
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
        String valgtType = (String) boligtype.getSelectedItem();
        
        if(e.getSource() == hentInfoPerson){
            hentInfoPerson();
        } else if(e.getSource() == visAlleBoligsoekere){
            visAlleBoligsoekere();
        } else if(e.getSource() == visBoligInfo){
            visBoligInfo();
        } else if(e.getSource() == hentBoligInteresse){
            hentBoligInteresse();
        } else if (e.getSource() == boligtype) {
            // drop-down box

            BorderLayout layout = (BorderLayout) parameterPanel.getLayout();

            if (valgtType.equals(TYPE[0])) {
                if (layout.getLayoutComponent(BorderLayout.PAGE_END) != null) {
                    parameterPanel.remove(layout.getLayoutComponent(BorderLayout.PAGE_END));
                }
            } else if (valgtType.equals(TYPE[1])) {
                if (layout.getLayoutComponent(BorderLayout.PAGE_END) != null) {
                    parameterPanel.remove(layout.getLayoutComponent(BorderLayout.PAGE_END));
                }
                parameterPanel.add(eneboligPanel, BorderLayout.PAGE_END);
            } else if (valgtType.equals(TYPE[2])) {
                if (layout.getLayoutComponent(BorderLayout.PAGE_END) != null) {
                    parameterPanel.remove(layout.getLayoutComponent(BorderLayout.PAGE_END));
                }
                parameterPanel.add(leilighetPanel, BorderLayout.PAGE_END);
            }
            // refresh vinduet
            this.getContentPane().revalidate();
            this.getContentPane().repaint();
        }
        
    
        
    }
}
