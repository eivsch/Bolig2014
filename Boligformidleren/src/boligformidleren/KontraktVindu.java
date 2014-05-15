/*
 * Innhold: 
 * Vindu som brukes for registrering, slettning av kontrakter.
 * Sist oppdatert:
 * Programmert av: Eivind, Gretar
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
import java.text.ParseException;
import java.util.Date;

public class KontraktVindu extends JFrame implements ActionListener, FocusListener {

    private JTextField gateadresse, postnr, poststed, utleierFornavn, utleierEtternavn,
            leietakerFornavn, leietakerEtternavn, pris, sluttDatoFelt, startDatoFelt;
    private JTextArea output;
    private JButton regKontrakt, siOppKontrakt, skrivUt;
    private int antRad, antKol, gap;
    private JPanel masterPanel, grid, under;

    private KontraktListe kontraktListe;

    // konstruktør
    public KontraktVindu() {
        super("Kontrakt");

        kontraktListe = new KontraktListe();

        // antall rader, antall kolonner og gap størrelse for GridLayout
        antRad = 12;
        antKol = 2;
        gap = 5;

        masterPanel = new JPanel(new BorderLayout());
        grid = new JPanel(new GridLayout(antRad, antKol, gap, gap));
        under = new JPanel(new BorderLayout());

        masterPanel.add(grid, BorderLayout.PAGE_START);
        masterPanel.add(under, BorderLayout.CENTER);

        this.getContentPane().add(masterPanel);
        setSize(300, 600);

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

        grid.add(new JLabel("Startdato (dd.mm.åååå)"));
        startDatoFelt = new JTextField(10);
        startDatoFelt.setText("dd.mm.åååå");
        startDatoFelt.addFocusListener(this);
        grid.add(startDatoFelt);

        grid.add(new JLabel("Sluttdato (dd.mm.åååå): "));
        sluttDatoFelt = new JTextField(10);
        sluttDatoFelt.setText("dd.mm.åååå");
        sluttDatoFelt.addFocusListener(this);
        grid.add(sluttDatoFelt);

        // buttons
        regKontrakt = new JButton("Register kontrakt");
        regKontrakt.addActionListener(this);
        grid.add(regKontrakt);

        siOppKontrakt = new JButton("Oppsigelse");
        siOppKontrakt.addActionListener(this);
        grid.add(siOppKontrakt);

        skrivUt = new JButton("Vis alle kontrakter");
        skrivUt.addActionListener(this);
        grid.add(skrivUt);

        // leser fil når konstruktør kjøres
        lesKontraktFraFil();
        sjekkOmKontraktErUtloept();
    }

    // get-metode for kontraktListe objektet.
    public KontraktListe getKontraktListe() {
        return kontraktListe;
    }

    // Metode for registrering av kontrakt.
    public void regKontrakt() {
        
        // regex
        if(!regexOK()){
            return;
        }
        
        // henter inn brukerinput
        String adr = gateadresse.getText();
        int pnr = Integer.parseInt(postnr.getText());
        String psted = poststed.getText();
        String uFornavn = utleierFornavn.getText();
        String uEtternavn = utleierEtternavn.getText();
        String lFornavn = leietakerFornavn.getText();
        String lEtternavn = leietakerEtternavn.getText();
        int leiepris = Integer.parseInt(pris.getText());
        Date startDato = StartVindu.konverterDato(startDatoFelt.getText()),
                sluttDato = StartVindu.konverterDato(sluttDatoFelt.getText());
        // Sjekker om bruker har skrevet dato på riktig format
        if (sluttDato == null || startDato == null) {
            output.setText("Feil ved innlesing av dato. Kotroller format (dd.mm.åååå)");
            return;
        }
        // sjekker om startdato mot formodning skulle være etter sluttdato
        if (startDato.after(sluttDato)) {
            output.setText("Startdato må være før sluttdato!");
            return;
        }

        //Opretter objekter som trengs for å registrere en ny kontrakt
        Bolig b = StartVindu.getUtleierVindu().getUtleierMengde().finnBolig(adr, pnr, psted);
        Utleier u = StartVindu.getUtleierVindu().getUtleierMengde().finnUtleier(uFornavn, uEtternavn);
        Boligsoeker bs = StartVindu.getBoligsoekerVindu().getBoligsoekerMengde().finnBoligsoeker(lFornavn, lEtternavn);

        if (b == null) {
            output.setText("Feil - finner ikke bolig");
            return;
        } else if (!b.getLedig()) {
            output.setText("Feil - bolig er opptatt");
            return;
        } else if (u == null) {
            output.setText("Feil - finner ikke utleier");
            return;
        } else if (bs == null) {
            output.setText("Feil - finner ikke leietaker");
            return;
        }

        // Sjekker om boligsøkeren alt har registrert en gjeldende kontrakt.
        if (kontraktListe.finnGjeldendeKontrakt(bs) != null) {
            output.setText("Boligsøkeren har allerede inngått en kontrakt");
            return;
        }
        // Oppretter kontrakt og setter inn i liste
        Kontrakt k = new Kontrakt(b, u, bs, leiepris, startDato, sluttDato);
        kontraktListe.settInn(k);

        // Endrer statusfelter for boligen og boligsøkeren
        b.boligErOpptatt();
        bs.leterIkkeEtterBolig();

        blankFelter();
        output.setText("Kontrakt registrert:\n" + k.toString());
        output.setCaretPosition(0);
    }

    // sjekker regular expression på alle feltene
    public boolean regexOK(){
        // Kontrollerer alle felt ved RegEx for å unngå exceptions (f.eks parseException, NullPointerException, osv.)
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
                output.setText("Feil - du må kun bruke heltall\n(4 sifre) i postnummer");
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
                output.setText("Feil - du må kun bruke bokstaver\n(min. 2 tegn) i poststed");
                return false;
            }
        }
        // utleier fornavn
        if(utleierFornavn.getText().equals("")){
            output.setText("Feil - du må fylle inn utleierens fornavn");
            return false;
        }
        else{
            if(!(StartVindu.kontrollerRegEx(StartVindu.PATTERNBOKSTAV, utleierFornavn.getText()))){
                output.setText("Feil - du må kun bruke bokstaver\n(min. 2 tegn) i utleierens fornavn");
                return false;
            }
        }
        // utleier etternavn
        if(utleierEtternavn.getText().equals("")){
            output.setText("Feil - du må fylle inn utleierens etternavn");
            return false;
        }
        else{
            if(!(StartVindu.kontrollerRegEx(StartVindu.PATTERNBOKSTAV, utleierEtternavn.getText()))){
                output.setText("Feil - du må kun bruke bokstaver\n(min. 2 tegn) i utleierens etternavn");
                return false;
            }
        }
        // leietaker fornavn
        if(leietakerFornavn.getText().equals("")){
            output.setText("Feil - du må fylle inn leietakerens fornavn");
            return false;
        }
        else{
            if(!(StartVindu.kontrollerRegEx(StartVindu.PATTERNBOKSTAV, leietakerFornavn.getText()))){
                output.setText("Feil - du må kun bruke norske bokstaver (min. 2 tegn) i leietakerens fornavn");
                return false;
            }
        }
        // leietaker etternavn
        if(leietakerEtternavn.getText().equals("")){
            output.setText("Feil - du må fylle inn leietakerens etternavn");
            return false;
        }
        else{
            if(!(StartVindu.kontrollerRegEx(StartVindu.PATTERNBOKSTAV, leietakerEtternavn.getText()))){
                output.setText("Feil - du må kun bruke bokstaver\n(min. 2 tegn) i leietakerens etternavn");
                return false;
            }
        }
        // pris
        if(pris.getText().equals("")){
        output.setText("Feil - du må fylle inn pris");
            return false;
        }
        else{
            if(!(StartVindu.kontrollerRegEx(StartVindu.PATTERNHELTALL, pris.getText()))){
                output.setText("Feil - pris må kun inneholde heltall");
                return false;
            }
        }
        // startdato
        if(startDatoFelt.getText().equals(StartVindu.DATOFORMAT)){
            output.setText("Feil - du må fylle inn startdato");
            return false;
        }
        else{
            if(StartVindu.konverterDato(startDatoFelt.getText()) == null){
                output.setText("Feil - dato må være på format\n('" + StartVindu.DATOFORMAT + "').");
                return false;
            }
        }
        // slutdato
        if(sluttDatoFelt.getText().equals(StartVindu.DATOFORMAT)){
            output.setText("Feil - du må fylle inn sluttdato");
            return false;
        }
        else{
            if(StartVindu.konverterDato(sluttDatoFelt.getText()) == null){
                output.setText("Feil - dato må være på format\n('" + StartVindu.DATOFORMAT + "').");
                return false;
            }
        }
        
        
        
        
        return true;
    }
    
    public void siOppKontrakt() {
        // Forandrer sluttdato i kontrakten tilsvarende oppsigelsestid.
    }

    /**
     * Skal sjekke alle kontrakter i registeret, dersom de er utløpt skal
     * gjeldende bolig settes til "ledig", og gjeldende boligsøker settes til
     * "leter etter bolig". Dette gjøres hver gang programmet starter, det er
     * derfor viktig at programmet avsluttes ordentlig minst en gang hver dag.
     */
    public void sjekkOmKontraktErUtloept() {
        Date idag;
        String s, melding = "Følgende kontrakter er utløpt og ble fjernet:\n";
        // Henter og formatterer dagens dato.
        try {
            idag = StartVindu.ENKELDATOFORMAT.parse(StartVindu.ENKELDATOFORMAT.format(
                    new Date()));
        } catch (ParseException pe) {
            StartVindu.visFeilmelding(pe);
            return;
        }
        // Sjekker hvilke kontrakter som er utløpt og skriver ut deres toString.
        do {
            s = kontraktListe.sjekkUtloepteOgArkiver(idag);
            if (s != null) {
                melding += s + "\n";
            }
        } while (s != null);

        output.setText(melding);
    }
    
    // Skrive ut alt som ligger lagret i KontraktListe-objektet
    public void utskrift() {
        output.setText(kontraktListe.toString());
    }

    // blanker alle felter i vinduet
    public void blankFelter() {
        gateadresse.setText("");
        postnr.setText("");
        poststed.setText("");
        utleierFornavn.setText("");
        utleierEtternavn.setText("");
        leietakerFornavn.setText("");
        leietakerEtternavn.setText("");
        pris.setText("");
        sluttDatoFelt.setText("dd.mm.åååå");
        startDatoFelt.setText("dd.mm.åååå");
        output.setText("");
    }

    // filbehandling
    public void skrivKontraktTilFil() {
        try (ObjectOutputStream utfil = new ObjectOutputStream(
                new FileOutputStream("kontraktliste.data"))) {
            utfil.writeObject(kontraktListe);
        } catch (NotSerializableException nse) {
            StartVindu.visFeilmelding(nse);
        } catch (IOException ioe) {
            StartVindu.visFeilmelding(ioe);
        }
    }

    public void lesKontraktFraFil() {
        try (ObjectInputStream innfil = new ObjectInputStream(new FileInputStream("kontraktliste.data"))) {
            kontraktListe = (KontraktListe) innfil.readObject();
        } catch (ClassNotFoundException cnfe) {
            StartVindu.visFeilmelding(cnfe);
        } catch (FileNotFoundException fnfe) {
            StartVindu.visFeilmelding(fnfe);
        } catch (IOException ioe) {
            StartVindu.visFeilmelding(ioe);
        }
    }

    // Lyttemetode
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == regKontrakt) {
            regKontrakt();
        } else if (e.getSource() == skrivUt) {
            utskrift();
        } else if (e.getSource() == siOppKontrakt) {
            siOppKontrakt();
        }
    }

    public void focusGained(FocusEvent fe) {

        if (fe.getSource() == sluttDatoFelt) {
            if (sluttDatoFelt.getText().equals("dd.mm.åååå")) {
                sluttDatoFelt.setText("");
            }
        }
        // fungerer ikke hvis vi bruker "else-if" for flere felter
        if (fe.getSource() == startDatoFelt) {
            if (startDatoFelt.getText().equals("dd.mm.åååå")) {
                startDatoFelt.setText("");
            }
        }
    }

    public void focusLost(FocusEvent fe) {

        if (fe.getSource() == sluttDatoFelt) {
            if (sluttDatoFelt.getText().equals("")) {
                sluttDatoFelt.setText("dd.mm.åååå");
            }
        }
        // fungerer ikke hvis vi bruker "else-if" for flere felter
        if (fe.getSource() == startDatoFelt) {
            if (startDatoFelt.getText().equals("")) {
                startDatoFelt.setText("dd.mm.åååå");
            }
        }
    }
}
