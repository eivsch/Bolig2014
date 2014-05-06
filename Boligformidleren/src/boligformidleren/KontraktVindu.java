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

        // åpner fil når konstruktør kjøres
        lesKontraktFraFil();
        sjekkOmKontraktErUtloept();
    }

    // get-metode
    public KontraktListe getKontraktListe() {
        return kontraktListe;
    }

    public void regKontrakt() {
        // Kontrollerer tallverdier for å unngå parseException
        JTextField[] testRegExTall = {postnr, pris};
        if (!(StartVindu.kontrollerRegEx(StartVindu.patternHeltall, testRegExTall))) {
            output.setText("Feil ved innelsning av tallverdier. Bruk kun heltall");
            return;
        }
        // Gir melding til bruker om han/hun har glemt å fylle noen felter.
        JTextField[] testTomme = {gateadresse, postnr, poststed, utleierFornavn,
            utleierEtternavn, leietakerFornavn, leietakerEtternavn, pris};
        if (StartVindu.tekstFeltErTomt(testTomme)) {
            output.setText("Vennligst fyll inn alle felter!");
            return;
        }
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
        if (sluttDato == null || startDato == null) {
            output.setText("Feil ved innlesing av dato. Kotroller format (dd.mm.åååå)");
            return;
        }

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

        // Sjekker om boligsøkeren har registrert en gjeldende kontrakt.
        if (kontraktListe.finnGjeldendeKontrakt(bs) != null) {
            output.setText("Kunden har allerede inngått en kontrakt");
            return;
        }
        /*Kontrakt[] kontrakter = kontraktListe.finnGjeldendeKontrakter(bs);
         if (kontrakter != null) {
         for (int i = 0; i < kontrakter.length; i++) {
         if (startDato.before(kontrakter[i].getSluttDato())) {
         output.setText("Kunden har allerede registrert en kontrakt innenfor gitt tidsrom!");
         return;
         }
         }
         }*/
        Kontrakt k = new Kontrakt(b, u, bs, leiepris, startDato, sluttDato);

        kontraktListe.settInn(k);

        b.boligErOpptatt();
        bs.leterIkkeEtterBolig();

        output.setText("Kontrakt registrert:\n" + k.toString());
    }

    public void siOppKontrakt() {
        // Forandrer sluttdato i kontrakten tilsvarende oppsigelsestid.
    }

    public void sjekkOmKontraktErUtloept() {
        /**
         * Skal sjekke alle kontrakter i registeret, dersom de er utløpt skal
         * gjeldende bolig settes til "ledig".
         */
        Date idag;
        String s, melding = "Følgende kontrakter er utløpt og ble fjernet:\n";
        try {
            idag = StartVindu.datoFormat.parse(StartVindu.datoFormat.format(
                    new Date()));
        } catch (ParseException pe) {
            StartVindu.visFeilmelding(pe);
            return;
        }
        do {
            s = kontraktListe.sjekkUtloepteOgArkiver(idag);
            if (s != null) {
                melding += s + "\n";
            }
        } while (s != null);

        output.setText(melding);
    }

    public void utskrift() {
        output.setText(kontraktListe.toString());
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
    
    public void focusGained(FocusEvent fe){
        
        if(fe.getSource() == sluttDatoFelt)
            if(sluttDatoFelt.getText().equals("dd.mm.åååå"))
                sluttDatoFelt.setText("");
        // fungerer ikke hvis vi bruker "else-if" for flere felter
        if(fe.getSource() == startDatoFelt)
            if(startDatoFelt.getText().equals("dd.mm.åååå"))
                startDatoFelt.setText("");
    }

    public void focusLost(FocusEvent fe) {
        
        if(fe.getSource() == sluttDatoFelt)
            if(sluttDatoFelt.getText().equals(""))
                sluttDatoFelt.setText("dd.mm.åååå");
        // fungerer ikke hvis vi bruker "else-if" for flere felter
        if(fe.getSource() == startDatoFelt)
            if(startDatoFelt.getText().equals(""))
                startDatoFelt.setText("dd.mm.åååå");
    }
}
