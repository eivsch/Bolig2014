/*
 * Innhold: Vindu som brukes for registrering og slettning av utleiere
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
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class UtleierVindu extends JFrame implements ActionListener, FocusListener {

    private JTextField RegPersFornavn, RegPersEtternavn, RegPersGateadr, RegPersPostnr, RegPersPoststed, RegEpost, RegTlf, RegFirma;
    private JTextArea output;
    private JButton regUtleier, slettUtleier, endreGateadresse, endrePostnr, endrePoststed, endreEpost, endreTelefonnr, endreFirma, blankFelter;
    private int antRad, antKol, gap;
    private JPanel masterPanel, grid, under;

    private UtleierMengde utleierMengde;

    // konstruktør
    public UtleierVindu() {
        super("Utleier");

        utleierMengde = new UtleierMengde();

        // antall rader, antall kolonner og gap størrelse for GridLayout
        antRad = 10;
        antKol = 3;
        gap = 5;

        masterPanel = new JPanel(new BorderLayout());
        grid = new JPanel(new GridLayout(antRad, antKol, gap, gap));
        under = new JPanel(new BorderLayout());
        masterPanel.add(grid, BorderLayout.PAGE_START);
        masterPanel.add(under, BorderLayout.CENTER);
        this.getContentPane().add(masterPanel);
        setSize(400, 450);

        output = new JTextArea();
        output.setEditable(false);
        JScrollPane scroll = new JScrollPane(output);
        under.add(scroll, BorderLayout.CENTER);

        // felt og knapper
        grid.add(new JLabel("Fornavn: "));
        RegPersFornavn = new JTextField(10);
        RegPersFornavn.addFocusListener(this);
        grid.add(RegPersFornavn);
        grid.add(new JLabel(""));   // tom felt, ingen knapp for endring av fornavn

        grid.add(new JLabel("Etternavn: "));
        RegPersEtternavn = new JTextField(10);
        RegPersEtternavn.addFocusListener(this);
        grid.add(RegPersEtternavn);
        grid.add(new JLabel(""));   // tom felt, ingen knapp for endring av etternavn

        grid.add(new JLabel("Gateadresse: "));
        RegPersGateadr = new JTextField(10);
        grid.add(RegPersGateadr);
        
        endreGateadresse = new JButton("Endre");
        endreGateadresse.addActionListener(this);
        grid.add(endreGateadresse);
        
        grid.add(new JLabel("Postnummer: "));
        RegPersPostnr = new JTextField(10);
        grid.add(RegPersPostnr);
        
        endrePostnr = new JButton("Endre");
        endrePostnr.addActionListener(this);
        grid.add(endrePostnr);

        grid.add(new JLabel("Poststed: "));
        RegPersPoststed = new JTextField(10);
        grid.add(RegPersPoststed);
        
        endrePoststed = new JButton("Endre");
        endrePoststed.addActionListener(this);
        grid.add(endrePoststed);

        grid.add(new JLabel("E-post: "));
        RegEpost = new JTextField(10);
        grid.add(RegEpost);
        
        endreEpost = new JButton("Endre");
        endreEpost.addActionListener(this);
        grid.add(endreEpost);

        grid.add(new JLabel("Telefonnummer: "));
        RegTlf = new JTextField(10);
        grid.add(RegTlf);
        
        endreTelefonnr = new JButton("Endre");
        endreTelefonnr.addActionListener(this);
        grid.add(endreTelefonnr);

        grid.add(new JLabel("Firma: "));
        RegFirma = new JTextField(10);
        grid.add(RegFirma);
        
        endreFirma = new JButton("Endre");
        endreFirma.addActionListener(this);
        grid.add(endreFirma);

        // tom rad
        grid.add(new JLabel(""));
        grid.add(new JLabel(""));
        grid.add(new JLabel(""));
        
        regUtleier = new JButton("Register utleier");
        regUtleier.addActionListener(this);
        grid.add(regUtleier);

        slettUtleier = new JButton("Slett utleier");
        slettUtleier.addActionListener(this);
        grid.add(slettUtleier);

        blankFelter = new JButton("Blank felter");
        blankFelter.addActionListener(this);
        grid.add(blankFelter);

        lesUtleierFraFil();
    }

    // get metode
    public UtleierMengde getUtleierMengde() {
        return utleierMengde;
    }

    //registrer utleier
    public void regUtleier() {

        // Kontrollerer tallverdier ved RegEx for å unngå parseException
        JTextField[] testRegEx = {RegPersPostnr, RegTlf};
        if (!(StartVindu.kontrollerRegEx(StartVindu.PATTERNHELTALL, testRegEx))) {
            output.setText("Feil ved innelsning av tallverdier. Bruk kun heltall");
            return;
        }
        // Gir melding til bruker om han/hun har glemt å fylle noen felter, unntatt firmafeltet.
        JTextField[] testTomme = {RegPersFornavn, RegPersEtternavn, RegPersGateadr,
            RegPersPostnr, RegPersPoststed, RegEpost, RegTlf};
        if (StartVindu.tekstFeltErTomt(testTomme)) {
            output.setText("Vennligst fyll inn alle felter! (Firma er valgfritt)");
            return;
        }

        String fornavn = RegPersFornavn.getText();
        String etternavn = RegPersEtternavn.getText();

        if (utleierMengde.finnUtleier(fornavn, etternavn) != null) {
            output.setText("Feil - Utleier allerede registrert!");
            return;
        }

        Utleier u = new Utleier(fornavn, etternavn, RegPersGateadr.getText(), 
                Integer.parseInt(RegPersPostnr.getText()), RegPersPoststed.getText(),
                RegEpost.getText(), Integer.parseInt(RegTlf.getText()), RegFirma.getText());

        utleierMengde.settInn(u);
        output.setText("Utleier " + fornavn + " " + etternavn + " registrert");
        blankFelter();
    }
    
    // sletter utleireren hvis brukeren har svart bekreftende på et kontrollspørsmål og utleieren har ikke noen boliger til utleie
    public void slettUtleier() {
        String fornavn = RegPersFornavn.getText();
        String etternavn = RegPersEtternavn.getText();
        Utleier ul = utleierMengde.finnUtleier(fornavn, etternavn);
        
        if (ul == null) {
            output.setText("Utleier " + fornavn + " " + etternavn + " ble ikke funnet");
            return;
        }
        
        if(StartVindu.visJaNeiMelding("Vil du slette utleieren?", "Slett utleier").equals("Nei")){
            return;
        }
        
        if (!utleierMengde.fjern(ul)){
            output.setText("Feil - utleier har boliger til utleie");
            return;
        }
        
        output.setText("Utleier " + fornavn + " " + etternavn + " slettet");
        blankFelter();
    }

    // blanker alle feltene
    public void blankFelter() {
        RegPersFornavn.setText("");
        RegPersEtternavn.setText("");
        RegPersGateadr.setText("");
        RegPersPostnr.setText("");
        RegPersPoststed.setText("");
        RegEpost.setText("");
        RegTlf.setText("");
        RegFirma.setText("");
        output.setText("");
    }

    // metoden endrer en felt for utleieren
    public void endreFelt(String felt, String ny){
        if(ny.equals("")){
            output.setText("Feil - du må skrive noe i feltet");
            return;
        }
        
        Utleier ul = StartVindu.getUtleierVindu().getUtleierMengde().finnUtleier(RegPersFornavn.getText(), RegPersEtternavn.getText());
        
        if(ul == null){
            output.setText("Finner ikke utleier");
            return;
        }
        
        String gammel = "";
        
        switch(felt){
            case "Gateadresse":{ 
                if(ny.equals(ul.getGateadresse())){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // ja-nei 
                if(StartVindu.visJaNeiMelding( "Vil du endre gateadressen?", "Endring av data").equals("Nei"))
                    return;
                
                gammel = ul.getGateadresse();
                ul.setGateadresse(ny);

                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + ul.getGateadresse());
                
                break;
            }
            case "Postnummer":{
                if(ny.equals(Integer.toString(ul.getPostnr()))){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre postnummeret?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                gammel = Integer.toString(ul.getPostnr());
                ul.setPostnr(Integer.parseInt(ny));
                
                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + ul.getPostnr());
                
                break;
            }
            case "Poststed":{
                if(ny.equals(ul.getPoststed())){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // ja-nei 
                if(StartVindu.visJaNeiMelding( "Vil du endre poststedet?", "Endring av data").equals("Nei"))
                    return;
                
                gammel = ul.getPoststed();
                ul.setPoststed(ny);
                
                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + ul.getPoststed());
                
                break;
            }
            case "Epost":{
                if(ny.equals(ul.getEpost())){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // ja-nei 
                if(StartVindu.visJaNeiMelding( "Vil du endre eposten?", "Endring av data").equals("Nei"))
                    return;
                
                gammel = ul.getEpost();
                ul.setEpost(ny);
                
                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + ul.getEpost());
                
                break;
            }
            case "Telefonnummer":{
                if(ny.equals(Integer.toString(ul.getTelefonnr()))){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // ja-nei 
                if(StartVindu.visJaNeiMelding( "Vil du endre telefonnummeret?", "Endring av data").equals("Nei"))
                    return;
                
                gammel = Integer.toString(ul.getTelefonnr());
                ul.setTelefonnr(Integer.parseInt(ny));
                
                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + ul.getTelefonnr());
                
                break;
            }
            case "Firma":{
                if(ny.equals(ul.getFirma())){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // ja-nei 
                if(StartVindu.visJaNeiMelding( "Vil du endre firmaet?", "Endring av data").equals("Nei"))
                    return;
                
                gammel = ul.getFirma();
                ul.setFirma(ny);

                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + ul.getFirma());
                
                break;
            }
        }
    }

    public void skrivUtleierTilFil() {
        try (ObjectOutputStream utfil = new ObjectOutputStream(
                new FileOutputStream("utleiermengde.data"))) {
            utfil.writeObject(utleierMengde.kopierMengdeUsortert());
        } catch (NotSerializableException nse) {
            StartVindu.visFeilmelding(nse);
        } catch (IOException e) {
            StartVindu.visFeilmelding(e);
        }
    }

    public void lesUtleierFraFil() {
        Set<Utleier> innlestUtleiere = new TreeSet<>();
        try (ObjectInputStream innfil = new ObjectInputStream(
                new FileInputStream("utleiermengde.data"))) {
            innlestUtleiere = (TreeSet<Utleier>) innfil.readObject();
            Iterator<Utleier> iter = innlestUtleiere.iterator();
            while (iter.hasNext()) {
                utleierMengde.settInn(iter.next());
            }
        } catch (ClassNotFoundException cnfe) {
            StartVindu.visFeilmelding(cnfe);
        } catch (FileNotFoundException fnfe) {
            StartVindu.visFeilmelding(fnfe);
        } catch (IOException e) {
            StartVindu.visFeilmelding(e);
        }
    }

    // Lyttemetode
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == regUtleier) {
            regUtleier();
        } else if (e.getSource() == blankFelter) {
            blankFelter();
        } else if (e.getSource() == slettUtleier) {
            slettUtleier();
        } else if (e.getSource() == endreGateadresse){
            endreFelt("Gateadresse", RegPersGateadr.getText());
        } else if (e.getSource() == endrePostnr){
            endreFelt("Postnummer", RegPersPostnr.getText());
        } else if (e.getSource() == endrePoststed){
            endreFelt("Poststed", RegPersPoststed.getText());
        } else if (e.getSource() == endreEpost){
            endreFelt("Epost", RegEpost.getText());
        } else if (e.getSource() == endreTelefonnr){
            endreFelt("Telefonnummer", RegTlf.getText());
        } else if (e.getSource() == endreFirma){
            endreFelt("Firma", RegFirma.getText());
        }
    }
    
    public void focusGained(FocusEvent fe){
        
        // fungerer ikke hvis vi bruker "else-if" for flere felter
        
    }
    
    public void focusLost(FocusEvent fe) {
        
        if(fe.getSource() == RegPersFornavn || fe.getSource() == RegPersEtternavn)
            if(!RegPersFornavn.getText().equals("") && !RegPersEtternavn.getText().equals("")){
                Utleier ul = StartVindu.getUtleierVindu().getUtleierMengde().finnUtleier(RegPersFornavn.getText(), RegPersEtternavn.getText());
                
                if(ul != null){
                    RegPersGateadr.setText(ul.getGateadresse());
                    RegPersPostnr.setText(Integer.toString(ul.getPostnr()));
                    RegPersPoststed.setText(ul.getPoststed());
                    RegEpost.setText(ul.getEpost());
                    RegTlf.setText(Integer.toString(ul.getTelefonnr()));
                    RegFirma.setText(ul.getFirma());
                }
            }
        // fungerer ikke hvis vi bruker "else-if" for flere felter
    }

}
