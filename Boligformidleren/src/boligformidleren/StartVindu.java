/*
 * Innhold: Vindu som åpnes når programmet er kjørt
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

public class StartVindu extends JFrame implements ActionListener {

    private final String vinduer[] = {"<html><center>Utleier<br><br>(Registrering,<br>Sletting,<br>Endring)</html>", "<html><center>Boligsøker<br><br>(Registrering,<br>Sletting,<br>Endring)</html>", "<html><center>Bolig<br><br>(Registrering,<br>Sletting,<br>Endring)</html>", "<html><center>Kontrakt<br><br>(Registrering,<br>Sletting,<br>Endring)</html>", "<html><center>Hente informasjon</html>", "<html><center>Generate data<br><br>Trykk kun en gang!</html>"};
    private JButton buttons[];

    /*
     Vinduer som åpnes når man trykker på knappene på forsiden/startvinduet
     Vinduene inneholder alle mengder/lister som brukes i programmet og
     må derfor være "static" så andre klasser har aksess til disse
     */
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
    public static final SimpleDateFormat ENKELDATOFORMAT = new SimpleDateFormat("dd.MM.yyyy");
    //public static final String PATTERNTALLBOKSTAV = "[0-9a-zæøåA-ZÆØÅ ,.\\-]{4,40}";
    public static final String PATTERNTALLBOKSTAV = "[a-zæøåA-ZÆØÅ ]{4,40}[ ][0-9]{1,3}";
    public static final String PATTERNBOKSTAV = "[a-zæøåA-ZÆØÅ]{2,30}";

    public StartVindu() {
        super("Boligformidleren");

        utleierVindu = new UtleierVindu();
        boligsoekerVindu = new BoligsoekerVindu();
        boligVindu = new BoligVindu();
        kontraktVindu = new KontraktVindu();
        informasjonVindu = new InformasjonVindu();

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
    }

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

    public static boolean kontrollerRegEx(String pattern, JTextField[] input) {
        for (int i = 0; i < input.length; i++) {
            if (!input[i].getText().matches(pattern)) {
                return false;
            }
        }
        return true;
    }

    public static int konverterBlanktFeltTilHeltall(JTextField jtf) {
        if (jtf.getText().equals("")) {
            return 0;
        } else {
            return Integer.parseInt(jtf.getText());
        }
    }

    public static boolean tekstFeltErTomt(JTextField[] jtf) {
        for (int i = 0; i < jtf.length; i++) {
            if (jtf[i].getText().equals("")) {
                return true;
            }
        }
        return false;
    }

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
                            Integer.parseInt(parameter[15]), parameter[16] == "true" ? true: false, Integer.parseInt(parameter[17]),
                            parameter[18] == "true" ? true: false, parameter[19] == "true" ? true: false);
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
                                Integer.parseInt(parameter[13]), parameter[14] == "true" ? true: false, parameter[15] == "true" ? true: false);
                        Utleier ul = um.finnUtleier(parameter[16].trim(), parameter[17].trim());
                        ul.regBolig(b);
                        teller = 0;
                    }
                    else{
                        Bolig b = new Enebolig(parameter[0], Integer.parseInt(parameter[1]), parameter[2], parameter[3], parameter[4],
                                konverterDato(parameter[5]), Integer.parseInt(parameter[6]), Integer.parseInt(parameter[7]),
                                Integer.parseInt(parameter[8]), Integer.parseInt(parameter[9]),
                                Integer.parseInt(parameter[10]), Integer.parseInt(parameter[11]), parameter[12] == "true" ? true: false);
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
        
    }

    public void skrivTilFil() {
        utleierVindu.skrivUtleierTilFil();
        boligsoekerVindu.skrivBoligsoekerTilFil();
        kontraktVindu.skrivKontraktTilFil();
        // evt. andre vinduer som skal skrives til fil
    }

    public static void visFeilmelding(StackTraceElement[] ste) {
        JOptionPane.showMessageDialog(null, ste);
    }

    public static void visFeilmelding(Object o) {
        JOptionPane.showMessageDialog(null, o);
    }
    
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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttons[0]) {
            utleierVindu.setVisible(true);
        } else if (e.getSource() == buttons[1]) {
            boligsoekerVindu.setVisible(true);
        } else if (e.getSource() == buttons[2]) {
            boligVindu.setVisible(true);
        } else if (e.getSource() == buttons[3]) {
            kontraktVindu.setVisible(true);
        } else if (e.getSource() == buttons[4]) {
            informasjonVindu.setVisible(true);
        } else if( e.getSource() == buttons[5]){
            generateData();
        }
    }

}
