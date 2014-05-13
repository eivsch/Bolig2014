/*
 * Innhold: Vindu som brukes for å hente informasjon om personer, boliger og kontrakter
 * Sist oppdatert: 13.05.2014 kl.12:15
 * Programmert av: Gretar, Sigurd
 */
package boligformidleren;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Iterator;
import javax.swing.table.TableColumn;

public class InformasjonVindu extends JFrame implements ActionListener, FocusListener {

    /* Informasjons-vinduet er i to deler, øvre og nedre.
     * Øvre delen inneholder top-panelen (GridLayout).
     * Nedre delen inneholder under-panelen for utskrift-området.
     * Top-panelen er delt opp i fire kolonner:
     *      #1 er en panel for person-informasjon, 
     *      #2 er en panel for bolig-informasjon for EN bolig
     *      #3 er en panel for bolig-informasjon ut fra parametre
     *      #4 er en panel for kontrakt-informasjon
     */
    private JPanel masterPanel, top, under, topLabels, topContent,
            personPanel, venstreBoligPanel, hoyreBoligPanel,
            boligFellesPanel, boligFellesTypePanel, boligFellesMinMaxPanel,
            boligEneboligPanel, boligLeilighetPanel, boligKnappPanel,
            kontraktPanel, kontraktFellesPanel, kontraktFellesTypePanel,
            kontraktFellesMinMaxPanel, kontraktEneboligPanel, kontraktLeilighetPanel,
            kontraktKnappPanel, tabellPanel;

    // person panel
    private JTextField fornavn, etternavn;
    private JButton hentInfoPerson, visAlleBoligsoekere, visAlleUtleiere,
            gaaTilBoligsoeker, gaaTilUtleier;

    // bolig panel
    private JTextField gateadresse, postnr, poststed;
    private JButton visBoligInfo, gaaTilBolig;

    // bolig type panel
    private JComboBox boligtype;
    private final String[] BOLIGTYPE = {"Ingen krav", "Enebolig/rekkehus", "Leilighet"};

    // bolig min-max panel
    private JTextField minBoligAreal, maxBoligAreal, minBoligRom, maxBoligRom,
            minBoligByggeaar, maxBoligByggeaar, minBoligPris, maxBoligPris,
            minBoligDato, maxBoligDato;

    // bolig enebolig panel
    private JTextField boligMinEtasjer, boligMaxEtasjer, boligMinTomtestorrelse, boligMaxTomtestorrelse;
    private JCheckBox boligKjeller;

    // bolig leilighet panel
    private JTextField boligMinEtasje, boligMaxEtasje;
    private JCheckBox boligHeis, boligBalkong;

    // bolig knapp panel
    private JButton finnBoliger, visAlleBoliger;

    // kontrakt type panel
    private JComboBox kontrakttype;
    private final String[] KONTRAKTTYPE = {"Ingen krav", "Enekontrakt/rekkehus", "Leilighet"};

    // kontrakt min-max panel
    private JTextField minKontraktAreal, maxKontraktAreal, minKontraktRom, maxKontraktRom,
            minKontraktByggeaar, maxKontraktByggeaar, minKontraktPris, maxKontraktPris,
            minKontraktDato, maxKontraktDato;

    // kontrakt enekontrakt panel
    private JTextField kontraktEtasjer, kontraktTomtestorrelse;
    private JCheckBox kontraktKjeller;

    // kontrakt leilighet panel
    private JTextField kontraktEtasje;
    private JCheckBox kontraktHeis, kontraktBalkong;

    // kontrakt knapp panel
    private JButton finnKontrakter, visAlleKontrakter;

    // for JTable
    private JTable boligsoekerTabell, boligTabell, utleierTabell;

    private JTextArea output;

    // konstruktør
    public InformasjonVindu() {
        super("Informasjon");

        // labels
        JLabel labelPerson = new JLabel("<html><center><font size=\"5\">Personer</html>");
        JLabel labelBolig1 = new JLabel("<html><font size=\"5\">Boliger</html>");
        JLabel labelBolig2 = new JLabel("<html><font size=\"5\">Boliger</html>");
        JLabel labelKontrakt = new JLabel("<html><font size=\"5\">Kontrakter</html>");
        Border paddingBorder = BorderFactory.createEmptyBorder(0, 0, 10, 0);
        labelPerson.setBorder(paddingBorder);
        labelBolig1.setBorder(paddingBorder);
        labelBolig2.setBorder(paddingBorder);
        labelKontrakt.setBorder(paddingBorder);
        labelPerson.setHorizontalAlignment(JLabel.CENTER);
        labelBolig1.setHorizontalAlignment(JLabel.CENTER);
        labelBolig2.setHorizontalAlignment(JLabel.CENTER);
        labelKontrakt.setHorizontalAlignment(JLabel.CENTER);

        // antall rader, antall kolonner og gap størrelse for top-panelene (GridLayout)
        int antRadTopPanel = 1;
        int antKolTopPanel = 4;
        int antRadPersonPanel = 8;
        int antRadBoligPanel = 8;
        int antRadTypePanel = 1;
        int antRadMinMaxPanel = 6;
        int antRadEneboligLeilighetPanel = 3;
        int antRadKnappPanel = 2;
        int defaultKolonner = 2;
        int antKnappKolonner = 1;
        int antMinMaxKolonner = 3;
        int gapTop = 20;
        int gap = 0;

        //vindu størrelse (pixler)
        int bredde = 1200;
        int hoyde = 750;

        // paneler
        masterPanel = new JPanel(new BorderLayout());
        top = new JPanel(new BorderLayout());
        topLabels = new JPanel(new GridLayout(antRadTopPanel, antKolTopPanel, gap, gapTop));
        topContent = new JPanel(new GridLayout(antRadTopPanel, antKolTopPanel, gapTop, gap));
        personPanel = new JPanel(new GridLayout(antRadPersonPanel, defaultKolonner, gap, gap));
        venstreBoligPanel = new JPanel(new GridLayout(antRadBoligPanel, defaultKolonner, gap, gap));
        hoyreBoligPanel = new JPanel(new BorderLayout());
        boligFellesPanel = new JPanel(new BorderLayout());
        boligFellesTypePanel = new JPanel(new GridLayout(antRadTypePanel, defaultKolonner, gap, gap));
        boligFellesMinMaxPanel = new JPanel(new GridLayout(antRadMinMaxPanel, antMinMaxKolonner, gap, gap));
        boligEneboligPanel = new JPanel(new GridLayout(antRadEneboligLeilighetPanel, antMinMaxKolonner, gap, gap));
        boligLeilighetPanel = new JPanel(new GridLayout(antRadEneboligLeilighetPanel, antMinMaxKolonner, gap, gap));
        boligKnappPanel = new JPanel(new GridLayout(antRadKnappPanel, antKnappKolonner, gap, gap));
        kontraktPanel = new JPanel(new BorderLayout());
        kontraktFellesPanel = new JPanel(new BorderLayout());
        kontraktFellesTypePanel = new JPanel(new GridLayout(antRadTypePanel, defaultKolonner, gap, gap));
        kontraktFellesMinMaxPanel = new JPanel(new GridLayout(antRadMinMaxPanel, antMinMaxKolonner, gap, gap));
        kontraktEneboligPanel = new JPanel(new GridLayout(antRadEneboligLeilighetPanel, defaultKolonner, gap, gap));
        kontraktLeilighetPanel = new JPanel(new GridLayout(antRadEneboligLeilighetPanel, defaultKolonner, gap, gap));
        kontraktKnappPanel = new JPanel(new GridLayout(antRadKnappPanel, antKnappKolonner, gap, gap));
        under = new JPanel(new BorderLayout());
        tabellPanel = new JPanel(new BorderLayout());

        masterPanel.add(top, BorderLayout.PAGE_START);
        masterPanel.add(under, BorderLayout.CENTER);
        top.add(topLabels, BorderLayout.PAGE_START);
        top.add(topContent, BorderLayout.CENTER);

        topLabels.add(labelPerson);
        topLabels.add(labelBolig1);
        topLabels.add(labelBolig2);
        topLabels.add(labelKontrakt);
        topContent.add(personPanel);
        topContent.add(venstreBoligPanel);
        topContent.add(hoyreBoligPanel);
        topContent.add(kontraktPanel);

        hoyreBoligPanel.add(boligFellesPanel, BorderLayout.PAGE_START);
        boligFellesPanel.add(boligFellesTypePanel, BorderLayout.PAGE_START);
        boligFellesPanel.add(boligFellesMinMaxPanel, BorderLayout.CENTER);
        hoyreBoligPanel.add(boligKnappPanel, BorderLayout.PAGE_END);
        kontraktPanel.add(kontraktFellesPanel, BorderLayout.PAGE_START);
        kontraktFellesPanel.add(kontraktFellesTypePanel, BorderLayout.PAGE_START);
        kontraktFellesPanel.add(kontraktFellesMinMaxPanel, BorderLayout.CENTER);
        kontraktPanel.add(kontraktKnappPanel, BorderLayout.PAGE_END);

        this.getContentPane().add(masterPanel);
        setSize(bredde, hoyde);

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

        visAlleUtleiere = new JButton("Vis alle utleiere");
        visAlleUtleiere.addActionListener(this);
        personPanel.add(visAlleUtleiere);

        gaaTilBoligsoeker = new JButton("Gå til boligsøker");
        gaaTilBoligsoeker.addActionListener(this);

        gaaTilUtleier = new JButton("Gå til utleier");
        gaaTilUtleier.addActionListener(this);

        // venstre bolig panel
        venstreBoligPanel.add(new JLabel("Gateadresse: "));
        gateadresse = new JTextField(10);
        venstreBoligPanel.add(gateadresse);

        venstreBoligPanel.add(new JLabel("Postnummer: "));
        postnr = new JTextField(10);
        venstreBoligPanel.add(postnr);

        venstreBoligPanel.add(new JLabel("Poststed: "));
        poststed = new JTextField(10);
        venstreBoligPanel.add(poststed);

        visBoligInfo = new JButton("Vis bolig info");
        visBoligInfo.addActionListener(this);
        venstreBoligPanel.add(visBoligInfo);

        gaaTilBolig = new JButton("Gå til bolig");
        gaaTilBolig.addActionListener(this);

        // høyre bolig panel
        // bolig felles panel
        boligFellesTypePanel.add(new JLabel("Boligtype: "));
        boligtype = new JComboBox(BOLIGTYPE);
        boligtype.setSelectedIndex(0);
        boligtype.addActionListener(this);
        boligFellesTypePanel.add(boligtype);

        // bolig min-max panel
        boligFellesMinMaxPanel.add(new JLabel(" "));
        boligFellesMinMaxPanel.add(new JLabel("MIN.", SwingConstants.CENTER));
        boligFellesMinMaxPanel.add(new JLabel("MAX.", SwingConstants.CENTER));

        boligFellesMinMaxPanel.add(new JLabel("Areal: "));
        minBoligAreal = new JTextField(10);
        maxBoligAreal = new JTextField(10);
        boligFellesMinMaxPanel.add(minBoligAreal);
        boligFellesMinMaxPanel.add(maxBoligAreal);

        boligFellesMinMaxPanel.add(new JLabel("Soverom: "));
        minBoligRom = new JTextField(10);
        maxBoligRom = new JTextField(10);
        boligFellesMinMaxPanel.add(minBoligRom);
        boligFellesMinMaxPanel.add(maxBoligRom);

        boligFellesMinMaxPanel.add(new JLabel("Byggeår: "));
        minBoligByggeaar = new JTextField(10);
        maxBoligByggeaar = new JTextField(10);
        boligFellesMinMaxPanel.add(minBoligByggeaar);
        boligFellesMinMaxPanel.add(maxBoligByggeaar);

        boligFellesMinMaxPanel.add(new JLabel("Leiepris: "));
        minBoligPris = new JTextField(10);
        maxBoligPris = new JTextField(10);
        boligFellesMinMaxPanel.add(minBoligPris);
        boligFellesMinMaxPanel.add(maxBoligPris);

        boligFellesMinMaxPanel.add(new JLabel("Dato: "));
        minBoligDato = new JTextField(10);
        maxBoligDato = new JTextField(10);
        minBoligDato.addFocusListener(this);
        maxBoligDato.addFocusListener(this);
        minBoligDato.setText(StartVindu.DATOFORMAT);
        maxBoligDato.setText(StartVindu.DATOFORMAT);
        boligFellesMinMaxPanel.add(minBoligDato);
        boligFellesMinMaxPanel.add(maxBoligDato);

        // bolig enebolig panel
        boligEneboligPanel.add(new JLabel("Antall etasjer: "));
        boligMinEtasjer = new JTextField(10);
        boligMaxEtasjer = new JTextField(10);
        boligEneboligPanel.add(boligMinEtasjer);
        boligEneboligPanel.add(boligMaxEtasjer);

        boligEneboligPanel.add(new JLabel("Tomtestørrelse: "));
        boligMinTomtestorrelse = new JTextField(10);
        boligMaxTomtestorrelse = new JTextField(10);
        boligEneboligPanel.add(boligMinTomtestorrelse);
        boligEneboligPanel.add(boligMaxTomtestorrelse);

        boligEneboligPanel.add(new JLabel("Kjeller: "));
        boligKjeller = new JCheckBox("");
        boligEneboligPanel.add(boligKjeller);

        // bolig leilighet panel
        boligLeilighetPanel.add(new JLabel("Etasje: "));
        boligMinEtasje = new JTextField(10);
        boligMaxEtasje = new JTextField(10);
        boligLeilighetPanel.add(boligMinEtasje);
        boligLeilighetPanel.add(boligMaxEtasje);

        boligLeilighetPanel.add(new JLabel("Heis: "));
        boligHeis = new JCheckBox("");
        boligLeilighetPanel.add(boligHeis);

        // empty label så leilighet feltene ser bra ut
        boligLeilighetPanel.add(new JLabel(" "));

        boligLeilighetPanel.add(new JLabel("Balkong: "));
        boligBalkong = new JCheckBox();
        boligLeilighetPanel.add(boligBalkong);

        // bolig knapp panel
        finnBoliger = new JButton("Finn boliger");
        finnBoliger.addActionListener(this);
        boligKnappPanel.add(finnBoliger);

        visAlleBoliger = new JButton("Vis alle boliger");
        visAlleBoliger.addActionListener(this);
        boligKnappPanel.add(visAlleBoliger);

        // kontrakt panel
        // kontrakt felles panel
        kontraktFellesTypePanel.add(new JLabel("Boligtype: "));
        kontrakttype = new JComboBox(KONTRAKTTYPE);
        kontrakttype.setSelectedIndex(0);
        kontrakttype.addActionListener(this);
        kontraktFellesTypePanel.add(kontrakttype);

        // kontrakt min-max panel
        kontraktFellesMinMaxPanel.add(new JLabel(" "));
        kontraktFellesMinMaxPanel.add(new JLabel("MIN.", SwingConstants.CENTER));
        kontraktFellesMinMaxPanel.add(new JLabel("MAX.", SwingConstants.CENTER));

        kontraktFellesMinMaxPanel.add(new JLabel("Areal: "));
        minKontraktAreal = new JTextField(10);
        maxKontraktAreal = new JTextField(10);
        kontraktFellesMinMaxPanel.add(minKontraktAreal);
        kontraktFellesMinMaxPanel.add(maxKontraktAreal);

        kontraktFellesMinMaxPanel.add(new JLabel("Soverom: "));
        minKontraktRom = new JTextField(10);
        maxKontraktRom = new JTextField(10);
        kontraktFellesMinMaxPanel.add(minKontraktRom);
        kontraktFellesMinMaxPanel.add(maxKontraktRom);

        kontraktFellesMinMaxPanel.add(new JLabel("Byggeår: "));
        minKontraktByggeaar = new JTextField(10);
        maxKontraktByggeaar = new JTextField(10);
        kontraktFellesMinMaxPanel.add(minKontraktByggeaar);
        kontraktFellesMinMaxPanel.add(maxKontraktByggeaar);

        kontraktFellesMinMaxPanel.add(new JLabel("Leiepris: "));
        minKontraktPris = new JTextField(10);
        maxKontraktPris = new JTextField(10);
        kontraktFellesMinMaxPanel.add(minKontraktPris);
        kontraktFellesMinMaxPanel.add(maxKontraktPris);

        kontraktFellesMinMaxPanel.add(new JLabel("Dato: "));
        minKontraktDato = new JTextField(10);
        maxKontraktDato = new JTextField(10);
        kontraktFellesMinMaxPanel.add(minKontraktDato);
        kontraktFellesMinMaxPanel.add(maxKontraktDato);

        // kontrakt enebolig panel
        kontraktEneboligPanel.add(new JLabel("Antall etasjer: "));
        kontraktEtasjer = new JTextField(10);
        kontraktEneboligPanel.add(kontraktEtasjer);

        kontraktEneboligPanel.add(new JLabel("Tomtestørrelse: "));
        kontraktTomtestorrelse = new JTextField(10);
        kontraktEneboligPanel.add(kontraktTomtestorrelse);

        kontraktEneboligPanel.add(new JLabel("Kjeller: "));
        kontraktKjeller = new JCheckBox("");
        kontraktEneboligPanel.add(kontraktKjeller);

        // kontrakt leilighet panel
        kontraktLeilighetPanel.add(new JLabel("Etasje: "));
        kontraktEtasje = new JTextField(10);
        kontraktLeilighetPanel.add(kontraktEtasje);

        kontraktLeilighetPanel.add(new JLabel("Heis: "));
        kontraktHeis = new JCheckBox("");
        kontraktLeilighetPanel.add(kontraktHeis);

        kontraktLeilighetPanel.add(new JLabel("Balkong: "));
        kontraktBalkong = new JCheckBox();
        kontraktLeilighetPanel.add(kontraktBalkong);

        // kontrakt knapp panel
        finnKontrakter = new JButton("Finn kontrakter");
        finnKontrakter.addActionListener(this);
        kontraktKnappPanel.add(finnKontrakter);

        visAlleKontrakter = new JButton("Vis alle kontrakter");
        visAlleKontrakter.addActionListener(this);
        kontraktKnappPanel.add(visAlleKontrakter);
    }

    // hent opplysninger om en navngitt person
    public void hentInfoPerson() {
        Utleier ul = StartVindu.getUtleierVindu().getUtleierMengde().finnUtleier(
                fornavn.getText(), etternavn.getText());
        Boligsoeker bs = StartVindu.getBoligsoekerVindu().getBoligsoekerMengde().
                finnBoligsoeker(fornavn.getText(), etternavn.getText());

        // blank felter
        fornavn.setText("");
        etternavn.setText("");

        if (ul != null) {
            output.setText(ul.toString());
            return;
        }
        if (bs != null) {
            // Viser hvilke boliger personen passer til
            int antallboliger = 0;
            String s = " mulige boliger: ";
            Iterator<Utleier> utleierIterator = StartVindu.getUtleierVindu().getUtleierMengde().getSortertMengde().iterator();
            Utleier utleier;
            while (utleierIterator.hasNext()) {
                utleier = utleierIterator.next();
                Iterator<Bolig> boligIterator = utleier.getBoligliste().getListe().iterator();
                Bolig b;
                while (boligIterator.hasNext()) {
                    b = boligIterator.next();
                    if (bs.passerTilBolig(b)) {
                        s += "\n" + b.getGateadresse() + "   " + b.getPoststed() + "   " + b.getPostnr();
                        antallboliger++;
                    }
                }
            }
            output.setText(bs.toString() + "\n" + antallboliger + s);
            return;
        }
        output.setText("Feil - finner ikke person " + fornavn.getText() + " " + etternavn.getText());
    }

    // henter og viser informasjon om en bolig og liste over interesserte boligsøkere
    public void visBoligInfo() {
        String adr = gateadresse.getText();
        String pnr = postnr.getText();
        String psted = poststed.getText();
        JTextField[] pstnr = {postnr};
        JTextField[] adrs = {gateadresse};
        JTextField[] pststed = {poststed};
        
        if (adr.equals("") || pnr.equals("") || psted.equals("")) {
            output.setText("Feil - du må fylle i alle felter over");
            return;

        }

        if (!(StartVindu.kontrollerRegEx(StartVindu.PATTERNHELTALL, pstnr))) {
            output.setText("Feil ved innlesing post nummer. Kun hele tallverdier.");
            return;
        }
        if (!(StartVindu.kontrollerRegEx(StartVindu.PATTERNTALLBOKSTAV, adrs))) {
            output.setText("Feil ved innlesing av adresse. Kun tall og bokstaver.");
            return;
        }
        if (!(StartVindu.kontrollerRegEx(StartVindu.PATTERNBOKSTAV, pststed))) {
            output.setText("Feil ved innlesing av poststed. Kun bokstaver.");
            return;
        }

        // her må komme regex for feltene
        Bolig b = StartVindu.getUtleierVindu().getUtleierMengde().finnBolig(adr, Integer.parseInt(pnr), psted);
        Utleier u = StartVindu.getUtleierVindu().getUtleierMengde().finnUtleierViaBolig(b);
        // Sjekker om boligen finnes, vet dermed også om utleieren finnes/ikke finnes.
        if (b == null) {
            output.setText("Finner ikke boligen");
            return;
        }

        // opprette en liste over interesserte boligsøkere
        Iterator<Boligsoeker> bsIter = StartVindu.getBoligsoekerVindu().getBoligsoekerMengde().getMengde().iterator();

        Boligsoeker bs;
        String interesserte = "";

        while (bsIter.hasNext()) {
            bs = bsIter.next();

            if (bs.getLeterEtterBolig() && bs.passerTilBolig(b)) {
                interesserte += bs.getFornavn() + " " + bs.getEtternavn() + "\n";
            }
        }

        if (interesserte.equals("")) {
            interesserte = "ikke noen";
        }

        // utskrift
        output.setText("Bolig informasjon:\n" + b.toString() + "\nUtleier: " + u.getFornavn() + " " + u.getEtternavn());
        output.append("\n\nBoligen oppfyller kravene hos flg. boligsøkere:\n");
        output.append(interesserte);

        // blank felter
        gateadresse.setText("");
        postnr.setText("");
        poststed.setText("");
    }

    public void tegnBoligtabell() {
        BoligTabellmodell btm = new BoligTabellmodell();
        boligTabell = new JTable(btm);
        boligTabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        boligTabell.setAutoCreateRowSorter(true);
    }

    public void hentBoligFraTabell() {
        BoligTabellmodell btm = new BoligTabellmodell();
        int[] nokkelkolonner = btm.getNokellkolonner();
        JTextField[] nokkelfelt = {gateadresse, poststed, postnr};

        // Setter data fra tabell inn i riktige inputfelter
        int rad = boligTabell.getSelectedRow();
        Object o;
        for (int i = 0; i < nokkelkolonner.length; i++) {
            o = boligTabell.getValueAt(rad, nokkelkolonner[i]);
            nokkelfelt[i].setText(o.toString());
        }
        visBoligInfo();
    }

    public void tegnUtleierTabell() {
        UtleierTabellmodell utm = new UtleierTabellmodell();
        utleierTabell = new JTable(utm);
        utleierTabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        utleierTabell.setAutoCreateRowSorter(true);
        //skjuler gitte kolonner fra display
        String[] skjules = utm.getKolonnerSkalSkjules();
        for (int i = 0; i < skjules.length; i++) {
            TableColumn tc = utleierTabell.getColumn(skjules[i]);
            tc.setMinWidth(0);
            tc.setMaxWidth(0);
            tc.setPreferredWidth(0);
        }
    }

    // Henter data om utleier fra tabell for å vise denne for brukeren. Henter
    // dataene fra kolonnene som skal skjules
    public void hentUtleierFraTabell() {
        UtleierTabellmodell utm = new UtleierTabellmodell();
        int[] nokkelkolonner = utm.getKolonnerSkalSkjulesIndeks();
        JTextField[] navnefelt = {fornavn, etternavn};

        // Setter data fra tabell inn i riktige inputfelter.
        int rad = utleierTabell.getSelectedRow();
        Object o;
        for (int i = 0; i < nokkelkolonner.length; i++) {
            o = utleierTabell.getValueAt(rad, nokkelkolonner[i]);
            navnefelt[i].setText(o.toString());
        }
        hentInfoPerson();
    }

    public void tegnBoligsoekerTabell() {
        BoligsoekerTabellmodell bstm = new BoligsoekerTabellmodell();
        boligsoekerTabell = new JTable(bstm);
        boligsoekerTabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        boligsoekerTabell.setAutoCreateRowSorter(true);
        //skjuler gitte kolonner fra display
        String[] skjules = bstm.getKolonnerSkalSkjules();
        for (int i = 0; i < skjules.length; i++) {
            TableColumn tc = boligsoekerTabell.getColumn(skjules[i]);
            tc.setMinWidth(0);
            tc.setMaxWidth(0);
            tc.setPreferredWidth(0);
        }
    }

    // Henter data om boligsøker fra tabell for å vise denne for brukeren. Henter
    // dataene fra kolonnene som skal skjules
    public void hentBoligsoekerFraTabell() {
        BoligsoekerTabellmodell bstm = new BoligsoekerTabellmodell();
        int[] nokkelkolonner = bstm.getKolonnerSkalSkjulesIndeks();
        JTextField[] navnefelt = {fornavn, etternavn};

        // Setter data fra tabell inn i riktige inputfelter.
        int rad = boligsoekerTabell.getSelectedRow();
        Object o;
        for (int i = 0; i < nokkelkolonner.length; i++) {
            o = boligsoekerTabell.getValueAt(rad, nokkelkolonner[i]);
            navnefelt[i].setText(o.toString());
        }
        hentInfoPerson();
    }

    public void finnBoliger() {
        final int MAX = 2147483647;
        final int MIN = -2147483648;
        
        

        // felles felt
        String type = (String) boligtype.getSelectedItem();
        int minAreal = minBoligAreal.getText().equals("") ? MIN : Integer.parseInt(minBoligAreal.getText());
        int maxAreal = maxBoligAreal.getText().equals("") ? MAX : Integer.parseInt(maxBoligAreal.getText());
        int minRom = minBoligRom.getText().equals("") ? MIN : Integer.parseInt(minBoligRom.getText());
        int maxRom = maxBoligRom.getText().equals("") ? MAX : Integer.parseInt(maxBoligRom.getText());
        int minByggeaar = minBoligByggeaar.getText().equals("") ? MIN : Integer.parseInt(minBoligByggeaar.getText());
        int maxByggeaar = maxBoligByggeaar.getText().equals("") ? MAX : Integer.parseInt(maxBoligByggeaar.getText());
        int minPris = minBoligPris.getText().equals("") ? MIN : Integer.parseInt(minBoligPris.getText());
        int maxPris = maxBoligPris.getText().equals("") ? MAX : Integer.parseInt(maxBoligPris.getText());
        Date minDato = (minBoligDato.getText().equals("") || minBoligDato.getText().equals(StartVindu.DATOFORMAT)) ? null : StartVindu.konverterDato(minBoligDato.getText());
        Date maxDato = (maxBoligDato.getText().equals("") || maxBoligDato.getText().equals(StartVindu.DATOFORMAT)) ? null : StartVindu.konverterDato(maxBoligDato.getText());
        
        //RegEx
        JTextField[] jtf = {minBoligAreal,maxBoligAreal,minBoligRom,maxBoligRom,minBoligByggeaar,maxBoligByggeaar,minBoligPris,maxBoligPris,boligMinEtasjer,boligMaxEtasjer,boligMinTomtestorrelse,boligMaxTomtestorrelse,boligMinEtasje,boligMaxEtasje};
        JTextField[] dato = {minBoligDato,maxBoligDato};
        
        if(!(StartVindu.kontrollerRegEx(StartVindu.PATTERNHELTALL, jtf))){
            output.setText("Feil ved innlesing av felter. Må kun inneholde hele tall.");
            return;
        }
        /*if(minBoligDato.getText().equals("dd.mm.åååå") || maxBoligDato.getText().equals("dd.mm.åååå")){
            
        }*/
        
        if(!(StartVindu.kontrollerRegEx(StartVindu.PATTERNDATO, dato))){
            output.setText("Feil ved innlesing av dato. Sjekk format.");
            return;
        }
        

        // enebolig felt
        int minEtasjer = boligMinEtasjer.getText().equals("") ? MIN : Integer.parseInt(boligMinEtasjer.getText());
        int maxEtasjer = boligMaxEtasjer.getText().equals("") ? MAX : Integer.parseInt(boligMaxEtasjer.getText());
        int minTomtestorrelse = boligMinTomtestorrelse.getText().equals("") ? MIN : Integer.parseInt(boligMinTomtestorrelse.getText());
        int maxTomtestorrelse = boligMaxTomtestorrelse.getText().equals("") ? MAX : Integer.parseInt(boligMaxTomtestorrelse.getText());
        boolean kjeller = boligKjeller.isSelected();

        // leilighet felt
        int minEtasje = boligMinEtasje.getText().equals("") ? MIN : Integer.parseInt(boligMinEtasje.getText());
        int maxEtasje = boligMaxEtasje.getText().equals("") ? MAX : Integer.parseInt(boligMaxEtasje.getText());
        boolean heis = boligHeis.isSelected();
        boolean balkong = boligBalkong.isSelected();

        // søker i gjennom alle boliger og sjekker hvis hver bolig passer til kravene
        Iterator<Utleier> ulIter = StartVindu.getUtleierVindu().getUtleierMengde().getSortertMengde().iterator();
        Utleier ul;
        BoligListe bl;
        String utskrift = "";
        String liste = "";
        int antall = 0;

        while (ulIter.hasNext()) {
            ul = ulIter.next();
            bl = ul.getBoligliste();
            Bolig b;
            Iterator<Bolig> bIter = bl.getListe().iterator();

            while (bIter.hasNext()) {
                b = bIter.next();
                boolean passer = true;

                // sjekker alle intervalene
                if (b.getAreal() < minAreal || b.getAreal() > maxAreal) {
                    passer = false;
                }
                if (b.getSoverom() < minRom || b.getSoverom() > maxRom) {
                    passer = false;
                }
                if (b.getByggeaar() < minByggeaar || b.getSoverom() > maxByggeaar) {
                    passer = false;
                }
                if (b.getPris() < minPris || b.getPris() > maxPris) {
                    passer = false;
                }
                if (minDato != null) {
                    if (b.getDato().before(minDato)) {
                        passer = false;
                    }
                }
                if (maxDato != null) {
                    if (b.getDato().after(maxDato)) {
                        passer = false;
                    }
                }

                if (b instanceof Enebolig) {
                    Enebolig e = (Enebolig) b;

                    if (e.getAntEtasjer() < minEtasjer || e.getAntEtasjer() > maxEtasjer) {
                        passer = false;
                    }
                    if (e.getTomtAreal() < minTomtestorrelse || e.getTomtAreal() > maxTomtestorrelse) {
                        passer = false;
                    }
                    if (!e.getKjeller() && kjeller) {
                        passer = false;
                    }
                }

                if (b instanceof Leilighet) {
                    Leilighet l = (Leilighet) b;

                    if (l.getEtasje() < minEtasjer || l.getEtasje() > maxEtasjer) {
                        passer = false;
                    }
                    if (!l.getBalkong() && balkong) {
                        passer = false;
                    }
                    if (!l.getHeis() && heis) {
                        passer = false;
                    }
                }

                if (passer) {
                    liste += "\n" + b.getGateadresse() + "\t" + b.getPostnr() + "\t" + b.getPoststed();
                    antall++;
                }
            }
        }

        if (antall > 0) {
            utskrift += "Antall boliger som tilsvarer kriteriene: " + antall
                    + "\nBoliger (gateadresse, postnummer, poststed):\n";
        } else {
            utskrift = "Ingen bolig tilsvarer kriteriene";
        }

        output.setText(utskrift);
        output.append(liste);
    }

    public void finnKontrakter() {
        //...
    }

    public void visAlleKontrakter() {
        //...
    }

    public void melding(String s) {
        JOptionPane.showMessageDialog(null, s);
    }

    // Lyttemetode
    public void actionPerformed(ActionEvent e) {
        String valgtBoligType = (String) boligtype.getSelectedItem();
        String valgtKontraktType = (String) kontrakttype.getSelectedItem();

        if (e.getSource() == hentInfoPerson) {
            hentInfoPerson();
            masterPanel.remove(tabellPanel);
            masterPanel.add(under);
            masterPanel.revalidate();
            masterPanel.repaint();
        } else if (e.getSource() == visAlleBoligsoekere) {
            tabellPanel.removeAll();
            tegnBoligsoekerTabell();
            tabellPanel.add(new JScrollPane(boligsoekerTabell));
            tabellPanel.add(gaaTilBoligsoeker, BorderLayout.PAGE_END);
            tabellPanel.revalidate();
            masterPanel.remove(under);
            masterPanel.add(tabellPanel, BorderLayout.CENTER);
            masterPanel.revalidate();
            masterPanel.repaint();
        } else if (e.getSource() == visAlleUtleiere) {
            tabellPanel.removeAll();
            tegnUtleierTabell();
            tabellPanel.add(new JScrollPane(utleierTabell));
            tabellPanel.add(gaaTilUtleier, BorderLayout.PAGE_END);
            tabellPanel.revalidate();
            masterPanel.remove(under);
            masterPanel.add(tabellPanel, BorderLayout.CENTER);
            masterPanel.revalidate();
            masterPanel.repaint();
        } else if (e.getSource() == gaaTilBoligsoeker) {
            hentBoligsoekerFraTabell();
            masterPanel.remove(tabellPanel);
            masterPanel.add(under);
            masterPanel.revalidate();
            masterPanel.repaint();
        } else if(e.getSource() == gaaTilUtleier){
            hentUtleierFraTabell();
            masterPanel.remove(tabellPanel);
            masterPanel.add(under);
            masterPanel.revalidate();
            masterPanel.repaint();
        }else if (e.getSource() == visBoligInfo) {
            visBoligInfo();
            masterPanel.remove(tabellPanel);
            masterPanel.add(under);
            masterPanel.revalidate();
            masterPanel.repaint();
        } else if (e.getSource() == finnBoliger) {
            finnBoliger();
            masterPanel.remove(tabellPanel);
            masterPanel.add(under);
            masterPanel.revalidate();
            masterPanel.repaint();
        } else if (e.getSource() == visAlleBoliger) {
            tabellPanel.removeAll();
            tegnBoligtabell();
            tabellPanel.add(new JScrollPane(boligTabell));
            tabellPanel.add(gaaTilBolig, BorderLayout.PAGE_END);
            tabellPanel.revalidate();
            masterPanel.remove(under);
            masterPanel.add(tabellPanel, BorderLayout.CENTER);
            masterPanel.revalidate();
            masterPanel.repaint();
        } else if (e.getSource() == gaaTilBolig) {
            hentBoligFraTabell();
            masterPanel.remove(tabellPanel);
            masterPanel.add(under);
            masterPanel.revalidate();
            masterPanel.repaint();
        } else if (e.getSource() == finnKontrakter) {
            finnKontrakter();
            masterPanel.remove(tabellPanel);
            masterPanel.add(under);
            masterPanel.revalidate();
            masterPanel.repaint();
        } else if (e.getSource() == visAlleKontrakter) {
            visAlleKontrakter();
            masterPanel.remove(tabellPanel);
            masterPanel.add(under);
            masterPanel.revalidate();
            masterPanel.repaint();
        } else if (e.getSource() == boligtype) {
            // bolig drop-down box

            BorderLayout layout = (BorderLayout) hoyreBoligPanel.getLayout();

            if (valgtBoligType.equals(BOLIGTYPE[0])) {
                if (layout.getLayoutComponent(BorderLayout.CENTER) != null) {
                    hoyreBoligPanel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
                }
            } else if (valgtBoligType.equals(BOLIGTYPE[1])) {
                if (layout.getLayoutComponent(BorderLayout.CENTER) != null) {
                    hoyreBoligPanel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
                }
                hoyreBoligPanel.add(boligEneboligPanel, BorderLayout.CENTER);
            } else if (valgtBoligType.equals(BOLIGTYPE[2])) {
                if (layout.getLayoutComponent(BorderLayout.CENTER) != null) {
                    hoyreBoligPanel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
                }
                hoyreBoligPanel.add(boligLeilighetPanel, BorderLayout.CENTER);
            }
            // refresh vinduet
            this.getContentPane().revalidate();
            this.getContentPane().repaint();
        } else if (e.getSource() == kontrakttype) {
            // kontrakt drop-down box

            BorderLayout layout = (BorderLayout) kontraktPanel.getLayout();

            if (valgtKontraktType.equals(KONTRAKTTYPE[0])) {
                if (layout.getLayoutComponent(BorderLayout.CENTER) != null) {
                    kontraktPanel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
                }
            } else if (valgtKontraktType.equals(KONTRAKTTYPE[1])) {
                if (layout.getLayoutComponent(BorderLayout.CENTER) != null) {
                    kontraktPanel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
                }
                kontraktPanel.add(kontraktEneboligPanel, BorderLayout.CENTER);
            } else if (valgtKontraktType.equals(KONTRAKTTYPE[2])) {
                if (layout.getLayoutComponent(BorderLayout.CENTER) != null) {
                    kontraktPanel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
                }
                kontraktPanel.add(kontraktLeilighetPanel, BorderLayout.CENTER);
            }
            // refresh vinduet
            this.getContentPane().revalidate();
            this.getContentPane().repaint();
        }
    }

    public void focusGained(FocusEvent fe) {

        if (fe.getSource() == minBoligDato) {
            if (minBoligDato.getText().equals(StartVindu.DATOFORMAT)) {
                minBoligDato.setText("");
            }
        }
        // fungerer ikke hvis vi bruker "else-if" for flere felter
        if (fe.getSource() == maxBoligDato) {
            if (maxBoligDato.getText().equals(StartVindu.DATOFORMAT)) {
                maxBoligDato.setText("");
            }
        }
    }

    public void focusLost(FocusEvent fe) {

        if (fe.getSource() == minBoligDato) {
            if (minBoligDato.getText().equals("")) {
                minBoligDato.setText(StartVindu.DATOFORMAT);
            }
        }
        // fungerer ikke hvis vi bruker "else-if" for flere felter
        if (fe.getSource() == maxBoligDato) {
            if (maxBoligDato.getText().equals("")) {
                maxBoligDato.setText(StartVindu.DATOFORMAT);
            }
        }
    }
}
