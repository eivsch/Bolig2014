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
    private JPanel masterPanel, top, fellesPanel, knappPanel, eneboligPanel, leilighetPanel, under;
    
    // felles for eneboliger og leiligheter
    private JTextField adresse, areal, antRom, byggeaar, beskrivelse, pris, avetertDato;
    private JComboBox<String> boligtype;
    private final String[] TYPE = {"Velg", "Enebolig/rekkehus", "Leilighet"};
    
    // for knappPanel
    private JTextField utleierFornavn, utleierEtternavn;
    private JButton regBolig, slettBolig, skrivUt;
    
    // kun for eneboliger
    private JTextField etasjer, tomtestorrelse;
    private JCheckBox kjeller;
    
    // kun for leiligheter
    private JTextField etasje;
    private JCheckBox heis, balkong;
    
    private JTextArea output;    
    
    // konstruktør
    public BoligVindu(){
        super("Bolig");
        
        int antRadFelles =  8;  // en rad for hver felles felt plus to for boligtyper
        int antRadKnapp = 4;
        int antRadEnebolig = 3; // en rad for hver enebolig variabel
        int antRadLeilighet = 3; // en rad for hver leilighet variabel
        int antKol = 2;
        int gap = 0;
        
        // startPanelen inneholder en knapp for hver boligtype
        masterPanel = new JPanel(new BorderLayout());
        top = new JPanel(new BorderLayout());
        fellesPanel = new JPanel(new GridLayout(antRadFelles, antKol, gap, gap));
        knappPanel = new JPanel(new GridLayout(antRadKnapp, antKol, gap, gap));
        eneboligPanel = new JPanel(new GridLayout(antRadEnebolig, antKol, gap, gap));
        eneboligPanel.setVisible(true);
        leilighetPanel = new JPanel(new GridLayout(antRadLeilighet, antKol, gap, gap));
        leilighetPanel.setVisible(true);
        under = new JPanel(new BorderLayout());
        masterPanel.add(top, BorderLayout.PAGE_START);
        masterPanel.add(under, BorderLayout.CENTER);
        top.add(fellesPanel, BorderLayout.PAGE_START);
        top.add(knappPanel, BorderLayout.PAGE_END);
        this.getContentPane().add(masterPanel);
        setSize(300, 700);

        output = new JTextArea();
        JScrollPane scroll = new JScrollPane(output);
        under.add(scroll, BorderLayout.CENTER);

        // felles textfields og drop-down list
        fellesPanel.add(new JLabel("Adresse: "));
        adresse = new JTextField(10);
        fellesPanel.add(adresse);
        
        fellesPanel.add(new JLabel("Boligtype: "));
        boligtype = new JComboBox(TYPE);
        boligtype.setSelectedIndex(0);
        boligtype.addActionListener(this);
        fellesPanel.add(boligtype);
        
        fellesPanel.add(new JLabel("Areal: "));
        areal = new JTextField(10);
        fellesPanel.add(areal);

        fellesPanel.add(new JLabel("Antall rom: "));
        antRom = new JTextField(10);
        fellesPanel.add(antRom);

        fellesPanel.add(new JLabel("Byggeår: "));
        byggeaar = new JTextField(10);
        fellesPanel.add(byggeaar);

        fellesPanel.add(new JLabel("Beskrivelse: "));
        beskrivelse = new JTextField(10);
        fellesPanel.add(beskrivelse);

        fellesPanel.add(new JLabel("Leiepris: "));
        pris = new JTextField(10);
        fellesPanel.add(pris);

        fellesPanel.add(new JLabel("Avetert dato: "));
        avetertDato = new JTextField(10);
        fellesPanel.add(avetertDato);
        
        knappPanel.add(new JLabel("Utleier fornavn: "));
        utleierFornavn = new JTextField(10);
        knappPanel.add(utleierFornavn);

        knappPanel.add(new JLabel("Utleier etternavn: "));
        utleierEtternavn = new JTextField(10);
        knappPanel.add(utleierEtternavn);

        // enebolig textfields
        eneboligPanel.add(new JLabel("Antall etasjer: "));
        etasjer = new JTextField(10);
        eneboligPanel.add(etasjer);

        eneboligPanel.add(new JLabel("Tomtestørrelse: "));
        tomtestorrelse = new JTextField(10);
        eneboligPanel.add(tomtestorrelse);
        
        eneboligPanel.add(new JLabel("Kjeller: "));
        kjeller = new JCheckBox("");
        eneboligPanel.add(kjeller);

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
        regBolig = new JButton("Register bolig");
        regBolig.addActionListener(this);
        knappPanel.add(regBolig);

        slettBolig = new JButton("Slett bolig");
        slettBolig.addActionListener(this);
        knappPanel.add(slettBolig);
        
        skrivUt = new JButton("Vis alle boliger");
        skrivUt.addActionListener(this);
        knappPanel.add(skrivUt);
    }
    
    // registrer bolig
    public void regBolig(String b){
        Bolig bolig;
        
        if( b.equals(TYPE[1]))
            bolig = new Enebolig(adresse.getText(), TYPE[1], beskrivelse.getText(), avetertDato.getText(),
                Integer.parseInt(areal.getText()), Integer.parseInt(antRom.getText()), Integer.parseInt(byggeaar.getText()),
                Integer.parseInt(pris.getText()), Integer.parseInt(etasjer.getText()), Integer.parseInt(tomtestorrelse.getText()), kjeller.isSelected());
        else
            bolig = new Leilighet(adresse.getText(), TYPE[1], beskrivelse.getText(), avetertDato.getText(),
                Integer.parseInt(areal.getText()), Integer.parseInt(antRom.getText()), Integer.parseInt(byggeaar.getText()),
                Integer.parseInt(pris.getText()), Integer.parseInt(etasje.getText()), heis.isSelected(), balkong.isSelected());
        
        Utleier ul = StartVindu.getUtleierVindu().getUtleierMengde().finnUtleier(utleierFornavn.getText(), utleierEtternavn.getText());
        
        if( ul == null )
            output.setText("Fann ikke utleier " + utleierFornavn.getText() + " " + utleierEtternavn.getText());
        else{
            StartVindu.getUtleierVindu().getUtleierMengde().regBolig(ul, bolig);
            output.setText("Bolig registrert");
        }
    }
    
    // slett bolig
    public void slettBolig(){
        
    }
    
    // Lyttemetode
    public void actionPerformed(ActionEvent e) {
        //JComboBox boks = (JComboBox)e.getSource();
        String valgtType = (String)boligtype.getSelectedItem();
        
        if(e.getSource() == regBolig){
            if(valgtType.equals(TYPE[0]))
                JOptionPane.showMessageDialog(null, "Feil - du må velge type");
            else if(valgtType.equals(TYPE[1]))
                regBolig(TYPE[1]);
            else
                regBolig(TYPE[2]);
        } else if(e.getSource() == slettBolig){
            
        } else if(e.getSource() == boligtype){
            // drop-down box
            
            BorderLayout layout = (BorderLayout)top.getLayout();

            if(valgtType.equals(TYPE[0])){
                if( layout.getLayoutComponent(BorderLayout.CENTER) != null)
                    top.remove(layout.getLayoutComponent(BorderLayout.CENTER));
            } else if(valgtType.equals(TYPE[1])){
                if( layout.getLayoutComponent(BorderLayout.CENTER) != null)
                    top.remove(layout.getLayoutComponent(BorderLayout.CENTER));
                top.add(eneboligPanel, BorderLayout.CENTER);
            } else if(valgtType.equals(TYPE[2])){
                if( layout.getLayoutComponent(BorderLayout.CENTER) != null)
                    top.remove(layout.getLayoutComponent(BorderLayout.CENTER));
                top.add(leilighetPanel, BorderLayout.CENTER);
            }
            // refresh vinduet
            this.getContentPane().revalidate();
            this.getContentPane().repaint();
        }
    }
}