/*
 * Innhold: Vindu som brukes for registrering og slettning av boligsøkere
 * Sist oppdatert: 29.04.2014 kl.14:45
 * Programmert av: Gretar
 */
package boligformidleren;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class BoligsoekerVindu extends JFrame implements ActionListener {
    
    /* Boligsoeker-vinduet er oppbygget sånn at en masterpanel inneholder allt i BorderLayout (vinduet selv).
     * Top-panelen inneholder tre paneler:
     *      øverst er en panel for felles felt, 
     *      i midten er enten enebolig felt eller leilighet felt
     *      nederst er en panel som inneholder knappene
     * Under-panelen er brukt for utskrift-området
     */
    private JPanel masterPanel, top, fellesPanel, knappPanel, eneboligPanel, leilighetPanel, under;
    
    // felt for boligsøkeren
    private JTextField RegPersFornavn, RegPersEtternavn, gateadresse, postnr, poststed, epost, tlf, pInfo;
    
    // felles felt for eneboliger og leiligheter
    private final String[] TYPE = {"Ingen krav", "Enebolig/rekkehus", "Leilighet"};   // boligtype drop-down list
    private JComboBox kravType;
    private JTextField kravAreal, kravRom, kravByggeaar, kravPris, kravAvertertDato;
    
    // felt for eneboliger
    private JTextField kravMaxEtasjer, kravTomtestorrelse;
    private JCheckBox kravKjeller;
    
    // felt for leiligheter    
    private JTextField kravEtasje;
    private JCheckBox kravHeis, kravBalkong;
    
    private JTextArea output;
    
    // knapper
    private JButton regBoligsoeker, slettBoligsoeker, skrivUt;
    
    private BoligsoekerMengde boligsoekerMengde;

    // konstruktør
    public BoligsoekerVindu() {
        super("Boligsøker");
        
        boligsoekerMengde = new BoligsoekerMengde();

        int antRadFelles = 14;  // en rad for hver felles felt plus to for boligtyper
        int antRadKnapp = 2;
        int antRadEnebolig = 3; // en rad for hver enebolig variabel
        int antRadLeilighet = 3; // en rad for hver leilighet variabel
        int antKol = 2;
        int gap = 0;
        
        // paneler
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
        output.setEditable(false);
        JScrollPane scroll = new JScrollPane(output);
        under.add(scroll, BorderLayout.CENTER);

        // felles felt og drop-down list
        fellesPanel.add(new JLabel("Fornavn: "));
        RegPersFornavn = new JTextField(10);
        fellesPanel.add(RegPersFornavn);

        fellesPanel.add(new JLabel("Etternavn: "));
        RegPersEtternavn = new JTextField(10);
        fellesPanel.add(RegPersEtternavn);

        fellesPanel.add(new JLabel("Gateadresse: "));
        gateadresse = new JTextField(10);
        fellesPanel.add(gateadresse);
 
        fellesPanel.add(new JLabel("Postnummer: "));
        postnr = new JTextField(10);
        fellesPanel.add(postnr);
 
        fellesPanel.add(new JLabel("Poststed: "));
        poststed = new JTextField(10);
        fellesPanel.add(poststed);
 
        fellesPanel.add(new JLabel("E-post: "));
        epost = new JTextField(10);
        fellesPanel.add(epost);

        fellesPanel.add(new JLabel("Telefon: "));
        tlf = new JTextField(10);
        fellesPanel.add(tlf);

        fellesPanel.add(new JLabel("Personlige opplysninger: "));
        pInfo = new JTextField(10);
        fellesPanel.add(pInfo);

        fellesPanel.add(new JLabel("Boligtype: "));
        kravType = new JComboBox(TYPE);
        kravType.setSelectedIndex(0);
        kravType.addActionListener(this);
        fellesPanel.add(kravType);

        fellesPanel.add(new JLabel("Min. areal: "));
        kravAreal = new JTextField(10);
        fellesPanel.add(kravAreal);

        fellesPanel.add(new JLabel("Min. rom: "));
        kravRom = new JTextField(10);
        fellesPanel.add(kravRom);

        fellesPanel.add(new JLabel("Min. byggeår: "));
        kravByggeaar = new JTextField(10);
        fellesPanel.add(kravByggeaar);

        fellesPanel.add(new JLabel("Max. pris: "));
        kravPris = new JTextField(10);
        fellesPanel.add(kravPris);
        
        fellesPanel.add(new JLabel("Min. avertert dato: "));
        kravAvertertDato = new JTextField(10);
        kravAvertertDato.setText("dd.mm.åååå");
        fellesPanel.add(kravAvertertDato);
        
        // enebolig felt
        eneboligPanel.add(new JLabel("Max. etasjer: "));
        kravMaxEtasjer = new JTextField(10);
        eneboligPanel.add(kravMaxEtasjer);
 
        eneboligPanel.add(new JLabel("Min. tomtestørrelse: "));
        kravTomtestorrelse = new JTextField(10);
        eneboligPanel.add(kravTomtestorrelse);
 
        eneboligPanel.add(new JLabel("Kjeller: "));
        kravKjeller = new JCheckBox("");
        eneboligPanel.add(kravKjeller);
        
        // leilighet felt
        leilighetPanel.add(new JLabel("Max. etasje: "));
        kravEtasje = new JTextField(10);
        leilighetPanel.add(kravEtasje);
        
        leilighetPanel.add(new JLabel("Heis: "));
        kravHeis = new JCheckBox("");
        leilighetPanel.add(kravHeis);

        leilighetPanel.add(new JLabel("Balkong: "));
        kravBalkong = new JCheckBox("");
        leilighetPanel.add(kravBalkong);

        // buttons
        regBoligsoeker = new JButton("Register boligsøker");
        regBoligsoeker.addActionListener(this);
        knappPanel.add(regBoligsoeker);

        slettBoligsoeker = new JButton("Slett boligsøker");
        slettBoligsoeker.addActionListener(this);
        knappPanel.add(slettBoligsoeker);

        skrivUt = new JButton("Vis alle boligsøkere");
        skrivUt.addActionListener(this);
        knappPanel.add(skrivUt);

        lesBoligsoekerFraFil();
    }// end constructor

    //registrer boligsøker
    public void regBoligsoeker() {

        String fnavn = RegPersFornavn.getText();
        String enavn = RegPersEtternavn.getText();
        Date dato = StartVindu.konverterDato(kravAvertertDato.getText());
        String type = (String)kravType.getSelectedItem();
        
        int areal, rom, byggeaar, pris, maxEtasjer, tomtestorrelse, etasje;
        
        
        areal = kravAreal.getText().equals("") ? 0 : Integer.parseInt(kravAreal.getText());
        rom = kravRom.getText().equals("") ? 0 : Integer.parseInt(kravRom.getText());
        byggeaar = kravByggeaar.getText().equals("") ? 0 : Integer.parseInt(kravByggeaar.getText());
        pris = kravPris.getText().equals("") ? 0 : Integer.parseInt(kravPris.getText());
        maxEtasjer = kravMaxEtasjer.getText().equals("") ? 0 : Integer.parseInt(kravMaxEtasjer.getText());
        tomtestorrelse = kravTomtestorrelse.getText().equals("") ? 0 : Integer.parseInt(kravTomtestorrelse.getText());
        etasje = kravEtasje.getText().equals("") ? 0 : Integer.parseInt(kravEtasje.getText());
            
        

        if (boligsoekerMengde.finnBoligsoeker(fnavn, enavn) != null) {
            output.setText("Feil - Boligsøker allerede registrert!");
            return;
        }
        
        Boligsoeker b = new Boligsoeker( fnavn, enavn, gateadresse.getText(), 
                Integer.parseInt(postnr.getText()), poststed.getText(), epost.getText(),
                Integer.parseInt(tlf.getText()), pInfo.getText(), 
                type, areal, rom, byggeaar, pris, dato, 
                maxEtasjer, tomtestorrelse, kravKjeller.isSelected(), 
                etasje, kravHeis.isSelected(), kravBalkong.isSelected() );

        boligsoekerMengde.settInn(b);
        output.setText("Boligsøker " + fnavn + " " + enavn + " registrert");
        
        blankFelter();
    }
    
    public void blankFelter(){
        RegPersFornavn.setText("");
        RegPersEtternavn.setText("");
        gateadresse.setText("");
        postnr.setText("");
        poststed.setText("");
        epost.setText("");
        tlf.setText("");
        pInfo.setText("");
        kravType.setSelectedIndex(0);
        kravAreal.setText("");
        kravRom.setText("");
        kravByggeaar.setText("");
        kravPris.setText("");
        kravAvertertDato.setText("");
        kravMaxEtasjer.setText("");
        kravTomtestorrelse.setText("");
        kravKjeller.setSelected(false);
        kravEtasje.setText("");
        kravHeis.setSelected(false);
        kravBalkong.setSelected(false);
        
    }
    
    public BoligsoekerMengde getBoligsoekerMengde(){
        return boligsoekerMengde;
    }

    public void slettBoligsoeker() {
        String fornavn = RegPersFornavn.getText();
        String etternavn = RegPersEtternavn.getText();
        Boligsoeker bs = boligsoekerMengde.finnBoligsoeker(fornavn, etternavn);
        if (bs == null) {
            output.setText("Boligsøker " + fornavn + " " + etternavn + " ble ikke funnet, kontroller skrivefeil.");
            return;
        }
        // try-catch ?
        if (boligsoekerMengde.fjern(bs)) {
            output.setText(fornavn + " " + etternavn + " slettet");
        }
    }

    public void skrivBoligsoekerTilFil() {
        try (ObjectOutputStream utfil = new ObjectOutputStream(
                new FileOutputStream("boligsoekermengde.data"))) {
            utfil.writeObject(boligsoekerMengde.kopierMengdeUsortert());
        } catch (NotSerializableException nse) {
            visFeilmelding(nse);
        } catch (IOException e) {
            visFeilmelding(e);
        }
    }

    public void lesBoligsoekerFraFil() {
        Set<Boligsoeker> innlestBoligsoekere = new TreeSet<>();
        try (ObjectInputStream innfil = new ObjectInputStream(
                new FileInputStream("boligsoekermengde.data"))) {
            innlestBoligsoekere = (TreeSet<Boligsoeker>) innfil.readObject();
            Iterator<Boligsoeker> iter = innlestBoligsoekere.iterator();
            while (iter.hasNext()) {
                boligsoekerMengde.settInn(iter.next());
            }
        } catch (ClassNotFoundException cnfe) {
            visFeilmelding(cnfe);
        } catch (FileNotFoundException fnfe) {
            visFeilmelding(fnfe);
        } catch (IOException e) {
            visFeilmelding(e);
        }
    }

    public void visFeilmelding(StackTraceElement[] ste) {
        JOptionPane.showMessageDialog(this, ste);
    }

    public void visFeilmelding(Object o) {
        JOptionPane.showMessageDialog(this, o);
    }

    public void utskrift() {
        /**
         * Få skrevet ut alt som er registrert til et tekstfelt. (Kall på
         * person- mengde, kontraktliste etc .toString)
         */
        output.setText(boligsoekerMengde.toString() + "\n");
    }

    // Lyttemetode
    public void actionPerformed(ActionEvent e) {
        String valgtType = (String) kravType.getSelectedItem();
        
        if (e.getSource() == regBoligsoeker) {
            regBoligsoeker();
        } else if (e.getSource() == skrivUt) {
            utskrift();
        } else if (e.getSource() == slettBoligsoeker) {
            slettBoligsoeker();
        } else if (e.getSource() == kravType ){
            // drop-down box
            
            BorderLayout layout = (BorderLayout) top.getLayout();
            
            if (valgtType.equals(TYPE[0])) {
                if (layout.getLayoutComponent(BorderLayout.CENTER) != null) {
                    top.remove(layout.getLayoutComponent(BorderLayout.CENTER));
                }
            } else if (valgtType.equals(TYPE[1])) {
                if (layout.getLayoutComponent(BorderLayout.CENTER) != null) {
                    top.remove(layout.getLayoutComponent(BorderLayout.CENTER));
                }
                top.add(eneboligPanel, BorderLayout.CENTER);
            } else if (valgtType.equals(TYPE[2])) {
                if (layout.getLayoutComponent(BorderLayout.CENTER) != null) {
                    top.remove(layout.getLayoutComponent(BorderLayout.CENTER));
                }
                top.add(leilighetPanel, BorderLayout.CENTER);
            }
            // refresh vinduet
            this.getContentPane().revalidate();
            this.getContentPane().repaint();
        }
            
    }

}
