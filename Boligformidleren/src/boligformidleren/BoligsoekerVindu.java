/*
 * Innhold: Vindu som brukes for registrering, slettning og endring av boligsøkere
 * Sist oppdatert: 06.05.2014 kl.12:00
 * Programmert av: Gretar, Eivind
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

public class BoligsoekerVindu extends JFrame implements ActionListener, FocusListener {

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
    private final String[] KRAVANTSOVEROM = {"Ingen krav", "1", "2", "3", "4", "5", "6", "7", "8"};

    private JComboBox kravType, kravRom;
    private JTextField kravAreal, kravByggeaar, kravPris, kravAvertertDato;

    // felt for eneboliger
    private final String[] KRAVETASJERENEBOLIG = {"Ingen krav", "1", "2", "3", "4", "5"};
    private JComboBox kravMaxEtasjer;
    private JTextField kravTomtestorrelse;
    private JCheckBox kravKjeller;

    // felt for leiligheter   
    private final String[] KRAVETASJELEILIGHET = {"Ingen krav", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private JComboBox kravEtasje;
    private JCheckBox kravHeis, kravBalkong;

    private JTextArea output;

    // knapper
    private JButton endreGateadresse, endrePostnr, endrePoststed, endreEpost, endreTelefonnr, endrePersInfo,
            endreType, endreAreal, endreRom, endreByggeaar, endrePris, endreDato, 
            endreEtasjer, endreTomtestorrelse, endreKjeller,
            endreEtasje, endreHeis, endreBalkong, 
            regBoligsoeker, slettBoligsoeker, blankFelter;

    private BoligsoekerMengde boligsoekerMengde;

    // konstruktør
    public BoligsoekerVindu() {
        super("Boligsøker");

        boligsoekerMengde = new BoligsoekerMengde();

        int antRadFelles = 14;  // en rad for hver felles felt plus to for boligtyper
        int antRadKnapp = 1;
        int antRadEnebolig = 3; // en rad for hver enebolig variabel
        int antRadLeilighet = 3; // en rad for hver leilighet variabel
        int antKol = 3;
        int gap = 5;

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
        setSize(400, 700);

        output = new JTextArea();
        output.setEditable(false);
        JScrollPane scroll = new JScrollPane(output);
        under.add(scroll, BorderLayout.CENTER);

        // felles felt og drop-down list
        fellesPanel.add(new JLabel("Fornavn: "));
        RegPersFornavn = new JTextField(10);
        RegPersFornavn.addFocusListener(this);
        fellesPanel.add(RegPersFornavn);
        fellesPanel.add(new JLabel(""));    // tom felt
        
        fellesPanel.add(new JLabel("Etternavn: "));
        RegPersEtternavn = new JTextField(10);
        RegPersEtternavn.addFocusListener(this);
        fellesPanel.add(RegPersEtternavn);
        fellesPanel.add(new JLabel(""));    // tom felt

        fellesPanel.add(new JLabel("Gateadresse: "));
        gateadresse = new JTextField(10);
        fellesPanel.add(gateadresse);
        
        endreGateadresse = new JButton("Endre");
        endreGateadresse.addActionListener(this);
        fellesPanel.add(endreGateadresse);
        
        fellesPanel.add(new JLabel("Postnummer: "));
        postnr = new JTextField(10);
        fellesPanel.add(postnr);
        
        endrePostnr = new JButton("Endre");
        endrePostnr.addActionListener(this);
        fellesPanel.add(endrePostnr);

        fellesPanel.add(new JLabel("Poststed: "));
        poststed = new JTextField(10);
        fellesPanel.add(poststed);
        
        endrePoststed = new JButton("Endre");
        endrePoststed.addActionListener(this);
        fellesPanel.add(endrePoststed);

        fellesPanel.add(new JLabel("E-post: "));
        epost = new JTextField(10);
        fellesPanel.add(epost);
        
        endreEpost = new JButton("Endre");
        endreEpost.addActionListener(this);
        fellesPanel.add(endreEpost);

        fellesPanel.add(new JLabel("Telefonnummer: "));
        tlf = new JTextField(10);
        fellesPanel.add(tlf);
        
        endreTelefonnr = new JButton("Endre");
        endreTelefonnr.addActionListener(this);
        fellesPanel.add(endreTelefonnr);

        fellesPanel.add(new JLabel("Pers. oppl.: "));
        pInfo = new JTextField(10);
        fellesPanel.add(pInfo);
        
        endrePersInfo = new JButton("Endre");
        endrePersInfo.addActionListener(this);
        fellesPanel.add(endrePersInfo);

        fellesPanel.add(new JLabel("Boligtype: "));
        kravType = new JComboBox(TYPE);
        kravType.setSelectedIndex(0);
        kravType.addActionListener(this);
        fellesPanel.add(kravType);
        
        endreType = new JButton("Endre");
        endreType.addActionListener(this);
        fellesPanel.add(endreType);

        fellesPanel.add(new JLabel("Min. areal: "));
        kravAreal = new JTextField(10);
        fellesPanel.add(kravAreal);
        
        endreAreal = new JButton("Endre");
        endreAreal.addActionListener(this);
        fellesPanel.add(endreAreal);

        fellesPanel.add(new JLabel("Min. soverom: "));
        kravRom = new JComboBox(KRAVANTSOVEROM);
        kravRom.setSelectedIndex(0);
        fellesPanel.add(kravRom);
        
        endreRom = new JButton("Endre");
        endreRom.addActionListener(this);
        fellesPanel.add(endreRom);

        fellesPanel.add(new JLabel("Min. byggeår: "));
        kravByggeaar = new JTextField(10);
        fellesPanel.add(kravByggeaar);
        
        endreByggeaar = new JButton("Endre");
        endreByggeaar.addActionListener(this);
        fellesPanel.add(endreByggeaar);

        fellesPanel.add(new JLabel("Max. pris: "));
        kravPris = new JTextField(10);
        fellesPanel.add(kravPris);
        
        endrePris = new JButton("Endre");
        endrePris.addActionListener(this);
        fellesPanel.add(endrePris);

        fellesPanel.add(new JLabel("Min. avertert dato: "));
        kravAvertertDato = new JTextField(10);
        kravAvertertDato.setText(StartVindu.DATOFORMAT);
        kravAvertertDato.addFocusListener(this);
        fellesPanel.add(kravAvertertDato);
        
        endreDato = new JButton("Endre");
        endreDato.addActionListener(this);
        fellesPanel.add(endreDato);

        // enebolig felt
        eneboligPanel.add(new JLabel("Max. etasjer: "));
        kravMaxEtasjer = new JComboBox(KRAVETASJERENEBOLIG);
        kravMaxEtasjer.setSelectedIndex(0);
        eneboligPanel.add(kravMaxEtasjer);
        
        endreEtasjer = new JButton("Endre");
        endreEtasjer.addActionListener(this);
        eneboligPanel.add(endreEtasjer);

        eneboligPanel.add(new JLabel("Min. tomtestørrelse: "));
        kravTomtestorrelse = new JTextField(10);
        eneboligPanel.add(kravTomtestorrelse);
        
        endreTomtestorrelse = new JButton("Endre");
        endreTomtestorrelse.addActionListener(this);
        eneboligPanel.add(endreTomtestorrelse);

        eneboligPanel.add(new JLabel("Kjeller: "));
        kravKjeller = new JCheckBox("");
        eneboligPanel.add(kravKjeller);
        
        endreKjeller = new JButton("Endre");
        endreKjeller.addActionListener(this);
        eneboligPanel.add(endreKjeller);

        // leilighet felt
        leilighetPanel.add(new JLabel("Max. etasje: "));
        kravEtasje = new JComboBox(KRAVETASJELEILIGHET);
        kravEtasje.setSelectedIndex(0);
        leilighetPanel.add(kravEtasje);
        
        endreEtasje = new JButton("Endre");
        endreEtasje.addActionListener(this);
        leilighetPanel.add(endreEtasje);

        leilighetPanel.add(new JLabel("Heis: "));
        kravHeis = new JCheckBox("");
        leilighetPanel.add(kravHeis);
        
        endreHeis = new JButton("Endre");
        endreHeis.addActionListener(this);
        leilighetPanel.add(endreHeis);

        leilighetPanel.add(new JLabel("Balkong: "));
        kravBalkong = new JCheckBox("");
        leilighetPanel.add(kravBalkong);
        
        endreBalkong = new JButton("Endre");
        endreBalkong.addActionListener(this);
        leilighetPanel.add(endreBalkong);

        // buttons
        regBoligsoeker = new JButton("Register boligsøker");
        regBoligsoeker.addActionListener(this);
        knappPanel.add(regBoligsoeker);

        slettBoligsoeker = new JButton("Slett boligsøker");
        slettBoligsoeker.addActionListener(this);
        knappPanel.add(slettBoligsoeker);

        blankFelter = new JButton("Blank felter");
        blankFelter.addActionListener(this);
        knappPanel.add(blankFelter);

        lesBoligsoekerFraFil();
    }// end constructor

    // get metode
    public BoligsoekerMengde getBoligsoekerMengde() {
        return boligsoekerMengde;
    }
    
    //registrer boligsøker
    public void regBoligsoeker() {

        // regex
        if(!regexOK()){
            return;
        }
        
        String fnavn = RegPersFornavn.getText();
        String enavn = RegPersEtternavn.getText();
        Date dato = null;
        
        String type = (String) kravType.getSelectedItem();
        int postnrSomHeltall, tlfnrSomHeltall, areal, byggeaar, pris, tomtestorrelse;
        int rom = 0;
        if(!((String)kravRom.getSelectedItem()).equals(KRAVANTSOVEROM[0]))
            Integer.parseInt((String) kravRom.getSelectedItem());
        int etasjeKravLeilighet = 0;
        if(!((String)kravEtasje.getSelectedItem()).equals(KRAVETASJERENEBOLIG[0]))
            etasjeKravLeilighet = Integer.parseInt((String) kravEtasje.getSelectedItem());
        int etasjeKravEnebolig = 0;
        if(!((String)kravMaxEtasjer.getSelectedItem()).equals(KRAVETASJELEILIGHET[0]))
            etasjeKravEnebolig = Integer.parseInt((String) kravMaxEtasjer.getSelectedItem());

        dato = StartVindu.konverterDato(kravAvertertDato.getText());
        
        // Setter defaultverdi 0 til felter brukeren ikke fyller inn
        postnrSomHeltall = StartVindu.konverterBlanktFeltTilHeltall(postnr);
        tlfnrSomHeltall = StartVindu.konverterBlanktFeltTilHeltall(tlf);
        areal = StartVindu.konverterBlanktFeltTilHeltall(kravAreal);
        byggeaar = StartVindu.konverterBlanktFeltTilHeltall(kravByggeaar);
        pris = StartVindu.konverterBlanktFeltTilHeltall(kravPris);
        tomtestorrelse = StartVindu.konverterBlanktFeltTilHeltall(kravTomtestorrelse);

        if (boligsoekerMengde.finnBoligsoeker(fnavn, enavn) != null) {
            output.setText("Feil - Boligsøker allerede registrert!");
            return;
        }

        Boligsoeker b = new Boligsoeker(fnavn, enavn, gateadresse.getText(),
                postnrSomHeltall, poststed.getText(), epost.getText(),
                tlfnrSomHeltall, pInfo.getText(),
                type, areal, rom, byggeaar, pris, dato,
                etasjeKravEnebolig, tomtestorrelse, kravKjeller.isSelected(),
                etasjeKravLeilighet, kravHeis.isSelected(), kravBalkong.isSelected());

        boligsoekerMengde.settInn(b);
        blankFelter();
        output.setText("Boligsøker " + fnavn + " " + enavn + " registrert");
    }

    // sletter boligsøkeren hvis brukeren har svart bekreftende på et kontrollspørsmål
    public void slettBoligsoeker() {
        String fornavn = RegPersFornavn.getText();
        String etternavn = RegPersEtternavn.getText();
        Boligsoeker bs = boligsoekerMengde.finnBoligsoeker(fornavn, etternavn);
        if (bs == null) {
            output.setText("Boligsøker " + fornavn + " " + etternavn + " ble ikke funnet, kontroller skrivefeil.");
            return;
        }
        
        // ja/nei
        if( StartVindu.visJaNeiMelding("Vil du slette boligsøkeren?", "Slett boligsøker").equals("Nei"))
                return;

        if (boligsoekerMengde.fjern(bs)) {
            blankFelter();
            output.setText(fornavn + " " + etternavn + " slettet");
        }
    }

    // sjekker regular expression på alle feltene
    public boolean regexOK(){
        // Kontrollerer alle felt ved RegEx for å unngå exceptions (f.eks parseException, NullPointerException, osv.)
        // fornavn
        if(RegPersFornavn.getText().equals("")){
            output.setText("Feil - du må fylle inn fornavn");
            return false;
        }
        else{
            if(!(StartVindu.kontrollerRegEx(StartVindu.PATTERNBOKSTAV, RegPersFornavn.getText()))){
                output.setText("Feil - du må kun bruke bokstaver (min. 2 tegn) i fornavn");
                return false;
            }
        }
        // etternavn
        if(RegPersEtternavn.getText().equals("")){
            output.setText("Feil - du må fylle inn etternavn");
            return false;
        }
        else{
            if(!(StartVindu.kontrollerRegEx(StartVindu.PATTERNBOKSTAV, RegPersEtternavn.getText()))){
                output.setText("Feil - du må kun bruke bokstaver (min. 2 tegn) i etternavn");
                return false;
            }
        }
        // gateadresse
        if(gateadresse.getText().equals("")){
            output.setText("Feil - du må fylle inn gateadresse");
            return false;
        }
        else{
            if(!(StartVindu.kontrollerRegEx(StartVindu.PATTERNTALLBOKSTAV, gateadresse.getText()))){
                output.setText("Feil - du må kun bruke bokstaver (min. 4 tegn)\nog heltall (1-3 sifre) i gateadresse");
                return false;
            }
        }
        // postnummer
        if(postnr.getText().equals("")){
            output.setText("Feil - du må fylle inn postnummer");
            return false;
        }
        else{
            if(!(StartVindu.kontrollerRegEx(StartVindu.PATTERNPOSTNUMMER, postnr.getText()))){
                output.setText("Feil - du må kun bruke heltall (4 sifre) i postnummer");
                return false;
            }
        }
        // poststed
        if(poststed.getText().equals("")){
            output.setText("Feil - du må fylle inn poststed");
            return false;
        }
        else{
            if(!(StartVindu.kontrollerRegEx(StartVindu.PATTERNBOKSTAV, poststed.getText()))){
                output.setText("Feil - du må kun bruke bokstaver (min. 2 tegn) i poststed");
                return false;
            }
        }
        // epost
        if(epost.getText().equals("")){
            output.setText("Feil - du må fylle inn epost");
            return false;
        }
        else{
            if(!(StartVindu.kontrollerRegEx(StartVindu.PATTERNEPOST, epost.getText()))){
                output.setText("Feil - epost må være på format\n'aaa@bbb.ccc' (ikke norsk bokstaver)");
                return false;
            }
        }
        // telefonnummer
        if(tlf.getText().equals("")){
            output.setText("Feil - du må fylle inn telefonnummer");
            return false;
        }
        else{
            if(!(StartVindu.kontrollerRegEx(StartVindu.PATTERNTELEFONNUMMER, tlf.getText()))){
                output.setText("Feil - telefonnummer må inneholde 8 sifre");
                return false;
            }
        }
        // pers. oppl.
        if(!pInfo.getText().equals("")){
            if(!(StartVindu.kontrollerRegEx(StartVindu.PATTERNTALLELLERBOKSTAV, pInfo.getText()))){
                output.setText("Feil - personlig opplysninger må kun\ninneholde norkse bokstaver og/eller heltall");
                return false;
            }
        }
        // min. areal
        if(!kravAreal.getText().equals("")){
            if(!(StartVindu.kontrollerRegEx(StartVindu.PATTERNHELTALL, kravAreal.getText()))){
                output.setText("Feil - areal må kun inneholde heltall");
                return false;
            }
        }
        // min. byggeår
        if(!kravByggeaar.getText().equals("")){
            if(!(StartVindu.kontrollerRegEx(StartVindu.PATTERNHELTALL, kravByggeaar.getText()))){
                output.setText("Feil - byggeår må kun inneholde heltall");
                return false;
            }
        }
        // max. pris
        if(!kravPris.getText().equals("")){
            if(!(StartVindu.kontrollerRegEx(StartVindu.PATTERNHELTALL, kravPris.getText()))){
                output.setText("Feil - pris må kun inneholde heltall");
                return false;
            }
        }
        // min. avertert dato
        if(!kravAvertertDato.getText().equals(StartVindu.DATOFORMAT)){
            if(StartVindu.konverterDato(kravAvertertDato.getText()) == null){
                output.setText("Feil - dato må være på format ('" + StartVindu.DATOFORMAT + "').");
                return false;
            }
        }
        // min. tomtestørrelse
        if(!kravTomtestorrelse.getText().equals("")){
            if(!(StartVindu.kontrollerRegEx(StartVindu.PATTERNHELTALL, kravTomtestorrelse.getText()))){
                output.setText("Feil - tomtestørrelse må kun inneholde heltall");
                return false;
            }
        }
        
        return true;
    }
    
    // metoden endrer en felt for boligsøkeren
    public void endreFelt(String felt, String ny){
        
        Boligsoeker bs = StartVindu.getBoligsoekerVindu().getBoligsoekerMengde().finnBoligsoeker(RegPersFornavn.getText(), RegPersEtternavn.getText());
        
        if(bs == null){
            output.setText("Finner ikke boligsøker");
            return;
        }
        
        String gammel = "";
        
        switch(felt){
            case "Gateadresse":{
                if(ny.equals("")){
                    output.setText("Feil - du må skrive noe i feltet");
                    return;
                }
                
                if(ny.equals(bs.getGateadresse())){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // regex
                if(!StartVindu.kontrollerRegEx(StartVindu.PATTERNTALLBOKSTAV, ny)){
                    output.setText("Feil - du må kun bruke bokstaver (min. 4 tegn)\nog heltall (1-3 sifre) i gateadresse");
                    return;
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre gateadressen?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                gammel = bs.getGateadresse();
                bs.setGateadresse(ny);

                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + bs.getGateadresse());
                
                break;
            }
            case "Postnummer":{
                if(ny.equals("")){
                    output.setText("Feil - du må skrive noe i feltet");
                    return;
                }
                
                if(ny.equals(Integer.toString(bs.getPostnr()))){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // regex
                if(!StartVindu.kontrollerRegEx(StartVindu.PATTERNPOSTNUMMER, ny)){
                    output.setText("Feil - du må kun bruke heltall (4 sifre) i postnummer");
                    return;
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre postnummeret?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                gammel = Integer.toString(bs.getPostnr());
                bs.setPostnr(Integer.parseInt(ny));
                
                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + bs.getPostnr());
                
                break;
            }
            case "Poststed":{
                if(ny.equals("")){
                    output.setText("Feil - du må skrive noe i feltet");
                    return;
                }
                
                if(ny.equals(bs.getPoststed())){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // regex
                if(!StartVindu.kontrollerRegEx(StartVindu.PATTERNBOKSTAV, ny)){
                    output.setText("Feil - du må kun bruke bokstaver (min. 2 tegn) i poststed");
                    return;
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre poststedet?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                gammel = bs.getPoststed();
                bs.setPoststed(ny);
                
                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + bs.getPoststed());
                
                break;
            }
            case "Epost":{
                if(ny.equals("")){
                    output.setText("Feil - du må skrive noe i feltet");
                    return;
                }
                
                if(ny.equals(bs.getEpost())){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // regex
                if(!StartVindu.kontrollerRegEx(StartVindu.PATTERNEPOST, ny)){
                    output.setText("Feil - epost må være på format\n'aaa@bbb.ccc' (ikke norsk bokstaver)");
                    return;
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre eposten?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                gammel = bs.getEpost();
                bs.setEpost(ny);
                
                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + bs.getEpost());
                
                break;
            }
            case "Telefonnummer":{
                if(ny.equals("")){
                    output.setText("Feil - du må skrive noe i feltet");
                    return;
                }
                
                if(ny.equals(Integer.toString(bs.getTelefonnr()))){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // regex
                if(!StartVindu.kontrollerRegEx(StartVindu.PATTERNTELEFONNUMMER, ny)){
                    output.setText("Feil - telefonnummer må inneholde 8 sifre");
                    return;
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre telefonnummeret?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                gammel = Integer.toString(bs.getTelefonnr());
                bs.setTelefonnr(Integer.parseInt(ny));
                
                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + bs.getTelefonnr());
                
                break;
            }
            case "Personelige opplysninger":{
                if(ny.equals(bs.getPersInfo())){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // regex
                if(!ny.equals("")){
                    if(!StartVindu.kontrollerRegEx(StartVindu.PATTERNTALLELLERBOKSTAV, ny)){
                        output.setText("Feil - personlige opplysninger må kun inneholde\nbokstaver og/eller heltall");
                        return;
                    }
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre pers. oppl.?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                gammel = bs.getPersInfo();
                bs.setPersInfo(ny);

                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + bs.getPersInfo());
                
                break;
            }
            case "Boligtype":{
                if(ny.equals(bs.getType())){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre boligtypen?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                gammel = bs.getType();
                bs.setType(ny);

                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + bs.getType());
                
                break;
            }
            case "Areal":{
                if((ny.equals("") && bs.getAreal() == 0)|| ny.equals(Integer.toString(bs.getAreal()))){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // regex
                if(!ny.equals(""))
                    if(!StartVindu.kontrollerRegEx(StartVindu.PATTERNHELTALL, ny)){
                        output.setText("Feil - areal må kun inneholde heltall");
                        return;
                    }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre min. areal?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                gammel = Integer.toString(bs.getAreal());
                
                if(ny.equals(""))
                    bs.setAreal(0);
                else
                    bs.setAreal(Integer.parseInt(ny));

                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + bs.getAreal());
                
                break;
            }
            case "Soverom":{
                if((ny.equals(KRAVANTSOVEROM[0]) && bs.getSoverom() == 0) || (ny.equals(Integer.toString(bs.getSoverom()))) ){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre min. soverom?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                gammel = Integer.toString(bs.getSoverom());
                if(ny.equals(KRAVANTSOVEROM[0]))
                    bs.setSoverom(0);
                else
                    bs.setSoverom(Integer.parseInt(ny));

                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + bs.getSoverom());
                
                break;
            }
            case "Byggeår":{
                if((ny.equals("") && bs.getByggeaar() == 0)|| ny.equals(Integer.toString(bs.getByggeaar()))){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // regex
                if(!ny.equals(""))
                    if(!StartVindu.kontrollerRegEx(StartVindu.PATTERNHELTALL, ny)){
                        output.setText("Feil - byggeår må kun inneholde heltall");
                        return;
                    }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre min. byggeår?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                gammel = Integer.toString(bs.getByggeaar());
                
                if(ny.equals(""))
                    bs.setByggeaar(0);
                else
                    bs.setByggeaar(Integer.parseInt(ny));

                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + bs.getByggeaar());
                
                break;
            }
            case "Leiepris":{
                if((ny.equals("") && bs.getPris() == 0)|| ny.equals(Integer.toString(bs.getPris()))){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // regex
                if(!ny.equals(""))
                    if(!StartVindu.kontrollerRegEx(StartVindu.PATTERNHELTALL, ny)){
                        output.setText("Feil - pris må kun inneholde heltall");
                        return;
                    }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre max. pris?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                gammel = Integer.toString(bs.getPris());
                if(ny.equals(""))
                    bs.setPris(0);
                else
                    bs.setPris(Integer.parseInt(ny));

                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + bs.getPris());
                
                break;
            }
            case "Avertert dato":{
                // regex
                if(!ny.equals(StartVindu.DATOFORMAT)){
                    if(!(StartVindu.kontrollerRegEx(StartVindu.PATTERNDATO, ny))){
                        output.setText("Feil - bruk format '" + StartVindu.DATOFORMAT + "' for dato");
                        return;
                    }
                }
                if(bs.getDato() == null && ny.equals(StartVindu.DATOFORMAT)){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                    
                if(!(ny.equals(StartVindu.DATOFORMAT)))
                    if(bs.getDato() != null && (bs.getDato()).equals(StartVindu.konverterDato(ny))){
                        output.setText(felt + " allerede lik: " + ny);
                        return;
                    }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre min. avertert dato?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                if(bs.getDato()!= null)
                    gammel = StartVindu.ENKELDATOFORMAT.format(bs.getDato());
                else
                    gammel = StartVindu.DATOFORMAT;
                
                bs.setDato(StartVindu.konverterDato(ny));

                String nyDato = "";
                if(bs.getDato() == null)
                    nyDato = StartVindu.DATOFORMAT;
                else
                    nyDato = StartVindu.ENKELDATOFORMAT.format(bs.getDato());
                
                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + nyDato);
                
                break;
            }
            case "Etasjer":{
                if((ny.equals(KRAVETASJERENEBOLIG[0]) && bs.getMaxAntEtasjer() == 0) || (ny.equals(Integer.toString(bs.getMaxAntEtasjer())))){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre max. etasjer?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                gammel = Integer.toString(bs.getMaxAntEtasjer());
                if(ny.equals(KRAVETASJERENEBOLIG[0]))
                    bs.setMaxAntEtasjer(0);
                else
                    bs.setMaxAntEtasjer(Integer.parseInt(ny));

                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + bs.getMaxAntEtasjer());
                
                break;
            }
            case "Tomtestørrelse":{
                if((ny.equals("") && bs.getTomtestorrelse() == 0)|| ny.equals(Integer.toString(bs.getTomtestorrelse()))){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // regex
                if(!ny.equals(""))
                    if(!StartVindu.kontrollerRegEx(StartVindu.PATTERNHELTALL, ny)){
                        output.setText("Feil - tomtestørrelse må kun inneholde heltall");
                        return;
                    }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre min. tomtestørrelse?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                gammel = Integer.toString(bs.getTomtestorrelse());
                
                if(ny.equals(""))
                    bs.setTomtestorrelse(0);
                else
                    bs.setTomtestorrelse(Integer.parseInt(ny));

                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + bs.getTomtestorrelse());
                
                break;
            }
            case "Kjeller":{
                if(ny.equals(Boolean.toString(bs.getKjeller()))){
                    output.setText(felt + " allerede lik: " + (ny.equals("true") ? "ja":"nei"));
                    return;
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre kjeller?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }

                gammel = Boolean.toString(bs.getKjeller());
                bs.setKjeller(Boolean.parseBoolean(ny));

                output.setText(felt + " endret"
                + "\nGammel:\t" + (gammel.equals("true") ? "ja":"nei")
                + "\nNy:\t" + (bs.getKjeller() ? "ja":"nei"));
                
                break;
            }
            case "Etasje":{
                if((ny.equals(KRAVETASJELEILIGHET[0]) && bs.getMaxEtasje() == 0) || (ny.equals(Integer.toString(bs.getMaxEtasje())))){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre max. etasje?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                gammel = Integer.toString(bs.getMaxEtasje());
                if(ny.equals(KRAVETASJELEILIGHET[0]))
                    bs.setMaxEtasje(0);
                else
                    bs.setMaxEtasje(Integer.parseInt(ny));

                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + bs.getMaxEtasje());
                
                break;
            }
            case "Heis":{
                if(ny.equals(Boolean.toString(bs.getHeis()))){
                    output.setText(felt + " allerede lik: " + (ny.equals("true") ? "ja":"nei"));
                    return;
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre heis?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }

                gammel = Boolean.toString(bs.getHeis());
                bs.setHeis(Boolean.parseBoolean(ny));

                output.setText(felt + " endret"
                + "\nGammel:\t" + (gammel.equals("true") ? "ja":"nei")
                + "\nNy:\t" + (bs.getHeis() ? "ja":"nei"));
                
                break;
            }
            case "Balkong":{
                if(ny.equals(Boolean.toString(bs.getBalkong()))){
                    output.setText(felt + " allerede lik: " + (ny.equals("true") ? "ja":"nei"));
                    return;
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre balkong?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }

                gammel = Boolean.toString(bs.getBalkong());
                bs.setBalkong(Boolean.parseBoolean(ny));

                output.setText(felt + " endret"
                + "\nGammel:\t" + (gammel.equals("true") ? "ja":"nei")
                + "\nNy:\t" + (bs.getBalkong() ? "ja":"nei"));
                
                break;
            }
        }
    }
    
    // blanker alle felter i vinduet
    public void blankFelter() {
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
        kravRom.setSelectedIndex(0);
        kravByggeaar.setText("");
        kravPris.setText("");
        kravAvertertDato.setText(StartVindu.DATOFORMAT);
        kravMaxEtasjer.setSelectedIndex(0);
        kravTomtestorrelse.setText("");
        kravKjeller.setSelected(false);
        kravEtasje.setSelectedIndex(0);
        kravHeis.setSelected(false);
        kravBalkong.setSelected(false);
        output.setText("");
    }

    // skriver boligsøkermengden til fil
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

    // Lyttemetode
    public void actionPerformed(ActionEvent e) {
        String valgtType = (String) kravType.getSelectedItem();

        if (e.getSource() == regBoligsoeker) {
            regBoligsoeker();
        } else if (e.getSource() == blankFelter) {
            blankFelter();
        } else if (e.getSource() == slettBoligsoeker) {
            slettBoligsoeker();
        } else if (e.getSource() == endreGateadresse){
            endreFelt("Gateadresse", gateadresse.getText());
        } else if (e.getSource() == endrePostnr){
            endreFelt("Postnummer", postnr.getText());
        } else if (e.getSource() == endrePoststed){
            endreFelt("Poststed", poststed.getText());
        } else if (e.getSource() == endreEpost){
            endreFelt("Epost", epost.getText());
        } else if (e.getSource() == endreTelefonnr){
            endreFelt("Telefonnummer", tlf.getText());
        } else if (e.getSource() == endrePersInfo){
            endreFelt("Personelige opplysninger", pInfo.getText());
        } else if (e.getSource() == endreType){
            endreFelt("Boligtype", valgtType);
        } else if (e.getSource() == endreAreal){
            endreFelt("Areal", kravAreal.getText());
        } else if (e.getSource() == endreRom){
            endreFelt("Soverom", (String)kravRom.getSelectedItem());
        } else if (e.getSource() == endreByggeaar){
            endreFelt("Byggeår", kravByggeaar.getText());
        } else if (e.getSource() == endrePris){
            endreFelt("Leiepris", kravPris.getText());
        } else if (e.getSource() == endreDato){
            endreFelt("Avertert dato", kravAvertertDato.getText());
        } else if (e.getSource() == endreEtasjer){
            endreFelt("Etasjer", (String)kravMaxEtasjer.getSelectedItem());
        } else if (e.getSource() == endreTomtestorrelse){
            endreFelt("Tomtestørrelse", kravTomtestorrelse.getText());
        } else if (e.getSource() == endreKjeller){
            endreFelt("Kjeller", Boolean.toString(kravKjeller.isSelected()));
        } else if (e.getSource() == endreEtasje){
            endreFelt("Etasje", (String)kravEtasje.getSelectedItem());
        } else if (e.getSource() == endreHeis){
            endreFelt("Heis", Boolean.toString(kravHeis.isSelected()));
        } else if (e.getSource() == endreBalkong){
            endreFelt("Balkong", Boolean.toString(kravBalkong.isSelected()));
        } else if (e.getSource() == kravType) {
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
    
    public void focusGained(FocusEvent fe){
        
        if(fe.getSource() == kravAvertertDato)
            if(kravAvertertDato.getText().equals(StartVindu.DATOFORMAT))
                kravAvertertDato.setText("");
        // fungerer ikke hvis vi bruker "else-if" for flere felter
        
    }

    public void focusLost(FocusEvent fe) {
        
        if(fe.getSource() == kravAvertertDato)
            if(kravAvertertDato.getText().equals(""))
                kravAvertertDato.setText(StartVindu.DATOFORMAT);
        // fungerer ikke hvis vi bruker "else-if" for flere felter
        if(fe.getSource() == RegPersFornavn || fe.getSource() == RegPersEtternavn)
            if(!RegPersFornavn.getText().equals("") && !RegPersEtternavn.getText().equals("")){
                Boligsoeker bs = StartVindu.getBoligsoekerVindu().getBoligsoekerMengde().finnBoligsoeker(RegPersFornavn.getText(), RegPersEtternavn.getText());
                
                if(bs != null){
                    gateadresse.setText(bs.getGateadresse());
                    postnr.setText(Integer.toString(bs.getPostnr()));
                    poststed.setText(bs.getPoststed());
                    epost.setText(bs.getEpost());
                    tlf.setText(Integer.toString(bs.getTelefonnr()));
                    pInfo.setText(bs.getPersInfo());
                    kravType.setSelectedItem(bs.getType());
                    kravAreal.setText(Integer.toString(bs.getAreal()));
                    if(bs.getSoverom() == 0)
                        kravRom.setSelectedItem(Integer.toString(0));
                    else
                        kravRom.setSelectedItem(Integer.toString(bs.getSoverom()));
                    kravByggeaar.setText(Integer.toString(bs.getByggeaar()));
                    kravPris.setText(Integer.toString(bs.getPris()));
                    kravAvertertDato.setText(bs.getDato() == null ? StartVindu.DATOFORMAT: StartVindu.ENKELDATOFORMAT.format(bs.getDato()));
                    if(bs.getMaxAntEtasjer() == 0)
                        kravMaxEtasjer.setSelectedItem(Integer.toString(0));
                    else
                        kravMaxEtasjer.setSelectedItem(Integer.toString(bs.getMaxAntEtasjer()));
                    kravTomtestorrelse.setText(Integer.toString(bs.getTomtestorrelse()));
                    kravKjeller.setSelected(bs.getKjeller());
                    if(bs.getMaxEtasje() == 0)
                        kravMaxEtasjer.setSelectedItem(Integer.toString(0));
                    else
                        kravEtasje.setSelectedItem(Integer.toString(bs.getMaxEtasje()));
                    kravHeis.setSelected(bs.getHeis());
                    kravBalkong.setSelected(bs.getBalkong());
                }
            }
    }
}
