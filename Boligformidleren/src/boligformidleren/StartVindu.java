/*
 * Innhold: Startvindu som åpnes når programmet er kjørt
 * Sist oppdatert:
 * Programmert av: Gretar, Sigurd
 */
package boligformidleren;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.border.EmptyBorder;

public class StartVindu extends JFrame implements ActionListener {

    //private final String vinduer[] = {"<html><center>Utleier<br><br>(Registrering,<br>Sletting,<br>Endring)</html>", "<html><center>Boligsøker<br><br>(Registrering,<br>Sletting,<br>Endring)</html>", "<html><center>Bolig<br><br>(Registrering,<br>Sletting,<br>Endring)</html>", "<html><center>Kontrakt<br><br>(Registrering,<br>Sletting,<br>Endring)</html>", "<html><center>Hente informasjon</html>", "<html><center>Generate data<br><br>Trykk kun en gang!</html>"};
    //private JButton buttons[];

    /*
     Vinduer som åpnes når man trykker på knappene på forsiden/startvinduet
     Vinduene inneholder alle mengder/lister som brukes i programmet og
     må derfor være "static" så andre klasser har aksess til disse
     */
    private JPanel masterPanel, topLabels, top, under;
    private JButton utleierKnapp, boligsoekerKnapp, boligKnapp, kontraktKnapp, infoKnapp, dataKnapp;
    
    private static UtleierVindu utleierVindu;
    private static BoligsoekerVindu boligsoekerVindu;
    private static BoligVindu boligVindu;
    private static KontraktVindu kontraktVindu;
    private static InformasjonVindu informasjonVindu;

    // knapper, rader, kolonner og gap for GridLayout
    private int antKnapper, antRad, antKol, gap;

    // Konstanter
    public static final String[] ANTSOVEROM = {"1", "2", "3", "4", "5", "6", "7", "8"};
    public static final String[] ETASJERENEBOLIG = {"1", "2", "3", "4", "5"};
    public static final String DATOFORMAT = "dd.mm.åååå";

    // RegEx
    public static final String PATTERNDATO = "[0-9]{1,2}.[0-9]{1,2}.[0-9]{4}";
    public static final String PATTERNHELTALL = "[0-9]*";
    public static final String PATTERNPOSTNUMMER = "[0-9]{1,4}";    // postnummer "0001" er lagret som "1"
    public static final String PATTERNTELEFONNUMMER = "[0-9]{8}";    // postnummer "0001" er lagret som "1"
    public static final SimpleDateFormat ENKELDATOFORMAT = new SimpleDateFormat("dd.MM.yyyy");
    //public static final String PATTERNTALLBOKSTAV = "[0-9a-zæøåA-ZÆØÅ ,.\\-]{4,40}";
    public static final String PATTERNTALLBOKSTAV = "[a-zæøåA-ZÆØÅ ]{4,40}[ ][0-9]{1,3}";
    public static final String PATTERNTALLELLERBOKSTAV = "[a-zæøåA-ZÆØÅ0-9 ]{1,40}";
    public static final String PATTERNBOKSTAV = "[a-zæøåA-ZÆØÅ\\- ]{2,30}";
    public static final String PATTERNEPOST = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}";
    
    // konstruktør
    public StartVindu() {
        super("Boligformidleren");

        utleierVindu = new UtleierVindu();
        boligsoekerVindu = new BoligsoekerVindu();
        boligVindu = new BoligVindu();
        kontraktVindu = new KontraktVindu();
        informasjonVindu = new InformasjonVindu();

        /*
        antKnapper = vinduer.length;
        antRad = (int) (Math.sqrt(antKnapper));    // hvis 2-8 knapper så 2 rader, hvis 9-15 knapper så 3 rader, osv.
        antKol = antKnapper - antRad;
        gap = 5;

        Container c = getContentPane();
        c.setLayout(new GridLayout(antRad, antKol, gap, gap));
        buttons = new JButton[antKnapper];

        for (int i = 0; i < antKnapper; i++) {
            buttons[i] = new JButton(vinduer[i]);
            buttons[i].addActionListener(this);
            c.add(buttons[i]);
        }

        setSize(600, 600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        */
        
        masterPanel = new JPanel(new GridLayout(2,1,10,20));
        top = new JPanel(new GridLayout(1,5,20,20));
        under = new JPanel(new BorderLayout());
        
        masterPanel.add(top);
        masterPanel.add(under);
        masterPanel.setBorder(new EmptyBorder(20, 20, 20, 20) );
        
        // knapper
        Icon ikonUtleier = new ImageIcon(getClass().getResource( "ikonUtleier.gif" ));
        Icon ikonUtleierValgt = new ImageIcon(getClass().getResource( "ikonUtleierValgt.gif" ));
        Icon ikonBoligsoeker = new ImageIcon(getClass().getResource( "ikonBoligsoeker.gif" ));
        Icon ikonBoligsoekerValgt = new ImageIcon(getClass().getResource( "ikonBoligsoekerValgt.gif" ));
        Icon ikonBolig = new ImageIcon(getClass().getResource( "ikonBolig.gif" ));
        Icon ikonBoligValgt = new ImageIcon(getClass().getResource( "ikonBoligValgt.gif" ));
        Icon ikonKontrakt = new ImageIcon(getClass().getResource( "ikonKontrakt.gif" ));
        Icon ikonKontraktValgt = new ImageIcon(getClass().getResource( "ikonKontraktValgt.gif" ));
        Icon ikonInformasjon = new ImageIcon(getClass().getResource( "ikonInformasjon.gif" ));
        Icon ikonInformasjonValgt = new ImageIcon(getClass().getResource( "ikonInformasjonValgt.gif" ));
        utleierKnapp = new JButton("", ikonUtleier);
        utleierKnapp.setRolloverIcon( ikonUtleierValgt );
        utleierKnapp.addActionListener(this);
        boligsoekerKnapp = new JButton("", ikonBoligsoeker);
        boligsoekerKnapp.setRolloverIcon( ikonBoligsoekerValgt );
        boligsoekerKnapp.addActionListener(this);
        boligKnapp = new JButton("", ikonBolig);
        boligKnapp.setRolloverIcon( ikonBoligValgt );
        boligKnapp.addActionListener(this);
        kontraktKnapp = new JButton("", ikonKontrakt);
        kontraktKnapp.setRolloverIcon( ikonKontraktValgt );
        kontraktKnapp.addActionListener(this);
        infoKnapp = new JButton("", ikonInformasjon);
        infoKnapp.setRolloverIcon( ikonInformasjonValgt );
        infoKnapp.addActionListener(this);
        dataKnapp = new JButton("Generer data");
        dataKnapp.addActionListener(this);
        
        top.add(utleierKnapp);
        top.add(boligsoekerKnapp);
        top.add(boligKnapp);
        top.add(kontraktKnapp);
        
        
        // vi må fjerne denne linje før programmet leveres!!!!!
        // vi må fjerne denne linje før programmet leveres!!!!!
        // vi må fjerne denne linje før programmet leveres!!!!!
        top.add(dataKnapp);
        // vi må fjerne denne linje før programmet leveres!!!!!
        // vi må fjerne denne linje før programmet leveres!!!!!
        // vi må fjerne denne linje før programmet leveres!!!!!
        
        
        under.add(infoKnapp, BorderLayout.CENTER);
        
        
        this.getContentPane().add(masterPanel);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 300);
        
        // lukkeknapp for UtleierVindu
        utleierVindu.addWindowListener(
                new WindowAdapter(){
                    public void windowClosing(WindowEvent e){
                        utleierVindu.blankFelter();
                    }
                }
        );
        
        // lukkeknapp for BoligsoekerVindu
        boligsoekerVindu.addWindowListener(
                new WindowAdapter(){
                    public void windowClosing(WindowEvent e){
                        boligsoekerVindu.blankFelter();
                    }
                }
        );
        
        // lukkeknapp for BoligVindu
        boligVindu.addWindowListener(
                new WindowAdapter(){
                    public void windowClosing(WindowEvent e){
                        boligVindu.blankFelter();
                    }
                }
        );
        
        // lukkeknapp for KontraktVindu
        kontraktVindu.addWindowListener(
                new WindowAdapter(){
                    public void windowClosing(WindowEvent e){
                        kontraktVindu.blankFelter();
                    }
                }
        );
        
        // lukkeknapp for InformasjonVindu
        informasjonVindu.addWindowListener(
                new WindowAdapter(){
                    public void windowClosing(WindowEvent e){
                        //informasjonVindu.blankFelter();
                    }
                }
        );
    }

    // get metoder
    public static UtleierVindu getUtleierVindu() {
        return utleierVindu;
    }

    public static BoligsoekerVindu getBoligsoekerVindu() {
        return boligsoekerVindu;
    }

    public static BoligVindu getBoligVindu() {
        return boligVindu;
    }

    public static KontraktVindu getKontraktVindu() {
        return kontraktVindu;
    }

    public static InformasjonVindu getInformasjonVindu() {
        return informasjonVindu;
    }

    // sjekker helt array av tekstfelter om de passer til regex-pattern
    public static boolean kontrollerRegEx(String pattern, JTextField[] input) {
        for (int i = 0; i < input.length; i++) {
            if (!input[i].getText().matches(pattern)) {
                return false;
            }
        }
        return true;
    }
    
    // sjekker helt array av tekstfelter om de passer til regex-pattern
    public static String kontrollerRegExTomFeltOK(String pattern, JTextField[] input) {
        for (int i = 0; i < input.length; i++) {
            if(!input[i].getText().equals(""))
                if (!input[i].getText().matches(pattern))
                    return input[i].getText();
        }
        return "";
    }
    
    // sjekker om en tekststreng (input) passer til en regex-pattern
    public static boolean kontrollerRegEx(String pattern, String input) {
        if (!input.matches(pattern))
            return false;
        return true;
    }
    
    // konverterer en tom felt til et heltall (0)
    public static int konverterBlanktFeltTilHeltall(JTextField jtf) {
        if (jtf.getText().equals("")) {
            return 0;
        } else {
            return Integer.parseInt(jtf.getText());
        }
    }

    // sjekker om en felt er tom
    public static boolean tekstFeltErTomt(JTextField[] jtf) {
        for (int i = 0; i < jtf.length; i++) {
            if (jtf[i].getText().equals("")) {
                return true;
            }
        }
        return false;
    }

    // konverterer en tekststreng av format dd.mm.åååå til et DATE objekt
    public static Date konverterDato(String datostreng) {
        if (!datostreng.matches(PATTERNDATO)) {
            return null;
        }
        try {
            Date d = ENKELDATOFORMAT.parse(datostreng);
            return d;
        } catch (ParseException pe) {
            return null;
        }
    }
    
    // generer data fra .txt filer
    public void generateData(){
        
        UtleierMengde um = utleierVindu.getUtleierMengde();
        BoligsoekerMengde bm = boligsoekerVindu.getBoligsoekerMengde();
        
        // generate utleiere
        try(BufferedReader inntekst = new BufferedReader(new FileReader("utleiere.txt"))){
            String innlinje = null;
            int teller = 0;
            int antParameterPerPerson = 8;
            String[] parameter = new String[antParameterPerPerson];
            
            do{
                innlinje = inntekst.readLine();
                
                if(innlinje != null){
                    parameter[teller++] = innlinje;
                }
                if(teller == antParameterPerPerson){
                    Utleier ul = new Utleier(parameter[0], parameter[1], parameter[2], Integer.parseInt(parameter[3]),
                            parameter[4], parameter[5], Integer.parseInt(parameter[6]), parameter[7]);
                    um.settInn(ul);
                    teller = 0;
                }
                    
            }while(innlinje != null);
                    
        }
        catch(FileNotFoundException fnfe){
            System.out.println(fnfe.getMessage());
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        
        // generate boligsøkere
        try(BufferedReader inntekst = new BufferedReader(new FileReader("boligsoekere.txt"))){
            String innlinje = null;
            int teller = 0;
            int antParameterPerPerson = 20;
            String[] parameter = new String[antParameterPerPerson];
            
            do{
                innlinje = inntekst.readLine();
                
                if(innlinje != null){
                    parameter[teller++] = innlinje;
                }
                if(teller == antParameterPerPerson){
                    Boligsoeker bs = new Boligsoeker(parameter[0], parameter[1], parameter[2], Integer.parseInt(parameter[3]),
                            parameter[4], parameter[5], Integer.parseInt(parameter[6]), parameter[7], parameter[8],
                            Integer.parseInt(parameter[9]), Integer.parseInt(parameter[10]), Integer.parseInt(parameter[11]),
                            Integer.parseInt(parameter[12]), konverterDato(parameter[13]), Integer.parseInt(parameter[14]),
                            Integer.parseInt(parameter[15]), parameter[16].equals("true") ? true: false, Integer.parseInt(parameter[17]),
                            parameter[18].equals("true") ? true: false, parameter[19].equals("true") ? true: false);
                    bm.settInn(bs);
                    teller = 0;
                }
                    
            }while(innlinje != null);
                    
        }
        catch(FileNotFoundException fnfe){
            System.out.println(fnfe.getMessage());
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        
        
        // generate boliger
        try(BufferedReader inntekst = new BufferedReader(new FileReader("boliger.txt"))){
            String innlinje = null;
            int teller = 0;
            int antParameterPerPerson = 18;
            String[] parameter = new String[antParameterPerPerson];
            
            do{
                innlinje = inntekst.readLine();
                
                if(innlinje != null){
                    parameter[teller++] = innlinje;
                }
                if(teller == antParameterPerPerson){
                    if(parameter[3].equals("Leilighet")){
                        Bolig b = new Leilighet(parameter[0], Integer.parseInt(parameter[1]), parameter[2], parameter[3], parameter[4],
                                konverterDato(parameter[5]), Integer.parseInt(parameter[6]), Integer.parseInt(parameter[7]),
                                Integer.parseInt(parameter[8]), Integer.parseInt(parameter[9]),
                                Integer.parseInt(parameter[13]), parameter[14].equals("true") ? true: false, parameter[15].equals("true") ? true: false);
                        Utleier ul = um.finnUtleier(parameter[16].trim(), parameter[17].trim());
                        ul.regBolig(b);
                        teller = 0;
                    }
                    else{
                        Bolig b = new Enebolig(parameter[0], Integer.parseInt(parameter[1]), parameter[2], parameter[3], parameter[4],
                                konverterDato(parameter[5]), Integer.parseInt(parameter[6]), Integer.parseInt(parameter[7]),
                                Integer.parseInt(parameter[8]), Integer.parseInt(parameter[9]),
                                Integer.parseInt(parameter[10]), Integer.parseInt(parameter[11]), parameter[12].equals("true") ? true: false);
                        Utleier ul = um.finnUtleier(parameter[16].trim(), parameter[17].trim());
                        ul.regBolig(b);
                        teller = 0;
                    }
                }
            }while(innlinje != null);
                    
        }
        catch(FileNotFoundException fnfe){
            System.out.println(fnfe.getMessage());
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        
        // generate kontrakter
        try(BufferedReader inntekst = new BufferedReader(new FileReader("kontrakter.txt"))){
            String innlinje = null;
            int teller = 0;
            int antParameterPerPerson = 10;
            String[] parameter = new String[antParameterPerPerson];
            
            do{
                innlinje = inntekst.readLine();
                
                if(innlinje != null){
                    parameter[teller++] = innlinje;
                }
                if(teller == antParameterPerPerson){
                    Bolig b = utleierVindu.getUtleierMengde().finnBolig(parameter[0], Integer.parseInt(parameter[1]), parameter[2]);
                    Utleier ul = utleierVindu.getUtleierMengde().finnUtleier(parameter[3], parameter[4]);
                    Boligsoeker bs = boligsoekerVindu.getBoligsoekerMengde().finnBoligsoeker(parameter[5], parameter[6]);
                    Kontrakt k = new Kontrakt(b, ul, bs, Integer.parseInt(parameter[7]), konverterDato(parameter[8]), konverterDato(parameter[9]));
                    
                    kontraktVindu.getKontraktListe().settInn(k);
                    b.boligErOpptatt();
                    bs.leterIkkeEtterBolig();
                    
                    teller = 0;
                }
            }while(innlinje != null);
                    
        }
        catch(FileNotFoundException fnfe){
            System.out.println(fnfe.getMessage());
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
                    
    // skriver utleiermengden, boligsøkermengden og kontraktlisten til fil
    public void skrivTilFil() {
        utleierVindu.skrivUtleierTilFil();
        boligsoekerVindu.skrivBoligsoekerTilFil();
        kontraktVindu.skrivKontraktTilFil();
    }

    // viser pop-up feilmelding vindu
    public static void visFeilmelding(StackTraceElement[] ste) {
        JOptionPane.showMessageDialog(null, ste);
    }

    // viser pop-up feilmelding vindu
    public static void visFeilmelding(Object o) {
        JOptionPane.showMessageDialog(null, o);
    }
    
    // viser pop-up ja/nei vindu
    public static String visJaNeiMelding(String melding, String vindutekst){
        String[] alternativer = { "Ja", "Nei" };
        
        int svar = JOptionPane.showOptionDialog( null,
                        melding, vindutekst,
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, alternativer, alternativer[ 0 ] );
        
        if ( svar == JOptionPane.YES_OPTION)
            return alternativer[0];
        else
            return alternativer[1];
    }

    // lyttemetode
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == utleierKnapp) {
            utleierVindu.setLocation(0, 0);
            utleierVindu.setVisible(true);
        } else if (e.getSource() == boligsoekerKnapp) {
            boligsoekerVindu.setLocation(400, 0);
            boligsoekerVindu.setVisible(true);
        } else if (e.getSource() == boligKnapp) {
            boligVindu.setLocation(800, 0);
            boligVindu.setVisible(true);
        } else if (e.getSource() == kontraktKnapp) {
            kontraktVindu.setLocation(1200, 0);
            kontraktVindu.setVisible(true);
        } else if (e.getSource() == infoKnapp) {
            informasjonVindu.setVisible(true);
        } else if( e.getSource() == dataKnapp){
            generateData();
        }
    }

}
