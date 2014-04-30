/*
 * Innhold: 
 * Vindu som brukes for registrering, slettning av kontrakter.
 * Sist oppdatert:
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
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class KontraktVindu extends JFrame implements ActionListener{
    
    private JTextField gateadresse, postnr, poststed, utleierFornavn, utleierEtternavn, leietakerFornavn, leietakerEtternavn, pris, sluttDato;
    private JTextArea output;
    private JButton regKontrakt, slettKontrakt, skrivUt;
    private int antRad, antKol, gap;
    private JPanel masterPanel, grid, under;
    
    private KontraktListe kontraktListe;
    
    // konstruktør
    public KontraktVindu(){
        super("Kontrakt");
        
        kontraktListe = new KontraktListe();
        
        // antall rader, antall kolonner og gap størrelse for GridLayout
        antRad = 11;
        antKol = 2;
        gap = 5;
        
        masterPanel = new JPanel(new BorderLayout());
        grid = new JPanel(new GridLayout(antRad, antKol, gap, gap));
        under = new JPanel(new BorderLayout());
        
        masterPanel.add(grid, BorderLayout.PAGE_START);
        masterPanel.add(under, BorderLayout.CENTER);
        
        this.getContentPane().add(masterPanel);
        setSize(300, 500);
        
        output = new JTextArea();
        JScrollPane scroll = new JScrollPane(output);
        under.add(scroll, BorderLayout.CENTER);
        
        // textfields
        grid.add(new JLabel("Gateadresse: "));
        gateadresse = new JTextField(10);
        grid.add(gateadresse);

        grid.add(new JLabel("Postnummer: "));
        postnr = new JTextField(10);
        grid.add(postnr);

        grid.add(new JLabel("Poststed: "));
        poststed = new JTextField(10);
        grid.add(poststed);

        grid.add(new JLabel("Fornavn (utleier): "));
        utleierFornavn = new JTextField(10);
        grid.add(utleierFornavn);

        grid.add(new JLabel("Etternavn (utleier): "));
        utleierEtternavn = new JTextField(10);
        grid.add(utleierEtternavn);
        
        grid.add(new JLabel("Fornavn (leietaker): "));
        leietakerFornavn = new JTextField(10);
        grid.add(leietakerFornavn);

        grid.add(new JLabel("Etternavn (leietaker): "));
        leietakerEtternavn = new JTextField(10);
        grid.add(leietakerEtternavn);

        grid.add(new JLabel("Leiepris: "));
        pris = new JTextField(10);
        grid.add(pris);

        grid.add(new JLabel("Sluttdato (dd.mm.åååå): "));
        sluttDato = new JTextField(10);
        grid.add(sluttDato);

        // buttons
        regKontrakt = new JButton("Register kontrakt");
        regKontrakt.addActionListener(this);
        grid.add(regKontrakt);

        slettKontrakt = new JButton("Slett kontrakt");
        slettKontrakt.addActionListener(this);
        grid.add(slettKontrakt);

        skrivUt = new JButton("Vis alle kontrakter");
        skrivUt.addActionListener(this);
        grid.add(skrivUt);
        
        // åpner fil når konstruktør kjøres
        lesKontraktFraFil();
        
    }
    
    // get-metode
    public KontraktListe getKontraktListe(){
        return kontraktListe;
    }
    
    public void regKontrakt(){
        String adr = gateadresse.getText();
        int pnr = Integer.parseInt(postnr.getText());
        String psted = poststed.getText();
        String uFornavn = utleierFornavn.getText();
        String uEtternavn = utleierEtternavn.getText();
        String lFornavn = leietakerFornavn.getText();
        String lEtternavn = leietakerEtternavn.getText();
        int leiepris = Integer.parseInt(pris.getText());
        String dato = sluttDato.getText();
        
        Bolig b = StartVindu.getUtleierVindu().getUtleierMengde().finnBolig( adr, pnr, psted);
        Utleier u = StartVindu.getUtleierVindu().getUtleierMengde().finnUtleier(uFornavn, uEtternavn);
        Boligsoeker bs = StartVindu.getBoligsoekerVindu().getBoligsoekerMengde().finnBoligsoeker(lFornavn, lEtternavn);
        
        if ( b == null ){
            output.setText("Feil - finner ikke bolig");
            return;
        } else if( u == null ){
            output.setText("Feil - finner ikke utleier");
            return;
        } else if( bs == null ){
            output.setText("Feil - finner ikke leietaker");
            return;
        }
            
        
        Kontrakt k = new Kontrakt( b, u, bs, leiepris, dato);
        
        kontraktListe.settInn(k);
        
        b.boligErOpptatt();
        bs.leterIkkeEtterBolig();
        
        output.setText("Kontrakt registrert:\n" + k.toString());
    }
    
    public void slettKontrakt(){
        //...
    }
    
    public void utskrift(){
        //...
    }
    
    // skrive til fil
    public void skrivKontraktTilFil() {
        // ...
    }
    
    // lese fra fil
    public void lesKontraktFraFil(){
        // ...
    }
    
   
    
    // Lyttemetode
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == regKontrakt){
            regKontrakt();
        } else if(e.getSource() == slettKontrakt){
            slettKontrakt();
        } else if(e.getSource() == skrivUt){
           utskrift();
        }
    }
}
