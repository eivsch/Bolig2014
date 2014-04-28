/*
 * Innhold: Vindu som åpnes når programmet er kjørt
 * Sist oppdatert:
 * Programmert av: Gretar
 */
package boligformidleren;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;
import java.util.TreeSet;

public class StartVindu extends JFrame implements ActionListener{
    
    private final String vinduer[] = {"Utleier", "Boligsøker", "Bolig", "Leilighet", "Kontrakt", "Matche"};
    private JButton buttons[];
    
    // Vinduer som åpnes når man trykker på knappene på forsiden/startvinduet
    private UtleierVindu utleierVindu;
    private BoligsoekerVindu boligsoekerVindu;
    private BoligVindu boligVindu;
    
    // knapper, rader, kolonner og gap for GridLayout
    private int antKnapper, antRad, antKol, gap;

    public StartVindu() {
        super("Boligformidleren");
        
        utleierVindu = new UtleierVindu();
        boligsoekerVindu = new BoligsoekerVindu();
        boligVindu = new BoligVindu();
        
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
    
    public void skrivTilFil(){
        utleierVindu.skrivUtleierTilFil();
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttons[0]) {
            utleierVindu.setVisible(true);
        } else if (e.getSource() == buttons[1]) {
            boligsoekerVindu.setVisible(true);
        }
        else if(e.getSource() == buttons[2]){
            boligVindu.setVisible(true);
        }
    }

}
