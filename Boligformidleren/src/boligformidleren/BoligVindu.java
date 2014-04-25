/*
 * Innhold: 
 * Vindu som brukes for registrering, slettning av boliger.
 * Sist oppdatert:
 * Programmert av: Gretar
 */
package boligformidleren;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoligVindu extends JFrame implements ActionListener {
    
    // en masterPanel, en top panel (inneholder paneler for felles felt, enebolig felt og leilighet felt), en under panel for utskrift
    private JPanel masterPanel, top, fellesPanel, eneboligPanel, leilighetPanel, under;
    
    // felles for eneboliger og leiligheter
    private JTextField adresse, boligtype, areal, antRom, byggeaar, beskrivelse, pris, avetertDato, utleierFornavn, utleierEtternavn;
    private JButton velgEnebolig, velgLeilighet, skrivUt;
    
    // kun for eneboliger
    private JTextField etasjer, tomtestorrelse;
    private JCheckBox kjeller;
    private JButton regEnebolig, slettEnebolig;
    
    // kun for leiligheter
    private JTextField etasje;
    private JCheckBox heis, balkong;
    private JButton regLeilighet, slettLeilighet;
    
    private JTextArea output;
    private int antRadFelles, antRadEnebolig, antRadLeilighet, antKol, gap;
    
    
    // konstruktør
    public BoligVindu(){
        super("Bolig");
        
        antRadFelles =  10;// en rad for hver felles felt
        antRadEnebolig = 3; // en rad for hver enebolig variabel plus 2 ekstra for knapper
        antRadLeilighet = 3; // en rad for hver leilighet variabel plus 2 ekstra for knapper
        antKol = 2;
        gap = 5;
        
        // startPanelen inneholder en knapp for hver boligtype
        masterPanel = new JPanel(new BorderLayout());
        top = new JPanel(new GridLayout(4,1,0,0));
        fellesPanel = new JPanel(new GridLayout(antRadFelles, antKol, gap, gap));
        eneboligPanel = new JPanel(new GridLayout(antRadEnebolig, antKol, gap, gap));
        eneboligPanel.setVisible(false);
        leilighetPanel = new JPanel(new GridLayout(antRadLeilighet, antKol, gap, gap));
        leilighetPanel.setVisible(false);
        under = new JPanel(new BorderLayout());
        masterPanel.add(top, BorderLayout.PAGE_START);
        masterPanel.add(under, BorderLayout.CENTER);
        top.add(fellesPanel);
        this.getContentPane().add(masterPanel);
        setSize(300, 700);

        output = new JTextArea();
        JScrollPane scroll = new JScrollPane(output);
        under.add(scroll, BorderLayout.CENTER);

        // felles textfields
        top.add(new JLabel("Adresse: "));
        adresse = new JTextField(10);
        top.add(adresse);

        top.add(new JLabel("Boligtype: "));
        boligtype = new JTextField(10);
        top.add(boligtype);

        top.add(new JLabel("Areal: "));
        areal = new JTextField(10);
        top.add(areal);

        top.add(new JLabel("Antall rom: "));
        antRom = new JTextField(10);
        top.add(antRom);

        top.add(new JLabel("Byggeår: "));
        byggeaar = new JTextField(10);
        top.add(byggeaar);

        top.add(new JLabel("Beskrivelse: "));
        beskrivelse = new JTextField(10);
        top.add(beskrivelse);

        top.add(new JLabel("Leiepris: "));
        pris = new JTextField(10);
        top.add(pris);

        top.add(new JLabel("Avetert dato: "));
        avetertDato = new JTextField(10);
        top.add(avetertDato);
        
        top.add(eneboligPanel);
        top.add(leilighetPanel);
        
        top.add(new JLabel("Utleier fornavn: "));
        utleierFornavn = new JTextField(10);
        top.add(utleierFornavn);

        top.add(new JLabel("Utleier etternavn: "));
        utleierEtternavn = new JTextField(10);
        top.add(utleierEtternavn);

        // enebolig textfields
        eneboligPanel.add(new JLabel("Antall etasjer: "));
        etasjer = new JTextField(10);
        eneboligPanel.add(etasjer);
        
        eneboligPanel.add(new JLabel("Kjeller: "));
        kjeller = new JCheckBox("");
        eneboligPanel.add(kjeller);

        eneboligPanel.add(new JLabel("Tomtestørrelse: "));
        tomtestorrelse = new JTextField(10);
        eneboligPanel.add(tomtestorrelse);

        // leilighet textfields
        leilighetPanel.add(new JLabel("Etasje: "));
        etasje = new JTextField(10);
        leilighetPanel.add(etasje);
        
        leilighetPanel.add(new JLabel("Heis: "));
        heis = new JCheckBox("");
        leilighetPanel.add(heis);

        leilighetPanel.add(new JLabel("Balkong: "));
        balkong = new JCheckBox();
        leilighetPanel.add(balkong);

        

        // buttons
        regEnebolig = new JButton("Register bolig");
        regEnebolig.addActionListener(this);
        top.add(regEnebolig, BorderLayout.PAGE_END);

        slettEnebolig = new JButton("Slett enebolig");
        slettEnebolig.addActionListener(this);
        top.add(slettEnebolig, BorderLayout.PAGE_END);
        
        skrivUt = new JButton("Vis alle eneboliger");
        skrivUt.addActionListener(this);
        top.add(skrivUt, BorderLayout.PAGE_END);
    }
    
    // Lyttemetode
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == velgEnebolig){
            eneboligPanel.setVisible(true);
        }
        else if(e.getSource() == velgLeilighet){
            leilighetPanel.setVisible(true);
        }
    }
}
