/*
 * Innhold: Vindu som åpnes når programmet er kjørt
 * Sist oppdatert:
 * Programmert av: Gretar
 */
package boligformidleren;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

public class StartVindu extends JFrame implements ActionListener{
    
    private final String vinduer[] = {"<html><center>Utleier<br><br>(Registrering,<br>Sletting,<br>Endring)</html>","<html><center>Boligsøker<br><br>(Registrering,<br>Sletting,<br>Endring)</html>","<html><center>Bolig<br><br>(Registrering,<br>Sletting,<br>Endring)</html>","<html><center>Kontrakt<br><br>(Registrering,<br>Sletting,<br>Endring)</html>","<html><center>Hente informasjon</html>","<html><center>X X X</html>"};
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
    
    // RegEx
    public static final String patternDato = "[0-9]{1,2}.[0-9]{1,2}.[0-9]{4}";
    public static final SimpleDateFormat datoFormat = new SimpleDateFormat("dd.MM.yyyy");

    public StartVindu() {
        super("Boligformidleren");
        
        utleierVindu = new UtleierVindu();
        boligsoekerVindu = new BoligsoekerVindu();
        boligVindu = new BoligVindu();
        kontraktVindu = new KontraktVindu();
        informasjonVindu = new InformasjonVindu();
        
        antKnapper = vinduer.length;
        antRad = (int)( Math.sqrt(antKnapper) );    // hvis 2-8 knapper så 2 rader, hvis 9-15 knapper så 3 rader, osv.
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
    
    public static UtleierVindu getUtleierVindu(){
        return utleierVindu;
    }
    
    public static BoligsoekerVindu getBoligsoekerVindu(){
        return boligsoekerVindu;
    }
    
    public static BoligVindu getBoligVindu(){
        return boligVindu;
    }
    
    public static KontraktVindu getKontraktVindu(){
        return kontraktVindu;
    }
    
    public static InformasjonVindu getInformasjonVindu(){
        return informasjonVindu;
    }
    
    public void skrivTilFil(){
        utleierVindu.skrivUtleierTilFil();
        boligsoekerVindu.skrivBoligsoekerTilFil();
        // evt. andre vinduer som skal skrives til fil
    }
    
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == buttons[0]) {
            utleierVindu.setVisible(true);
        } else if (e.getSource() == buttons[1]){
            boligsoekerVindu.setVisible(true);
        } else if(e.getSource() == buttons[2]){
            boligVindu.setVisible(true);
        } else if(e.getSource() == buttons[3]){
            kontraktVindu.setVisible(true);
        } else if(e.getSource() == buttons[4]){
            informasjonVindu.setVisible(true);
        }
    }

}
