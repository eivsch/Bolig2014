/*
 * Innhold: Vindu som brukes for å hente informasjon om personer, boliger og kontrakter
 * Sist oppdatert: 30.04.2014 kl.13:45
 * Programmert av: Gretar
 */
package boligformidleren;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;

public class InformasjonVindu extends JFrame implements ActionListener{
    
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
            kontraktKnappPanel;
    
    // person panel
    private JTextField fornavn, etternavn;
    private JButton hentInfoPerson, visAlleBoligsoekere;
    
    // bolig panel
    private JTextField gateadresse, postnr, poststed;
    private JButton visBoligInfo, visInteresserte;
    
    // bolig type panel
    private JComboBox boligtype;
    private final String[] BOLIGTYPE = {"Ingen krav", "Enebolig/rekkehus", "Leilighet"};
    
    // bolig min-max panel
    private JTextField minBoligAreal, maxBoligAreal, minBoligRom, maxBoligRom, 
            minBoligByggeaar, maxBoligByggeaar, minBoligPris, maxBoligPris,
            minBoligDato, maxBoligDato;

    // bolig enebolig panel
    private JTextField boligEtasjer, boligTomtestorrelse;
    private JCheckBox boligKjeller;
    
    // bolig leilighet panel
    private JTextField boligEtasje;
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
    
            
    private JTextArea output;
    
    // konstruktør
    public InformasjonVindu(){
        super("Informasjon");
        
        // labels
        JLabel labelPerson = new JLabel("<html><center><font size=\"5\">Personer</html>");
        JLabel labelBolig1 = new JLabel("<html><font size=\"5\">Boliger</html>");
        JLabel labelBolig2 = new JLabel("<html><font size=\"5\">Boliger</html>");
        JLabel labelKontrakt = new JLabel("<html><font size=\"5\">Kontrakter</html>");
        Border paddingBorder = BorderFactory.createEmptyBorder(0,0,10,0);
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
                    boligEneboligPanel = new JPanel(new GridLayout(antRadEneboligLeilighetPanel, defaultKolonner, gap, gap));
                    boligLeilighetPanel = new JPanel(new GridLayout(antRadEneboligLeilighetPanel, defaultKolonner, gap, gap));
                    boligKnappPanel = new JPanel(new GridLayout(antRadKnappPanel, antKnappKolonner, gap, gap));
                kontraktPanel = new JPanel(new BorderLayout());
                    kontraktFellesPanel = new JPanel(new BorderLayout());
                        kontraktFellesTypePanel = new JPanel(new GridLayout(antRadTypePanel, defaultKolonner, gap, gap));
                        kontraktFellesMinMaxPanel = new JPanel(new GridLayout(antRadMinMaxPanel, antMinMaxKolonner, gap, gap));
                    kontraktEneboligPanel = new JPanel(new GridLayout(antRadEneboligLeilighetPanel, defaultKolonner, gap, gap));
                    kontraktLeilighetPanel = new JPanel(new GridLayout(antRadEneboligLeilighetPanel, defaultKolonner, gap, gap));
                    kontraktKnappPanel = new JPanel(new GridLayout(antRadKnappPanel, antKnappKolonner, gap, gap));
        under = new JPanel(new BorderLayout());
        
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
        
        visInteresserte = new JButton("Vis interesserte personer");
        visInteresserte.addActionListener(this);
        venstreBoligPanel.add(visInteresserte);
        
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
        boligFellesMinMaxPanel.add(minBoligDato);
        boligFellesMinMaxPanel.add(maxBoligDato);
        
        // bolig enebolig panel
        boligEneboligPanel.add(new JLabel("Antall etasjer: "));
        boligEtasjer = new JTextField(10);
        boligEneboligPanel.add(boligEtasjer);

        boligEneboligPanel.add(new JLabel("Tomtestørrelse: "));
        boligTomtestorrelse = new JTextField(10);
        boligEneboligPanel.add(boligTomtestorrelse);

        boligEneboligPanel.add(new JLabel("Kjeller: "));
        boligKjeller = new JCheckBox("");
        boligEneboligPanel.add(boligKjeller);
        
        // bolig leilighet panel
        boligLeilighetPanel.add(new JLabel("Etasje: "));
        boligEtasje = new JTextField(10);
        boligLeilighetPanel.add(boligEtasje);

        boligLeilighetPanel.add(new JLabel("Heis: "));
        boligHeis = new JCheckBox("");
        boligLeilighetPanel.add(boligHeis);

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
    public void hentInfoPerson(){
        Utleier ul = StartVindu.getUtleierVindu().getUtleierMengde().finnUtleier(fornavn.getText(), etternavn.getText());
        Boligsoeker bs = StartVindu.getBoligsoekerVindu().getBoligsoekerMengde().finnBoligsoeker(fornavn.getText(), etternavn.getText());
        
        // blank felter
        fornavn.setText("");
        etternavn.setText("");
        
        if( ul != null){
            output.setText(ul.toString());
            return;
        }
        if( bs != null){
            output.setText(bs.toString());
            return;
        }
        output.setText("Feil - finner ikke person " + fornavn.getText() + " " + etternavn.getText());
    }
    
    // utskrift av alle registrerte boligsøkere, med opplysninger om hvilken bolig de eventuelt er interessert i
    public void visAlleBoligsoekere(){
        Iterator<Boligsoeker> bsIter = StartVindu.getBoligsoekerVindu().getBoligsoekerMengde().getMengde().iterator();
        
        Boligsoeker bs;
        String utskrift = "";
        
        while(bsIter.hasNext()){
            bs = bsIter.next();
            utskrift += bs.getFornavn() + " " + bs.getEtternavn() + "\n";
            
            Iterator<Utleier> ulIter = StartVindu.getUtleierVindu().getUtleierMengde().getSortertMengde().iterator();
            Utleier ul;
            BoligListe bl;
            
            while(ulIter.hasNext()){
                ul = ulIter.next();
                bl = ul.getBoligliste();
                Bolig b;
                Iterator<Bolig> bIter = bl.getListe().iterator();
                
                while(bIter.hasNext()){
                    b = bIter.next();
                    
                    if(bs.passerTilBolig(b)){
                        utskrift += b.getGateadresse() + "\t" + b.getPostnr() + "\t" + b.getPoststed() + "\n";
                    }
                }
            }
        }
        output.setText(utskrift);
    }
    
    public void visBoligInfo(){
        //...
    }
    
    public void visInteresserte(){
        //...
    }
    
    public void finnBoliger(){
        //...
    }
    
    public void visAlleBoliger(){
        //...
    }
    
    public void finnKontrakter(){
        //...
    }
    
    public void visAlleKontrakter(){
        //...
    }
    
    
    
    // Lyttemetode
    public void actionPerformed(ActionEvent e) {
        String valgtBoligType = (String) boligtype.getSelectedItem();
        String valgtKontraktType = (String) kontrakttype.getSelectedItem();
        
        if(e.getSource() == hentInfoPerson){
            hentInfoPerson();
        } else if(e.getSource() == visAlleBoligsoekere){
            visAlleBoligsoekere();
        } else if(e.getSource() == visBoligInfo){
            visBoligInfo();
        } else if(e.getSource() == visInteresserte){
            visInteresserte();
        } else if(e.getSource() == finnBoliger){
            finnBoliger();
        } else if(e.getSource() == visAlleBoliger){
            visAlleBoliger();
        } else if(e.getSource() == finnKontrakter){
            finnKontrakter();
        } else if(e.getSource() == visAlleKontrakter){
            visAlleKontrakter();
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
}
